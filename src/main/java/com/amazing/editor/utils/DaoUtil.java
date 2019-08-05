package com.amazing.editor.utils;

import com.amazing.editor.dao.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DaoUtil {

    private static UserDao userDao;
    private static LogDao logDao;
    private static MessageDao messageDao;
    private static WebsiteDao websiteDao;
    private static WebsiteTypeDao websiteTypeDao;

    @Resource
    private void setUserDao(UserDao userDao) {
        DaoUtil.userDao = userDao;
    }

    @Resource
    private void setLogDao(LogDao logDao) {
        DaoUtil.logDao = logDao;
    }

    @Resource
    private void setMessageDao(MessageDao messageDao) {
        DaoUtil.messageDao = messageDao;
    }

    @Resource
    private void setWebsiteDao(WebsiteDao websiteDao) {
        DaoUtil.websiteDao = websiteDao;
    }

    @Resource
    private void setWebsiteTypeDao(WebsiteTypeDao websiteTypeDao) {
        DaoUtil.websiteTypeDao = websiteTypeDao;
    }

    public static UserDao getUserDao() {
        return userDao;
    }

    public static LogDao getLogDao() {
        return logDao;
    }

    public static MessageDao getMessageDao() {
        return messageDao;
    }

    public static WebsiteDao getWebsiteDao() {
        return websiteDao;
    }

    public static WebsiteTypeDao getWebsiteTypeDao() {
        return websiteTypeDao;
    }
}
