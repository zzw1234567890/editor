package com.amazing.editor.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Map;

public interface WebsiteTypeDao {
    /**
     * 获取所有website_type
     * @return
     */
    @Select("select id,name from website_type where status=1")
    Map[] getWebsiteType();
}
