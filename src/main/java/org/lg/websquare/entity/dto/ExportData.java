package org.lg.websquare.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.lg.websquare.entity.Employee;

import java.util.List;

@Getter
@Setter
@Builder
public class ExportData {
    private List<Employee> employees;
}
