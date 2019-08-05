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
public class LogEntity {
    int id;
    int userId;
    int websiteId;
    int operating;
    String path;
    String message;
}
