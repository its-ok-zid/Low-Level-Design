package EmployeePayrollSystem;

public abstract class AbstractEmployee implements Employee {

    private final String employeeId;
    private final String name;
    private boolean isClockedIn;

    protected AbstractEmployee(String employeeId, String name) {
        this.employeeId = employeeId;
        this.name = name;
        isClockedIn = false;
    }

    @Override
    public boolean clockIn() {

        if (isClockedIn) {
            System.out.println(name + " is already clocked in.");
            return false;
        }
        this.isClockedIn = true;
        System.out.println(name + " clocked in successfully.");
        return true;
    }


    @Override
    public boolean clockOut() {
        if (!isClockedIn) {
            System.out.println(name + " is not clocked in.");
            return false;
        }
        this.isClockedIn = false;
        System.out.println(name + " clocked out successfully.");
        return true;

    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [ID: " + employeeId + ", Name: " + name + "]";
    }
}
