package org.lg.websquare.app.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lg.websquare.app.constant.ResponseCode;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    protected ResponseCode responseCode;

    private String param;

    public BusinessException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}