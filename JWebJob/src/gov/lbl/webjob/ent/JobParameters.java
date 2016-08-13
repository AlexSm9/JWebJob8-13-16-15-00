package gov.lbl.webjob.ent;

import org.mongodb.morphia.annotations.*;

import gov.lbl.webjob.util.Util;

@Entity("jobParams")
/*@Indexes(
	@Index("jobId, -created")
)*/

public class JobParameters {

	@Id private String objectId;
	public String jobId;
	public String created;
	
	public boolean button1;
	public String textInput1;
	public boolean checkbox1;
	public boolean checkbox2;
	public String dialogEnt;
	public String jobName;
	
	/*
	JobParameters(String jobId){
		this.jobId = jobId;
		Util util = new Util();
		objectId = util.genStringObjectId();
		created = util.genTimestamp();
	}
	*/
	
	public JobParameters(){ //try placing contents of initialize into the constructor to see if Morphia plays nice
		
	}
	
	public void initialize(){
		jobId = Util.genStringUUID();
		objectId = Util.genStringObjectId();
		created = Util.genTimestamp();
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

	public boolean isButton1() {
		return button1;
	}

	public void setButton1(boolean button1) {
		this.button1 = button1;
	}

	public String getTextInput1() {
		return textInput1;
	}

	public void setTextInput1(String textInput1) {
		this.textInput1 = textInput1;
	}

	public boolean isCheckbox1() {
		return checkbox1;
	}

	public void setCheckbox1(boolean checkbox1) {
		this.checkbox1 = checkbox1;
	}

	public boolean isCheckbox2() {
		return checkbox2;
	}

	public void setCheckbox2(boolean checkbox2) {
		this.checkbox2 = checkbox2;
	}

	public String getDialogEnt() {
		return dialogEnt;
	}

	public void setDialogEnt(String dialogEnt) {
		this.dialogEnt = dialogEnt;
	}
	
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String toString(){
		return "JobParameters{objectId: " + objectId + ", jobId: " + jobId + ", created: " + created + ", button1: " + button1 + ", textInput1: " + textInput1 + ", checkbox1: " + checkbox1 + ", checkbox2: " + checkbox2 + ", dialogEntId: " + dialogEnt + "}";
	}
	
}
