layui.use(['form', 'layer', 'table', 'laytpl'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //用户列表
    var tableIns = table.render({
        elem: '#permissionList',
        url: '/api/permission/list',
        headers: {Authorization: 'Bearer ' + layui.data('jwtToken')['Bearer']},
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：page
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },
        parseData: function (res) { //res 即为原始返回的数据
            return {
                "code": res.code, //解析接口状态
                "msg": res.msg, //解析提示文本
                "count": res.data.total, //解析数据长度
                "data": res.data.records //解析数据列表
            };
        },
        response: {
            statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
        },
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [10, 20, 50, 100],
        limit: 10,
        id: "permissionListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {title: '操作', width: 120, templet: '#permissionListBar', fixed: "left", align: "center"},
            {field: 'permissionId', title: '权限id', minWidth: 100, align: "center"},
            {field: 'pid', title: '权限父id', minWidth: 100, align: "center"},
            {field: 'name', title: '权限名称', minWidth: 100, align: "center"},
            {field: 'value', title: '权限值', align: 'center'},
            {field: 'url', title: '路径', align: 'center'},
            {
                field: 'icon', title: '图标', align: 'center', templet: function (d) {
                    return '<i class = "layui-icon ' + d.icon + '">';
                }
            },
            {field: 'description', title: '权限描述', align: 'center'},
            {
                field: 'type', title: '权限类型', align: 'center', templet: function (d) {
                    return d.type === 1 ? "菜单" : "按钮";
                }
            },
            {
                field: 'status', title: '状态', align: 'center', templet: function (d) {
                    return d.status === 1 ? "启用" : "禁用";
                }
            },
            {field: 'createTime', title: '创建时间', align: 'center', minWidth: 150, sort: true},
            {field: 'updateTime', title: '更新时间', align: 'center', minWidth: 150, sort: true}
        ]],
        size: 'sm',
        toolbar: true
    });

    //搜索
    $("#searchBtn").on("click", function () {
        table.reload("permissionListTable", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                name: $("#searchVal").val()  //搜索的关键字
            }
        })
    });

    //添加用户
    $("#addPermissionBtn").click(function () {
        addPermission();
    });

    function addPermission(edit) {
        var index = layui.layer.open({
            title: "添加权限",
            type: 2,
            content: "permissionAdd.html",
            maxmin: true,
            area: ['600px', '600px'],
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                if (edit) {
                    layui.layer.title('编辑权限', index);
                    body.find("#permissionId").val(edit.permissionId);
                    body.find("#pid").val(edit.pid);
                    body.find("#name").val(edit.name);
                    body.find("#value").val(edit.value);
                    body.find("#url").val(edit.url);
                    body.find("#icon").val(edit.icon);
                    body.find("#description").val(edit.description);
                    body.find("#type input[value=" + edit.type + "]").prop("checked", "checked");
                    body.find("#status").val(edit.status);
                    body.find("#add").remove();
                    form.render();
                } else {
                    body.find("#edit").remove();
                }
            }
        });
    }

    //批量删除
    $("#delAllBtn").click(function () {
        var checkStatus = table.checkStatus('permissionListTable'),
            data = checkStatus.data,
            permissionIds = [];
        if (data.length > 0) {
            for (let i in data) {
                permissionIds.push(data[i].permissionId);
            }
            permissionIds = JSON.stringify(permissionIds);
            layer.confirm('确定删除选中的权限？', {icon: 3, title: '提示信息'}, function (index) {
                $.ajax({
                    type: 'post',
                    url: '/api/permission/delete',
                    headers: {Authorization: 'Bearer ' + layui.data('jwtToken')['Bearer']},
                    data: permissionIds,
                    contentType: 'application/json;charset=utf-8',
                    success: function (res) {
                        layer.msg(res.msg);
                        tableIns.reload();
                        layer.close(index);
                    }
                })
            })
        } else {
            layer.msg("请选择需要删除的权限");
        }
    });

    //列表操作
    table.on('tool(permissionList)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === 'edit') { //编辑
            addPermission(data);
        } else if (layEvent === 'del') { //删除
            layer.confirm('确定删除此权限？', {icon: 3, title: '提示信息'}, function (index) {
                var permissionIds = [];
                permissionIds.push(data.permissionId);
                permissionIds = JSON.stringify(permissionIds);
                $.ajax({
                    type: 'post',
                    url: '/api/permission/delete',
                    headers: {Authorization: 'Bearer ' + layui.data('jwtToken')['Bearer']},
                    data: permissionIds,
                    contentType: 'application/json;charset=utf-8',
                    success: function (res) {
                        layer.msg(res.msg);
                        tableIns.reload();
                        layer.close(index);
                    }
                })
            });
        }
    });

});
