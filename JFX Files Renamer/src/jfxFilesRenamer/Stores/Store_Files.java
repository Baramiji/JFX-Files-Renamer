package jfxFilesRenamer.Stores;

import jfxFilesRenamer.Enumerators.Validation;

public class Store_Files {
	

	//**************************************************************
	//*********************** Declarations *************************
	//**************************************************************
	String fileID;
	String filePath;
	String parentFolder;
	String nameOriginal;
	String nameOriginalWithoutExt;
	String extension;
	String nameRenamed;
	String renamingStatus;
	Validation nameValidation;
	boolean readyForRename;
	boolean renameApplied;



	//**************************************************************
	//************************ Constructors ************************
	//**************************************************************
	
	public Store_Files() {
		super();
		this.fileID = "";
		this.filePath = "";
		this.parentFolder = "";
		this.nameOriginal = "";
		this.nameOriginalWithoutExt = "";
		this.nameRenamed = "";
		this.renamingStatus = "";
		this.extension = "";
		this.nameValidation = Validation.NOTRENAMED;
		this.readyForRename = false;
		this.renameApplied = false;
	}


	public Store_Files(String fileID, String filePath, String parentFolder, String nameOriginal, String nameOriginalWithoutExt,
			String extension, String nameRenamed,	String renamingStatus, boolean readyForRename, boolean appliedRename) {
		super();
		this.fileID = fileID;
		this.filePath = filePath;
		this.parentFolder = parentFolder;
		this.nameOriginal = nameOriginal;
		this.nameOriginalWithoutExt = nameOriginalWithoutExt;
		this.extension = extension;
		this.nameRenamed = nameRenamed;
		this.renamingStatus = renamingStatus;
		this.nameValidation = Validation.NOTRENAMED;
		this.readyForRename = readyForRename;
		this.renameApplied = appliedRename;
	}



	//**************************************************************
	//********************* Getters / Setters **********************
	//**************************************************************

	public String getFileID() {
		return fileID;
	}

	public String getNameOriginal() {
		return nameOriginal;
	}

	public String getNameOriginalWithoutExt() {
		return nameOriginalWithoutExt;
	}

	public String getNameRenamed() {
		return nameRenamed;
	}

	public String getRenamingStatus() {
		return renamingStatus;
	}

	public String getExtension() {
		return extension;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getParentFolder() {
		return parentFolder;
	}

	public Validation getNameValidation() {
		return nameValidation;
	}

	public boolean isReadyForRename() {
		return readyForRename;
	}

	public boolean isRenameApplied() {
		return renameApplied;
	}




	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	public void setNameOriginal(String nameOriginal) {
		this.nameOriginal = nameOriginal;
	}

	public void setNameOriginalWithoutExt(String nameOriginalWithoutExt) {
		this.nameOriginalWithoutExt = nameOriginalWithoutExt;
	}

	public void setNameRenamed(String nameRenamed) {
		this.nameRenamed = nameRenamed;
	}

	public void setRenamingStatus(String renamingStatus) {
		this.renamingStatus = renamingStatus;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setParentFolder(String parentFolder) {
		this.parentFolder = parentFolder;
	}

	public void setNameValidation(Validation nameValidation) {
		this.nameValidation = nameValidation;
	}

	public void setReadyForRename(boolean readyForRename) {
		this.readyForRename = readyForRename;
	}

	public void setRenameApplied(boolean renameApplied) {
		this.renameApplied = renameApplied;
	}




	//**************************************************************
	//************************** Methods ***************************
	//**************************************************************

	public void cancelRenaming() {
		this.setNameRenamed("");
		this.setRenamingStatus("");
		this.setReadyForRename(false);
		this.renameApplied = false;
	}


	@Override
	public String toString() {
		return "\nfileID=" + fileID + "\nfilePath=" + filePath + "\nparentFolder="
				+ parentFolder + "\nnameOriginal=" + nameOriginal
				+ "\nnameOriginalWithoutExt=" + nameOriginalWithoutExt
				+ "\nextension=" + extension + "\nnameRenamed=" + nameRenamed
				+ "\nrenamingStatus=" + renamingStatus + "\nnameValidation="
				+ nameValidation + "\nreadyForRename=" + readyForRename
				+ "\nrenameApplied=" + renameApplied + "\n-----------------------------------------------------------------";
	}


}
