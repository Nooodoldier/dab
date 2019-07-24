<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/11/25 0020
  Time: 下午 18:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
    <title>定时任务</title>
    <link rel="stylesheet" href="${basePath}/static/bootstrap-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${basePath}/static/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${basePath}/static/css/bootstrap-table.min.css">
    <script src="${basePath}/static/js/jquery-2.0.3.min.js"></script>
    <script src="${basePath}/static/bootstrap-dist/js/bootstrap.min.js"></script>
    <script src="${basePath}/static/js/bootstrap-table.min.js"></script>
    <script src="${basePath}/static/js/bootstrap-table-zh-CN.js"></script>
</head>
<body>

<div id="rrapp" style="margin: 100px;">
    <div id="showList">
        <div class="grid-btn" style="height:34px;">
            <a class="btn btn-primary" onclick="update();"><i class="fa fa-pencil-square-o"></i>&nbsp;修改</a>
            <a class="btn btn-primary" onclick="pause();"><i class="fa fa-pause"></i>&nbsp;暂停</a>
            <a class="btn btn-primary" onclick="resume();"><i class="fa fa-play"></i>&nbsp;恢复</a>
            <a class="btn btn-primary" onclick="add();"><i class="fa fa-pencil-square-o"></i>&nbsp;添加</a>
            <a class="btn btn-primary" onclick="runOnce();"><i class="fa fa-arrow-circle-right"></i>&nbsp;立即执行</a>
        </div>
        <table id="table"></table>
    </div>

    <!-- 修改时间模态框 -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">修改定时任务时间</h4>
                </div>
                <div class="modal-body">
                    <input type="text" id="modalId" style="display:none"/>
                    Cron表达式
                    <input type="text" class="form-control" id="modalCron">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="updateCron();">Save changes</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 修改时间模态框 -->
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="addModalLabel">添加定时任务</h4>
                </div>
                <div class="modal-body">
                    <a>任务名</a> <input type="text" class="form-control" id="jobName"><br>
                    <a>任务组</a> <input type="text" class="form-control" id="jobGroup"><br>
                    <a>方法名</a> <input type="text" class="form-control" id="methodName"><br>
                    <a>方法路径</a> <input type="text" class="form-control" id="beanClass"><br>
                    <a>时间</a> <input type="text" class="form-control" id="cronExpression">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="addCron();">Save</button>
                </div>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    $(function () {
        //初始化表格
        initTable();
    });

    function addCron(){
        $('#addModal').modal('hide');
        var queryUrl = "${basePath}/scheduleJob/addScheduleJob";
        $.ajax({
            type: 'POST',
            data: {
                cronExpression: $("#cronExpression").val(),
                jobName:$("#jobName").val(),
                jobGroup:$("#jobGroup").val(),
                methodName:$("#methodName").val(),
                beanClass:$("#beanClass").val(),
                status:0

            },
            url: queryUrl,
            success: function (result) {
                //刷新表格
                var opt = {
                    url: '${basePath}/scheduleJob/listAllJob'
                };
                $("#table").bootstrapTable('refresh', opt);
            }
        })
    }

    function  add(){
        $('#addModal').modal('toggle');
    }
    //修改按钮
    function update() {
        //获取选中的行
        var a = $("#table").bootstrapTable('getSelections');
        if (a.length == 0) {
            alert("请先选中需要修改的项");
            return false;
        } else if (a.length > 1) {
            alert("只能选择一项");
            return false;
        }
        $("#modalId").val(a[0].id);
        $('#myModal').modal('toggle');
    }

    //更新定时任务时间
    function updateCron() {
        $('#myModal').modal('hide');
        var id = $("#modalId").val();
        var queryUrl = "${basePath}/scheduleJob/updateCron";
        $.ajax({
            type: 'POST',
            data: {
                id: id,
                cronExpression: $("#modalCron").val(),
            },
            url: queryUrl,
            success: function (result) {
                //刷新表格
                var opt = {
                    url: '${basePath}/scheduleJob/listAllJob'
                };
                $("#table").bootstrapTable('refresh', opt);
            }
        })
    }

    //暂停一个定时任务
    function pause() {
        var queryUrl = '${basePath}/scheduleJob/pauseJob';
        commonSubmit(queryUrl);
    }

    //恢复一个定时任务
    function resume() {
        var queryUrl = '${basePath}/scheduleJob/resumeJob';
        commonSubmit(queryUrl);
    }

    //立即执行一个定时任务
    function runOnce() {
        var queryUrl = "${basePath}/scheduleJob/runOnce";
        commonSubmit(queryUrl);
    }

    //暂停、恢复、立即执行提交函数
    function commonSubmit(queryUrl) {
        //获取选中的行
        var a = $("#table").bootstrapTable('getSelections');
        if (a.length == 0) {
            alert("请先选中需要修改的项");
            return false;
        } else if (a.length > 1) {
            alert("只能选择一项");
            return false;
        }
        var obj = a[0];
        $.ajax({
            type: 'post',
            data: {
                jobId: obj.id
            },
            url: queryUrl,
            success: function (result) {
                //刷新表格，状态变更
                var opt = {
                    url: '${basePath}/scheduleJob/listAllJob'
                };
                $("#table").bootstrapTable('refresh', opt);
            }
        });
    }

    //表格详情
    function initTable() {
        var queryUrl = '${basePath}/scheduleJob/listAllJob';
        $('#table').bootstrapTable({
            method: 'POST',//请求方式（*）
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",//在服务端分页时必须配置
            dataType: 'json',
            //toolbar: '#toolbar',//工具按钮用哪个容器
            striped: true,//是否显示行间隔色
            cache: false,//是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,//是否显示分页（*）、
            onlyInfoPagination: false,//设置为true时只显示总数据，而不显示分页按钮
            showPaginationSwitch: false,
            sortable: true,//是否启用排序
            sortOrder: "asc",//排序方式
            sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,//初始化加载第一页，默认第一页,并记录
            pageSize: 10,//每页的记录行数（*）
            pageList: [10, 25, 50, 100],//可供选择的每页的行数（*）
            url: queryUrl,//请求后台的URL（*）
            search: false,//是否显示表格搜索
            strictSearch: true,
            showColumns: false,//是否显示所有的列（选择显示的列）
            showRefresh: false,//是否显示刷新按钮
            minimumCountColumns: 1,//最少允许的列数
            clickToSelect: true,//是否启用点击选中行
            //height: 500,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",//每一行的唯一标识，一般为主键列
            showToggle: false, //是否显示详细视图和列表视图的切换按钮
            cardView: false,//是否显示详细视图
            detailView: false,//是否显示父子表
            paginationDetailHAlign: "left",//设置页面条数信息位置,默认在左边
            showExport: false,                     //是否显示导出
            exportDataType: "selected",              //basic', 'all', 'selected'.
            //获取查询参数
            queryParams: function queryParams(params) {
                //这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
                var param = {
                    pageSize: params.limit,                       //页面大小
                    pageNumber: (params.offset / params.limit) + 1,   //页码
                    menuName: $("#menuNameQuery").val(), //菜单名称
                    parentName: $("#parentNameQuery").val(),//上级菜单名称
                };
                return param;
            },
            columns: [
                {
                    field: 'Number',
                    title: '',
                    align: 'center',
                    width: 20,
                    formatter: function (value, row, index) {
                        return index + 1;
                    }
                },
                {
                    checkbox: true,
                    visible: true                  //是否显示复选框
                }, {
                    field: 'id',
                    title: '任务ID',
                    width: 50,
                    align: 'center'
                }, {
                    field: 'jobName',
                    title: 'JobName',
                    width: 150,
                    align: 'center'
                }, {
                    field: 'jobGroup',
                    title: 'JobGroup',
                    width: 50,
                    align: 'center'
                }, {
                    field: 'beanClass',
                    title: 'BeanClass',
                    align: 'center'
                }, {
                    field: 'methodName',
                    title: 'MethodName',
                    width: 100,
                    align: 'center'
                }, {
                    field: 'params',
                    title: '参数',
                    width: 100,
                    align: 'center'
                }, {
                    field: 'cronExpression',
                    title: 'cron表达式',
                    width: 100,
                    align: 'center'
                }, {
                    field: 'status',
                    title: '状态',
                    width: 50,
                    align: 'center',
                    formatter: function (value, row, index) {
                        if (value == 0) {
                            return "<a href='javascript:void(0);' class='btn btn-primary btn-xs'>正常</a>";
                        }
                        if (value == 1) {
                            return "<a href='javascript:void(0);' class='btn btn-danger btn-xs'>暂停</a>";
                        }
                    }
                }, {
                    field: 'remark',
                    title: '备注',
                    width: 100,
                    align: 'center'
                }],
        });
    }
</script>
</body>
</html>