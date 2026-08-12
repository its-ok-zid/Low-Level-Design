package EmployeePayrollSystem;

public class FullTimeEmployee extends AbstractEmployee {

    private final double monthlyBaseSalary;
    private final double performanceBonusPercentage;

    public FullTimeEmployee(String employeeId, String name, double monthlyBaseSalary, double performanceBonusPercentage) {
        super(employeeId, name);
        this.monthlyBaseSalary = monthlyBaseSalary;
        this.performanceBonusPercentage = performanceBonusPercentage;
    }

    @Override
    public double calculatePay() {
        return monthlyBaseSalary + (monthlyBaseSalary * (performanceBonusPercentage / 100.0));
    }
}
