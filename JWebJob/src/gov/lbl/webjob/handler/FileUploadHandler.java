package gov.lbl.webjob.handler;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
import org.json.JSONArray;
import org.json.JSONObject;

import gov.lbl.webjob.db.DataProvider;
import gov.lbl.webjob.ent.TempStorage;
import gov.lbl.webjob.test.Preparor;

/**
 * Servlet implementation class FileUploadHandler
 */
@WebServlet("/FileUploadHandler")
public class FileUploadHandler extends HttpServlet {
	private static final long serialVersionUID = 123432L;
	private File fileUploadPath;
    private static Properties prop;
    private HttpSession session;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
    	String pathtofile = getServletContext().getInitParameter("file-upload");
    	System.out.println("after getContext");
    	System.out.println("Pathname = " + pathtofile);
        fileUploadPath = new File(pathtofile);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
                file.delete(); // TODO:check and report success
            } 
        } else {
            PrintWriter writer = response.getWriter();
            writer.write("call POST with multipart form data");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
                        
                        
                        //INSTANTIATE OBJECT THAT CAN CAN READ FILE AND EXTRACT ITS CONTENTS
                        Preparor prep = new Preparor();
                        //
                        
                        boolean fileContentsAccepted = /*functionThatReadsFileAndReturnsBooleanIfFileFormatIsValid*/prep.extractFile(file);  
                        System.out.println("File accepted: " + fileContentsAccepted);
                        
                        
                        
                        if (fileContentsAccepted == true){
                        //create
                        	jsono.put("name", item.getName());
                        	jsono.put("size", item.getSize());
                        	jsono.put("url", this.getClass().getSimpleName()+"?getfile=" + item.getName());
                        	jsono.put("thumbnail_url", this.getClass().getSimpleName()+"?getthumb=" + item.getName());
                        	jsono.put("delete_url", this.getClass().getSimpleName()+"?delfile=" + item.getName());
                        	jsono.put("delete_type", "GET");
                        	
                        	jsono.put("error", 0);
                        	
                        	
                        	/*CODE TO ADD FILE CONTENTS TO DATABASE
                        	 * 
                        	 * GOES HERE 
                        	 * 
                        	 * THEN RETURN ID BY WHICH TO GET BACK STORED CONTENTS
                        	 * 
                        	 */ 
                        	DataProvider dp = DataProvider.getInstance();
                        	TempStorage ts = new TempStorage(/*RELEVANT CONTENTS OF FILE*/prep.getSequence());
                        	dp.save(ts);
                        	
                        	session = request.getSession();
                        	session.setAttribute("uploadTempStorageId", /*ID BY WHICH TO ACCESS TEMP STORED CONTENTS*/ts.storageId);
                        	//CAN ALSO SEND ID BACK TO CALLING SERVLET TO STORE IN HIDDEN VALUE FOR LATER USE
                        	
                        }else{
                        //otherwise make another JSON document with info about the error
                        	jsono.put("name", item.getName());
                        	jsono.put("size", item.getSize());
                        	jsono.put("url", this.getClass().getSimpleName()+"?getfile=" + item.getName());
                        	jsono.put("delete_url", this.getClass().getSimpleName()+"?delfile=" + item.getName());
                        	jsono.put("delete_type", "GET");
                        	
                        	/* Add string representation of upload error
                            Properties properties = new Properties();
                    		InputStream input = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
                    		properties.load(input);
                    		input.close();
                    		prop = properties;
                        	ArrayList<Integer> errorCodeList = //ERRORS IN CODE FROM ;
                        	ArrayList<String> errorStringList = new ArrayList<String>();
                        	for(int i = 0; i<errorCodeList.size(); i++){
                        		errorStringList.add(prop.getProperty(String.valueOf(errorCodeList.get(i))));
                        	}
                        	jsono.put("error", );
                        	*/
                        	
                        }
                        json.put(jsono);
                        file.delete();
                }
            }
        } catch (FileUploadException e) {
                throw new RuntimeException(e);
        } catch (Exception e) {
        		e.printStackTrace();
                throw new RuntimeException(e);
        } finally {
            writer.write(json.toString());
            writer.close();
        }
		
	}
    
    private String getMimeType(File file) {
        String mimetype = "";
        if (file.exists()) {
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
