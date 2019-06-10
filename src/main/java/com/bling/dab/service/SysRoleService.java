package com.bling.dab.service;

import com.bling.dab.common.model.SysRoleReq;
import com.bling.dab.common.result.Result;
import com.bling.dab.dao.SysRoleRepository;
import com.bling.dab.domain.SysRole;
import com.bling.dab.domain.UserInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author: hxp
 * @date: 2019/6/4 15:19
 * @description:角色管理
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleRepository sysRoleRepository;

    /**
     * 添加角色
     * @param sysRoleReq
     * @return
     */
    @Transactional(rollbackFor = Exception.class ,propagation = Propagation.REQUIRED ,isolation = Isolation.READ_COMMITTED)
    public Result saveRole(SysRoleReq sysRoleReq){
        SysRole sysRole = new SysRole();
        sysRole.setAvailable(sysRoleReq.getAvailable());
        sysRole.setDescription(sysRoleReq.getDescription());
        sysRole.setRole(sysRoleReq.getRole());
        SysRole save = sysRoleRepository.save(sysRole);
        Set<Integer> sets = sysRoleReq.getSets();
        if(!CollectionUtils.isEmpty(sets)){
            Iterator<Integer> iterator = sets.iterator();
            while (iterator.hasNext()){
                Integer next = iterator.next();
                sysRoleRepository.saveRolePermission(next, save.getId());
            }
        }
        return Result.success(save);
    }
    public Result saveBatchRole(List<SysRoleReq> list){
        ArrayList<SysRole> list1 = Lists.newArrayList();
        list.forEach((t)->{
            SysRole sysRole = new SysRole();
            sysRole.setAvailable(t.getAvailable());
            sysRole.setDescription(t.getDescription());
            sysRole.setRole(t.getRole());
            list1.add(sysRole);
        });
        List<SysRole> roles = sysRoleRepository.saveAll(list1);
        return Result.success(roles);
    }


    public Result findAll(SysRoleReq sysRoleReq) {

        int page = NumberUtils.toInt(sysRoleReq.getCurrentPage(), 0);
        int size = NumberUtils.toInt(sysRoleReq.getPageSize(), 10);
        String order = sysRoleReq.getOrder();
        SysRole sysRole = new SysRole();
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, order);
        Example<SysRole> example = Example.of(sysRole);
        Page<SysRole> infoPage = sysRoleRepository.findAll(example, pageable);
        return Result.success(infoPage);
    }

    public Result deleteRole(SysRoleReq sysRoleReq) {
        Set<Integer> rids = sysRoleReq.getSets();
        HashSet<SysRole> roles = new HashSet<>();
        rids.forEach((t)->{
            SysRole role = sysRoleRepository.getOne(t);
            roles.add(role);
        });
        sysRoleRepository.deleteInBatch(roles);
        return Result.success();
    }
}
