layui.use(['form', 'layer'], function () {
    let form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    form.on("submit(addRole)", function (data) {
        const req = JSON.stringify({
            name: $("#name").val(),
            description: $("#description").val(),
            status: data.field.status,
        });
        $.ajax({
            method: 'post',
            url: '/api/role/add',
            contentType: 'application/json;charset=utf-8',
            headers:  {Authorization:'Bearer ' + layui.data('jwtToken')['Bearer']},
            dataType: 'json',
            data: req,
            success: function (res) {
                if (res.code === 200) {
                    top.layer.msg("角色添加成功");
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

    form.on("submit(updateRole)", function (data) {
        const req = JSON.stringify({
            name: $("#name").val(),
            description: $("#description").val(),
            status: data.field.status,
        });
        const roleId = $("#roleId").val();
        $.ajax({
            method: 'post',
            url: '/api/role/update/' + roleId,
            contentType: 'application/json;charset=utf-8',
            headers:  {Authorization:'Bearer ' + layui.data('jwtToken')['Bearer']},
            dataType: 'json',
            data: req,
            success: function (res) {
                if (res.code === 200) {
                    top.layer.msg("角色修改成功");
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