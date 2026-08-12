package EmployeePayrollSystem;

import java.util.ArrayList;
import java.util.List;

public class PayrollService {
    private final List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee) {
        if (employee != null) {
            employees.add(employee);
        }
    }

    public void processPayout(Employee employee) {
        if (employee == null) return;
        double payout = employee.calculatePay();
        System.out.println("Payout for " + employee + ": $" + payout);
    }
}