package com.amazing.editor.entity.socket;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JoinSocketEntity {
    private final String cmd = "joinPartner";
    private int userId;
}
