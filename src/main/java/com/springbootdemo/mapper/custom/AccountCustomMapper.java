package com.springbootdemo.mapper.custom;

import com.github.pagehelper.Page;
import com.springbootdemo.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountCustomMapper {

    @Select("select * from account")
    Page<Account> list();
}
