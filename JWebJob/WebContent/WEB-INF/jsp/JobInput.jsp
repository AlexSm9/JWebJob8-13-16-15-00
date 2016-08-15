0<%@ page language="java" contentType="text/html; charset=UTF-8"
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
<script type="text/javascript" src="<c:url value="js/JobInput.js" />"></script>
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
	
	<div id="fileUploadDiv">
		<div id="fileUploadErrorDisplayDiv">
			<div class="ui-widget" id="errorFile">
    			<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;"> 
        		<p>
            		<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
            		<strong>Upload Error</strong>
            		<span id="errorFileText">
        	    	
        	    	</span>
        		</p>
    			</div>
			</div>
		</div>
		<form id="upload" method="post" action="FileUploadHandler" enctype="multipart/form-data">
			<div id="drop">				
				<a>✚ Select File to Upload</a>
				<input type="file" name="upl"/><!-- Removed Multiple -->
			</div>
			<div class="ui-corner-all  ui-widget-content" id="content" style="display:none; padding:15px 15px 0px 10px">
				<div style="float:left; width:100px">X</div>
				<div class="fileName" style="float:left"></div>
				<div style="float:right; width:16px">
					<span>
						<button class="deleteButton" data-url="" onclick="deleteFile(); return false" >
							<span class="ui-icon ui-icon-trash"></span>
						</button>
					</span>
				</div>
				<div class="fileSize" style="float:right; width:110px; text-align:center">Sz</div>
				<div style="clear:both"></div>
			</div>			
			<div id="progressbar" style="display:none">
			
			</div>
		</form>
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
						<input id="dialogSelection" name="dialogSelection" type="hidden" value=""> <!-- change type to hidden later -->
					</div>
				</div>
				<div class="interactiveContent">
					<div id="submitdiv">
						<input type="hidden" id="fileNameParameter" name="fileName" value="null">
						<button id="submitButton" class="simpleButton" type="submit" value="Submit">
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