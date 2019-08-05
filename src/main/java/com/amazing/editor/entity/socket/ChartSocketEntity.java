package com.amazing.editor.entity.socket;

import com.amazing.editor.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChartSocketEntity {

    private String content;
    private String cmd;
    private UserEntity user;

}
