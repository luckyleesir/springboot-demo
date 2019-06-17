package com.lucky.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author: lucky
 * @date: 2019/6/17 10:37
 */
public class PageUtil {

    public static void start(Page page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
    }

    public static Page set(int pageNum, int pageSize, String orderBy) {
        Page page = new Page();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setOrderBy(orderBy);
        return page;
    }
}
