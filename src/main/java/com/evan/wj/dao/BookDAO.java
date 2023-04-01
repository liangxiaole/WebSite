package com.evan.wj.dao;

import com.evan.wj.pojo.Book;
import com.evan.wj.pojo.BookVO;
import com.evan.wj.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDAO {
    List<Book> selByCategory(Category category);
    List<Book> selectAll();
    List<Book> findAllByTitleLikeOrAuthorLike(String keyword1, String keyword2);
    void addBook(Book book);
    int batchInsert(@Param("books") List<BookVO> books);
}
