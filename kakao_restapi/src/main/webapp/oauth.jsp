<%@page import="com.fasterxml.jackson.databind.JsonNode"%>
<%@page import="org.kakao.api.kakao_restapi"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>oauth</title>
</head>
<body>
	 나와라~~
	${sessionScope.token}
</body>
</html>