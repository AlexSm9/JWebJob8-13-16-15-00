package gov.lbl.webjob.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Preparor {
	
	public ArrayList<Integer> errorCodes = new ArrayList<Integer>();
	private String sequence = null;
	
	public boolean extractFile(File fi) throws FileNotFoundException{
		boolean hasError = false;
		StringBuffer sb = new StringBuffer();
		if((getSuffix(fi.getPath())).compareTo("gbff") != 0){
	    	errorCodes.add(1001);
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
	    				errorCodes.add(1003);
	    				hasError = true;
	    				break;
	    			}
	    		}
	    	}else{
	    		errorCodes.add(1002);
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
				errorCodes.add(1005);
			}
		}else{
			errorCodes.add(1004);
		}
		return false;
	}
	
	public String getSequence(){
		return sequence;
	}
	
	//PRIVATE___________________________
	
	private void resetErrors(){
		errorCodes = new ArrayList<Integer>();
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
