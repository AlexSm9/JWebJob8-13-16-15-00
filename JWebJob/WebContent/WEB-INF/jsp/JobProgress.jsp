<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- \\ -->
<!-- JQuery -->
<%@ include file="/WEB-INF/templates/includeJquery.jsp" %>
<!-- JSTL -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- // -->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Job Progress</title>
<!-- \\ -->
<!-- Javascript and Stylesheet -->
<script type="text/javascript" src="<c:url value="js/JobProgress.js" />" defer></script><!-- defer loads the script after page has loaded -->
<link href="<c:url value="css/JobProgress.css" />" rel="stylesheet">
<!-- // -->
</head>
<body>
<div class="header">
	<div class="headerText">
		
	</div>
</div>

<div class="main">

	<div id="mainContent">
		<h2><span>Processing Job: </span><c:out value="${requestScope.jobName}"></c:out></h2>
		<c:forEach var="contentItem" items="${requestScope.iterableItemList}">
			<tr class="displayTableRow">
				<td>
					<div id="${contentItem.taskId}_Icon" class="taskStatusIcon">
						
					</div>
				</td>
				<td>
					<c:out value="${contentItem.taskName}"></c:out>
					<Input id="${contentItem.taskId}" type="hidden" disabled value="${contentItem.subTaskLevel}">
				</td>
			</tr>
			<br>
			<br>
		</c:forEach>
	</div>
	
</div>

<hr>

<div class="footer">
	<div class="footerText">
		<p>
			Footer Text
		</p>
	</div>
</div>
</body>
</html>