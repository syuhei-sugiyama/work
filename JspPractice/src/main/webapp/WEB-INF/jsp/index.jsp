<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP Index Page</title>
<style>
h1 {font-size: 18pt; font-weight: bold; color: gray;}
body {
	font-size: 13pt;
	color: gray;
	margin: 5px 25px;
}
</style>
</head>
<body>
<h1>Index page</h1>
<p>this is sample page.</p>
<%=new SimpleDateFormat("yyyy年 MM月 dd日 hh:mm").format(new Date()) %>
</body>
</html>