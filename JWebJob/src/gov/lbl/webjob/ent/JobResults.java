package gov.lbl.webjob.ent;

import org.mongodb.morphia.annotations.*;

import gov.lbl.webjob.util.Util;

@Entity("jobResult")
public class JobResults {

	@Id private String objectId;
	public String jobId;
	
	public String created;
	public String lastUpdated;
	public Object resultObj;
	
	public JobResults(){
		//Empty for Morphia
	}
	
	public JobResults(String jobId) {
		this.jobId = jobId;
		objectId = Util.genStringObjectId();
		created = Util.genTimestamp();
		lastUpdated = created;
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

	public Object getResultObj() {
		return resultObj;
	}

	public void setResultObj(Object resultObj) {
		this.resultObj = resultObj;
	}

}
