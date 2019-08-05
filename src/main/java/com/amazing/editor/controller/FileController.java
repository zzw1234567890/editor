package com.amazing.editor.controller;

import com.amazing.editor.entity.BaseEntity;
import com.amazing.editor.entity.file.SwitchFileEntity;
import com.amazing.editor.utils.*;
import com.amazing.editor.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/editor/file")
public class FileController {

    @Autowired
    private HttpSession session;

    /**
     * 获取所有文件
     * @return
     */
    @RequestMapping(value = "getFileList", method = RequestMethod.GET)
    public List<Map<String, Object>> getFileList() {

        String directory = ConfigUtil.getDevPath(session);
        List<Map<String, Object>> fileList = FilesUtil.getFileListFunction(new File(directory), directory.length());
        return fileList;
    }


    /**
     * 创建文件或文件夹
     *
     * @param path 文件名
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public BaseEntity createPath(@RequestParam("path") String path) {
        BaseEntity result = new BaseEntity();
        String address = ConfigUtil.getDevPath(session) + path;
        File file = new File(address);
        try {
            if (path.length() >= 8 && path.substring(1, 8).equals(".config"))
                throw new IOException(HintUtil.notAllowCreateObject);
            if (file.exists())
                throw new IOException(HintUtil.objectExisted);
            if (path.indexOf('.') == -1) {
                file.mkdir();
            } else {
                file.createNewFile();
            }
            LogUtil.write(session, CodeUtil.CREATE, path, "创建: " + path);
        } catch (IOException e) {
            result.setResult(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 删除文件或文件夹
     *
     * @param path 文件名
     */
    @RequestMapping(value = "del", method = RequestMethod.GET)
    public BaseEntity delPath(@RequestParam("path") String path) {
        int websiteId = (Integer) session.getAttribute(FinalUtil.websiteId);
        BaseEntity result = new BaseEntity();
        String address = ConfigUtil.getDevPath(session);
        File file = new File(address + path);
        if (!file.exists()){
            result.setResult(false);
            result.setErrorMsg(HintUtil.objectNotFound);
        }else if ((path.length() >= 8 && path.substring(1, 8).equals(".config")) ||
                FilesUtil.isEditor(WebSocketServer.getAllEditorFiles(websiteId).values(), file, address.length())){
            result.setResult(false);
            result.setErrorMsg(HintUtil.objectEditing);
        }else {
            FilesUtil.delByRecursive(file);
            LogUtil.write(session,CodeUtil.DEL, path, "删除: " + path);
        }
        return result;
    }

    /**
     * 获取文件内容
     * @param newPath   要打开的新文件
     * @return
     */
    @RequestMapping(value = "getFileContent", method = RequestMethod.GET)
    public BaseEntity getFileContent(@RequestParam("path") String newPath){
        BaseEntity result = new BaseEntity();
        int userId = (Integer) session.getAttribute(FinalUtil.userId);
        int websiteId = (Integer) session.getAttribute(FinalUtil.websiteId);
        String address = ConfigUtil.getDevPath(session) + newPath;
        Map<Integer, String> editorFiles = WebSocketServer.getAllEditorFiles(websiteId);
        if (newPath == null) {
            editorFiles.remove(userId);
        } else if (!new File(address).exists()){
            result.setResult(false);
            result.setErrorMsg(HintUtil.objectNotFound);
        } else if (editorFiles.values().contains(newPath)){
            result.setResult(false);
            result.setErrorMsg(HintUtil.objectEditing);
        } else {
            SwitchFileEntity data = new SwitchFileEntity();
            data.setNewPath(newPath);
            data.setOldPath(editorFiles.get(userId));
            data.setOperator(DaoUtil.getUserDao().findById(userId));
            editorFiles.put(userId, newPath);
            WebSocketServer.sendMessageToWebsiteUsers(websiteId,data);
            try {
                result.setContent(FilesUtil.readFileContent(address));
            } catch (IOException e) {
                result.setResult(false);
                result.setErrorMsg(e.getMessage());
            }
        }
        return result;
    }

    /**
     * 更新文件
     *
     * @param path    文件名
     * @param content 内容
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public BaseEntity update(@RequestParam("path") String path, @RequestParam("content") String content) {
        String address = ConfigUtil.getDevPath(session) + path;
        BaseEntity result = FilesUtil.writeFileContent(address, content);
        if (result.isResult()){
            LogUtil.write(session,CodeUtil.UPDATE, path, "更新: " + path);
        }
        return result;
    }

    /**
     * 文件重命名、移动
     *
     * @param oldPath 要重命名的文件名
     * @param newPath 重命名为
     * @return
     */
    @RequestMapping(value = "rename", method = RequestMethod.GET)
    public BaseEntity renamePath(@RequestParam("oldPath") String oldPath, @RequestParam("newPath") String newPath) {
        BaseEntity result = new BaseEntity();
        String address = ConfigUtil.getDevPath(session);
        File oldFile = new File(address + oldPath);
        File newFile = new File(address + newPath);
        if ((oldFile.getName().length() >= 8 && oldFile.getName().substring(1, 8).equals(".config")) || !oldFile.exists() || newFile.exists()) {
            result.setResult(false);
        } else  {
            oldFile.renameTo(newFile);
            String path = oldPath + "|" + newPath;
            String message = "移动: " + oldPath + " --> " + newPath;
            LogUtil.write(session,CodeUtil.MOVE, path, message);
        }
        return result;
    }

    /**
     * 复制文件
     *
     * @param fromPath    被复制的文件
     * @param toPath      复制后的文件
     * @return
     */
    @RequestMapping(value = "copy", method = RequestMethod.GET)
    public BaseEntity copyPath(@RequestParam("fromPath") String fromPath, @RequestParam("toPath") String toPath) {
        BaseEntity result = new BaseEntity();
        String address = ConfigUtil.getDevPath(session);
        File oldFile = new File(address + fromPath);
        File newFile = new File(address + toPath);
        if ((oldFile.getName().length() >= 8 && oldFile.getName().substring(1, 8).equals(".config")) || !oldFile.exists()) {
            result.setResult(false);
        }else {
            FilesUtil.copyByRecursive(oldFile, newFile, ConfigUtil.getSeparator());
            String path = fromPath + "|" + toPath;
            String message = "复制: " + fromPath + " ==> " + toPath;
            LogUtil.write(session,CodeUtil.COPY, path, message);
        }
        return result;
    }

    /**
     * 获取正在编辑的文件
     * @return
     */
    @GetMapping("getEditorFiles")
    public List<String> getEditorFiles(){
        int websiteId = (Integer) session.getAttribute("websiteId");
        return new ArrayList<>(WebSocketServer.getAllEditorFiles(websiteId).values());
    }

}
