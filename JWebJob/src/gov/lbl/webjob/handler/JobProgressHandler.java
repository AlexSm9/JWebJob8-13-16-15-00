package gov.lbl.webjob.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import gov.lbl.webjob.db.DataProvider;
import gov.lbl.webjob.ent.JobStatus;
import gov.lbl.webjob.ent.TaskCollection;

/**
 * Servlet implementation class JobProgressHandler
 */
@WebServlet("/JobProgressHandler")
public class JobProgressHandler extends HttpServlet {
	private static final long serialVersionUID = 12L;
	private HttpSession session;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JobProgressHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		session = request.getSession();
		
		DataProvider dp = DataProvider.getInstance();
		JobStatus jStatus = dp.findOne(JobStatus.class, "jobId", session.getAttribute("jobId"));
		
		PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        JSONArray json = new JSONArray();
        JSONObject jsono = new JSONObject();
        ArrayList<JSONObject> taskList = new ArrayList<JSONObject>();
        try {
            ArrayList<TaskCollection> items = jStatus.getTasks();
            jsono.put("jobId", jStatus.getJobId());
            jsono.put("jobName", jStatus.getJobName());
            jsono.put("jobStatus", jStatus.getOverallStatus());
            //JSONObject subtasks = new JSONObject();
            for (TaskCollection item : items) {
                
                /*
                Properties properties = new Properties();
        		InputStream input = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
        		properties.load(input);
        		input.close();
				*/
            	
                JSONObject task = new JSONObject();
                task.put("taskId", item.getTaskId());
                task.put("taskStatus", item.getTaskStatus());
                taskList.add(task);
                statusFill(taskList, item.getSubTasks());
            	
        		//subtasks.put(item.getTaskId().toString(), item.getTaskStatus());
            	//statusFill(subtasks, item.getSubTasks());
                

            }
            jsono.put("subtasks", taskList); //attempt to put jsonobject within jsonobject
            json.put(jsono);
        } catch (Exception e) {
        		e.printStackTrace();
                throw new RuntimeException(e);
        } finally {
        	System.out.println("Ajax returned json:" + jsono);
            writer.write(json.toString());
            writer.close();
        }
	}

	private void statusFill(ArrayList<JSONObject> taskList, ArrayList<TaskCollection> taskCol){
		if(taskCol != null){
			for(int i = 0; i< taskCol.size(); i++){
				TaskCollection tc = taskCol.get(i);
				/*try {
					jsono.put(tc.getTaskId().toString(), tc.getTaskStatus());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
                JSONObject task = new JSONObject();
                try{
                task.put("taskId", tc.getTaskId());
                task.put("taskStatus", tc.getTaskStatus());
                } catch (JSONException e){
                	e.printStackTrace();
                }
                taskList.add(task);
				statusFill(taskList, tc.getSubTasks());
			}
		}
	}
	
}
