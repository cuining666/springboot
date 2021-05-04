package com.springboot.chapter9.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传控制器
 */
@Controller
@RequestMapping("/file")
public class FileController {

    /**
     * 进入文件上传页面
     * @return
     */
    @GetMapping("/upload/page")
    public String uploadPage() {
        return "file/page";
    }

    /**
     * 使用HttpServletRequest作为参数文件上传
     * @param request
     * @return
     */
    @PostMapping("/upload/request")
    @ResponseBody
    public Map<String, Object> uploadRequest(HttpServletRequest request) {
        boolean flag = false;
        MultipartHttpServletRequest mreq = null;
        // 强制转换为MultipartHttpServletRequest接口对象
        if (request instanceof MultipartHttpServletRequest) {
            mreq = (MultipartHttpServletRequest) request;
        } else {
            return dealResultMap(flag, "上传文件失败");
        }
        // 获取MultipartFile文件信息
        MultipartFile mf = mreq.getFile("file");
        // 获取源文件名称
        String fileName = mf.getOriginalFilename();
        File file = new File(fileName);
        try {
            // 保存文件
            mf.transferTo(file);
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
            return dealResultMap(flag, "上传文件失败");
        }
        return dealResultMap(flag, "上传文件成功");
    }

    /**
     * 使用Spring MVC的MultipartFile类作为参数
     * @param file
     * @return
     */
    @PostMapping("/upload/multipart")
    @ResponseBody
    public Map<String, Object> uploadMultipartFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        File dest = new File(fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return dealResultMap(false, "上传文件失败");
        }
        return dealResultMap(true, "上传文件成功");
    }

    @PostMapping("/upload/part")
    @ResponseBody
    public Map<String, Object> uploadPart(Part file) {
        // 获取提交文件名称
        String fileName = file.getSubmittedFileName();
        try {
            // 写入文件
            file.write(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return dealResultMap(false, "上传文件失败");
        }
        return dealResultMap(true, "上传文件成功");
    }

    /**
     * 处理上传文件结果
     * @param success
     * @param msg
     * @return
     */
    private Map<String, Object> dealResultMap(boolean success, String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", success);
        map.put("msg", msg);
        return map;
    }
}
