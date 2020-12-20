<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户列表</title>
    <link rel="stylesheet" href="${ctx}/static/plugins/layui/css/layui.css">
    <script type="text/javascript" src="${ctx}/static/js/jquery-3.2.1.min.js"></script>

    <style type="text/css">
        .demoTable {
            float: left;
            margin-right: 15px;
        }
    </style>
</head>
<br>
<form style="height: 40px" method="get">
    <div class="demoTable">
        <div class="layui-inline">
            <input class="layui-input" name="userName" id="userName"
                   autocomplete="off" placeholder="用户名">
        </div>
        <button class="layui-btn bt_search" name="reload">搜索</button>
        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
    </div>
    <div class="demoTable">
        <div class="layui-inline">
            <input class="layui-input" name="userName" id="tel"
                   autocomplete="off" placeholder="电话">
        </div>
        <button class="layui-btn bt_search" name="reload">搜索</button>
        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
    </div>
    <div class="demoTable">
        <div class="layui-inline">
            <input class="layui-input" name="userName" id="email"
                   autocomplete="off" placeholder="邮箱">
        </div>
        <button class="layui-btn bt_search" name="reload">搜索</button>
        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
    </div>
</form>
<div style="height: 10px;"/>
</br>
<div>
    <button class="layui-btn bt_add" data="893px, 550px" data-url="${ctx}/user/toAddPage"><span
            class='iconfont icon-add'></span>&nbsp;新增
    </button>
    <button class="layui-btn layui-btn-warm bt_update" data="893px, 550px" data-url="${ctx}/user/toUpdatePage"><span
            class='iconfont icon-brush'></span>&nbsp;修改
    </button>
    <button class="layui-btn layui-btn-danger bt_delete" data-url="${ctx}/user/delete"><span
            class='iconfont icon-delete'></span>&nbsp;删除
    </button>
    <button class="layui-btn layui-btn-normal bt_setRole" data="893px, 550px" data-url="${ctx}/user/toSetRolePage"><span
            class='iconfont icon-group'></span>&nbsp;分配角色
    </button>
</div>

<table class="layui-hide" id="user" lay-data="{id: 'user'}"></table>
<script type="text/javascript" src="${ctx}/static/js/hp_list.js"></script>
<script>
    layui.use(['table', 'util', 'laypage'], function () {
        var table = layui.table;
        var util = layui.util;
        var laypage = layui.laypage;
        var transfer = layui.transfer;
        table.render({
            elem: '#user',
            url: '${ctx}/user/list',
            parseData: function (res) {
                return {
                    "code": 0,
                    "msg": "success",
                    "count": res.total,
                    "data": res.records
                }
            },
            cellMinWidth: 80,
            loading: true,
            page: true,
            cols: [[
                {type: 'checkbox',width: 90},
                {field: 'id', width: 300, title: 'ID', sort: true},
                {field: 'userName', title: '用户名'},
                {field: 'nickname', title: '昵称'},
                {field: 'tel', title: '电话'},
                {
                    field: 'sex', title: '性别', width: 60,
                    templet: function (data) {
                        if (data.sex == '1') {
                            return '男';
                        } else {
                            return '女';
                        }
                    }
                },
                {field: 'email', title: '邮箱'},
                {
                    field: 'status', title: '状态', width: 60,
                    templet: function (data) {
                        if (data.status == 'on') {
                            return '启用';
                        } else {
                            return '禁用';
                        }
                    }
                },
                {field: 'userImg', title: '头像'},
                {
                    field: 'createTime', title: '创建时间',
                    templet: function (data) {
                        if (data != null) {
                            return util.toDateString(data.createTime, "yyyy-MM-dd HH-mm-ss");
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'updateTime', title: '更新时间',
                    templet: function (data) {
                        if (data != null) {
                            return util.toDateString(data.updateTime, "yyyy-MM-dd HH-mm-ss");
                        } else {
                            return "";
                        }
                    }
                }
            ]],
        });


        function reload() {
            table.reload($('table').attr("id"), {
                where: {
                    userName: $('#userName').val(),
                    tel: $('#tel').val(),
                    email: $('#email').val()
                }
            });
        };

        //触发搜索条件事件
        $('.bt_search').on('click', function (e) {
            // var type = $(this).prop("name");
            // alert(type);
            // active[type] ? active[type].call(this) : '';
            reload();
            return false;
        })

    })


</script>
</body>
</html>
