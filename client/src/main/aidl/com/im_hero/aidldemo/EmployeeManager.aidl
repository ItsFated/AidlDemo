package com.im_hero.aidldemo;

import java.util.List;
import com.im_hero.aidldemo.Employee;

interface EmployeeManager {
    List<Employee> getEmployees();
    Employee addEmployeeIn(in Employee employee);
    Employee addEmployeeOut(out Employee employee);
    Employee addEmployeeInout(inout Employee employee);
}
