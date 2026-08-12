package EmployeePayrollSystem;

public class PayrollApplication {
    public static void main(String[] args) {
        PayrollService payrollService = new PayrollService();

        FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("2153970", "Satoru", 500000, 1500);

        ContractorEmployee contractorEmployee = new ContractorEmployee("909134", "Obito", 80, 900);

        payrollService.processPayout(fullTimeEmployee);

        payrollService.processPayout(contractorEmployee);
    }
}
