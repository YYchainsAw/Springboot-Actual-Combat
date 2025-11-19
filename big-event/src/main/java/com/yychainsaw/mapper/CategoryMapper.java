package com.yychainsaw.mapper;

import com.yychainsaw.pojo.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Insert("insert into category(category_name, category_alias, create_user, create_time, update_time) " +
            "values(#{categoryName}, #{categoryAlias}, #{createUser}, now(), now())")
    void add(Category category);

    @Select("SELECT * " +
            "FROM category " +
            "WHERE create_user = #{id} " +
            "ORDER BY id DESC")
    List<Category> list(Integer id);

    @Select("SELECT * " +
            "FROM category " +
            "WHERE id = #{id}")
    Category findById(Integer id);
}
