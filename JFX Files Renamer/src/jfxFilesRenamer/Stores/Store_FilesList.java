package jfxFilesRenamer.Stores;

public class Store_FilesList {

	
	//**************************************************************
	//*********************** Declarations *************************
	//**************************************************************
	String fileID;
	String fileName;
	String fileLocation;
		
	
	
	//**************************************************************
	//************************ Constructors ************************
	//**************************************************************
	
	public Store_FilesList() {
		super();
	}


	public Store_FilesList(String fileID, String fileName, String fileLocation) {
		super();
		this.fileID = fileID;
		this.fileName = fileName;
		this.fileLocation = fileLocation;
	}

	
	

	//**************************************************************
	//********************* Getters / Setters **********************
	//**************************************************************
	
	public String getFileID() {
		return fileID;
	}


	public String getFileName() {
		return fileName;
	}


	public String getFileLocation() {
		return fileLocation;
	}

	

	
	public void setFileID(String fileID) {
		this.fileID = fileID;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	
	
	
	
}
