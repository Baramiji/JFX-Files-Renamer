package jfxFilesRenamer.Operations;

import java.util.function.Consumer;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import jfxFilesRenamer.MiscellaneousMethods;
import jfxFilesRenamer.MyThreadListener;
import jfxFilesRenamer.Enumerators.Actions;
import jfxFilesRenamer.Stores.Store_Files;
import jfxFilesRenamer.Stores.Store_NameValidator;
import jfxFilesRenamer.Stores.Store_ReturnedData;

public class Op_Extensions implements Runnable {

	//**************************************************************
	//*********************** Declarations *************************
	//**************************************************************

	private final Consumer<Store_ReturnedData> processor;
	MyThreadListener listener;

	Actions action;
	private ObservableList<Store_Files> observableList_Files;
	private String TF_ExtAdd;
	private String TF_ExtReplaceWith;
	private String TF_ExtReplace;
	private String TF_ExtRemove;
	private boolean CheckBox_ExtReplaceAll;
	private boolean CheckBox_ExtRemoveAll;
	private boolean RB_ExtUppercase;
	private boolean RB_ExtLowercase;





	//**************************************************************
	//************************ Constructors ************************
	//**************************************************************
	public Op_Extensions() {
		super();
		this.processor = null;
	}


	public Op_Extensions(Consumer<Store_ReturnedData> processor) {
		super();
		this.processor = processor;
	}


	//**************************************************************
	//********************* Getters / Setters **********************
	//**************************************************************

	public MyThreadListener getListener() {
		return this.listener;
	}

	public Actions getAction() {
		return action;
	}

	public ObservableList<Store_Files> getObservableList_Files() {
		return observableList_Files;
	}

	public String getTF_ExtAdd() {
		return TF_ExtAdd;
	}

	public String getTF_ExtReplaceWith() {
		return TF_ExtReplaceWith;
	}

	public String getTF_ExtReplace() {
		return TF_ExtReplace;
	}

	public String getTF_ExtRemove() {
		return TF_ExtRemove;
	}

	public boolean isCheckBox_ExtReplaceAll() {
		return CheckBox_ExtReplaceAll;
	}

	public boolean isCheckBox_ExtRemoveAll() {
		return CheckBox_ExtRemoveAll;
	}

	public boolean isRB_ExtUppercase() {
		return RB_ExtUppercase;
	}

	public boolean isRB_ExtLowercase() {
		return RB_ExtLowercase;
	}



	public void setListener(MyThreadListener listener) {
		this.listener = listener;
	}

	public void setAction(Actions action) {
		this.action = action;
	}

	public void setObservableList_Files(
			ObservableList<Store_Files> observableList_Files) {
		this.observableList_Files = observableList_Files;
	}

	public void setTF_ExtAdd(String tF_ExtAdd) {
		TF_ExtAdd = tF_ExtAdd;
	}

	public void setTF_ExtReplaceWith(String tF_ExtReplaceWith) {
		TF_ExtReplaceWith = tF_ExtReplaceWith;
	}

	public void setTF_ExtReplace(String tF_ExtReplace) {
		TF_ExtReplace = tF_ExtReplace;
	}

	public void setTF_ExtRemove(String tF_ExtRemove) {
		TF_ExtRemove = tF_ExtRemove;
	}

	public void setCheckBox_ExtReplaceAll(boolean cB_ExtReplaceAll) {
		CheckBox_ExtReplaceAll = cB_ExtReplaceAll;
	}

	public void setCheckBox_ExtRemoveAll(boolean cB_ExtRemoveAll) {
		CheckBox_ExtRemoveAll = cB_ExtRemoveAll;
	}

	public void setRB_ExtUppercase(boolean rB_ExtUppercase) {
		RB_ExtUppercase = rB_ExtUppercase;
	}

	public void setRB_ExtLowercase(boolean rB_ExtLowercase) {
		RB_ExtLowercase = rB_ExtLowercase;
	}




	//**************************************************************
	//************************** Methods ***************************
	//**************************************************************

	@Override
	public void run() {

		int filesCount = observableList_Files.size();
		int i = 0;

		try {

			for (Store_Files storeFile : observableList_Files) {

				String renamedName = "";

				String increment = String.format("(%d / %d)", ++i, filesCount);

				final Store_ReturnedData returnedData = new Store_ReturnedData();
				returnedData.setFileID(storeFile.getFileID());			
				returnedData.setCurrentFile(String.format("Scanning Infos ...     |   %s   |   %s", increment, storeFile.getNameOriginal()));
				returnedData.setLabelStatus("Scanning for infos ...");
				processor.accept(returnedData);

				String originalName = storeFile.getNameOriginal();
				String originalNameWithoutExt = storeFile.getNameOriginalWithoutExt();
				String extension = storeFile.getExtension();

				try {
					switch (action) {
						case EXTADD :
							renamedName = extAdd(originalName, extension);
							break;

						case EXTREPLACE :
							renamedName = extReplace(originalName, originalNameWithoutExt, extension);
							break;

						case EXTREMOVE :
							renamedName = extRemove(originalName, originalNameWithoutExt, extension);
							break;

						case EXTLETTERSCASE :
							renamedName = extLettersCase(originalName, originalNameWithoutExt, extension);
							break;

						default :
							break;
					}


					// check the filename validity
					Store_NameValidator filenameValidator = MiscellaneousMethods.filenameValidator(storeFile.getNameOriginal(), storeFile.getNameRenamed());
					returnedData.setValidation(filenameValidator.getFileStatus());

					if (filenameValidator.isValid()) {
						returnedData.setReadyForRename(true);
					} else {
						returnedData.setReadyForRename(false);
					}

					returnedData.setRenamedName(renamedName);

				}  catch (Exception e) {
					returnedData.setReadyForRename(false);

				} finally {
					returnedData.setSendedTimes(1);
					processor.accept(returnedData);
				}			
			}

		} catch (Exception e) {
			listener.threadFailed();
		}

		listener.threadFinished();

	}


	private String extAdd(String originalName, String extension) {
		if (extension.isEmpty()) {				
			String renamedName = fixExtension(TF_ExtAdd);

			if (renamedName.isEmpty()) {
				return originalName;
			} else {
				return String.format("%s.%s", originalName, renamedName);
			}

		} else {
			return originalName;
		}
	}


	private String extReplace(String originalName, String originalNameWithoutExt, String extension) {

		if (extension.isEmpty()) {
			return originalName;

		} else {		
			if (CheckBox_ExtReplaceAll) {
				if (fixExtension(TF_ExtReplaceWith).isEmpty()) {
					return originalName;
				} else {
					return String.format("%s.%s", originalNameWithoutExt, fixExtension(TF_ExtReplaceWith));
				}

			} else {
				if (fixExtension(TF_ExtReplace).isEmpty() || fixExtension(TF_ExtReplaceWith).isEmpty()) {
					MiscellaneousMethods.createMessageBox(AlertType.WARNING, "Error", "Extension", null).showAndWait();
					return "originalExtension";
				} else if (extension.equalsIgnoreCase(fixExtension(TF_ExtReplace))) {
					return originalName.replaceAll(fixExtension(TF_ExtReplace), fixExtension(TF_ExtReplaceWith));
				} else {
					return originalName;
				}
			}
		}
	}


	private String extRemove(String originalName, String originalNameWithoutExt, String extension) {

		if (CheckBox_ExtRemoveAll) {
			return originalNameWithoutExt;

		} else {
			if (fixExtension(TF_ExtRemove).isEmpty()) {
				MiscellaneousMethods.createMessageBox(AlertType.WARNING, "Error", "Extension", null).showAndWait();
				return "originalExtension";
			} else if (extension.equalsIgnoreCase(fixExtension(TF_ExtRemove))) {
				return originalNameWithoutExt;
			} else {
				return originalName;
			}
		}
	}


	private String extLettersCase(String originalName, String originalNameWithoutExt, String extension) {
		if (RB_ExtUppercase && !extension.isEmpty()) {
			return String.format("%s.%s", originalNameWithoutExt, extension.toUpperCase());
		} else if (RB_ExtLowercase && !extension.isEmpty()){
			return String.format("%s.%s", originalNameWithoutExt, extension.toLowerCase());
		} else {
			return originalName;
		}
	}




	private static String fixExtension(String extension) {

		String repairedExtension = "";

		repairedExtension = extension.trim()
				//.replaceAll("^\\.{0,}|\\.{2,}", ".")
				.replaceAll("\\.{2,}", ".")
				.replaceAll("^\\.{0,}|\\.{0,}$|[ ]+", "");

		return repairedExtension;
	}


}
