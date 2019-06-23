layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    form.on("submit(addUser)", function (data) {
        var req = JSON.stringify({
            username: $("#username").val(),  //登录名
            email: $("#email").val(),  //邮箱
            sex: data.field.sex,  //性别
            status: data.field.status,    //用户状态
            signature: $("#signature").val()
        });
        $.ajax({
            method: 'post',
            url: '/api/user/add',
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            data: req,
            success: function (res) {
                if (res.code === 200) {
                    top.layer.msg("用户添加成功！");
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                } else {
                    layer.msg(res.msg);
                }
            }
        });
        return false;
    });

    form.on("submit(updateUser)", function (data) {
        var req = JSON.stringify({
            username: $("#username").val(),  //登录名
            email: $("#email").val(),  //邮箱
            sex: data.field.sex,  //性别
            status: data.field.status,    //用户状态
            signature: $("#signature").val()
        });
        var userId = userId;
        $.ajax({
            method: 'post',
            url: '/api/user/update/' + userId,
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            data: req,
            success: function (res) {
                if (res.code === 200) {
                    top.layer.msg("用户修改成功！");
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                } else {
                    layer.msg(res.msg);
                }
            }
        });
        return false;
    })
});