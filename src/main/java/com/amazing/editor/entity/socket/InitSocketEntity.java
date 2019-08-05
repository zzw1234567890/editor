package com.amazing.editor.entity.socket;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class InitSocketEntity {
    private int userId;
    private final String cmd = "init";
    private List<String> editorFileEntityList = new ArrayList<>();
    private Object config;
}
