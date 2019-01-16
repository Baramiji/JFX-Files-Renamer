package jfxFilesRenamer.Stores;

public class Store_FilesIncrement {

	
	//**************************************************************
	//*********************** Declarations *************************
	//**************************************************************
	String originalFilePath;
	String renamedFileWithoutExtPath;
	String renamedFileWithExtPath;
	String parentFolder;
	int folderInrement;
	int fileIncrement;
		
	
	
	//**************************************************************
	//************************ Constructors ************************
	//**************************************************************
	
	public Store_FilesIncrement() {
		super();
		this.originalFilePath = "";
		this.renamedFileWithoutExtPath = "";
		this.renamedFileWithExtPath = "";
		this.parentFolder = "";
		this.folderInrement = 0;
		this.fileIncrement = 0;
	}


	public Store_FilesIncrement(String originalFilePath, String renamedFileWithoutExtPath, String renamedFileWithExtPath,
			String parentFolder, int folderInrement, int fileIncrement) {
		super();
		this.originalFilePath = originalFilePath;
		this.renamedFileWithoutExtPath = renamedFileWithoutExtPath;
		this.renamedFileWithExtPath = renamedFileWithoutExtPath;
		this.parentFolder = parentFolder;
		this.folderInrement = folderInrement;
		this.fileIncrement = fileIncrement;
	}

	
	
	
	//**************************************************************
	//********************* Getters / Setters **********************
	//**************************************************************

	public String getOriginalFilePath() {
		return originalFilePath;
	}

	public String getRenamedFileWithoutExtPath() {
		return renamedFileWithoutExtPath;
	}
	
	public String getRenamedFileWithExtPath() {
		return renamedFileWithExtPath;
	}
	
	public String getParentFolder() {
		return parentFolder;
	}

	public int getFolderInrement() {
		return folderInrement;
	}


	public int getFileIncrement() {
		return fileIncrement;
	}

	
	

	public void setOriginalFilePath(String originalFilePath) {
		this.originalFilePath = originalFilePath;
	}
	
	public void setRenamedFileWithoutExtPath(String renamedFileWithoutExtPath) {
		this.renamedFileWithoutExtPath = renamedFileWithoutExtPath;
	}
	
	public void setRenamedFileWithExtPath(String renamedFileWithExtPath) {
		this.renamedFileWithExtPath = renamedFileWithExtPath;
	}

	public void setParentFolder(String parentFolder) {
		this.parentFolder = parentFolder;
	}
	
	public void setFolderInrement(int folderInrement) {
		this.folderInrement = folderInrement;
	}


	public void setFileIncrement(int fileIncrement) {
		this.fileIncrement = fileIncrement;
	}

	
	
	
	
}
