//注意：导航 依赖 element 模块，否则无法进行功能性操作
layui.config({
    base: '../ext/'   //navbar组件js所在目录
}).use(['element', 'form', 'navbar', 'jquery', 'tabrightmenu', 'layer'], function () {
    var $ = layui.jquery,
        element = layui.element,
        form = layui.form,
        rightmenu_ = layui.tabrightmenu,
        navbar = layui.navbar();

    // 默认方式渲染全部：关闭当前（closethis）、关闭所有但固定（closeallbutpin）、关闭其它但固定（closeothersbutpin）、关闭左侧所有但固定（closeleftbutpin）、关闭右侧所有但固定（closerightbutpin）、刷新当前页（refresh）
    rightmenu_.render({
        container: '#nav1',
        filter: 'main_tab1',
    });

    $.ajax({
        type: "get",
        url: "/api/user/menu/treeList",
        contentType: "application/json; charset=utf-8",
        headers: {Authorization: 'Bearer ' + layui.data('jwtToken')['Bearer']},
        data: 'json',
        success: function (res) {
            if (res.code === 200) {
                navbar.set({
                    elem: $('#nav'),
                    data: res.data
                });
                navbar.render();
            } else {
                layer.msg(res.msg);
            }
        }
    });

    //菜单栏收缩
    $('#leftNav').click(function () {
        if ($(this).hasClass('layui-icon-spread-left')) {
            $(".layui-body").animate({left: '200px'});
            $(".layui-footer").animate({left: '200px'});
            $(".layui-side").animate({width: 'toggle'});
            $('#leftNav').removeClass('layui-icon-spread-left').addClass('layui-icon-shrink-right');
        } else {
            $('#leftNav').removeClass('layui-icon-shrink-right').addClass('layui-icon-spread-left');
            $(".layui-body").animate({left: '0px'});
            $(".layui-footer").animate({left: '0px'});
            $(".layui-side").animate({width: 'toggle'});
        }
    });

    // 添加新窗口
    $("body").on("click", ".layui-nav .layui-nav-item a", function () {
        navbar.tabAdd($(this));
        $(this).parent("li").siblings().removeClass("layui-nav-itemed");
    });

    //删除tab
    $("body").on("click", "li i.layui-tab-close", function () {
        navbar.removeTab($(this));
    });

    //有户名显示
    $("#userName").text(layui.data('user')['username']);

    //注销
    $("#logout").on("click", function () {
        layui.data('jwtToken', {
            key: 'Bearer',
            remove: true
        });
        layui.data('user', {
            key: 'username',
            remove: true
        });
    });

    //修改密码
    $("#changePassword").on("click", function () {
        var index = layui.layer.open({
            title: "修改密码",
            type: 2,
            content: "page/user/changePassword.html",
            area: ['400px', '300px'],
            shade: 0.5
        });
    });


    if (layui.data('jwtToken')['Bearer'] === undefined || layui.data('jwtToken')['Bearer'] === '') {
        setTimeout(function () {
            location.href = "../page/user/login.html";
        }, 2000);
    }

});