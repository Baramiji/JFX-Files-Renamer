package jfxFilesRenamer.Stores;

public class Store_CommonExtension {

	
	//**************************************************************
	//*********************** Declarations *************************
	//**************************************************************
	private String type;
	private String extension;
	
	
	
	
	//**************************************************************
	//************************ Constructors ************************
	//**************************************************************
	
	public Store_CommonExtension() {
		super();
		this.type = "";
		this.extension = "";
	}
	
	public Store_CommonExtension(String type, String extension) {
		super();
		this.type = type;
		this.extension = extension;
	}

		
	
	
	//**************************************************************
	//********************* Getters / Setters **********************
	//**************************************************************
	
	public String getType() {
		return type;
	}

	public String getExtension() {
		return extension;
	}

	
	
	public void setType(String type) {
		this.type = type;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	


}
