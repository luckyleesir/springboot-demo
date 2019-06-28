layui.define(['element'], function (exports) {
    "use strict";
    let $ = layui.jquery,
        element = layui.element;

    let Navbar = function () {
        /**
         *  默认配置
         */
        this.config = {
            elem: undefined, //容器
            data: undefined, //数据源
        };
    };

    Navbar.prototype.render = function () {
        let html;
        let $container = this.config.elem;
        html = getHtml(this.config.data);
        $container.html(html);
        element.init();  //初始化页面元素
    };


    function getHtml(data) {
        let ulHtml = '';
        for (let i in data) {
            if (data[i].children != null && data[i].children.length > 0) {
                if (data[i].icon !== undefined && data[i].icon !== '') {
                    ulHtml += '<li class="layui-nav-item"><a href="javascript:;"><i class="layui-icon ' + data[i].icon + '"></i><cite>' + data[i].title + '</cite></a><dl class="layui-nav-child">';
                } else {
                    ulHtml += '<li class="layui-nav-item"><a href="javascript:;"><cite>' + data[i].title + '</cite></a><dl class="layui-nav-child">';
                }
                for (let j in data[i].children) {
                    ulHtml += getChildrenMenu(data[i].children[j], 0);
                }
                ulHtml += '</dl></li>';
            } else {
                if (data[i].icon !== undefined && data[i].icon !== '') {
                    ulHtml += '<li class="layui-nav-item"><a data-url="' + data[i].href + '"><i class="layui-icon ' + data[i].icon + '"></i><cite>' + data[i].title + '</cite></a>';
                } else {
                    ulHtml += '<li class="layui-nav-item"><a data-url="' + data[i].href + '"><cite>' + data[i].title + '</cite></a>';
                }
            }
        }
        return ulHtml;
    }


    // 递归生成子菜单
    function getChildrenMenu(subMenu, num) {
        let childHtml = '';
        num++;
        if (subMenu.children != null && subMenu.children.length > 0) {
            childHtml += '<dd><ul><li class="layui-nav-item layui-nav-itemed"><a style="margin-left:' + 20 * num + 'px" href="javascript:;"><i class="layui-icon ' + subMenu.icon + '"></i><cite>' + subMenu.title + '</cite></a><dl class="layui-nav-child">';
            for (let k in subMenu.children) {
                childHtml += getChildrenMenu(subMenu.children[k], num);
            }
            childHtml += '</dl></li></ul></dd>';
        } else {
            childHtml += '<dd><a style="margin-left:' + 20 * num + 'px" data-url="' + subMenu.href + '"><i class="layui-icon ' + subMenu.icon + '"></i><cite>' + subMenu.title + '</cite></a></dd>';
        }
        return childHtml;
    }

    /**
     * 配置Navbar
     * @param {Object} options
     */
    Navbar.prototype.set = function (options) {
        let _this = this;
        $.extend(true, _this.config, options);
        return _this;
    };


    /**
     * 打开新窗口
     */
        //右侧内容tab操作
    let tabIdIndex = 0;
    Navbar.prototype.tabAdd = function (_this) {
        let that = this;
        if (_this.attr("target") === "_blank") {
            window.open(_this.attr("data-url"));
        } else if (_this.attr("data-url") !== undefined) {
            //已打开的窗口中不存在
            if (that.hasTab(_this.find("cite").text()) === -1 && _this.siblings("dl.layui-nav-child").length === 0) {
                if ($(".layui-tab-title li").length === 10) {
                    layer.msg('只能同时打开' + 10 + '个选项卡哦。不然系统会卡的！');
                    return;
                }
                let title = '';
                tabIdIndex++;
                title += '<cite>' + _this.find("cite").text() + '</cite>';
                title += '<i class="layui-unselect layui-tab-close layui-icon layui-icon-close" data-id="' + tabIdIndex + '"></i>';
                let content = "<iframe class='iframe' src='" + _this.attr("data-url") + "' data-id='" + tabIdIndex + "'></frame>";
                element.tabAdd('addTab', {
                    title: title,
                    content: content,
                    id: new Date().getTime()
                });
                element.tabChange('addTab', that.getLayId(_this.find("cite").text()));
                element.tabChange('addTab', $(this).attr("lay-id")).init();
                navbar.changeRegresh($(this).index());
            }
        }
    };

    //通过title判断tab是否存在
    Navbar.prototype.hasTab = function (title) {
        let tabIndex = -1;
        $(".layui-tab-title li").each(function () {
            if ($(this).find("cite").text() === title) {
                tabIndex = 1;
            }
        });
        return tabIndex;
    };

    let layId;
    //通过title获取lay-id
    Navbar.prototype.getLayId = function (title) {
        $(".layui-tab-title li").each(function () {
            if ($(this).find("cite").text() === title) {
                layId = $(this).attr("lay-id");
            }
        });
        return layId;
    };

    //是否点击窗口切换刷新页面
    Navbar.prototype.changeRegresh = function (index) {
        $(".layui-tab-item").eq(index).find("iframe")[0].contentWindow.location.reload();
    };

    let navbar = new Navbar();
    exports('navbar', function (options) {
        return navbar.set(options);
    });
});
