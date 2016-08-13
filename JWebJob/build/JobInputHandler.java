package gov.lbl.webjob.handler;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.bson.Document;
import org.imgscalr.Scalr;
import org.json.JSONArray;
import org.json.JSONObject;

import org.lbl.regprecise.pgp.DBA;
import org.lbl.regprecise.pgp.Preparor;
import org.lbl.regprecise.pgp.RefColDBAccess;
import org.lbl.regprecise.pgp.RefColNode;
import org.lbl.regprecise.pgp.TempDBStorage;

/**
 * Servlet implementation class JobInputHandler
 */
@WebServlet("/JobInputHandler")
public class JobInputHandler extends HttpServlet {
	private static final long serialVersionUID = 11L;
    private File fileUploadPath;
    private static Properties prop;
    private HttpSession session;

    @Override
    public void init(ServletConfig config) throws ServletException {
    	System.out.println("before getContext");
    	super.init(config);
    	String pathtofile = getServletContext().getInitParameter("file-upload");
    	System.out.println("after getContext");
    	System.out.println("Pathname = " + pathtofile);
        fileUploadPath = new File(pathtofile);
        
    }
        
    /**
        * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
        * 
        */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        
        if (request.getParameter("getfile") != null 
                && !request.getParameter("getfile").isEmpty()) {
            File file = new File(fileUploadPath,
                    request.getParameter("getfile"));
            if (file.exists()) {
                int bytes = 0;
                ServletOutputStream op = response.getOutputStream();

                response.setContentType(getMimeType(file));
                response.setContentLength((int) file.length());
                response.setHeader( "Content-Disposition", "inline; filename=\"" + file.getName() + "\"" );

                byte[] bbuf = new byte[1024];
                DataInputStream in = new DataInputStream(new FileInputStream(file));

                while ((in != null) && ((bytes = in.read(bbuf)) != -1)) {
                    op.write(bbuf, 0, bytes);
                }

                in.close();
                op.flush();
                op.close();
            }
        } else if (request.getParameter("delfile") != null && !request.getParameter("delfile").isEmpty()) {
            File file = new File(fileUploadPath, request.getParameter("delfile"));
            if (file.exists()) {
                file.delete();
            }
        } else {
            PrintWriter writer = response.getWriter();
            writer.write("call POST with multipart form data");
        }
    }
    
    /**
        * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
        * 
        */
    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(request.getAttribute("displayChosenCol")==null){ //might need a better check
	    	if (!ServletFileUpload.isMultipartContent(request)) {
	            throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
	        }
	        ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
	        PrintWriter writer = response.getWriter();
	        response.setContentType("application/json");
	        JSONArray json = new JSONArray();
	        try {
	            List<FileItem> items = uploadHandler.parseRequest(request);
	            System.out.println(items.size());
	            for (FileItem item : items) {
	            	System.out.println(item.getFieldName());
	                if (!item.isFormField()) {
	                		System.out.println("Within 'if'");
	                        File file = new File(fileUploadPath, item.getName());
	                        item.write(file);
	                        JSONObject jsono = new JSONObject();
	                        Preparor prep = new Preparor();
	                        boolean extractSuccess = prep.extractFile(file);  
	                        System.out.println("extractSuccess value: " + extractSuccess);
	                        Properties properties = new Properties();
	                		InputStream input = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
	                		properties.load(input);
	                		input.close();
	                		prop = properties;
	                		System.out.println(prep.errorCode);
	                        
	                        if (extractSuccess == true){
	                        	jsono.put("name", item.getName());
	                        	jsono.put("size", item.getSize());
	                        	jsono.put("url", "UploadFile?getfile=" + item.getName());
	                        	jsono.put("thumbnail_url", "UploadFile?getthumb=" + item.getName());
	                        	jsono.put("delete_url", "UploadFile?delfile=" + item.getName());
	                        	jsono.put("delete_type", "GET");
	                        	jsono.put("error", 0);
	                        	//add values to database temp storage
	                        	TempDBStorage dbs = new TempDBStorage();
	                        	System.out.println(prep.getSequence());
	                        	dbs.store("sequence", prep.getSequence());
	                        	//put storageId in session
	                        	session = request.getSession();
	                        	System.out.println("dbs.sId = " + dbs.sId);
	                        	session.setAttribute("uploadTempStorageId", dbs.sId);
	                        	session.setAttribute("lastSuccessfullyExtractedSequence", prep.getSequence() );
	                        }else{
	                        //otherwise make another json document with info about the error
	                        	jsono.put("name", item.getName());
	                        	jsono.put("size", item.getSize());
	                        	jsono.put("url", "UploadFile?getfile=" + item.getName());
	                        	jsono.put("delete_url", "UploadFile?delfile=" + item.getName());
	                        	jsono.put("delete_type", "GET");
	                        	jsono.put("error", prop.getProperty(String.valueOf(prep.errorCode)));
	                        }
	                        json.put(jsono);
	                        file.delete();
	                        //delete files here
	                }
	            }
	        } catch (FileUploadException e) {
	                throw new RuntimeException(e);
	        } catch (Exception e) {
	        		e.printStackTrace();
	                throw new RuntimeException(e);
	        } finally {
	        	//VERY IMPORTANT END OF AJAX
	            writer.write(json.toString());
	            writer.close();
	        }
	
	    }else{
	    	System.out.println("in doPost of InputStepFormHandler");
			ArrayList<String> errorList = new ArrayList<String>();	
			/*String inputType = (String) request.getParameter("inputType");
			System.out.println("inputType is: " + inputType);*/
			
			session = request.getSession();
			DBA dba = new DBA();
			
			/*Usertoken useful for associating job with user profile, not needed for this version.
			session.setAttribute("userToken", "fakeTokenToLetProjectWork"); //this line only for temporary testing, delete with userToken implementation
			String userToken = (String) session.getAttribute("userToken");
			if(userToken == null){
				dba.setUserToken();
				session.setAttribute("userToken", dba.getUserToken());
			}else{
				dba.setUserToken(userToken);
			}
			*/
			
			System.out.println("attempting to access storage by key: " + (String) session.getAttribute("uploadTempStorageId"));
			TempDBStorage dbs = new TempDBStorage((String) session.getAttribute("uploadTempStorageId"));
			dba.newSeq((String) dbs.getValue("sequence"));
			System.out.println("sequence inserted into job is: " + dbs.getValue("sequence"));
			System.out.println("dba.getJobId is " + dba.getJobId());
			
			RefColDBAccess rcdba = new RefColDBAccess();
			ArrayList<Document> refColDocNodeList = new ArrayList<Document>();
			
			
			RefColNode rcnode = new RefColNode();
			System.out.println("RefColNode, seting name to: " + (String) request.getParameter("displayChosenCol"));
			rcnode.setName((String) request.getParameter("displayChosenCol"));
			System.out.println("RefColNode, setting collection to: " + rcdba.getReferenceCollectionByName((String) request.getParameter("displayChosenCol")));
			//rcnode.setCollection(rcdba.getReferenceCollectionByName((String) request.getParameter("displayChosenCol"))); //Incorrect to store duplicate data in a different database, fix to be an arraylist of found regulators?
			
			ArrayList<String> list = new ArrayList<String>();
			rcnode.setFoundRegulators(list);
			
			rcnode.setStatus("unprocessed");
			rcnode.setLinkedJobId(dba.getJobId());
			refColDocNodeList.add(rcnode.makeDBDoc());
			
			dba.addKeyValue("refCols", refColDocNodeList);
			System.out.println("Email inputted: "+ (String)request.getParameter("emailInput"));
			if(((String)request.getParameter("emailInput")).compareTo("") == 0){
				dba.addKeyValue("addedEmail", null);
			}else{
				dba.addKeyValue("addedEmail", (String)request.getParameter("emailInput"));
			}
			
			
			session.setAttribute("jobId", dba.getJobId());
			dbs.deleteStorage();
			session.removeAttribute("storageId");
			System.out.print("Testing database insert. Insert occured at ");
			System.out.println(dba.getDocValue(dba.getJobId(), "timestamp"));
			response.sendRedirect("Progress");
		}

	    }
    }

    private String getMimeType(File file) {
        String mimetype = "";
        if (file.exists()) {
//            URLConnection uc = new URL("file://" + file.getAbsolutePath()).openConnection();
//            String mimetype = uc.getContentType();
//            MimetypesFIleTypeMap gives PNG as application/octet-stream, but it seems so does URLConnection
//            have to make dirty workaround
            if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
                mimetype = "image/png";
            } else {
                javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
                mimetype  = mtMap.getContentType(file);
            }
        }
        System.out.println("mimetype: " + mimetype);
        return mimetype;
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