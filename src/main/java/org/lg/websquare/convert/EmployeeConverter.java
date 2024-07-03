package org.lg.websquare.convert;

import org.lg.websquare.entity.Employee;
import org.lg.websquare.entity.dto.EmployeeDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class EmployeeConverter {

    public List<EmployeeDTO> convertToDTO(List<Employee> employeeEntity) {
        if (employeeEntity == null || employeeEntity.isEmpty()) {
            return Collections.emptyList();
        }

        List<EmployeeDTO> employeeDTO = new ArrayList<>(employeeEntity.size());

        for (Employee employee : employeeEntity) {
            EmployeeDTO dto = new EmployeeDTO();
            dto.setId(employee.getId());
            dto.setName(employee.getName());
            dto.setBirthDate(formatDate(employee.getBirthDate()));
            dto.setGender(employee.getGender());
            dto.setPhone(employee.getPhone());
            dto.setEmail(employee.getEmail());
            dto.setAddress(employee.getAddress());
            dto.setTeam(employee.getTeam());
            dto.setStatus(employee.getStatus());

            employeeDTO.add(dto);
        }

        return employeeDTO;
    }

    public String formatDate(Date date) {
        if (date == null) {
            return null;
        }

        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}
