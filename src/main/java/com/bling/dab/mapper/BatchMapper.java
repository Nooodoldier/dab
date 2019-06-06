package com.bling.dab.mapper;

import com.bling.dab.domain.BatchData;
import com.bling.dab.domain.Car;
import com.bling.dab.domain.User;

import java.util.List;

/**
 * @author: hxp
 * @date: 2019/1/10 17:02
 * @description:
 */
//@Mapper
public interface BatchMapper {

    List<BatchData> selectBatchData();

    int addUser(User user);

    int addCar(Car car);
}
