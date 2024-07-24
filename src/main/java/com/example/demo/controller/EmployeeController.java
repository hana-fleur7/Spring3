package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domein.Employee;
import com.example.demo.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    @RequestMapping("/execute")
    public String execute(Model model){
        Employee employee = new Employee();
        employee.setName("山田太郎");
        employee.setAge(20);
        employee.setGender("男");
        employee.setDepartmentId(1);
        service.save(employee);


        Employee employee2 = service.load(3);
        System.out.println(employee2);

        List<Employee> employee = new EmployeeList<>(employee);

        model.addAttribute("employee", employee);
        // service.findAll().forEach(System.out::println); //コンソールに出力する
        
        return "finished";
    }

}
