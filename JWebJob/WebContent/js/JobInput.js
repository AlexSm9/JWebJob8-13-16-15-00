/*
* Javascript for JobInput.jsp
*/

//-//JQuery UI

$(".simpleButton").button();

$("#selectionDialog").dialog({ autoOpen: false, modal: true, height: "auto", width: "80%" });

//-//Other

$("#button1").click(function(){
	$("#button1Clicked").val("true");
});

$("#button2").click(function(){
	openSelectionDialog();
});

function openSelectionDialog(){
	$("#selectionDialog").dialog("open");
	$(".contentItemSelectButton").click(function(){
		$("#dialogSelection").val($(this).val()), $("#selectionDialog").dialog("close");
	});
}