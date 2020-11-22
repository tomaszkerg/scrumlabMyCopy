<%@ page import="pl.coderslab.model.Admin" %><%--
  Created by IntelliJ IDEA.
  User: tomek
  Date: 10.11.2020
  Time: 19:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    Admin admin = (Admin)(session.getAttribute("username"));
    if(admin.getSuperAdmin()==1) {
%>
<jsp:include page="menuSuperAdmin.jsp"/>
<%
}else{
%>
<jsp:include page="menuAdmin.jsp"/>
<%
    }
%>
