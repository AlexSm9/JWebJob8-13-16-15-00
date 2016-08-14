package gov.lbl.webjob.ent;

import org.mongodb.morphia.annotations.*;

import gov.lbl.webjob.util.Util;

@Entity("tempStorage")
public class TempStorage {
	
	@Id private String objectId;
	public String storageId;
	public String created;
	public String lastAccessed;
	public boolean okToDelete; //in case something needs to remain in the temporary storage long enough to differentiate it from non-deleted files.
	public Object itemToStore;
	
	TempStorage(){
		//Empty constructor for Morphia
	}
	
	public TempStorage(Object itemToStore){
		this.itemToStore = itemToStore;
		created = Util.genTimestamp();
		lastAccessed = Util.genTimestamp();
		okToDelete = false;
	}

	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getLastAccessed() {
		return lastAccessed;
	}

	public void setLastAccessed(String lastAccessed) {
		this.lastAccessed = lastAccessed;
	}

	public boolean isOkToDelete() {
		return okToDelete;
	}

	public void setOkToDelete(boolean okToDelete) {
		this.okToDelete = okToDelete;
	}

	public Object getItemToStore() {
		return itemToStore;
	}

	public void setItemToStore(Object itemToStore) {
		this.itemToStore = itemToStore;
	}
	
}
