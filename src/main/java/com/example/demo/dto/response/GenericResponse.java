package com.example.demo.dto.response;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse<T> {

    @Builder.Default
    int status = 200;

    String            message;
    T                 data;
    Collection<Error> errors;

}
