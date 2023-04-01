package com.evan.wj.controller;


import com.alibaba.excel.EasyExcel;
import com.evan.wj.pojo.Book;
import com.evan.wj.pojo.BookVO;
import com.evan.wj.service.BookService;
import com.evan.wj.untils.DemoDataListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件上传解析入库
 * 要创建excel一行一行的回调监听器  ：DemoDataListener
 */
@RestController
public class DemoController {

    @Autowired
    private BookService bookService;

    /**
     * 文件上传
     * <p>
     * 1. 创建excel对应的实体对象
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器
     * <p>
     * 3. 直接读即可
     */
    @PostMapping("upload")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), BookVO.class, new DemoDataListener(bookService)).sheet().doRead();
        return "success";
    }

    /**
     * excel 导出
     * @param response
     * @throws IOException
     */
    @GetMapping("/exportExcel")
    public void download(@ApiIgnore HttpServletResponse response
    ) throws IOException {

//        List<Book> books = bookRepo.list();
        List<Book> books = bookService.selectAll();
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("书单", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Book.class).sheet("书单").doWrite(books);
    }

}
