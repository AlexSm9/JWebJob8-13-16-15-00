package gov.lbl.webjob.db;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**  MongoDB Morphia Documentation, version 1.2.1:
 * <hr>
 * Read: <a target="_top" href="http://mongodb.github.io/morphia/1.2/javadoc/">Morphia General Documentation</a><br>
 * Read: <a target="_top" href="http://mongodb.github.io/morphia/1.2/javadoc/org/mongodb/morphia/query/UpdateOperations.html">UpdateOperations</a><br>
 * Read: <a target="_top" href="http://mongodb.github.io/morphia/1.2/javadoc/org/mongodb/morphia/query/Query.html">Query
 */

public class DataProvider {

	//private static String mongoPath = "localhost";
	//private static String dbName = "pgpDatabase";
	String mongoPath;
	String dbName;
	
	private static DataProvider _instance = null;
	private MongoClient mongoClient;
	private MongoDatabase database;
	private Morphia morphia; 
	private Datastore datastore;
	
	public static DataProvider getInstance() {
		if (_instance == null) {
			_instance = new DataProvider();
			_instance.init();
		} 
		return _instance;
	}
	
	private void init(){
		
		Context env;
		try {
			env = (Context)new InitialContext().lookup("java:comp/env");
			mongoPath = (String)env.lookup("mongoPath");
			dbName = (String)env.lookup("dbName");
			
			mongoClient = new MongoClient(mongoPath);
			
			morphia = new Morphia();
			datastore = morphia.createDatastore(mongoClient, dbName);
			datastore.ensureIndexes();
			morphia.mapPackage("gov.lbl.webjob.test");
		} catch (NamingException e) {
			System.out.println("Database parameter is missing in webxml file. Please read project notes.");
			e.printStackTrace();
		}
		
	}
	
	public <T> Key<T> save(T ent){
		System.out.println("Saving in database: " + ent);
		return datastore.save(ent);
	}
	
	public <T> Object saveGetID(T ent){
		Key<T> key = datastore.save(ent);
		return key.getId();
	}
	
	public <T, V> T findById(Class<T> dotClass, V id){
		return datastore.get(dotClass, id);
	}
	
	public <T, V> List<T> findList(Class<T> dotClass, String filterKey, V filterValue){
		return datastore.find(dotClass, filterKey, filterValue).asList();
	}
	
	public <T> List<T> getAllAsList(Class<T> dotClass){
		return datastore.find(dotClass).asList();
	}
	
	public <T, V> T findOne(Class<T> dotClass, String filterKey, V filterValue){
		List<T> list = datastore.find(dotClass, filterKey, filterValue, 0, 1).asList();
		if(list.size() != 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public <T> void deleteOne(T ent){
		datastore.delete(ent);
	}
	
	public <T, V> void deleteEntity(Class<T> dotClass, String filterKey, V filterValue){
		Query<T> filter = createEqualQuery(dotClass, filterKey, filterValue);
		datastore.findAndDelete(filter);
	}
	
	public <T, V> void deleteKey(Class<T> dotClass, String filterKey, V filterValue, String keyToDelete){
		Query<T> filter = createEqualQuery(dotClass, filterKey, filterValue);
		UpdateOperations<T> uo = datastore.createUpdateOperations(dotClass).unset(keyToDelete);
		datastore.findAndModify(filter, uo);
	}
	
	public <T, V, U> void updateKey(Class<T> dotClass, String filterKey, V filterValue, String keyToEdit, U valueToInsert){ //the added field must already exist in the class
		Query<T> filter = createEqualQuery(dotClass, filterKey, filterValue);
		UpdateOperations<T> uo = datastore.createUpdateOperations(dotClass).set(keyToEdit, valueToInsert);
		datastore.findAndModify(filter, uo); //could add optional 3rd parameter: true, which will add key even if it already exists
	}
	
	private <T, V> Query<T> createEqualQuery(Class<T> dotClass, String queryKey, V queryValue){
		return datastore.createQuery(dotClass).filter(queryKey + " ==", queryValue);
	}
	
}
