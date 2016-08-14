/*
* Javascript for JobInput.jsp
*/


$( document ).ready(function() {
	onInit();
});

function onInit(){
	
	//-//JQuery UI

	$('#upload #drop a').button();

	$(".simpleButton").button();

	$("#selectionDialog").dialog({ autoOpen: false, modal: true, height: "auto", width: "80%" });

	$( "#progressbar" ).progressbar();

	//-//Other
	
	$("#errorFile").hide();
	console.log($("#errorFile"));
	
	$("#errorFile").click(function(){
	    $("#errorFile").hide();
	});

	$("#button1").click(function(){
		$("#button1Clicked").val("true");
	});

	$("#button2").click(function(){
		openSelectionDialog();
	});
	
	$("#mainInputForm").on('submit', function () {
		 var delurl = ($('#content .deleteButton').attr("data-url"));
		 $.ajax({url: delurl, success: function(result){
			 //do nothing, just delete the file
		 }})
	});
}



function openSelectionDialog(){
	$("#selectionDialog").dialog("open");
	$(".contentItemSelectButton").click(function(){
		$("#dialogSelection").val($(this).val()), $("#selectionDialog").dialog("close");
	});
}

function deleteFile(){
	var delurl = ($('#content .deleteButton').attr("data-url"));
	 $.ajax({url: delurl, success: function(result){
		 $("#content").hide();
		 $("#drop").show();
		 $( "#errorFile").hide()
		 $("#containsFile").val("false");
		 $("#fileNameParameter").val("null");
		 //disableSubmit();
     }});
}

