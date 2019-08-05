package com.amazing.editor.dao;

import com.amazing.editor.entity.Log.PathLogEntity;
import com.amazing.editor.entity.LogEntity;
import com.amazing.editor.entity.user.PublicUserEntity;
import org.apache.ibatis.annotations.*;

public interface LogDao {
    @Insert("insert into log(user_id,website_id,path,message,operating) values(#{logEntity.userId}," +
            "#{logEntity.websiteId},#{logEntity.path},#{logEntity.message},#{logEntity.operating})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(@Param("logEntity") LogEntity logEntity);

    /**
     * 获取历史日志
     * @param userId
     * @param websiteId
     * @param offset
     * @param limit
     * @return
     */
    @Select("select id,user_id,operating,message,path from log where website_id=#{websiteId} and " +
            "status=1 order by id desc limit #{offset},#{limit}")
    @Results({
            @Result(column = "user_id", property = "operator",javaType = PublicUserEntity.class,
                    one = @One(select = "com.amazing.editor.dao.LogDao.getPublicUserById")),
    })
    PathLogEntity[] getHistoryLog(int userId, int websiteId, int offset, int limit);

    @Select("select id,user_id,operating,message,path from log where id=#{id} and status=1")
    @Results({
            @Result(column = "user_id", property = "operator",javaType = PublicUserEntity.class,
                    one = @One(select = "com.amazing.editor.dao.LogDao.getPublicUserById")),
    })
    PathLogEntity getHistoryLogById(int id);

    /**
     * 根据id获取用户开放信息
     * @param userId
     * @return
     */
    @Select("select id,account,head_link as 'headLink',status from user where id=#{userId}")
    PublicUserEntity getPublicUserById(int userId);
}
