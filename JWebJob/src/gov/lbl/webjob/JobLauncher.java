package gov.lbl.webjob;

import java.util.ArrayList;

import gov.lbl.webjob.db.DataProvider;
import gov.lbl.webjob.ent.JobResults;
import gov.lbl.webjob.ent.JobStatus;
import gov.lbl.webjob.ent.TaskCollection;
import gov.lbl.webjob.exception.ParameterMissingException;
import gov.lbl.webjob.util.Util;

public class JobLauncher implements Runnable{

	DataProvider dp;
	
	String jobId;
	
	public JobLauncher(String jobId){
		this.jobId = jobId;
		dp = DataProvider.getInstance();
	}
	
	@Override
	public void run(){
		
		if(jobId == null){
			try {
				throw new ParameterMissingException();
			} catch (ParameterMissingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("JobLauncher thread started.");
		
		

		
		
		
		//TEST, PROCESSING JOB
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JobStatus jStatus = dp.findOne(JobStatus.class, "jobId", jobId);
		ArrayList<TaskCollection> tc = jStatus.getTasks();
		tc.get(0).getSubTasks().get(0).setTaskStatus(1);
		
		
		dp.updateKey(JobStatus.class, "jobId", jobId, "tasks", tc);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Example job time is done.");
		
		
		//dp.updateKey(JobResults.class, "jobId", jobId, "", valueToInsert);
		JobResults jr = dp.findOne(JobResults.class, "jobId", jobId);
		jr.setLastUpdated(Util.genTimestamp());
		jr.setResultObj("Job is completed, showing result string, but this could really be any result you need to show.");
		dp.save(jr);
		System.out.println("JobResults is updated with result");
		
		//END OF JOB PROCESS
		terminateJob();
		System.out.println("JobLauncher thread finished.");
		
		return; //added to close thread?
	}
	
	private void terminateJob(){
		dp.updateKey(JobStatus.class, "jobId", jobId, "overallStatus", 1);
	}
	
}
