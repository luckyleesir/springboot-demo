package com.springbootdemo.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springbootdemo.mapper.auto.AccountMapper;
import com.springbootdemo.mapper.custom.AccountCustomMapper;
import com.springbootdemo.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户管理
 *
 * @author lucky
 */
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountCustomMapper accountCustomMapper;


    @GetMapping("/list")
    public PageInfo<Account> listAccount(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Account> accountList = accountCustomMapper.list();
        PageInfo<Account> pageInfo = new PageInfo<>(accountList);
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
        return pageInfo;
    }

    @GetMapping("/get")
    public Account get(Long userId) {
        return accountMapper.selectByPrimaryKey(userId);
    }

}
