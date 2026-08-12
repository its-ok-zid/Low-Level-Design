package EmployeePayrollSystem;

public class ContractorEmployee extends AbstractEmployee {
    private final double hourlyRate;
    private final double hoursWorked;

    public ContractorEmployee(String employeeId, String name, double hourlyRate, double hoursWorked) {
        super(employeeId, name);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculatePay() {
        if (hoursWorked <= 160) {
            return hourlyRate * hoursWorked;
        }
        double regularPay = 160 * hourlyRate;
        double overtimePay = (hoursWorked - 160) * (hourlyRate * 1.5);
        return regularPay + overtimePay;
    }
}