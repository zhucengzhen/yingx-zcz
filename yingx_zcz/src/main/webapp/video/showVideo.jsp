<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script>


    $(function(){
        pageInit();
    });


    function pageInit(){

        //创建表格设置表格参数
        $("#videoTable").jqGrid({
            url : "${path}/video/queryAllPage",
            editurl:"${path}/video/edit",
            datatype : "json",
            mtype : "post",
            rowNum : 5,
            rowList : [ 5,10, 20, 30 ],
            pager : '#videoPage',
            sortname : 'id',
            sortorder : "desc",
            viewrecords : true,   //是否展示总条数
            caption : "用户信息展示",
            styleUI:"Bootstrap",
            autowidth:true,
            height:"auto",
            colNames : [ 'Id', '标题', '描述', '视频','封面', '上传时间','类别id', '分组id',"用户id" ],
            colModel : [
                {name : 'id',width : 55,align : "center"},
                {name : 'title',editable:true,width : 90,align : "center"},
                {name : 'description',editable:true,width : 100,align : "center"},
                {name : 'videoPath',editable:true,width : 120,align : "center",edittype:"file",
                    formatter:function(cellvalue,options,row){
                        return "<video src='"+cellvalue+"' width='200px' height='100px' controls poster='"+row.coverPath+"'  >";
                    }
                },
                {name : 'coverPath',hidden:true,width : 80,align : "center"},
                {name : 'uploadTime',width : 80,align : "center"},
                {name : 'cateId',width : 80,align : "center"},
                {name : 'groupId',width : 150,align : "center"},
                {name : 'userId',width : 150,align : "center"}
            ]
        });

        $("#videoTable").jqGrid('navGrid', '#videoPage', {edit : true,add : true,del : true},
            {
                closeAfterEdit:true,
                beforeShowForm :function(obj){
                    obj.find("#videoPath").attr("disabled",true);//禁用input
                }
            },  //修改后的的额外操作
            {
                closeAfterAdd:true,  //关闭对话框
                //1.添加数据
                //2.在数据添加成功之后执行文件上传  异步上传
                afterSubmit: function(data){  //在提交之后触发的函数


                    //文件上传  异步上传
                    $.ajaxFileUpload({
                        url:"${path}/video/fileUpload",
                        type:"post",
                        dataType:"json",
                        data:{"videoId":data.responseJSON.videoId},
                        fileElementId:"videoPath", //fileElementId　　　需要上传的文件域的ID，即<input type="file" >的ID。
                        success:function (data){
                            //文件上传成功返回
                            //alert(data.message);
                            //刷新表格
                            $("#videoTable").trigger("reloadGrid");
                        }
                    });
                    return  "hello"; //必须要有一个返回值
                }
            },  //添加后的的额外操作
            {
                afterSubmit:function (data){
                    alert(data.responseJSON.message);
                    return  "hello"; //必须要有一个返回值
                }
            },  //删除后的的额外操作
            {}  //搜索后的的额外操作
        );
    }

</script>


<%--创建一个面板--%>
<div class="panel panel-info">

    <%--设置面板的内容--%>
    <div class="panel panel-heading">视频信息</div>

    <!-- 创建标签页 -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">视频信息</a></li>
    </ul><br>

    <%--初始化表格--%>
    <table id="videoTable" />

    <%--创建分页工具栏--%>
    <div id="videoPage" />

</div>