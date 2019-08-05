package com.amazing.editor.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseEntity {
    private boolean result = true;
    private String errorMsg;
    private Object content;
}
