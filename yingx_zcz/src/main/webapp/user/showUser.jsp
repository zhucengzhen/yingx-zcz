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
            url : "${path}/user/queryAllPage",
            editurl:"${path}/user/edit",
            datatype : "json",
            mtype : "post",
            rowNum : 5,
            rowList : [ 5,10, 20, 30 ],
            pager : '#userPage',
            sortname : 'id',
            sortorder : "desc",
            viewrecords : true,   //是否展示总条数
            caption : "用户信息展示",
            styleUI:"Bootstrap",
            autowidth:true,
            height:"auto",
            colNames : [ 'Id', '手机号', '用户名', '头像', '签名','状态', '注册时间',"微信" ],
            colModel : [
                {name : 'id',width : 55,align : "center"},
                {name : 'phone',editable:true,width : 90,align : "center"},
                {name : 'name',editable:true,width : 100,align : "center"},
                {name : 'headImg',editable:true,width : 120,align : "center",edittype:"file",
                    formatter:function(cellvalue){
                        return "<img src='${path}/upload/headImg/"+cellvalue+"' width='200px' height='100px' >";
                    }
                },
                {name : 'sign',editable:true,width : 80,align : "center"},
                {name : 'status',width : 80,align : "center",
                    formatter:function(cellvalue,options,row){
                        if(cellvalue==1){
                            //1 正常  绿色  冻结
                            return "<button class='btn btn-success' onclick='updateStatus(\""+row.id+"\",\""+row.status+"\")'>冻结</button>";
                        }else{
                            //0 冻结  红色  解冻
                            return "<button class='btn btn-danger' onclick='updateStatus(\""+row.id+"\",\""+row.status+"\")'>解冻</button>";
                        }
                    }
                },
                {name : 'registTime',width : 80,align : "center"},
                {name : 'wechat',width : 150,align : "center"}
            ]
        });

        $("#userTable").jqGrid('navGrid', '#userPage', {update : true,add : true,del : true},
            {
                closeAfterEdit:true  //关闭对话框
                //1.修改数据
                //2.如果修改了文件就把原文件删掉,  回到页面异步上传文件
                // 没有修改文件
            },  //修改后的的额外操作
            {
                closeAfterAdd:true,  //关闭对话框
                //1.添加数据
                //2.在数据添加成功之后执行文件上传  异步上传
                afterSubmit: function(data){  //在提交之后触发的函数

                    //data.responseText=添加数据时返回的id

                    //console.log(data.responseJSON.userId);

                    //文件上传  异步上传
                    $.ajaxFileUpload({
                        url:"${path}/user/fileUpload",
                        type:"post",
                        dataType:"json",
                        data:{"userId":data.responseJSON.userId},
                        fileElementId:"headImg", //fileElementId　　　需要上传的文件域的ID，即<input type="file" >的ID。
                        success:function (data){
                            //文件上传成功返回
                            //alert(data.message);
                            //刷新表格
                            $("#userTable").trigger("reloadGrid");
                        }
                    });
                    return  "hello"; //必须要有一个返回值
                }
            },  //添加后的的额外操作
            {
                afterSubmit:function (data){
                    alert(data.responseJSON.message);
                    //console.log(data.responseJSON.message);
                    return  "hello"; //必须要有一个返回值
                }
            },  //删除后的的额外操作
            {}  //搜索后的的额外操作
        );
    }

    //点击修改状态按钮触发
    function updateStatus(id,status){
        if(status==1){
            $.get("${path}/user/updateStatus",{"id":id,"status":"0"},function (){
                //刷新页面
                $("#userTable").trigger("reloadGrid");
            });
        }else{
            $.get("${path}/user/updateStatus",{"id":id,"status":"1"},function (){
                //刷新页面
                $("#userTable").trigger("reloadGrid");
            });
        }
    }

</script>


<%--创建一个面板--%>
<div class="panel panel-info">

    <%--设置面板的内容--%>
    <div class="panel panel-heading">用户信息</div>

    <!-- 创建标签页 -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">用户信息</a></li>
    </ul><br>

    <%--按钮--%>
    &emsp;&emsp;<button class="btn btn-info">导出用户信息</button>&emsp;&emsp;
    <button class="btn btn-success">导出用户信息</button>&emsp;&emsp;
    <button class="btn btn-warning">导出用户信息</button>&emsp;&emsp;
    <button class="btn btn-danger">导出用户信息</button><br>

    <%--初始化表格--%>
    <table id="userTable" />

    <%--创建分页工具栏--%>
    <div id="userPage" />



</div>