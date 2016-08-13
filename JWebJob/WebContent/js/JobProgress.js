/*
* Javascript for JobProgress.jsp
*/

function updateStatus(){
	$.ajax({url:"JobProgressHandler", 
	success: function(data){
		console.log("JobProgressandler ajax response recieved");
		console.log("data" + data)
		checkCompletion(data);
		updateHtmlStatus(data);
	},
	complete: function(){
		setTimeout(updateStatus, 3000);
	}});
}

function checkCompletion(data){ 
	console.log("Check on completion " + data[0]);
	if(data[0].jobStatus == 1){
		window.location.replace("JobResultVC");
	};
	
}

$(updateStatus);

function updateHtmlStatus(data){
	console.log("in updateHtmlStatus");
	console.log("access of subtasks: " + data[0].subtasks);
	console.log("size of subtasks object is: " + data[0].subtasks.length);
	for(i = 0; i<data[0].subtasks.length; i++){
		searchId = data[0].subtasks[i].taskId;
		console.log("taskid: " + searchId);
		console.log("taskStatus: " + data[0].subtasks[i].taskStatus)
		$("#" + searchId + "_Icon").html(data[0].subtasks[i].taskStatus); //TEMPORARY
		
	}
}