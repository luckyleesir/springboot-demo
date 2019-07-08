layui.config({
    base: '../../ext/'   //navbar组件js所在目录
}).use(['form', 'layer', 'authtree'], function () {
    let form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        authtree = layui.authtree;

    const roleId = $("#roleId").val();
    $.ajax({
        method: 'get',
        url: '/api/role/permissionTree/' + roleId,
        contentType: 'application/json;charset=utf-8',
        headers:  {Authorization:'Bearer ' + layui.data('jwtToken')['Bearer']},
        dataType: 'json',
        success: function (res) {
            if (res.code === 200) {
                authtree.render('#LAY-auth-tree-index', res.data, {
                    inputname: 'authids[]',
                    layfilter: 'lay-check-auth',
                    // openall: true,
                    autowidth: true,
                });

            } else {
                layer.msg(res.msg);
            }
        }
    });

    // 使用 authtree.on() 不会有冒泡延迟
    authtree.on('change(lay-check-auth)', function (data) {
        //console.log('监听 authtree 触发事件数据', data);
        // 获取所有节点
        var all = authtree.getAll('#LAY-auth-tree-index');
        //console.log('all', all);
        // 获取所有已选中节点
        var checked = authtree.getChecked('#LAY-auth-tree-index');
        //console.log('checked', checked);
        // 获取所有未选中节点
        var notchecked = authtree.getNotChecked('#LAY-auth-tree-index');
        //console.log('notchecked', notchecked);
        // 获取选中的叶子节点
        var leaf = authtree.getLeaf('#LAY-auth-tree-index');
        //console.log('leaf', leaf);
        // 获取最新选中
        var lastChecked = authtree.getLastChecked('#LAY-auth-tree-index');
        //console.log('lastChecked', lastChecked);
        // 获取最新取消
        var lastNotChecked = authtree.getLastNotChecked('#LAY-auth-tree-index');
        //console.log('lastNotChecked', lastNotChecked);
    });

    form.on('submit(LAY-auth-tree-submit)', function () {
        let checked = authtree.getChecked('#LAY-auth-tree-index');
        let permissionIds = JSON.stringify(checked);
        $.ajax({
            method: 'post',
            url: '/api/role/permission/update/' + roleId,
            headers:  {Authorization:'Bearer ' + layui.data('jwtToken')['Bearer']},
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            data: permissionIds,
            success: function (res) {
                if (res.code === 200) {
                    layer.msg("分配成功");
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

});