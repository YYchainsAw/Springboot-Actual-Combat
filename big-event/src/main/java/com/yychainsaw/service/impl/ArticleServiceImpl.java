package com.yychainsaw.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yychainsaw.mapper.ArticleMapper;
import com.yychainsaw.pojo.Article;
import com.yychainsaw.pojo.PageBean;
import com.yychainsaw.service.ArticleService;
import com.yychainsaw.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        article.setCreateUser(id);
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        PageBean<Article> pb = new PageBean<>();

        PageHelper.startPage(pageNum, pageSize);

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        List<Article> as = articleMapper.list(userId, categoryId, state);
        Page<Article> page = (Page<Article>) as;

        pb.setTotal(page.getTotal());
        pb.setItems(page.getResult());

        return pb;
    }

    @Override
    public void update(Article article) {
        articleMapper.update(article);
    }

    @Override
    public Article findById(Integer id) {
        Article ar = articleMapper.findById(id);
        return ar;
    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }
}
