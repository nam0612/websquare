package org.lg.websquare.service;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.lg.websquare.entity.Employee;
import org.lg.websquare.entity.dto.CreateRequest;
import org.lg.websquare.entity.dto.SearchRequest;
import org.lg.websquare.repository.EmployeeRepository;
import org.lg.websquare.util.DataUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Page<Employee> search(SearchRequest searchRequest, Pageable pageable) {

        return employeeRepository.search(
                DataUtil.appendPercent(searchRequest.getPname()),
                DataUtil.appendPercent(searchRequest.getPteam()),
                DataUtil.appendPercent(searchRequest.getPphone()),
                DataUtil.appendPercent(searchRequest.getPgender()),
                DataUtil.stringToDate(searchRequest.getPfromDate()),
                DataUtil.stringToDate(searchRequest.getPtoDate()),
                pageable
        );
    };

    public String create(CreateRequest createRequest) {
        if(createRequest.getId() == null || employeeRepository.findById(createRequest.getId()).isEmpty()) {
            Employee employee = new Employee();
            employee.setName(createRequest.getName());
            employee.setTeam(createRequest.getTeam());
            employee.setPhone(createRequest.getPhone());
            employee.setGender(createRequest.getGender());
            employee.setEmail(createRequest.getEmail());
            employee.setBirthDate(DataUtil.stringToDate(createRequest.getBirthDate()));
            employee.setAddress(createRequest.getAddress());
            employee.setStatus(createRequest.getStatus());

            try {
                employeeRepository.save(employee);
                return "Add success";
            } catch (Exception e) {
                return "Add fail";
            }
        } else {
            Employee employee =employeeRepository.findById(createRequest.getId()).orElse(null);
            if(employee == null) {
                return "update fail";
            }

            employee.setName(createRequest.getName() == null ? employee.getName() : createRequest.getName());
            employee.setTeam(createRequest.getTeam() == null ? employee.getTeam() : createRequest.getTeam());
            employee.setPhone(createRequest.getPhone() == null ? employee.getPhone() : createRequest.getPhone());
            employee.setGender(createRequest.getGender() == null ? employee.getGender() : createRequest.getGender());
            employee.setEmail(createRequest.getEmail() == null ? employee.getEmail() : createRequest.getEmail());
            employee.setBirthDate(createRequest.getBirthDate() == null ? employee.getBirthDate() : DataUtil.stringToDate(createRequest.getBirthDate()));
            employee.setAddress(createRequest.getAddress() == null ? employee.getAddress() : createRequest.getAddress());
            employee.setStatus(createRequest.getStatus() == null ? employee.getStatus() : createRequest.getStatus());
            employeeRepository.save(employee);
            return "update success";
        }
    }

    public String delete(List<String> ids) {
        var employees = employeeRepository.findAllById(ids);
        if (employees.isEmpty()) {
            return "Employee not exist";
        }
        for (var employee : employees) {
            employee.setStatus("INACTIVE");
        }

        try {
            employeeRepository.saveAll(employees);
            return "Delete success";
        } catch (Exception e) {
            return "Delete fail";
        }
    }

    public void exportDataToExcel(HttpServletResponse response) throws IOException {
        List<Employee> dataList = employeeRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Num ");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Birth Date");
        headerRow.createCell(3).setCellValue("Gender");
        headerRow.createCell(4).setCellValue("Phone");
        headerRow.createCell(5).setCellValue("Email");
        headerRow.createCell(6).setCellValue("Address");
        headerRow.createCell(7).setCellValue("Team");
        headerRow.createCell(8).setCellValue("Status");


        // Create data rows
        int rowNum = 1;
        for (Employee data : dataList) {
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(0).setCellValue(rowNum);
            dataRow.createCell(1).setCellValue(data.getName());
            dataRow.createCell(2).setCellValue(data.getBirthDate());
            dataRow.createCell(3).setCellValue(data.getGender());
            dataRow.createCell(4).setCellValue(data.getPhone());
            dataRow.createCell(5).setCellValue(data.getEmail());
            dataRow.createCell(6).setCellValue(data.getAddress());
            dataRow.createCell(7).setCellValue(data.getTeam());
            dataRow.createCell(8).setCellValue(data.getStatus());

        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=data.xlsx");

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
