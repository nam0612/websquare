package org.lg.websquare.service;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.lg.websquare.convert.EmployeeConverter;
import org.lg.websquare.entity.Employee;
import org.lg.websquare.entity.dto.*;
import org.lg.websquare.repository.EmployeeRepository;
import org.lg.websquare.util.DataUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public SearchResponse search(Params searchRequest, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created_date");
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Page<Employee> page = employeeRepository.search(
                DataUtil.appendPercent(searchRequest.getPname()),
                DataUtil.appendPercent(searchRequest.getPteam()),
                DataUtil.appendPercent(searchRequest.getPphone()),
                DataUtil.formatEmpty(searchRequest.getPgender()),
                DataUtil.stringToDate(searchRequest.getPfromDate()),
                DataUtil.stringToDate(searchRequest.getPtoDate()),
               // pageable
                sortedPageable
        );

        EmployeeConverter employeeConverter = new EmployeeConverter();
        List<EmployeeDTO> employeeDTOS = employeeConverter.convertToDTO(page.getContent());

        return SearchResponse.builder()
                .employees(employeeDTOS)
                .paginations(Pagination.builder()
                        .totalElement(page.getTotalElements())
                        .totalPage(page.getTotalPages())
                        .build())
                .build();
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

            employee.setCreatedDate(new Date());

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
        try {
            employeeRepository.deleteAll(employees);
            return "Delete success";
        } catch (Exception e) {
            return "Delete fail";
        }
    }

    public List<Employee> exportDataToExcel(Params searchRequest) {
        List<Employee> listEmployee = employeeRepository.downloadsExcel(
                DataUtil.appendPercent(searchRequest.getPname()),
                DataUtil.appendPercent(searchRequest.getPteam()),
                DataUtil.appendPercent(searchRequest.getPphone()),
                DataUtil.formatEmpty(searchRequest.getPgender()),
                DataUtil.stringToDate(searchRequest.getPfromDate()),
                DataUtil.stringToDate(searchRequest.getPtoDate())
        );
        return listEmployee;
    }

}
