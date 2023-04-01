package com.evan.wj.dao;

import com.evan.wj.pojo.Category;
import org.springframework.stereotype.Repository;



@Repository
public interface CategoryDAO {
    Category selCategoryById(int id);
}
