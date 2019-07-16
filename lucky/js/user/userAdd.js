layui.config({
    base: '../../ext/'   //navbar组件js所在目录
}).use(['form', 'layer', 'selectN', 'selectM', 'jquery'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        selectN = layui.selectN,
        selectM = layui.selectM,
        $ = layui.jquery;

    var roleId;
    $.ajax({
        method: 'get',
        url: '/api/role/listAll',
        contentType: 'application/json;charset=utf-8',
        headers: {Authorization: 'Bearer ' + layui.data('jwtToken')['Bearer']},
        dataType: 'json',
        success: function (res) {
            if (res.code === 200) {
                //多选标签-所有配置
                roleId = selectM({
                    //元素容器【必填】
                    elem: '#roleTag'
                    //候选数据【必填】
                    , data: res.data
                    //默认值
                    , selected: $("#roleTag").val().split(',')
                    //值的分隔符
                    , delimiter: ','
                    //候选项数据的键名
                    , field: {idName: 'roleId', titleName: 'name'}
                });
            } else {
                layer.msg(res.msg);
            }
        }
    });

    form.on("submit(addUser)", function (data) {
        var req = JSON.stringify({
            username: $("#username").val(),  //登录名
            email: $("#email").val(),  //邮箱
            sex: data.field.sex,  //性别
            status: data.field.status,    //用户状态
            signature: $("#signature").val(),
            roleId: roleId.values.join(',')
        });
        $.ajax({
            method: 'post',
            url: '/api/user/add',
            contentType: 'application/json;charset=utf-8',
            headers: {Authorization: 'Bearer ' + layui.data('jwtToken')['Bearer']},
            dataType: 'json',
            data: req,
            success: function (res) {
                if (res.code === 200) {
                    top.layer.msg("用户添加成功");
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
            signature: $("#signature").val(),
            roleId: roleId.values.join(',')
        });
        var userId = $("#userId").val();
        $.ajax({
            method: 'post',
            url: '/api/user/update/' + userId,
            contentType: 'application/json;charset=utf-8',
            headers: {Authorization: 'Bearer ' + layui.data('jwtToken')['Bearer']},
            dataType: 'json',
            data: req,
            success: function (res) {
                if (res.code === 200) {
                    top.layer.msg("用户修改成功");
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