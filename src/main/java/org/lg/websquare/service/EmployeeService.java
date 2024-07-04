package org.lg.websquare.service;

import lombok.RequiredArgsConstructor;
import org.lg.websquare.convert.EmployeeConverter;
import org.lg.websquare.dto.*;
import org.lg.websquare.entity.Employee;
import org.lg.websquare.repository.EmployeeRepository;
import org.lg.websquare.util.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeConverter employeeConverter;

    public SearchResponse search(Params searchRequest, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created_date");
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Page<Employee> page = employeeRepository.search(
                DataUtils.appendPercent(searchRequest.getPname()),
                DataUtils.appendPercent(searchRequest.getPteam()),
                DataUtils.appendPercent(searchRequest.getPphone()),
                DataUtils.formatEmpty(searchRequest.getPgender()),
                DataUtils.stringToDate(searchRequest.getPfromDate()),
                DataUtils.stringToDate(searchRequest.getPtoDate()),
               // pageable
                sortedPageable
        );

        List<EmployeeDTO> employeeDTOS = employeeConverter.convertToDTO(page.getContent());

        return SearchResponse.builder()
                .employees(employeeDTOS)
                .paginations(Pagination.builder()
                        .totalElement(page.getTotalElements())
                        .totalPage(page.getTotalPages())
                        .build())
                .build();
    };




//    private LocalDate updateBirthDate(LocalDate currentBirthDate, String newBirthDate) {
//        return newBirthDate != null ? DataUtils.convertStringToDate(newBirthDate) : currentBirthDate;
//    }

//    public String create(CreateRequest createRequest) throws ParseException {
//        if(createRequest.getId() == null || employeeRepository.findById(createRequest.getId()).isEmpty()) {
//            Employee employee = new Employee();
//            employee.setName(createRequest.getName());
//            employee.setTeam(createRequest.getTeam());
//            employee.setPhone(createRequest.getPhone());
//            employee.setGender(createRequest.getGender());
//            employee.setEmail(createRequest.getEmail());
//            employee.setBirthDate(DataUtils.convertStringToDate(createRequest.getBirthDate()));
//            employee.setAddress(createRequest.getAddress());
//            employee.setStatus(createRequest.getStatus());
//
//            employee.setCreatedDate(new Date());
//
//            try {
//                employeeRepository.save(employee);
//                return "Add success";
//            } catch (Exception e) {
//                return "Add fail";
//            }
//        } else {
//            Employee employee =employeeRepository.findById(createRequest.getId()).orElse(null);
//            if(employee == null) {
//                return "update fail";
//            }
//
//            employee.setName(createRequest.getName() == null ? employee.getName() : createRequest.getName());
//            employee.setTeam(createRequest.getTeam() == null ? employee.getTeam() : createRequest.getTeam());
//            employee.setPhone(createRequest.getPhone() == null ? employee.getPhone() : createRequest.getPhone());
//            employee.setGender(createRequest.getGender() == null ? employee.getGender() : createRequest.getGender());
//            employee.setEmail(createRequest.getEmail() == null ? employee.getEmail() : createRequest.getEmail());
//            employee.setAddress(createRequest.getAddress() == null ? employee.getAddress() : createRequest.getAddress());
//            employee.setStatus(createRequest.getStatus() == null ? employee.getStatus() : createRequest.getStatus());
//            employee.setBirthDate(createRequest.getBirthDate() == null ? employee.getBirthDate() : DataUtils.convertStringToDate(createRequest.getBirthDate()));
//
//            employeeRepository.save(employee);
//            return "update success";
//        }
//    }

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
                DataUtils.appendPercent(searchRequest.getPname()),
                DataUtils.appendPercent(searchRequest.getPteam()),
                DataUtils.appendPercent(searchRequest.getPphone()),
                DataUtils.formatEmpty(searchRequest.getPgender()),
                DataUtils.stringToDate(searchRequest.getPfromDate()),
                DataUtils.stringToDate(searchRequest.getPtoDate())
        );
        return listEmployee;
    }

    public String createOrUpdate(CreateRequest createRequest) throws ParseException {
        Employee employee = getEmployeeFromCreateRequest(createRequest);
        try {
            employeeRepository.save(employee);
            return "Add success";
        } catch (Exception e) {
            return "Add fail";
        }
    }

    private Employee getEmployeeFromCreateRequest(CreateRequest createRequest) throws ParseException {
        Employee employee;
        if (createRequest.getId() == null || employeeRepository.findById(createRequest.getId()).isEmpty()) {
            employee = new Employee();
        } else {
            employee = employeeRepository.findById(createRequest.getId()).orElse(null);
            if (employee == null) {
                throw new RuntimeException("Update fail");
            }
        }

        updateEmployeeFromCreateRequest(employee, createRequest);
        return employee;
    }

    private void updateEmployeeFromCreateRequest(Employee employee, CreateRequest createRequest) throws ParseException {
        employee.setName(updateField(employee.getName(), createRequest.getName()));
        employee.setTeam(updateField(employee.getTeam(), createRequest.getTeam()));
        employee.setPhone(updateField(employee.getPhone(), createRequest.getPhone()));
        employee.setGender(updateField(employee.getGender(), createRequest.getGender()));
        employee.setEmail(updateField(employee.getEmail(), createRequest.getEmail()));
        employee.setAddress(updateField(employee.getAddress(), createRequest.getAddress()));
        employee.setStatus(updateField(employee.getStatus(), createRequest.getStatus()));
        employee.setBirthDate(createRequest.getBirthDate() == null ? employee.getBirthDate() : DataUtils.convertStringToDate(createRequest.getBirthDate()));
        employee.setCreatedDate(new Date());
    }

    private <T> T updateField(T currentValue, T newValue) {
        return newValue != null ? newValue : currentValue;
    }
}
