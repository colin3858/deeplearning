package com.reggie.demo.service.iml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.demo.entity.Employee;
import com.reggie.demo.mapper.employeeMapper;
import com.reggie.demo.service.employservice;
import org.springframework.stereotype.Service;

@Service
public class employeeserviceiml  extends ServiceImpl<employeeMapper, Employee> implements employservice{

}


