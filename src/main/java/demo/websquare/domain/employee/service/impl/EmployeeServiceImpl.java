package demo.websquare.domain.employee.service.impl;

import demo.websquare.domain.employee.converter.EmployeeConverter;
import demo.websquare.domain.employee.dto.Pagination;
import demo.websquare.domain.employee.dto.request.EmployeeRequest;
import demo.websquare.domain.employee.dto.request.ParamSearchRequest;
import demo.websquare.domain.employee.entity.Employee;
import demo.websquare.domain.employee.repository.EmployeeRepository;
import demo.websquare.domain.employee.service.IEmployeeService;
import demo.websquare.infrastructure.util.DataUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Employee createOrUpdate(EmployeeRequest createRequest) throws ParseException {
        Employee employee = getEmployeeFromCreateRequest(createRequest);

        try {
            return employeeRepository.save(employee);
        } catch (Exception e) {
            logger.error("Failed to save employee: {}", employee, e);
            throw new RuntimeException("Failed to save employee", e);
        }
    }

    public Pagination searchEmployees(ParamSearchRequest searchRequest) {
        Pageable sortedPageable = Pageable.ofSize(searchRequest.getPageSize()).withPage(searchRequest.getPage());

        Page<Employee> page = employeeRepository.searchCondition(
                searchRequest.getName(),
                searchRequest.getTeam(),
                searchRequest.getPhone(),
                searchRequest.getGender(),
                DataUtils.stringToDate(searchRequest.getDateFrom()),
                DataUtils.stringToDate(searchRequest.getDateTo()),
                sortedPageable
        );

        return Pagination.builder()
                .data(employeeConverter.convertToDTO(page.getContent()))
                .totalElement(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .pageIndex(page.getNumber())
                .pageSize(page.getSize())
                .build();
    };

    public List<Employee> exportDataToExcel(ParamSearchRequest searchRequest) {
        List<Employee> listEmployee = employeeRepository.downloadsExcel(
                DataUtils.appendPercent(searchRequest.getName()),
                DataUtils.appendPercent(searchRequest.getTeam()),
                DataUtils.appendPercent(searchRequest.getPhone()),
                DataUtils.formatEmpty(searchRequest.getGender()),
                DataUtils.stringToDate(searchRequest.getDateFrom()),
                DataUtils.stringToDate(searchRequest.getDateTo())
        );
        return listEmployee;
    }

    private Employee getEmployeeFromCreateRequest(EmployeeRequest createRequest) throws ParseException {
        Employee employee = new Employee();

        updateEmployeeFromCreateRequest(employee, createRequest);

        if (createRequest.getId() != null) {
            employee = employeeRepository.findById(createRequest.getId()).orElseThrow(null);

        } else {
            employee.setCreatedDate(new Date());
        }

        return employee;
    }

    private void updateEmployeeFromCreateRequest(Employee employee, EmployeeRequest createRequest) throws ParseException {
        employee.setName(createRequest.getName());
        employee.setTeam(createRequest.getTeam());
        employee.setPhone( createRequest.getPhone());
        employee.setGender(createRequest.getGender());
        employee.setEmail(createRequest.getEmail());
        employee.setAddress(createRequest.getAddress());
        employee.setStatus( createRequest.getStatus());
        employee.setBirthDate(createRequest.getBirthDate() == null ? employee.getBirthDate() : DataUtils.convertStringToDate(createRequest.getBirthDate()));
    }


}
