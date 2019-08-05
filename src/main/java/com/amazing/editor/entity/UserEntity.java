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
public class UserEntity {
    private int id;
    private short status;
    private String account;
    private String email;
    private String password;
    private String headLink;
    private String create_time;
    private String update_time;

}
