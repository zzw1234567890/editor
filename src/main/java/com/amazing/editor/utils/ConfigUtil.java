package com.amazing.editor.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class ConfigUtil {
    private static String repositoryDirectory;
    private static String separator;
    private static String developmentDirectory;
    private static String configDirectory;
    private static String configSuffix;

    public static String getDevPath(HttpSession session) {
        int websiteId = (Integer) session.getAttribute("websiteId");
        return repositoryDirectory + websiteId + separator + developmentDirectory;
    }

    public static String getConfigPath(HttpSession session){
        int websiteId = (Integer) session.getAttribute("websiteId");
        return getDevPath(session) + getSeparator() + getConfigDirectory() + websiteId + getConfigSuffix();
    }

    @Value("${repositoryDirectory}")
    public void setRepositoryDirectory(String repositoryDirectory) {
        ConfigUtil.repositoryDirectory = repositoryDirectory;
    }
    @Value("${separator}")
    public void setSeparator(String separator) {
        ConfigUtil.separator = separator;
    }
    @Value("${developmentDirectory}")
    public void setDevelopmentDirectory(String developmentDirectory) {
        ConfigUtil.developmentDirectory = developmentDirectory;
    }
    @Value("${configDirectory}")
    public void setConfigDirectory(String configDirectory) {
        ConfigUtil.configDirectory = configDirectory;
    }
    @Value("${configSuffix}")
    public void setConfigSuffix(String configSuffix) {
        ConfigUtil.configSuffix = configSuffix;
    }

    public static String getRepositoryDirectory() {
        return repositoryDirectory;
    }

    public static String getSeparator() {
        return separator;
    }

    public static String getDevelopmentDirectory() {
        return developmentDirectory;
    }

    public static String getConfigDirectory() {
        return configDirectory;
    }

    public static String getConfigSuffix() {
        return configSuffix;
    }
}
