//注意：导航 依赖 element 模块，否则无法进行功能性操作
layui.config({
    base: '../ext/'   //navbar组件js所在目录
}).use(['element', 'form', 'navbar', 'jquery'], function () {
    var $ = layui.jquery,
        element = layui.element,
        form = layui.form,
        navbar = layui.navbar();
    $.ajax({
        type: "get",
        url: "/api/user/menu/treeList",
        contentType: "application/json; charset=utf-8",
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


});