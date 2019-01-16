package jfxFilesRenamer.Stores;

import jfxFilesRenamer.Enumerators.Validation;

public class Store_NameValidator {

	
	//**************************************************************
	//*********************** Declarations *************************
	//**************************************************************
	Boolean isValid;
	Validation fileStatus;
	String statusMessage;
		
	

	//**************************************************************
	//************************ Constructors ************************
	//**************************************************************
	public Store_NameValidator() {
		super();
		this.isValid = false;
		this.fileStatus = Validation.NEWNAMEEQUALSORIGINALNAME;
		
	}

	public Store_NameValidator(Boolean isValid, Validation fileStatus) {
		super();
		this.isValid = isValid;
		this.fileStatus = fileStatus;
	}

	
	

	
	//**************************************************************
	//********************* Getters / Setters **********************
	//**************************************************************

	public Boolean isValid() {
		return isValid;
	}

	public Validation getFileStatus() {
		return fileStatus;
	}

	public String getStatusMessage() {
		return statusMessage;
	}
	
	
	

	public void setValidation(Boolean isValid) {
		this.isValid = isValid;
	}

	public void setFileStatus(Validation fileStatus) {
		this.fileStatus = fileStatus;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	
	
	
	
	
	
	
	
	
	
	
}
