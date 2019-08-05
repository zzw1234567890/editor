package com.amazing.editor.dao;

import com.amazing.editor.entity.MessageEntity;
import com.amazing.editor.entity.chart.GetHistoryMessageEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

public interface MessageDao {

    @Insert("insert into message(user_id,website_id,content) values(#{message.userId},#{message.websiteId},#{message.content})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(@Param("message") MessageEntity message);
    /**
     * 获取历史消息
     * @param offset
     * @return
     */
    @Select("select b.id as 'userId',b.account,b.head_link as 'headLink',a.id as 'messageId',a.content," +
            "(IF(b.id=#{userId}, true, false )) as 'isSelf' from message a,user b where a.website_id=#{websiteId} " +
            "and a.user_id=b.id order by a.id desc limit #{offset},#{limit}")
    GetHistoryMessageEntity[] selectHistoryMessage(@Param("userId") int userId,@Param("websiteId") int websiteId,
                                                   @Param("offset") int offset, @Param("limit") int limit);
}
