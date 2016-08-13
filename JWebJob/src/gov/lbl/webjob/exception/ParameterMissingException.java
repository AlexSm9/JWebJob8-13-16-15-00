package gov.lbl.webjob.exception;

public class ParameterMissingException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1403200861743417076L;
	
	String parameter;
	Throwable cause;
	
	public ParameterMissingException(){
		outLine();
	}
	
	public ParameterMissingException(Throwable cause){
		this.cause = cause;
		outLine();
		stackTracePrint();
	}
	
	public ParameterMissingException(String parameter){
		this.parameter = parameter;
		outLine();
	}
	
	public ParameterMissingException(String parameter, Throwable cause){
		this.cause = cause;
		this.parameter = parameter; 
		outLine();
		stackTracePrint();
	}
	
	private void outLine(){
		System.out.print("The requested item");
		if(parameter!=null){
			System.out.print(" > " + parameter + " <");
		}
		System.out.println(" was not found in the Database.");
	}
	
	private void stackTracePrint(){
		System.out.println("Stacktrace: ");
		cause.printStackTrace();
	}
	
}
