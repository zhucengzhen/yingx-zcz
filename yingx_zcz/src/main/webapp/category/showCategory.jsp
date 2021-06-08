<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script>
    $(function(){
        pageInit();
    });


    //设置表格的参数  父表格
    function pageInit(){
        $("#cateTable").jqGrid({
            url : "${path}/category/queryAllPage",
            editurl:"${path}/category/edit",
            datatype : "json",
            height : 190,
            rowNum : 3,
            rowList : [ 3,5, 10, 20, 30 ],
            pager : '#catePage',
            sortname : 'id',
            viewrecords : true,
            sortorder : "desc",
            multiselect : false,
            subGrid : true,
            styleUI:"Bootstrap",
            autowidth:true,
            height:"auto",
            caption : "类别父子表格",
            colNames : [ 'Id', '类别名', '级别', '父类别id'],
            colModel : [
                {name : 'id',index : 'id',  width : 55},
                {name : 'cateName',editable:true,index : 'invdate',width : 90},
                {name : 'levels',index : 'name',width : 100},
                {name : 'parentId',hidden:true,index : 'note',width : 150,sortable : false}
            ],
            /**  当点一级类别左侧的黑色三角号,展示该一级类别下的二级类别时触发
             * subgrid_id:是在创建表数据时创建的div标签的ID   <div id="subgrid_id">
             * subgrid_id:当开启字表格时会在页面中创建一个div来容纳子表格,subgrid_id就是这个div的id
             * row_id:row_id是该行的ID
             * */
            subGridRowExpanded : function(subgrid_id, row_id) {  //
                addSubGrid(subgrid_id, row_id);
            }
        });
        //表格的分页工具栏   父表格
        $("#cateTable").jqGrid('navGrid', '#catePage', {add : true,edit : true,del : true},
            {
                closeAfterEdit:true
            },
            {
                closeAfterAdd:true
            },
            {
                afterSubmit:function(data){

                    if(data.responseJSON.status==104){
                        alert(data.responseJSON.message);
                    }
                    return "ok";
                }
            },
            {}
        );
    }

    function addSubGrid(subgridId, rowId){

        //声明两个变量并赋值
        var tableId=subgridId+"Table";  //table的Id
        var pageId=subgridId+"Page";  //分页工具栏的id

        //向子表格创建的div中添加一个table标签和div
        $("#"+subgridId).html("" +
            "<table id='"+tableId+"'/>" +
            "<div id='"+pageId+"'/>" +
            "")

        //设置初始化子表格的参数
        $("#" + tableId).jqGrid({
            url : "${path}/category/queryByTwoCategory?parentId="+rowId,
            editurl:"${path}/category/edit?parentId="+rowId,
            datatype : "json",
            rowNum : 20,
            pager : "#"+pageId,
            sortname : 'num',
            sortorder : "asc",
            styleUI:"Bootstrap",
            viewrecords:true,
            autowidth:true,
            height:"auto",
            colNames : [ 'Id', '类别名', '级别', '父类别id'],
            colModel : [
                {name : 'id',index : 'id',  width : 55},
                {name : 'cateName',editable:true,index : 'invdate',width : 90},
                {name : 'levels',index : 'name',width : 100},
                {name : 'parentId',index : 'note',width : 150,sortable : false}
            ],
        });

        //设置子表格的分页工具栏
        $("#" + tableId).jqGrid('navGrid',"#" + pageId, {edit : true,add : true,del : true},
            {
                closeAfterEdit:true
            },
            {
                closeAfterAdd:true
            },
            {
                afterSubmit:function(data){

                    if(data.responseJSON.status==104){
                        alert(data.responseJSON.message);
                    }
                    return "ok";
                }
            },
            {}
        );
    }

</script>


<%--创建一个面板--%>
<div class="panel panel-danger">

    <%--设置面板的内容--%>
    <div class="panel panel-heading">类别信息</div>

    <!-- 创建标签页 -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">类别信息</a></li>
    </ul><br>

    <%--初始化表格--%>
    <table id="cateTable" />

    <%--创建分页工具栏--%>
    <div id="catePage" />

</div>