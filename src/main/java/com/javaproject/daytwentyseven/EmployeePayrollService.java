package com.javaproject.daytwentyseven;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService {

    public enum IOService {CONSOLE_IO, FILE_IO, DB_IO, REST_IO;}

    private List<EmployeePayrollData> employeePayrollList;

    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList) {
        this.employeePayrollList = employeePayrollList;
    }

    public EmployeePayrollService(){}

    public static void main(String[] args) {
        ArrayList<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
        Scanner consoleInput = new Scanner(System.in);
        employeePayrollService.readEmployeeData(consoleInput);
        employeePayrollService.writeEmployeeData(IOService.FILE_IO);
    }

    private void readEmployeeData(Scanner consoleInput){
        System.out.println("Enter the ID");
        int id = consoleInput.nextInt();
        System.out.println("Enter the Name");
        String name = consoleInput.next();
        System.out.println("Enter the Salary");
        double salary = consoleInput.nextDouble();
        employeePayrollList.add(new EmployeePayrollData(id, name, salary));
    }

    public void writeEmployeeData(IOService ioService){
        if (ioService.equals(IOService.CONSOLE_IO))
            System.out.println("\nWriting the data from console\n" + employeePayrollList);
        else if(ioService.equals(IOService.FILE_IO))
            new EmployeePayrollFileIOService().writeData(employeePayrollList);
    }

    public void printData(IOService ioService) {
        if (ioService.equals(IOService.FILE_IO))
            new EmployeePayrollFileIOService().printData();
    }

    public long countEntries(IOService ioService) {
        if (ioService.equals(IOService.FILE_IO))
            new EmployeePayrollFileIOService().countEntries();
        return 0;
    }

    public long readData(IOService ioService) {
        if (ioService.equals(IOService.FILE_IO))
            this.employeePayrollList = new EmployeePayrollFileIOService().readData();
        return employeePayrollList.size();
    }
}
