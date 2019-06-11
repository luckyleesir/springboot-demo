package com.lucky.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lucky.mapper.auto.AccountMapper;
import com.lucky.mapper.custom.AccountCustomMapper;
import com.lucky.model.Account;
import com.lucky.model.AccountExample;
import com.lucky.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 账户管理
 *
 * @author lucky
 */
@Api(value = "账户管理")
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    private AccountMapper accountMapper;
    @Resource
    private AccountCustomMapper accountCustomMapper;
    @Resource
    private AccountService accountService;

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体account", required = true, dataType = "Account")
    @GetMapping("/list")
    public PageInfo<Account> listAccount(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andUserIdGreaterThan(150L);
        List<Account> accounts = accountMapper.selectByExample(accountExample);
        PageInfo<Account> pageInfo = new PageInfo<>(accounts);
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

    @PostMapping("/add")
    public void add(){
        for(int i=0;i<100;i++){
            Account account = new Account();
            account.setUserName(i+"");
            account.setName(i+"");
            account.setAge(i);
            account.setNick(i+"");
            account.setPassword(i+"");
            account.setSex(i+"");
            accountMapper.insertSelective(account);
        }
    }
}
