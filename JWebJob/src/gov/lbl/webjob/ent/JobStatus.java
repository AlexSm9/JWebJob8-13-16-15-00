package gov.lbl.webjob.ent;

import java.util.ArrayList;

import org.mongodb.morphia.annotations.*;

import gov.lbl.webjob.util.Util;

@Entity("jobStatus")
public class JobStatus {

	@Id private String objectId;
	public String jobId;

	public int overallStatus;
	public String created;
	public String lastUpdated;
	public String finished;
	public ArrayList<TaskCollection> tasks;
	public String jobName;
	
	public JobStatus(){
		//Empty for Morphia
	}
	
	public JobStatus(String jobId){
		this.jobId = jobId;
		objectId = Util.genStringObjectId();
		overallStatus = 0;
		created = Util.genTimestamp();
		lastUpdated = created;
		tasks = null;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public int getOverallStatus() {
		return overallStatus;
	}

	public void setOverallStatus(int overallStatus) {
		this.overallStatus = overallStatus;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getFinished() {
		return finished;
	}

	public void setFinished(String finished) {
		this.finished = finished;
	}

	public ArrayList<TaskCollection> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<TaskCollection> tasks) {
		this.tasks = tasks;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
}
