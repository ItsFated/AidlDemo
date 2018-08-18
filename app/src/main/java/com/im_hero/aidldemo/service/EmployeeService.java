package com.im_hero.aidldemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.im_hero.aidldemo.Employee;
import com.im_hero.aidldemo.EmployeeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason
 * @version 1.0
 */

public class EmployeeService extends Service {
    public static final String TAG = "EmployeeService";

    private List<Employee> employees;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, String.format("on bind,intent = %s", intent.toString()));
        return stub;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        employees = new ArrayList<>();
        Employee employee = new Employee();
        employee.setName("AndroidDev");
        employee.setAge((short) 28);
        employees.add(employee);
    }

    private final EmployeeManager.Stub stub = new EmployeeManager.Stub() {
        @Override
        public List<Employee> getEmployees() {
            synchronized (this) {
                Log.i(TAG, "invoking getEmployees() method , now the list is : " + employees.toString());
                if (employees != null) {
                    return employees;
                }
                return new ArrayList<>();
            }
        }

        @Override
        public Employee addEmployeeIn(Employee employee) {
            synchronized (this) {
                if (employees == null) {
                    employees = new ArrayList<>();
                }
                if (employee == null) {
                    Log.i(TAG, "Employee is null in In");
                    employee = new Employee();
                }
                if (!employees.contains(employee)) {
                    employees.add(employee);
                }
                //打印employees列表，观察客户端传过来的值
                Log.i(TAG, "invoking addEmployeeIn() method , now the list is : " + employees.toString());
                //尝试修改employee的参数，主要是为了观察其到客户端的反馈
                employee.setName("Server In");
                return employee;
            }
        }

        @Override
        public Employee addEmployeeOut(Employee employee) {
            synchronized (this) {
                if (employees == null) {
                    employees = new ArrayList<>();
                }
                if(employee == null){
                    Log.i(TAG , "Employee is null in Out");
                    employee = new Employee();
                }
                if (!employees.contains(employee)) {
                    employees.add(employee);
                }
                Log.i(TAG, "invoking addEmployeeOut() method , now the list is : " + employees.toString());
                employee.setName("Server Out");
                return employee;
            }
        }

        @Override
        public Employee addEmployeeInout(Employee employee) {
            synchronized (this) {
                if (employees == null) {
                    employees = new ArrayList<>();
                }
                if(employee == null){
                    Log.i(TAG , "Employee is null in Inout");
                    employee = new Employee();
                }
                if (!employees.contains(employee)) {
                    employees.add(employee);
                }
                Log.i(TAG, "invoking addEmployeeInout() method , now the list is : " + employees.toString());
                employee.setName("Server Inout");
                return employee;
            }
        }
    };
}
