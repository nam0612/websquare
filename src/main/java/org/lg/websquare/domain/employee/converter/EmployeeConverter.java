package org.lg.websquare.domain.employee.converter;

import org.lg.websquare.domain.employee.dto.EmployeeDTO;
import org.lg.websquare.domain.employee.entity.Employee;
import org.lg.websquare.infrastructure.util.DataUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeConverter {

    private final ModelMapper modelMapper;

    public EmployeeConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<EmployeeDTO> convertToDTO(List<Employee> employeeEntities) {
        if (employeeEntities == null || employeeEntities.isEmpty()) {
            return Collections.emptyList();
        }

        return employeeEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = modelMapper.map(employee, EmployeeDTO.class);
        dto.setBirthDate(formatDate(employee.getBirthDate()));
        return dto;
    }

    public String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}
