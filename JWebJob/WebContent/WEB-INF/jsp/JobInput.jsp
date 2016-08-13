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
<title>Job Input</title>
<!-- \\ -->
<!-- Javascript and Stylesheet -->
<script type="text/javascript" src="<c:url value="js/JobInput.js" />" defer></script><!-- defer loads the script after page has loaded -->
<link href="<c:url value="css/JobInput.css" />" rel="stylesheet">
<!-- // -->
</head>
<body>
<div class="header">
	<div class="headerText">
		
	</div>
</div>

<div class="main">

	<div id="mainContent">
		<c:forEach var="contentItem" items="${requestScope.iterableItemList}">
			<tr class="displayTableRow">
				<td><c:out value="${contentItem.name}"></c:out></td>
			</tr>
			<br>
		</c:forEach>
	</div>
	
	<br>
	
	<div id="mainInputForm">
		<form id="sumbitForm1" method="post" action="JobInputHandler">
			<div id="inputsDiv">
				<div class="interactiveContent">
					<div id="textInput1Div">
						<input type="text" name="jobNameInput" id="jobNameInput" class="simpleTextInput" value="Enter Job Name">
					</div>
				</div>
				<div class="interactiveContent">
					<div id="button1Div">
						<button type="button" id="button1" class="simpleButton">Example Button</button>
						<input id="button1Clicked" name="button1Clicked" type=hidden value="false">
					</div>
				</div>
				<div class="interactiveContent">
					<div id="textInput1Div">
						<input type="text" name="textInput1" id="textInput1" class="simpleTextInput" value="Example Input Text">
					</div>
				</div>
				<div class="interactiveContent">
					<div id="checkboxDiv">
						<input class="simpleCheckbox" type="checkbox" name="checkbox1" value="checked1"> Box1
						<br>
	 					<input class="simpleCheckbox" type="checkbox" name="checkbox2" value="checked2"> Box2 
					</div>
				</div>
				<div class="interactiveContent">
					<div id="button2Div">
						<button type="button" id="button2" class="simpleButton">Click to show Dialog</button>
						<input id="dialogSelection" name="dialogSelection" type="text" value=""> <!-- change type to hidden later -->
					</div>
				</div>
				<div class="interactiveContent">
					<div id="submitdiv">
						<button id= submitButton class="simpleButton" type="submit" value="Submit">
							Submit this Form!
						</button>
					</div>
				</div>
			</div>
		</form>
	</div>
	
	<div id="selectionDialog"><!-- Works by javascript, setting a hidden value of the submit form to the selection -->
		<div>
			<c:forEach var="contentItem" items="${requestScope.iterableItemList}">
			<tr class="displayTableRow">
				<td><button type="button" class="contentItemSelectButton simpleButton" value="${contentItem.name}">Select</button>
				<td><c:out value="${contentItem.name}"></c:out></td>
				<br>
			</tr>
			</c:forEach>
		</div>
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