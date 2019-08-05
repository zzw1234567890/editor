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
public class WebsiteEntity {
    private int id;
    private int user_id;
    private String name;
    private String appid;
    private String appkey;
    private int website_type_id;
    private String describe;
    private String development_status;
    private String database_name;
    private String database_username;
    private String database_password;
    private int cooperator_num;
    private short status;
    private String create_time;
    private String update_time;
}
