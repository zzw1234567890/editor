package com.amazing.editor.entity.chart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

@Setter
@Getter
@ToString
@Repository
public class GetHistoryMessageEntity {
    int userId;
    int messageId;
    String account;
    String headLink;
    String content;
    boolean isSelf;
}
