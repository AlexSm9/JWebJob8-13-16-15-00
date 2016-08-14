package gov.lbl.webjob.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Preparor {
	
	public int errorCode = 0;
	private String sequence = null;
	
	public boolean extractFile(File fi) throws FileNotFoundException{
		boolean hasError = false;
		StringBuffer sb = new StringBuffer();
		if((getSuffix(fi.getPath())).compareTo("gbff") != 0){
	    	errorCode = 1001;
	    	hasError = true;
	    }else{
		    boolean originFound = false;
		    System.out.println(fi);
			Scanner sc = new Scanner(fi).useDelimiter("\n");
		    while(sc.hasNext()){
	    		if(sc.hasNext("ORIGIN")){
	    			originFound = true;
	    			sc.next();
	    			break;
	    		}
	    		sc.next();
	    	}
	    	if(originFound == true){
	    		while(!sc.hasNext("//")){		
	    			sb.append(sc.nextLine());
	    			if (sc.hasNextLine() == false){
	    				errorCode = 1003;
	    				hasError = true;
	    				break;
	    			}
	    		}
	    	}else{
	    		errorCode = 1002;
	    		hasError = true;
	    	}
	    	sc.close();
	    }
		if(hasError == false){
			String seqTemp = sb.toString();
			//currently ignores all non-actg characters. May need to insert error 1005.
			seqTemp = seqTemp.replaceAll("[^acgtACGT]", "");
			sequence = seqTemp.toUpperCase();
			resetErrors();
			return true;
		}else{
			return false;
		}
	}
	
	public boolean extractText(String textSeq){
		String nonValidChars = textSeq.replaceAll("[acgtACGT]", "");
		String validChars = textSeq.replaceAll("[^acgtACGT]", "");
		if(validChars.compareTo("") != 0){
			if(nonValidChars.compareTo("") == 0){
				sequence = validChars;
				sequence = sequence.toUpperCase();
				resetErrors();
				return true;
			}else{
				errorCode = 1005;
			}
		}else{
			errorCode = 1004;
		}
		return false;
	}
	
	public String getSequence(){
		return sequence;
	}
	
	//PRIVATE___________________________
	
	private void resetErrors(){
		errorCode = 0;
	}
	
	private String getSuffix(String filename) {
	    String suffix = "";
	    int pos = filename.lastIndexOf('.');
	    if (pos > 0 && pos < filename.length() - 1) {
	        suffix = filename.substring(pos + 1);
	    }
	    System.out.println("suffix: " + suffix);
	    return suffix;
	}
	
}
