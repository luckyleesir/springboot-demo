layui.use(['form', 'layer', 'table', 'laytpl'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //角色列表
    var tableIns = table.render({
        elem: '#roleList',
        url: '/api/role/list',
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：page
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },
        parseData: function (res) { //res 即为原始返回的数据
            return {
                "code": res.code, //解析接口状态
                "msg": res.msg, //解析提示文本
                "count": res.data.total, //解析数据长度
                "data": res.data.list //解析数据列表
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
        id: "roleListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {title: '操作', width: 200, templet: '#roleListBar', fixed: "right", align: "center"},
            {field: 'name', title: '角色名', minWidth: 100, align: "center"},
            {field: 'description', title: '角色描述', align: 'center'},
            {
                field: 'status', title: '角色状态', align: 'center', templet: function (d) {
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
        table.reload("roleListTable", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                name: $("#searchVal").val()  //搜索的关键字
            }
        })
    });

    //添加角色
    $("#addRoleBtn").click(function () {
        addRole();
    });

    function addRole(edit) {
        var index = layui.layer.open({
            title: "添加角色",
            type: 2,
            content: "roleAdd.html",
            maxmin: true,
            area: ['600px', '500px'],
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                if (edit) {
                    layui.layer.title('编辑角色', index);
                    body.find("#roleId").val(edit.roleId);
                    body.find("#name").val(edit.name);
                    body.find("#description").val(edit.description);
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
        var checkStatus = table.checkStatus('roleListTable'),
            data = checkStatus.data,
            roleIds = [];
        if (data.length > 0) {
            for (let i in data) {
                roleIds.push(data[i].roleId);
            }
            roleIds = JSON.stringify(roleIds);
            layer.confirm('确定删除选中的角色？', {icon: 3, title: '提示信息'}, function (index) {
                $.ajax({
                    type: 'post',
                    url: '/api/role/delete',
                    data: roleIds,
                    contentType: 'application/json;charset=utf-8',
                    success: function (res) {
                        layer.msg(res.msg);
                        tableIns.reload();
                        layer.close(index);
                    }
                })
            })
        } else {
            layer.msg("请选择需要删除的角色");
        }
    });

    //列表操作
    table.on('tool(roleList)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === 'edit') { //编辑
            addRole(data);
        } else if (layEvent === 'del') { //删除
            layer.confirm('确定删除此角色？', {icon: 3, title: '提示信息'}, function (index) {
                var roleIds = [];
                roleIds.push(data.roleId);
                roleIds = JSON.stringify(roleIds);
                $.ajax({
                    type: 'post',
                    url: '/api/role/delete',
                    data: roleIds,
                    contentType: 'application/json;charset=utf-8',
                    success: function (res) {
                        layer.msg(res.msg);
                        tableIns.reload();
                        layer.close(index);
                    }
                })
            });
        } else if (layEvent === 'assignPermission') {
            assignPermission(data);
        }
    });


    function assignPermission(data) {
        var index = layui.layer.open({
            title: "分配权限",
            type: 2,
            content: "assignPermission.html",
            maxmin: true,
            area: ['600px', '500px'],
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                body.find("#roleId").val(data.roleId);
                body.find("#name").val(data.name);
                form.render();
            }
        });
    }

});
