package com.yychainsaw.mapper;

import com.yychainsaw.anno.State;
import com.yychainsaw.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("INSERT INTO article (title, content, cover_img, state, category_id, create_user, create_time, update_time) " +
            "VALUES (#{title}, #{content}, #{coverImg}, #{state}, #{categoryId}, #{createUser}, now(), now())")
    void add(Article article);


    List<Article> list(Integer userId, Integer categoryId, String state);



    @Update("UPDATE article " +
            "SET title = #{title}, content = #{content}, cover_img = #{coverImg}, " +
            "state = #{state}, category_id = #{categoryId}, update_time = now() " +
            "WHERE id = #{id}")
    void update(Article article);


    @Select("SELECT * " +
            "FROM article " +
            "WHERE id = #{id}")
    Article findById(Integer id);


    @Delete("DELETE FROM article " +
            "WHERE id = #{id}")
    void delete(Integer id);
}
