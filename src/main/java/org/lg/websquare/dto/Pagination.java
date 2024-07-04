package org.lg.websquare.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Pagination {
    private long totalElement;
    private int totalPage;
}
