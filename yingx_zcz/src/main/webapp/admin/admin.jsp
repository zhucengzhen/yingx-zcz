<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script>
    $(function(){
        pageInit();
    });


    function pageInit(){

        //创建表格设置表格参数
        $("#userTable").jqGrid({
            url : "${path}/admin/queryAll",
            editurl:"${path}/admin/edit",
            datatype : "json",
            mtype : "post",
            rowNum : 5,
            rowList : [ 5,10, 20, 30 ],
            pager : '#adminqueryAll',
            sortname : 'id',
            sortorder : "desc",
            viewrecords : true,   //是否展示总条数
            caption : "管理员信息展示",
            styleUI:"Bootstrap",
            autowidth:true,
            height:"auto",
            colNames : [ 'Id', '用户名', '密码', '状态', '级别','盐' ],
            colModel : [
                {name : 'id',width : 55,align : "center"},
                {name : 'username',editable:true,width : 90,align : "center"},
                {name : 'password',editable:true,width : 100,align : "center"},
                {name : 'status',editable:true,width : 80,align : "center"},
                {name : 'level',width : 80,align : "center"},
                {name : 'salt',width : 80,align : "center"}

            ]
        });

        $("#userTable").jqGrid('navGrid', '#adminqueryAll', {edit : true,add : true,del : true},
            {
                closeAfterEdit:true  //关闭对话框
            },  //修改后的的额外操作
            {
                closeAfterAdd:true  //关闭对话框
            },  //添加后的的额外操作
            {},  //删除后的的额外操作
            {}  //搜索后的的额外操作
        );
    }


</script>


<%--创建一个面板--%>
<div class="panel panel-info">

    <%--设置面板的内容--%>
    <div class="panel panel-heading">用户信息</div>

    <!-- 创建标签页 -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">管理员信息</a></li>
    </ul><br>

    <%--初始化表格--%>
    <table id="userTable" />

        <div id="adminqueryAll" />


</div>