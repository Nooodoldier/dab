package com.bling.dab.service;

import com.bling.dab.common.model.SysRoleReq;
import com.bling.dab.dao.SysRoleRepository;
import com.bling.dab.domain.SysRole;
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
    public SysRole saveRole(SysRoleReq sysRoleReq){
        SysRole sysRole = new SysRole();
        sysRole.setAvailable(sysRoleReq.getAvailable());
        sysRole.setDescription(sysRoleReq.getDescription());
        sysRole.setRole(sysRoleReq.getRole());
        SysRole role = sysRoleRepository.save(sysRole);
        Set<Integer> sets = sysRoleReq.getSets();
        if(!CollectionUtils.isEmpty(sets)){
            Iterator<Integer> iterator = sets.iterator();
            while (iterator.hasNext()){
                Integer next = iterator.next();
                sysRoleRepository.saveRolePermission(next, role.getId());
            }
        }
        return role;
    }
    public List<SysRole> saveBatchRole(List<SysRoleReq> list){
        ArrayList<SysRole> list1 = Lists.newArrayList();
        list.forEach((t)->{
            SysRole sysRole = new SysRole();
            sysRole.setAvailable(t.getAvailable());
            sysRole.setDescription(t.getDescription());
            sysRole.setRole(t.getRole());
            list1.add(sysRole);
        });
        List<SysRole> roles = sysRoleRepository.saveAll(list1);
        return roles;
    }


    public Page<SysRole> findAll(SysRoleReq sysRoleReq) {

        int page = NumberUtils.toInt(sysRoleReq.getCurrentPage(), 0);
        int size = NumberUtils.toInt(sysRoleReq.getPageSize(), 10);
        String order = sysRoleReq.getOrder();
        SysRole sysRole = new SysRole();
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, order);
        Example<SysRole> example = Example.of(sysRole);
        Page<SysRole> infoPage = sysRoleRepository.findAll(example, pageable);
        return infoPage;
    }

    public void deleteRole(SysRoleReq sysRoleReq) {
        Set<Integer> rids = sysRoleReq.getSets();
        HashSet<SysRole> roles = new HashSet<>();
        rids.forEach((t)->{
            SysRole role = sysRoleRepository.getOne(t);
            roles.add(role);
        });
        sysRoleRepository.deleteInBatch(roles);
    }

    @Transactional(rollbackFor = Exception.class ,propagation = Propagation.REQUIRED ,isolation = Isolation.READ_COMMITTED)
    public void updateRolePermission(SysRoleReq sysRoleReq){
        Integer rid = sysRoleReq.getId();
        Set<Integer> sets = sysRoleReq.getSets();
        sysRoleRepository.deleteRolePermission(rid);
        Iterator<Integer> iterator = sets.iterator();
        while (iterator.hasNext()){
            Integer next = iterator.next();
            sysRoleRepository.saveRolePermission(next,rid);
        }
    }

    public List<SysRole> getRoleList() {
        return sysRoleRepository.getRoleList();
    }
}
