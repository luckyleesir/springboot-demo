layui.use(['form', 'layer'], function () {
    let form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    form.on("submit(addPermission)", function (data) {
        const req = JSON.stringify({
            pid: $("#pid").val(),
            name: $("#name").val(),
            value: $("#value").val(),
            url: $("#url").val(),
            icon: $("#icon").val(),
            description: $("#description").val(),
            type: data.field.type,
            status: data.field.status
        });
        $.ajax({
            method: 'post',
            url: '/api/permission/add',
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            data: req,
            success: function (res) {
                if (res.code === 200) {
                    top.layer.msg("权限添加成功");
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

    form.on("submit(updatePermission)", function (data) {
        const req = JSON.stringify({
            pid: $("#pid").val(),
            name: $("#name").val(),
            value: $("#value").val(),
            url: $("#url").val(),
            icon: $("#icon").val(),
            description: $("#description").val(),
            type: data.field.type,
            status: data.field.status
        });
        const permissionId = $("#permissionId").val();
        $.ajax({
            method: 'post',
            url: '/api/permission/update/' + permissionId,
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            data: req,
            success: function (res) {
                if (res.code === 200) {
                    top.layer.msg("权限修改成功");
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