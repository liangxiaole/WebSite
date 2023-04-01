package com.evan.wj.service;

import com.evan.wj.dao.BookDAO;
import com.evan.wj.pojo.Book;
import com.evan.wj.pojo.BookVO;
import com.evan.wj.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookDAO bookDAO;
    public List<Book> selectAll(){
        return bookDAO.selectAll();
    }
    public List<Book>selByCategory(Category category){
        return bookDAO.selByCategory(category);
    }
    public List<Book>Search(String keywords){
        return bookDAO.findAllByTitleLikeOrAuthorLike(keywords,keywords);
    }
    public void addBook(Book book){
        bookDAO.addBook(book);
    }
    public int batchInsert(List<BookVO> books){
       return bookDAO.batchInsert(books);
    }
}
