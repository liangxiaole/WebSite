package com.evan.wj.service;

import com.evan.wj.dao.CategoryDAO;
import com.evan.wj.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoryService {
    @Autowired
    private CategoryDAO categoryDAO;

    public Category selCategoryById(int id){
       return categoryDAO.selCategoryById(id);
    }



}
