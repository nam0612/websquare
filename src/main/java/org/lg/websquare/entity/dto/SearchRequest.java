package org.lg.websquare.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {
    private String pname;
    private String pteam;
    private String pphone;
    private String pgender;
    private String pfromDate;
    private String ptoDate;
}
