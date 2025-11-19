package com.yychainsaw.service;

import com.yychainsaw.pojo.Article;
import com.yychainsaw.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
}
