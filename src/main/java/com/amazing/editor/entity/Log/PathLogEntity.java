package com.amazing.editor.entity.Log;

import com.amazing.editor.entity.user.PublicUserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PathLogEntity {
    String cmd = "log";
    int id;
    int operating;
    String path;
    String message;
    PublicUserEntity operator;
}
