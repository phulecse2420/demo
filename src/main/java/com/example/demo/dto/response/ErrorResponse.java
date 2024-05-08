package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@JsonInclude(Include.NON_EMPTY)
@ToString
public class ErrorResponse {

    private Integer code;
    private String message;
    private Object errors;

}
