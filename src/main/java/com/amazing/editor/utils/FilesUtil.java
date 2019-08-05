package com.amazing.editor.utils;

import com.amazing.editor.entity.BaseEntity;
import com.amazing.editor.websocket.WebSocketServer;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class FilesUtil {

    /**
     * 递归获取指定目录下的所有文件和文件夹
     *
     * @param file
     * @return
     */
    public static List<Map<String, Object>> getFileListFunction(File file, int dirLen) {
        List<Map<String, Object>> fileList = new ArrayList<>();
        for (File f : file.listFiles()) {
            Map<String, Object> temp = new HashMap<>();
            String name = f.getName();
            if (name.equals(".config"))
                continue;
            String path = f.getPath();
            temp.put("name", name);
            if (f.isFile()) {
                temp.put("type", "file");
                temp.put("name", name);
                temp.put("link", path.substring(dirLen));
            } else {
                temp.put("type", "folder");
                temp.put("name", name);
                temp.put("link", path.substring(dirLen));
                temp.put("data", getFileListFunction(f, dirLen));
            }
            fileList.add(temp);
        }
        int i,j,fileIndex = fileList.size();
        for (i = 0; i < fileIndex; i ++){
            if (fileList.get(i).get("type").equals("folder")) {
                continue;
            }
            for (j = fileIndex; j < fileList.size(); j ++){
                if (((String) fileList.get(i).get("name")).compareTo((String) fileList.get(j).get("name")) < 0){
                    fileList.add(j, fileList.get(i));
                    fileList.remove(i --);
                    fileIndex --;
                    break;
                }
            }
            if (j >= fileList.size()){
                fileList.add(fileList.get(i));
                fileList.remove(i --);
                fileIndex --;
            }
        }
        return fileList;
    }

    /**
     * 递归删除文件
     *
     * @param file
     */
    public static void delByRecursive(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                if (f.isDirectory())
                    delByRecursive(f);
                f.delete();
            }
        }
        file.delete();
    }

    /**
     * 判断文件夹下是否有文件正在被编辑
     * @param editorFiles   正在编辑文件列表
     * @param file          递归文件夹
     * @return
     */
    public static boolean isEditor(Collection editorFiles, File file, int len){
        if (editorFiles == null)
            return false;
        if (file.isDirectory()){
            for (File f : file.listFiles()){
                if (f.isDirectory())
                    return isEditor(editorFiles, f, len);
                else if (editorFiles.contains(f.getPath().substring(len)))
                    return true;
            }
        }else if (editorFiles.contains(file.getPath().substring(len)))
            return true;
        return false;
    }

    /**
     * 递归复制文件
     *
     * @param from
     * @param to
     * @param separator
     */
    public static void copyByRecursive(File from, File to, String separator) {
        try {
            Files.copy(from.toPath(), to.toPath());
            if (from.isDirectory()) {
                for (File file : from.listFiles()) {
                    File newTo = new File(to.getPath() + separator + file.getName());
                    if (file.isDirectory()) {
                        copyByRecursive(file, newTo, separator);
                    } else {
                        Files.copy(file.toPath(), newTo.toPath());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 读取文件内容
     * @param path      文件路径
     * @return
     * @throws IOException
     */
    public static String readFileContent(String path) throws IOException {
        String string;
        StringBuffer content = new StringBuffer();
        File f = new File(path);
        if (!f.exists()) {
            throw new IOException("目标不存在");
        }
        BufferedReader file = new BufferedReader(new FileReader(f));
        while ((string = file.readLine()) != null) {
            content.append(string + "\n");
        }
        file.close();
        return content.toString();
    }

    public static BaseEntity writeFileContent(String directory, String content){
        BaseEntity result = new BaseEntity();
        File f = new File(directory);
        try {
            if (!f.exists())
                throw new IOException("目标不存在");
            BufferedWriter file = new BufferedWriter(new FileWriter(f));
            file.write(content);
            file.close();
        } catch (IOException e) {
            result.setResult(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

}
