package demo.websquare.app.dto.exception;

import demo.websquare.app.constant.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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