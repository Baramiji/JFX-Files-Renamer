package jfxFilesRenamer.Extensions;

public class FilenameInformation {

	String simpleExtension;
	String complexeExtension;
	String baseFilename_SimpleExt;
	String baseFilename_ComplexeExt;
	
	public FilenameInformation() {
		super();
		
		simpleExtension = "";
		complexeExtension = "";
		baseFilename_SimpleExt = "";
		baseFilename_ComplexeExt = "";
	}

	public String getSimpleExtension() {
		return simpleExtension;
	}

	public String getComplexeExtension() {
		return complexeExtension;
	}

	public String getBaseFilename_SimpleExt() {
		return baseFilename_SimpleExt;
	}

	public String getBaseFilename_ComplexeExt() {
		return baseFilename_ComplexeExt;
	}

	public void setSimpleExtension(String theSimpleExtension) {
		simpleExtension = theSimpleExtension;
	}

	public void setComplexeExtension(String theComplexeExtension) {
		complexeExtension = theComplexeExtension;
	}

	public void setBaseFilename_SimpleExt(String theBaseFilename_SimpleExt) {
		baseFilename_SimpleExt = theBaseFilename_SimpleExt;
	}

	public void setBaseFilename_ComplexeExt(String theBaseFilename_ComplexeExt) {
		baseFilename_ComplexeExt = theBaseFilename_ComplexeExt;
	}
	
}
