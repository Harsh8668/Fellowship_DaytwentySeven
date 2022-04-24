package com.javaproect.daytwentyseven;

import com.javaproject.daytwentyseven.EmployeePayrollData;
import com.javaproject.daytwentyseven.EmployeePayrollService;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static com.javaproject.daytwentyseven.EmployeePayrollService.IOService.FILE_IO;

public class EmployeePayrollServiceTest {
    @Test
    public void given3EmployeesWhenWrittenToFileShouldMatchEmployeeEntries(){
        EmployeePayrollData[] arrOfEmps = {
                new EmployeePayrollData(1, "Shubham", 250000.0),
                new EmployeePayrollData(2, "Abhas", 550000.0),
                new EmployeePayrollData(3, "Harsh", 450000.0)
        };

        EmployeePayrollService employeePayrollService;
        employeePayrollService = new EmployeePayrollService(Arrays.asList(arrOfEmps));
        employeePayrollService.writeEmployeeData(FILE_IO);
        employeePayrollService.printData(FILE_IO);
        long entries = employeePayrollService.countEntries(FILE_IO);
        Assert.assertEquals(0, entries);
    }

    @Test
    public void givenFileOnReadingFromFileShouldMatchEmployeeCount(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        long entries = employeePayrollService.readData(FILE_IO);

    }
}
