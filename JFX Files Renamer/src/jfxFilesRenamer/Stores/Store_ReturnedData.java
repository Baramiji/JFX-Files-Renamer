package jfxFilesRenamer.Stores;

import jfxFilesRenamer.Enumerators.Validation;

public class Store_ReturnedData {

	
	//**************************************************************
	//*********************** Declarations *************************
	//**************************************************************
	
	String fileID;
	String currentFile;
	String renamedName;
	Validation validation;
	String labelStatus;
	boolean ReadyForRename;
	int sendedTimes;


	
	//**************************************************************
	//************************ Constructors ************************
	//**************************************************************
	
	public Store_ReturnedData() {
		super();
		this.fileID = "";
		this.currentFile = String.format(" ...     |   %s   |   %s", "", "");
		this.renamedName = "";
		this.validation = Validation.NOTRENAMED;
		this.labelStatus = "";
		this.ReadyForRename = false;
		this.sendedTimes = 0;
	}

	

	//**************************************************************
	//********************* Getters / Setters **********************
	//**************************************************************

	public String getFileID() {
		return fileID;
	}

	public String getRenamedName() {
		return renamedName;
	}

	public boolean isReadyForRename() {
		return ReadyForRename;
	}

	public Validation getValidation() {
		return validation;
	}

	public String getCurrentFile() {
		return currentFile;
	}

	public String getLabelStatus() {
		return labelStatus;
	}
	
	public int getSendedTimes() {
		return sendedTimes;
	}
	
	


	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	public void setRenamedName(String renamedName) {
		this.renamedName = renamedName;
	}

	public void setReadyForRename(boolean readyForRename) {
		ReadyForRename = readyForRename;
	}

	public void setValidation(Validation validation) {
		this.validation = validation;
	}

	public void setCurrentFile(String currentFile) {
		this.currentFile = currentFile;
	}

	public void setLabelStatus(String labelStatus) {
		this.labelStatus = labelStatus;
	}

	public void setSendedTimes(int sendedTimes) {
		this.sendedTimes = sendedTimes;
	}
	


	//**************************************************************
	//************************** Methods ***************************
	//**************************************************************

	public String getValidationMessage() {

		String message = "";

		switch (validation) {
			case VALID:
				message = "Ready to be renamed";
				break;

			case INVALID:
				message = "Invalid name";
				break;

			case NOTRENAMED:
				message = "Not yet renamed";
				break;

			case NEWNAMEEQUALSORIGINALNAME:
				message = "No need to rename";
				break;

			case NEWNAMEALREADYEXISTS:
				message = "A file with the new name already exists";
				break;

			case NEWNAMEHASILLEGALCHARACTERS:
				message = "The new name contains an invalid character or more";
				break;

			case NEWNAMEEMPTY:
				message = "The new name is empty";
				break;

			default :
				break;
		}

		return message;
	}


}
