package com.reggie.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.demo.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface employeeMapper extends BaseMapper<Employee> {
}
