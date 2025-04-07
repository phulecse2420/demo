package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class Error {

    Integer code;
    String  message;
    String  errorField;

    public Error (Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Error (String message, String errorField) {
        this.message = message;
        this.errorField = errorField;
    }
}
