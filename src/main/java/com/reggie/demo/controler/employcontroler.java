package com.reggie.demo.controler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.reggie.demo.common.CommonsConst;
import com.reggie.demo.common.R;
import com.reggie.demo.entity.Employee;
import com.reggie.demo.service.employservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/employee")
public class employcontroler {
    @Autowired
    private employservice employservice;
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password=employee.getPassword();
        password= DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<Employee>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp=employservice.getOne(queryWrapper);
        if(emp == null){
            return R.error(CommonsConst.LOGIN_FAIL);

        }
        if(emp.getPassword().equals(password)){
            return R.error(CommonsConst.LOGIN_FAIL);
        }
        if(emp.getStatus() ==CommonsConst.EMPLOYEE_STATUS_NO){
            return R.error(CommonsConst.LOGIN_ACCOUNT_STOP);
        }
        request.getSession().setAttribute("employee", emp.getId());

        return R.success(emp);

    }
    @PostMapping("/logout")
    public R<String> loginOut(HttpServletRequest request) {
        // 去除session
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
    @PostMapping
    public  R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        employee.setPassword(DigestUtils.md5DigestAsHex(CommonsConst.INIT_PASSWORD.getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        Long empId = (Long) request.getSession().getAttribute("employee");
        System.out.println("ss"+empId);
        employee.setCreateUser((1L));
        employee.setUpdateUser((1l));
        // 调用存储方法
        employservice.save(employee);
        return R.success("添加成功");

    }


}
