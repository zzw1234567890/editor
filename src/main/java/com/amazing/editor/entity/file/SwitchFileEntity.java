package com.amazing.editor.entity.file;

import com.amazing.editor.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SwitchFileEntity {
    private String cmd = "switchFile";
    private String oldPath;
    private String newPath;
    private UserEntity operator;
}
