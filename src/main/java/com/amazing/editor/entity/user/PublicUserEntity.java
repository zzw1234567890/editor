package com.amazing.editor.entity.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

@Setter
@Getter
@ToString
@Repository
public class PublicUserEntity {
    int id;
    short status;
    String account;
    String headLink;
}
