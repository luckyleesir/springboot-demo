layui.use(['form', 'layer', 'jquery'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer;
    $ = layui.jquery;

    $(".loginBody .seraph").click(function () {
        layer.msg("这只是做个样式，至于功能，你见过哪个后台能这样登录的？还是老老实实的找管理员去注册吧", {
            time: 5000
        });
    })

    //登录按钮
    form.on("submit(login)", function (data) {
        $.ajax({
            url: '/api/user/login',
            method: 'post',
            data: data.field,
            success: function (res) {
                if (res.code === 200) {
                    //请求成功后，写入token
                    layui.data('jwtToken', {
                        key: 'Bearer',
                        value: res.data.token
                    });
                    layui.data('user', {
                        key: 'username',
                        value: $("#userName").val()
                    });
                    location.href = "../../index.html";
                } else {
                    layer.msg(res.msg, {
                        time: 1500
                    });
                }
            },
            error:function () {
                layer.msg("网络连接中断");
            }
        });
        return false;
    });

    //表单输入效果
    $(".loginBody .input-item").click(function (e) {
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".loginBody .layui-form-item .layui-input").focus(function () {
        $(this).parent().addClass("layui-input-focus");
    })
    $(".loginBody .layui-form-item .layui-input").blur(function () {
        $(this).parent().removeClass("layui-input-focus");
        if ($(this).val() != '') {
            $(this).parent().addClass("layui-input-active");
        } else {
            $(this).parent().removeClass("layui-input-active");
        }
    })
});
