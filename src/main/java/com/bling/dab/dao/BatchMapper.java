package com.bling.dab.dao;

import com.bling.dab.domain.BatchData;

import java.util.List;

/**
 * @author: hxp
 * @date: 2019/1/10 17:02
 * @description:
 */
//@Mapper
public interface BatchMapper {

    List<BatchData> selectBatchData();
}
