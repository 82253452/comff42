package com.f4w.mapper;

import com.f4w.dto.req.CarTypeReq;
import com.f4w.entity.CarType;
import com.f4w.utils.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author admin
 */
public interface CarTypeMapper extends BaseMapper<CarType> {


    /**
     * car type list
     *
     * @param req
     * @return
     */
    @Select("select * from car_type where `delete` = 0 order by mtime desc ")
    List<CarType> getList(CarTypeReq req);
}
