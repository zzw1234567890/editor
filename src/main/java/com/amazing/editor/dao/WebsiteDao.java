package com.amazing.editor.dao;

import com.amazing.editor.entity.WebsiteEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

public interface WebsiteDao {

    /**
     * 创建网站
     * @param websiteEntity
     * @return
     */
    @Insert("insert into website(user_id,name,appid,appkey,website_type_id,describe,database_name,database_username,database_password)" +
            "values(#{websiteEntity.user_id},#{websiteEntity.name},#{websiteEntity.name},#{websiteEntity.name}," +
            "#{websiteEntity.name},#{websiteEntity.name},#{websiteEntity.name},#{websiteEntity.name},#{websiteEntity.name})")
    int create(@Param("websiteEntity")WebsiteEntity websiteEntity);

}
