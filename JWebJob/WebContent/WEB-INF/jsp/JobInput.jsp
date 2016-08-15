0<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- \\ -->
<!-- JQuery -->
<%@ include file="/WEB-INF/templates/includeJquery.jsp" %>
<link rel="stylesheet" type="text/css" href="./css/propagator_base.css">
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




<div  class="ui-corner-all ui-widget ui-widget-content" style="background-color:white">

<div class="header">
		<img src="images/propagator_logo.png" class="logo">
        <h1>RegPrecise Propagator</h1><span class="slogan">Tool for identifying Inferences of Regulons in Prokaryotic Genomes</span>
	<div class="clear"></div>        	
</div>
<div id="main_nav"><div class="version">-- version 1.0 --</div> </div>


	<div class="main">
		<div id="mainContent">
		<div class="content">
	        <div class="grey_primebox">
	        	<form id="upload" method="post" action="FileUploadHandler" enctype="multipart/form-data">
					<fieldset class="ui-corner-all ui-widget ui-widget-content field_set">
	   				<legend><strong>Step 1: </strong>File selection:</legend>

					
					<div id="fileUploadDiv">
						<div id="fileUploadErrorDisplayDiv">
							<div class="ui-widget" id="errorFile">
				    			<div class="ui-state-error ui-corner-all" style="margin: 5px 0 10px 0;padding: 10px .7em;"> 
				            			<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
				            			<strong>Upload Error</strong>
				            			<span id="errorFileText"></span>
				    			</div>
							</div>
						</div>
					</div>
					
					
					<div id="drop">				
						<a>✚ Select File to Upload</a>
						<input type="file" name="upl"/>
					</div>
					<div class="ui-state-highlight ui-corner-all" id="content" style="display:none; padding:10px 5px 0px 5px; height:30px;">
						<div style="float:left; width:30px"><span class="ui-icon ui-icon-circle-check"></span></div>
						<div class="fileName" style="float:left"></div>
						<div style="float:right;">
							<span>
								<button class="deleteButton" data-url="" onclick="deleteFile(); return false" >
									<span class="ui-icon ui-icon-trash"></span>
								</button>
							</span>
						</div>
						<div class="fileSize" style="float:right;">Sz</div>
					</div>			
					<div id="progressbar" style="display:none">
					</div>
					
					
					
					
					
					</fieldset>
				</form>
				<div id="mainInputForm">
					<form id="sumbitForm1" method="post" action="JobInputHandler">
						 <fieldset class="ui-corner-all ui-widget ui-widget-content field_set">
	   					 <legend><strong>Step 2:</strong> Job information:</legend>
						
							<div class="interactiveContent clear_start">
								<div id="textInput1Div">
									<div class="fieldset-label">
										Name for Job:
									</div>
									<div class="fieldset-input">
										<input type="text" name="jobNameInput" id="jobNameInput" class="simpleTextInput" value="Unique Job Name">
									</div>
								</div>
							</div>
							
							<div class="interactiveContent  clear_start">
								<div id="textInput1Div">
									<div class="fieldset-label">
										Your Email:
									</div>
									<div class="fieldset-input">
										<input type="text" name="textInput1" id="textInput1" class="simpleTextInput" value="">
									</div>
								</div>
							</div>
							<div class="interactiveContent  clear_start">
								<div id="button1Div">
									<button type="button" id="button1" class="simpleButton">Example Button</button>
									<input id="button1Clicked" name="button1Clicked" type=hidden value="false">
								</div>
							</div>
							
							
						 </fieldset>
						 
						<fieldset class="ui-corner-all ui-widget ui-widget-content field_set">
						 
	   					<legend>Options:</legend> 
	
							<div class="interactiveContent">
								<div id="checkboxDiv">
									<input class="simpleCheckbox" type="checkbox" name="checkbox1" value="checked1"> Lorem ipsum dolor sit amet
									<br>
				 					<input class="simpleCheckbox" type="checkbox" name="checkbox2" value="checked2"> Ut enim ad minim 
								</div>
							</div>
						</fieldset>
						
						<fieldset class="ui-corner-all ui-widget ui-widget-content field_set">
	   					 <legend><strong>Step 3: </strong>Selection of collection:</legend>
							<div class="interactiveContent">
								<div id="button2Div">
									<button type="button" id="button2" class="simpleButton"><span class="ui-icon ui-icon-newwin"></span>Select a Reference Collection <span class="ui-icon ui-icon-circle-triangle-e"></span></button>
									<input id="dialogSelection" name="dialogSelection" type="hidden" value=""> <!-- change type to hidden later -->
								</div>
							</div>
						</fieldset>
						
							<div class="interactiveContent">
								<div id="submitdiv">
									<input type="hidden" id="fileNameParameter" name="fileName" value="null">
									<button id="submitButton" class="simpleButton green_button" type="submit" value="Submit" style="">
										Submit this Form!
									</button>
								</div>
							</div>
						</div>
					</form>
				</div>
	

		
		<br>
		
		
		<div id="selectionDialog"><!-- Works by javascript, setting a hidden value of the submit form to the selection -->
			<div>
				
			</div>
		</div>
		
	</div>
	</div>
</div>
	

<div class="footer_divider"></div>
<div class="footer">
	<div class="footerText">
    <div class="footer">© 2009-2016 RegPrecise</div>
</div>
</div>
</div>




<!-- 



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
				<input type="file" name="upl"/>
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
						<input id="dialogSelection" name="dialogSelection" type="hidden" value="">
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
	
	<div id="selectionDialog">
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
-->
</body>
</html>