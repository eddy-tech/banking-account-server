package com.java.bankingaccount.handlers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
public class ExceptionRepresentation {
    private String errorMessage;
    private String errorSource;
    private Set<String> validationErrors;
}
