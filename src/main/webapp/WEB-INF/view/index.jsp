<%--
  Created by IntelliJ IDEA.
  User: nikk
  Date: 05.07.2017
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
  <title>TASK</title>

  <!— jQuery —>
  <spring:url value="/webjars/jquery/1.11.1/jquery.js" var="jqueryjs"/>
  <script src="${jqueryjs}"></script>

  <spring:url value="/webjars/bootstrap/3.3.6/js/bootstrap.js" var="bootstrapjs"/>
  <script src="${bootstrapjs}"></script>

  <spring:url value="/webjars/jqgrid/4.7.0/js/minified/jquery.jqGrid.min.js" var="jqgridjs"/>
  <script src="${jqgridjs}"></script>

  <spring:url value="/webjars/jqgrid/4.7.0/js/i18n/grid.locale-ru.js" var="jqgridi18njs"/>
  <script src="${jqgridi18njs}"></script>

  <!— CSS —>
  <spring:url value="/webjars/jqgrid/4.7.0/css/ui.jqgrid.css" var="jqgridcss"/>
  <link href="${jqgridcss}" rel="stylesheet" />

  <spring:url value="/webjars/jquery-ui/1.12.1/jquery-ui.theme.min.css" var="jqueryuicss"/>
  <link href="${jqueryuicss}" rel="stylesheet" />

  <spring:url value="/webjars/bootstrap/3.3.6/css/bootstrap.css" var="bootstrap"/>
  <link href="${bootstrap}" rel="stylesheet" />

</head>
<body>


<div class="col-md-12">
  <h1>DIVISIONS</h1>
  <table id="jqGrid"></table>
  <div id="jqGridPager"></div>

</div>

<div class="col-md-12 alert">
  <c:url value="/uploadFile" var="fileUploadControllerURL" />
  <form action="${fileUploadControllerURL}" method="post"
        enctype="multipart/form-data">
    <table>
      <tr>
        <td><b>File:</b></td>
        <td><input type="file" name="file"></td>
        <td><input type="submit" value="загрузить файл"></td>
      </tr>
    </table>
  </form>

</div>

<div class="ccol-md-12 alert">
  <c:url value="/getxml" var="getxml" />
  <a href="${getxml}">Скачать файл</a>
</div>

<script type="text/javascript">

    function getData() {
        return $.ajax({
            url : 'getDivisionsAjax',
            type: 'GET'
        });
    }

    function handleData(data){

        var obj = $.parseJSON(data);
        var grid = [];

        for(var i=0; i<obj.length; i++){
            grid[i] = {
                "depCode" : obj[i].depCode,
                "depJob" : obj[i].depJob,
                "description" : obj[i].description
            };
        }

        $("#jqGrid").jqGrid({
            data: grid,
            datatype: "local",
            postData: "",
            colModel: [
                { label: 'код отдела', name: 'depCode', sortable: false},
                { label: 'название должности в отделе', name: 'depJob', sortable: true},
                { label: 'комментарий', name: 'description', sortable: true}
            ],
            viewrecords: true,
            height: 260,
            rowNum: 20,
            pager: "#jqGridPager"
        });
    }

    $(function () {
        getData().done(handleData);
    });
</script>


</body>
</html>