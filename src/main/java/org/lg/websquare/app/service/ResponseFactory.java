package org.lg.websquare.app.service;


import lombok.RequiredArgsConstructor;
import org.lg.websquare.app.constant.ResponseCode;
import org.lg.websquare.app.dto.response.Meta;
import org.lg.websquare.app.dto.response.ResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseFactory {

    @Value("${spring.application.name}")
    String appName;

    public ResponseDto response(ResponseCode responseCode) {
        var meta = Meta.builder()
                .status(responseCode.getType())
                .serviceId(appName)
                .build();

        return new ResponseDto(meta, null);
    }

    public ResponseDto response(Object data) {
        var meta = Meta.builder()
                .status(ResponseCode.SUCCESS.getType())
                .serviceId(appName)
                .build();

        return new ResponseDto(meta, data);
    }
}