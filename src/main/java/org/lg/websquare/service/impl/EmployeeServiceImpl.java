package org.lg.websquare.service.impl;

import lombok.RequiredArgsConstructor;
import org.lg.websquare.convert.EmployeeConverter;
import org.lg.websquare.dto.*;
import org.lg.websquare.entity.Employee;
import org.lg.websquare.repository.EmployeeRepository;
import org.lg.websquare.service.IEmployeeService;
import org.lg.websquare.util.DataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeConverter employeeConverter;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Transactional
    public void delete(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            logger.warn("Attempt to delete with null or empty ids");
            return;
        }

        List<Employee> employees = employeeRepository.findAllById(ids);
        try {
            employeeRepository.deleteAll(employees);
            logger.info("Successfully deleted employees with ids: {}", ids);
        } catch (Exception e) {
            logger.error("Failed to delete employees with ids: {}", ids, e);
            throw new RuntimeException("Failed to delete employees", e);
        }
    }

    @Transactional
    public Employee createOrUpdate(CreateRequest createRequest) throws ParseException {
        Employee employee = getEmployeeFromCreateRequest(createRequest);

        try {
            return employeeRepository.save(employee);
        } catch (Exception e) {
            logger.error("Failed to save employee: {}", employee, e);
            throw new RuntimeException("Failed to save employee", e);
        }
    }

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
