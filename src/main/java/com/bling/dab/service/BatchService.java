package com.bling.dab.service;

import com.alibaba.fastjson.JSONObject;
import com.bling.dab.common.exception.MyException;
import com.bling.dab.common.util.CRExcelUtil;
import com.bling.dab.common.util.ZipCompress;
import com.bling.dab.dao.BatchMapper;
import com.bling.dab.domain.BatchData;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: hxp
 * @date: 2019/3/28 17:47
 * @description:
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
public class BatchService {

    public static final Logger logger = LoggerFactory.getLogger(BatchService.class);

    @Autowired
    private BatchMapper batchMapper;
    /**
     * 导出数据
     * @return
     */
    public int batchExport() {
        int result = 1;
        HSSFWorkbook wb = new HSSFWorkbook();
        logger.info("开始");
        HSSFSheet sheet = wb.createSheet("信息表");
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short)10);
        font.setFontName("宋体");
        style.setFont(font);
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("用户信息");
        row.createCell(1);
        row.createCell(2);
        row.createCell(3).setCellValue("车信息");
        row.createCell(4);
        row.createCell(5);
        sheet.addMergedRegion(new CellRangeAddress(
                0,0,0,2
        ));
        sheet.addMergedRegion(new CellRangeAddress(
                0,0,3,5
        ));
        HSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("id");
        row1.createCell(1).setCellValue("name");
        row1.createCell(2).setCellValue("age");
        row1.createCell(3).setCellValue("cid");
        row1.createCell(4).setCellValue("carName");
        row1.createCell(5).setCellValue("carPrice");
        List<BatchData> batchData = batchMapper.selectBatchData();
        logger.info(JSONObject.toJSONString(batchData));
        for (int i = 1; i <= batchData.size(); i++) {
            HSSFRow rowX = sheet.createRow(i + 1);
            BatchData dataX = batchData.get(i - 1);
            rowX.createCell(0).setCellValue(dataX.getId());
            rowX.createCell(1).setCellValue(dataX.getName());
            rowX.createCell(2).setCellValue(dataX.getAge());
            rowX.createCell(3).setCellValue(dataX.getCid());
            rowX.createCell(4).setCellValue(dataX.getCarName());
            rowX.createCell(5).setCellValue(dataX.getCarPrice().setScale(2,BigDecimal.ROUND_UP).toString()
            );
        }
        HSSFRow row00 = sheet.getRow(0);
        for (Cell cell : row00) {
            cell.setCellStyle(style);
        }
        HSSFRow row01 = sheet.getRow(1);
        for (Cell cell : row01) {
            cell.setCellStyle(style);
        }

        for (int i = 0; i < 6; i++) {
            if (i < 4) {
                sheet.setColumnWidth(i, CRExcelUtil.pixel2WidthUnits(80));
            } else {
                sheet.setColumnWidth(i, CRExcelUtil.pixel2WidthUnits(220));
            }
        }
        FileOutputStream out = null;
        try {
            String filePath = "D:/dabFile/";
            String fileName = "dab"+System.currentTimeMillis();
            out = new FileOutputStream(filePath+fileName+".xls");
            wb.write(out);
            String fileZipPath = "D:/dabZipFile/";
            String fileZipName = "dab"+System.currentTimeMillis();
            ZipCompress zipCom = new ZipCompress(fileZipPath+fileZipName+".zip",filePath);
            zipCom.zip();
            String mail = "15810665479@163.com";
            MailUtil.sendEmail(fileZipPath+fileZipName+".zip",mail);
        } catch (Exception e) {
            logger.error("接口内部错误",e);
            throw new MyException("接口内部错误",e);
        }finally {
            logger.info("进入finally代码块");
            if(out != null){
                try {
                    out.close();
                    logger.info("关闭输出流");
                } catch (IOException e) {
                    logger.error("接口内部错误",e);
                    throw new MyException("接口内部错误",e);
                }
            }
        }
        return result;
    }
}
