package com.amazing.editor.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@Repository
public class ConfigEntity {
    boolean autoSave;
    int autoSaveTime;
}
