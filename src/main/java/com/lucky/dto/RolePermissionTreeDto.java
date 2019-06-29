package com.lucky.dto;

import lombok.Data;

import java.util.List;

/**
 * 权限树结构，前端结构体
 *
 * @author: lucky
 * @date: 2019/6/29 14:05
 */
@Data
public class RolePermissionTreeDto {

    /**
     * 参考权限树插件：https://github.com/wangerzi/layui-authtree
     * <p>
     *     [
     *        {"name": "用户管理", "value": "xsgl", "checked": true, "list": [
     *            {"name": "用户组", "value": "xsgl-basic", "checked": true, "list": [
     *                {"name": "本站用户", "value": "xsgl-basic-xsxm", "checked": true, "list": [
     *                    {"name": "用户列表", "value": "xsgl-basic-xsxm-readonly", "checked": true},
     *                    {"name": "新增用户", "value": "xsgl-basic-xsxm-editable", "checked": false}
     *     			]},
     *                 {"name": "第三方用户", "value": "xsgl-basic-xsxm", "checked": true, "list": [
     *                     {"name": "用户列表", "value": "xsgl-basic-xsxm-readonly", "checked": true}
     *                 ]}
     *     		]}
     *     	]},
     *        {"name": "用户组管理", "value": "sbgl", "checked": true, "list": [
     *            {"name": "角色管理", "value": "sbgl-sbsjlb", "checked": true, "list":[
     *                {"name": "添加角色", "value": "sbgl-sbsjlb-dj", "checked": true},
     *                {"name": "角色列表", "value": "sbgl-sbsjlb-yl", "checked": false}
     *     		]},
     *             {"name": "管理员管理", "value": "sbgl-sbsjlb", "checked": true, "list":[
     *                 {"name": "添加管理员", "value": "sbgl-sbsjlb-dj", "checked": true},
     *                 {"name": "管理员列表", "value": "sbgl-sbsjlb-yl", "checked": false}
     *             ]}
     *     	]}
     *     ]
     *  name是节点名称，value是需要上传的值（如：菜单id），checked控制是否默认选中，list内部是子节点。
     * </p>
     */

    /**
     * 节点名称
     */
    private String name;
    /**
     * 节点id
     */
    private String value;
    /**
     * 是否被选中
     */
    private boolean checked;
    /**
     * 子节点
     */
    private List<RolePermissionTreeDto> list;

}
