layui.use(['form', 'layer', 'table', 'laytpl'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //用户列表
    var tableIns = table.render({
        elem: '#userList',
        url: '/api/user/list',
        headers:  {Authorization:'Bearer ' + layui.data('jwtToken')['Bearer']},
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
        id: "userListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {title: '操作', width: 180, templet: '#userListBar', fixed: "left", align: "center"},
            {field: 'username', title: '用户名', minWidth: 100, align: "center"},
            {field: 'name', title: '姓名', minWidth: 100, align: "center"},
            {field: 'nick', title: '昵称', minWidth: 100, align: "center"},
            {field: 'sex', title: '性别', align: 'center'},
            {field: 'age', title: '年龄', align: 'center'},
            {
                field: 'status', title: '用户状态', align: 'center', templet: function (d) {
                    return d.status === 1 ? "启用" : "禁用";
                }
            },
            {field: 'createTime', title: '创建时间', align: 'center', minWidth: 150, sort: true},
            {
                field: 'email', title: '用户邮箱', minWidth: 200, align: 'center', templet: function (d) {
                    return '<a class="layui-blue" href="mailto:' + d.email + '">' + d.email + '</a>';
                }
            },
            {field: 'signature', title: '个性签名', align: 'center'},
        ]],
        size: 'sm',
        toolbar: true
    });

    //搜索
    $("#searchBtn").on("click", function () {
        table.reload("userListTable", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                name: $("#searchVal").val()  //搜索的关键字
            }
        })
    });

    //添加用户
    $("#addUserBtn").click(function () {
        addUser();
    });

    function addUser(edit) {
        var index = layui.layer.open({
            title: "添加用户",
            type: 2,
            content: "userAdd.html",
            maxmin: true,
            area: ['600px', '500px'],
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                if (edit) {
                    layui.layer.title('编辑用户', index);
                    body.find("#userId").val(edit.userId);  //登录名
                    body.find("#username").val(edit.username).attr('readonly');  //登录名
                    body.find("#email").val(edit.email);  //邮箱
                    body.find("#sex input[value=" + edit.sex + "]").prop("checked", "checked");  //性别
                    body.find("#status").val(edit.status);    //用户状态
                    body.find("#signature").text(edit.signature);    //个性签名
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
        var checkStatus = table.checkStatus('userListTable'),
            data = checkStatus.data,
            userIds = [];
        if (data.length > 0) {
            for (let i in data) {
                userIds.push(data[i].userId);
            }
            userIds = JSON.stringify(userIds);
            layer.confirm('确定删除选中的用户？', {icon: 3, title: '提示信息'}, function (index) {
                $.ajax({
                    type: 'post',
                    url: '/api/user/delete',
                    data: userIds,
                    contentType: 'application/json;charset=utf-8',
                    headers:  {Authorization:'Bearer ' + layui.data('jwtToken')['Bearer']},
                    success: function (res) {
                        layer.msg(res.msg);
                        tableIns.reload();
                        layer.close(index);
                    }
                })
            })
        } else {
            layer.msg("请选择需要删除的用户");
        }
    });

    //列表操作
    table.on('tool(userList)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === 'edit') { //编辑
            addUser(data);
        } else if (layEvent === 'assignRole') { //授权

        } else if (layEvent === 'del') { //删除
            layer.confirm('确定删除此用户？', {icon: 3, title: '提示信息'}, function (index) {
                var userIds = [];
                userIds.push(data.userId);
                userIds = JSON.stringify(userIds);
                $.ajax({
                    type: 'post',
                    url: '/api/user/delete',
                    data: userIds,
                    contentType: 'application/json;charset=utf-8',
                    headers:  {Authorization:'Bearer ' + layui.data('jwtToken')['Bearer']},
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
