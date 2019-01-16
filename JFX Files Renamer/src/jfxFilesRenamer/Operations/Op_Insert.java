package jfxFilesRenamer.Operations;

import java.io.File;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import javafx.collections.ObservableList;
import javafx.scene.control.SingleSelectionModel;
import jfxFilesRenamer.MiscellaneousMethods;
import jfxFilesRenamer.MyThreadListener;
import jfxFilesRenamer.Enumerators.Actions;
import jfxFilesRenamer.Enumerators.Validation;
import jfxFilesRenamer.Stores.Store_Files;
import jfxFilesRenamer.Stores.Store_NameValidator;
import jfxFilesRenamer.Stores.Store_ReturnedData;

public class Op_Insert implements Runnable {


	//**************************************************************
	//*********************** Declarations *************************
	//**************************************************************

	private static DateTimeFormatter fileDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh'h'mm'm'ss");
	private final Consumer<Store_ReturnedData> processor;
	MyThreadListener listener;

	private Actions action;
	private ObservableList<Store_Files> observableList_Files;
	private int Spin_InsertCounterStart;
	private int Spin_InsertCounterIncrementStep;
	private int Spin_InsertCounterFormat;
	private int Spin_InsertAtPosition;
	private String TF_InsertText;
	private SingleSelectionModel<Object> CB_InsertSeparator;
	private SingleSelectionModel<Object> CB_InsertMiscellaneous;
	private boolean RB_InsertAtBegin;
	private boolean RB_InsertAtEnd;
	private boolean RB_InsertAtPosition;
	private boolean CB_InsertResetCounterFolder;



	//**************************************************************
	//************************ Constructors ************************
	//**************************************************************

	public Op_Insert() {
		super();
		this.processor = null;
	}


	public Op_Insert(Consumer<Store_ReturnedData> processor) {
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

	public int getSpin_InsertCounterStart() {
		return Spin_InsertCounterStart;
	}

	public int getSpin_InsertCounterIncrementStep() {
		return Spin_InsertCounterIncrementStep;
	}

	public String getTF_InsertText() {
		return TF_InsertText;
	}

	public int getSpin_InsertCounterFormat() {
		return Spin_InsertCounterFormat;
	}

	public int getSpin_InsertAtPosition() {
		return Spin_InsertAtPosition;
	}

	public SingleSelectionModel<Object> getCB_InsertSeparator() {
		return CB_InsertSeparator;
	}

	public SingleSelectionModel<Object> getCB_InsertMiscellaneous() {
		return CB_InsertMiscellaneous;
	}

	public boolean isRB_InsertAtBegin() {
		return RB_InsertAtBegin;
	}

	public boolean isRB_InsertAtEnd() {
		return RB_InsertAtEnd;
	}

	public boolean isRB_InsertAtPosition() {
		return RB_InsertAtPosition;
	}

	public boolean isCB_InsertResetCounterFolder() {
		return CB_InsertResetCounterFolder;
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

	public void setSpin_InsertCounterStart(int Spin_InsertCounterStart) {
		this.Spin_InsertCounterStart = Spin_InsertCounterStart;
	}

	public void setSpin_InsertCounterIncrementStep(
			int Spin_InsertCounterIncrementStep) {
		this.Spin_InsertCounterIncrementStep = Spin_InsertCounterIncrementStep;
	}

	public void setTF_InsertText(String tF_InsertText) {
		TF_InsertText = tF_InsertText;
	}

	public void setSpin_InsertCounterFormat(int spin_InsertCounterFormat) {
		Spin_InsertCounterFormat = spin_InsertCounterFormat;
	}

	public void setSpin_InsertAtPosition(int spin_InsertAtPosition) {
		Spin_InsertAtPosition = spin_InsertAtPosition;
	}

	public void setCB_InsertSeparator(
			SingleSelectionModel<Object> cB_InsertSeparator) {
		CB_InsertSeparator = cB_InsertSeparator;
	}

	public void setCB_InsertMiscellaneous(
			SingleSelectionModel<Object> cB_InsertMiscellaneous) {
		CB_InsertMiscellaneous = cB_InsertMiscellaneous;
	}

	public void setRB_InsertAtBegin(boolean rB_InsertAtBegin) {
		RB_InsertAtBegin = rB_InsertAtBegin;
	}

	public void setRB_InsertAtEnd(boolean rB_InsertAtEnd) {
		RB_InsertAtEnd = rB_InsertAtEnd;
	}

	public void setRB_InsertAtPosition(boolean rB_InsertAtPosition) {
		RB_InsertAtPosition = rB_InsertAtPosition;
	}

	public void setCB_InsertResetCounterFolder(
			boolean cB_InsertResetCounterFolder) {
		CB_InsertResetCounterFolder = cB_InsertResetCounterFolder;
	}





	//**************************************************************
	//************************** Methods ***************************
	//**************************************************************

	@Override
	public void run() {

		switch (action) {
			case INSERTTEXT :
				Insert(Actions.INSERTTEXT);
				break;

			case INSERTNUMBER :
				insertNumber();
				break;

			case INSERTMISCELLANEOUS :
				Insert(Actions.INSERTMISCELLANEOUS);
				break;

			default :
				break;
		}
	}


	private void Insert(Actions action) {

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

				switch (action) {
					case INSERTTEXT :
						renamedName = insertText(storeFile.getNameOriginalWithoutExt());
						break;

					case INSERTMISCELLANEOUS :
						renamedName = insertMiscellaneous(storeFile.getFilePath(), storeFile.getNameOriginalWithoutExt());
						break;

					default :
						break;
				}	

				try {
					if (renamedName.trim().isEmpty()) {				
						storeFile.setNameRenamed("");
						storeFile.setReadyForRename(false);
						returnedData.setRenamedName("");
						returnedData.setReadyForRename(false);
						returnedData.setValidation(Validation.NEWNAMEEMPTY);
						continue;
					}

					if (storeFile.getExtension().isEmpty()) {
						storeFile.setNameRenamed(renamedName);
					} else {
						renamedName = renamedName + "." + storeFile.getExtension();
						storeFile.setNameRenamed(renamedName);
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

				} catch (Exception e) {
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




	private void insertNumber() {

		HashMap<String, Integer> parentFoldersMap = new HashMap<>();
		List<File> unnamedFiles = new ArrayList<>();

		// Sort the observableList_Files by Parent Folders
		observableList_Files.sort(new Comparator<Store_Files>() {
			@Override
			public int compare(Store_Files o1, Store_Files o2) {
				return o1.getParentFolder().compareToIgnoreCase(o2.getParentFolder());
			}
		});

		//
		if (CB_InsertResetCounterFolder) {
			// Create a HashMap of Parent directories
			for (Store_Files storeFile : observableList_Files) {
				if (!parentFoldersMap.containsKey(storeFile.getParentFolder())) {
					parentFoldersMap.put(storeFile.getParentFolder(), Spin_InsertCounterStart);
				}
			}
		}


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


				try {
					final String parentFolder = storeFile.getParentFolder();
					final String originalFilePath = storeFile.getFilePath();
					final String extension = storeFile.getExtension();
					final String originalNameWithoutExt = storeFile.getNameOriginalWithoutExt();

					File renamedFile = null;
					boolean isExistsInObsFiles = false;


					if (unnamedFiles.contains(new File(originalFilePath))) {
						storeFile.setNameRenamed(originalNameWithoutExt);
					} else {
						while (renamedFile == null || renamedFile.exists() || isExistsInObsFiles) {
							int fileIncrement = 0;
							if (CB_InsertResetCounterFolder) {
								fileIncrement = parentFoldersMap.get(parentFolder);	
								renamedName = insertNumber(originalNameWithoutExt, parentFoldersMap.get(parentFolder));
							} else {
								renamedName = insertNumber(originalNameWithoutExt, Spin_InsertCounterStart);
							}

							if (!extension.isEmpty())
								renamedName = renamedName + "." +  extension;	

							renamedFile = new File(parentFolder + "/" + renamedName);	

							if (renamedFile.exists()) {
								unnamedFiles.add(renamedFile);
							} else {
								storeFile.setNameRenamed(renamedName);
							}

							isExistsInObsFiles = existsInObservableFiles(renamedName);
							if (CB_InsertResetCounterFolder) {
								fileIncrement = fileIncrement + Spin_InsertCounterIncrementStep;
								parentFoldersMap.put(parentFolder, fileIncrement);
							} else {
								Spin_InsertCounterStart = Spin_InsertCounterStart + Spin_InsertCounterIncrementStep;	
							}
						}
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

				} catch (Exception e) {
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








	private String insertText(String originalName) {

		if (TF_InsertText.isEmpty()) 
			return originalName;

		return insertFunction(originalName, TF_InsertText);
	}


	private String insertNumber(String originalName, Integer incrementedNum) {
		if (Spin_InsertCounterFormat == 0) {
			return insertFunction(originalName, String.format("%d", incrementedNum));
		} else {
			String numberFormat = String.join("", Collections.nCopies(Spin_InsertCounterFormat, "0"));
			return insertFunction(originalName, String.format("%s", new DecimalFormat(String.format("%s", numberFormat)).format(incrementedNum)));
		}
	}


	private String insertMiscellaneous(String fileLocation, String originalName) {

		String result = "";
		Date currentDate = java.util.Calendar.getInstance().getTime();

		if (!CB_InsertMiscellaneous.isEmpty()) {
			switch (CB_InsertMiscellaneous.getSelectedIndex()) {
				case 0 :
					result = new SimpleDateFormat("dd-MM-yyyy").format(currentDate);
					break;

				case 1 :
					result = new SimpleDateFormat("dd").format(currentDate);
					break;

				case 2 :
					DateFormat df = new SimpleDateFormat("EEEE");
					result = Character.toUpperCase(df.format(currentDate).charAt(0)) + df.format(currentDate).substring(1);
					break;

				case 3 :
					DateFormat df1 = new SimpleDateFormat("EEE");
					result = Character.toUpperCase(df1.format(currentDate).charAt(0)) + df1.format(currentDate).substring(1);
					break;

				case 4 :
					result = new SimpleDateFormat("MM").format(currentDate);
					break;

				case 5 :
					DateFormat df3 = new SimpleDateFormat("MMMM");
					result = Character.toUpperCase(df3.format(currentDate).charAt(0)) + df3.format(currentDate).substring(1);
					break;

				case 6 :
					DateFormat df4 = new SimpleDateFormat("MMM");
					result = Character.toUpperCase(df4.format(currentDate).charAt(0)) + df4.format(currentDate).substring(1);
					break;

				case 7 :
					result = new SimpleDateFormat("yyyy").format(currentDate);
					break;

				case 8 :					
					result = new SimpleDateFormat("hh'h'mm'm'ss").format(currentDate);
					break;

				case 9 :
					LocalDateTime creationDate = MiscellaneousMethods.getFileDateTime(fileLocation, "Creation");
					if (creationDate != null) 
						result = creationDate.format(fileDateFormatter);
					break;

				case 10 :	
					LocalDateTime modificationDate = MiscellaneousMethods.getFileDateTime(fileLocation, "Modification");
					if (modificationDate != null) 
						result = modificationDate.format(fileDateFormatter);
					break;

				case 11 :	
					LocalDateTime accessDate = MiscellaneousMethods.getFileDateTime(fileLocation, "Access");
					if (accessDate != null) 
						result = accessDate.format(fileDateFormatter);
					break;

				default :
					break;
			}
		}

		return insertFunction(originalName, result);
	}


	private String insertFunction(String originalName, String insertString) {

		String result = "";

		if (insertString.isEmpty()) {
			return originalName;
		} else {
			if (!RB_InsertAtPosition){
				if (RB_InsertAtBegin) {
					result = insertSeparatorPosition(insertString) + originalName;
				} else if (RB_InsertAtEnd){
					result = originalName + insertSeparatorPosition(insertString);
				} 

			} else {
				StringBuilder sb = new StringBuilder(originalName);
				if (Spin_InsertAtPosition >= originalName.length()) {
					result = originalName + insertSeparatorPosition(insertString);
				} else {
					result = sb.insert(Spin_InsertAtPosition, insertSeparatorPosition(insertString)).toString();
				}
			}
		}

		return result;
	}


	private String insertSeparatorPosition(String theString) {

		String result = "";

		if (RB_InsertAtBegin || RB_InsertAtPosition) {
			switch (CB_InsertSeparator.getSelectedIndex()) {
				case 0 :
					result = "(" + theString + ") ";
					break;
				case 1 :
					result = "-" + theString + "- ";
					break;
				case 2 :
					result = "_" + theString + "_ ";
					break;
				case 3 :
					result = theString + " - ";
					break;
				case 4 :
					result = theString + " _ ";
					break;
				case 5 :
					result = theString + " ";
					break;
				case 6 :
					result = theString;
					break;
				default :
					break;
			}

		} else if (RB_InsertAtEnd){
			switch (CB_InsertSeparator.getSelectedIndex()) {
				case 0 :
					result = " (" + theString + ")";
					break;
				case 1 :
					result = " -" + theString + "-";
					break;
				case 2 :
					result = " _" + theString + "_";
					break;
				case 3 :
					result = " - " + theString;
					break;
				case 4 :
					result = " _ " + theString;
					break;
				case 5 :
					result = " " + theString;
					break;
				case 6 :
					result = theString;
					break;
				default :
					break;
			}
		} 

		return result;
	}


	private boolean existsInObservableFiles(String value) {
		return observableList_Files.stream().anyMatch(p -> (Paths.get("/", p.getParentFolder(), p.getNameRenamed()).toString()).equals(value));
	}
}
