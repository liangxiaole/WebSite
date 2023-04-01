package com.evan.wj.controller;

import com.evan.wj.pojo.Book;
import com.evan.wj.pojo.Category;
import com.evan.wj.service.BookService;
import com.evan.wj.service.CategoryService;
import com.evan.wj.untils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class LibraryController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;

    @CrossOrigin
    @RequestMapping(value = "/api/books",method = RequestMethod.GET)
    @ResponseBody
    public List<Book> selectAll(){
       return bookService.selectAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/api/categories/{cid}/books",method = RequestMethod.GET)
    @ResponseBody
    public List<Book> selByCategory(@PathVariable("cid")int cid){
        Category category = categoryService.selCategoryById(cid);
        List<Book> books = bookService.selByCategory(category);
        return books;
    }
    @CrossOrigin
    @RequestMapping(value = "/api/search",method = RequestMethod.GET)
    @ResponseBody
    public List<Book>searchResult(String keywords){
        if("".equals(keywords)){
            return bookService.selectAll();
        }else {
            return bookService.Search(keywords);
        }
    }
    @CrossOrigin
    @RequestMapping(value = "/api/books",method = RequestMethod.POST)
    @ResponseBody
    public void addBook(@RequestBody Book book){
         bookService.addBook(book);
    }


    @CrossOrigin
    @PostMapping("api/covers")
    @ResponseBody
    public String coverUpload(MultipartFile file)throws Exception{
        String folder = "D:\\javaproject\\img";
        File imageFolder = new File(folder);
        File f = new File(imageFolder, StringUtils.getRandomString(6) + file.getOriginalFilename()
                .substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String imgURL = "http://localhost:8443/api/file/" + f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
