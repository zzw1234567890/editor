package com.amazing.editor.dao;

import com.amazing.editor.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

public interface UserDao {

    @Select("select id,account,head_link as 'headLink',status from user where id=#{id}")
    UserEntity findById(@Param("id") int id);

    @Select("select id,account,email,password,head_link as 'headLink' from user where account=#{account}")
    UserEntity findByAccount(@Param("account") String account);

    @Insert("insert into user(account,email,password) select #{user.account},#{user.email},#{user.password} from dual " +
            "where not exists(select 1 from user where account=#{user.account})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void register(@Param("user") UserEntity userEntity);
}
