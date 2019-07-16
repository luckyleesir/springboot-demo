layui.use(['form', 'layer', 'jquery'], function () {
    let form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    //添加验证规则
    form.verify({
        confirmPassword: function (value, item) {
            if (value.length < 6) {
                return "密码长度不能小于6位";
            }
            if ($("#newPassword").val() !== value) {
                return "两次输入密码不一致，请重新输入！";
            }
        }
    });

    form.on('submit(changePwd)', function (data) {
        $.ajax({
            method: 'post',
            url: '/api/user/changePassword?username=' + layui.data('user')['username'] + '&password=' + $("#newPassword").val() + '&oldPassword=' + $("#ordPassword").val(),
            headers: {Authorization: 'Bearer ' + layui.data('jwtToken')['Bearer']},
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            success: function (res) {
                if (res.code === 200) {
                    layer.msg("修改成功");
                    layui.data('jwtToken', {
                        key: 'Bearer',
                        remove: true
                    });
                    layui.data('user', {
                        key: 'username',
                        remove: true
                    });
                    setTimeout(function () {
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    }, 1000);
                } else {
                    layer.msg(res.msg);
                }
            }
        });
        return false;
    });

});