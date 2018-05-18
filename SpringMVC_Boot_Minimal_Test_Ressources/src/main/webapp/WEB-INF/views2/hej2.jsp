<!-- niezbedne aby poprawnie interpretowac linie z c:url -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<html>
<body>
Hej2 jsp !! <br/>
<img alt="img0"  src="<c:url value="files/img0.jpg" />" />
<img alt="img3"  src="<c:url value="resources/img3.jpg" />" />
<img alt="multi img5"  src="<c:url value="multiResources/img5.jpg" />" />
<img alt="multi img6"  src="<c:url value="multiResources/img6.jpg" />" />
<%= new java.util.Date().toString() %>
</body>
</html>