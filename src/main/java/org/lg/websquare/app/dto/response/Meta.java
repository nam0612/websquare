package org.lg.websquare.app.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meta {

    private String status;
    private String message;

    @JsonProperty("service_id")
    private String serviceId;


}