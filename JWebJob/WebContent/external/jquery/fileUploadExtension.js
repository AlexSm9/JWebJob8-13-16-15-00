$(function(){

    $("#upload").find("input").hide();
	
    var container = $('#upload div#content');

    $('#drop a').click(function(){
        // Simulate a click on the file input button
        // to show the file browser dialog
        $(this).parent().find('input').click();
    });

    // Initialize the jQuery File Upload plugin
    $('#upload').fileupload({

        // This element will accept file drag/drop uploading
        dropZone: $('#drop'),

        // This function is called when a file is added to the queue;
        // either via the browse button, or via drag/drop:
        add: function (e, data) {

        	console.log("in add: function");
        	
        	document.getElementById('drop').style.display = 'none';
        	document.getElementById('progressbar').style.display = 'block';
        	
            var tpl = $('<div class="working"><input data-width="0" data-height="0"><p></p><span></span></div>');

            // Append the file name and file size
            //tpl.find('p').text(data.files[0].name);
                         
            $('#content .fileName').text(data.files[0].name);
            
            $('#content .fileSize').text(formatFileSize(data.files[0].size));

            // Add the HTML to the UL element
            data.context = tpl.appendTo(container);

            // Initialize the knob plugin
            //tpl.find('input').knob();
            tpl.find('input').hide();

            // Listen for clicks on the cancel icon
            tpl.find('span').click(function(){

                if(tpl.hasClass('working')){
                    jqXHR.abort();
                }

                tpl.fadeOut(function(){
                    tpl.remove();
                });
   
                
            });

            // Automatically upload the file once it is added to the queue
            var jqXHR = data.submit();
        },

        progress: function(e, data){

            // Calculate the completion percentage of the upload
            var progress = parseInt(data.loaded / data.total * 100, 10);
            console.log("In progress. Progress = " + data.loaded + ", total = " + data.total);

            // Update the hidden input field and trigger a change
            // so that the jQuery knob plugin knows to update the dial
            //data.context.find('input').val(progress).change();

            $( "#progressbar" ).progressbar("value", progress);
            
            if(progress == 100){
                
                document.getElementById('progressbar').style.display = 'none';
                document.getElementById('content').style.display = 'block';

            }
        },

        fail:function(e, data){
            // Something has gone wrong!
            data.context.addClass('error');
        },
        
        done: function (e, data) {
        	
        	//--
        	
        	console.log("Update is done, deleteurl is: " + data.delete_url);
        	console.log(data);
        	console.log(data.result[0].delete_url);
        	
            $('#content .deleteButton').attr("data-url", data.result[0].delete_url);
            $('#submit').attr("fileUrl", data.url);
            //$("#fileUrl").val(data.url);
            //$("#delFileUrl").val(data.result[0].delete_url);
            $('#fileNameParameter').val(data.files[0].name);
            
            data.context.removeClass('working');
            
            //fileFieldValidator();

            //Below function does not account for non-existent data.result[0], placed 'jsono.put("error", 0);' in FileUploadHandler json output to fix
            if(data.result[0].error != 0){	
            	//alert(data.result[0].error);
            		$("#errorFile").show();
            		
            		$("#errorFileText").html(data.result[0].error);

            		$("#errorText").hide()
            		$("#sbmt").attr('disabled', 'disabled');
            	//moved this if down from the top of the done function, may cause unintentional errors.
            }
            
        }

    });


    // Prevent the default action when a file is dropped on the window
    $(document).on('drop dragover', function (e) {
        e.preventDefault();
    });

    // Helper function that formats the file sizes
    function formatFileSize(bytes) {
        if (typeof bytes !== 'number') {
            return '';
        }

        if (bytes >= 1000000000) {
            return (bytes / 1000000000).toFixed(2) + ' GB';
        }

        if (bytes >= 1000000) {
            return (bytes / 1000000).toFixed(2) + ' MB';
        }

        return (bytes / 1000).toFixed(2) + ' KB';
    }
    
});