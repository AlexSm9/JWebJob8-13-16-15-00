package gov.lbl.webjob.exception;

public class DatabaseQueryException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4864998489393360282L;
	
	String searchItem;
	Throwable cause;
	
	public DatabaseQueryException(Throwable cause){
		this.cause = cause;
		outLine();
		stackTracePrint();
	}
	
	public DatabaseQueryException(Throwable cause, String searchItem){
		this.cause = cause;
		this.searchItem = searchItem;
		outLine();
		stackTracePrint();
	}
	
	public DatabaseQueryException(String searchItem){
		this.searchItem = searchItem;
		outLine();
	}
	
	public DatabaseQueryException(){
		outLine();
	}
	
	private void outLine(){
		System.out.print("The requested item");
		if(searchItem!=null){
			System.out.print(" > " + searchItem + " <");
		}
		System.out.println(" was not found in the Database.");
	}
	
	private void stackTracePrint(){
		System.out.println("Stacktrace: ");
		cause.printStackTrace();
	}
	
}
