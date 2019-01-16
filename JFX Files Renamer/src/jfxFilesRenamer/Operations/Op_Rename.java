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
import jfxFilesRenamer.Stores.Store_Files;
import jfxFilesRenamer.Stores.Store_FilesIncrement;
import jfxFilesRenamer.Stores.Store_NameValidator;
import jfxFilesRenamer.Stores.Store_ReturnedData;

public class Op_Rename implements Runnable {

	//**************************************************************
	//*********************** Declarations *************************
	//**************************************************************

	private static final DateTimeFormatter fileDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh'h'mm'm'ss");
	private List<Store_FilesIncrement> storeFI_List = new ArrayList<>();
	private HashMap<String, Integer> filesIncrementMap = new HashMap<>();

	private final Consumer<Store_ReturnedData> processor;
	MyThreadListener listener;

	private Actions action;
	private ObservableList<Store_Files> observableList_Files;
	private SingleSelectionModel<Object> ChoiceBox_RenameMiscellaneous;
	private SingleSelectionModel<Object> ChoiceBox_RenameSeparator;
	private String TF_RenameText;
	private int Spin_RenameCounterFormat;
	private int Spin_RenameCounterIncrementStep;
	private int Spin_RenameCounterStart;
	private boolean CheckBox_RenameResetCounterFolder;




	//**************************************************************
	//************************ Constructors ************************
	//**************************************************************

	public Op_Rename() {
		super();
		this.processor = null;
		storeFI_List.clear();
		filesIncrementMap.clear();
	}

	public Op_Rename(Consumer<Store_ReturnedData> processor) {
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

	public SingleSelectionModel<Object> getChoiceBox_RenameMiscellaneous() {
		return ChoiceBox_RenameMiscellaneous;
	}

	public SingleSelectionModel<Object> getChoiceBox_RenameSeparator() {
		return ChoiceBox_RenameSeparator;
	}

	public String getTF_RenameText() {
		return TF_RenameText;
	}

	public int getSpin_RenameCounterFormat() {
		return Spin_RenameCounterFormat;
	}

	public int getSpin_RenameCounterIncrementStep() {
		return Spin_RenameCounterIncrementStep;
	}

	public int getSpin_RenameCounterStart() {
		return Spin_RenameCounterStart;
	}

	public boolean isCheckBox_RenameResetCounterFolder() {
		return CheckBox_RenameResetCounterFolder;
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

	public void setChoiceBox_RenameMiscellaneous(
			SingleSelectionModel<Object> choiceBox_RenameMiscellaneous) {
		ChoiceBox_RenameMiscellaneous = choiceBox_RenameMiscellaneous;
	}

	public void setChoiceBox_RenameSeparator(
			SingleSelectionModel<Object> choiceBox_RenameSeparator) {
		ChoiceBox_RenameSeparator = choiceBox_RenameSeparator;
	}

	public void setTF_RenameText(String tF_RenameText) {
		TF_RenameText = tF_RenameText;
	}

	public void setSpin_RenameCounterFormat(int spin_RenameCounterFormat) {
		Spin_RenameCounterFormat = spin_RenameCounterFormat;
	}

	public void setSpin_RenameCounterIncrementStep(
			int spin_RenameCounterIncrementStep) {
		Spin_RenameCounterIncrementStep = spin_RenameCounterIncrementStep;
	}

	public void setSpin_RenameCounterStart(int spin_RenameCounterStart) {
		Spin_RenameCounterStart = spin_RenameCounterStart;
	}

	public void setCheckBox_RenameResetCounterFolder(
			boolean checkBox_RenameResetCounterFolder) {
		CheckBox_RenameResetCounterFolder = checkBox_RenameResetCounterFolder;
	}



	//**************************************************************
	//************************** Methods ***************************
	//**************************************************************

	@Override
	public void run() {

		switch (action) {
			case RENAMENUMBER :
				renameNumber();
				break;

			case RENAMEMISCELLANEOUS :
				renameMiscellaneous();
				break;

			default :
				break;
		}	

	}




	private void renameNumber() {

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
		if (CheckBox_RenameResetCounterFolder) {
			// Create a HashMap of Parent directories
			for (Store_Files storeFile : observableList_Files) {
				if (!parentFoldersMap.containsKey(storeFile.getParentFolder())) {
					parentFoldersMap.put(storeFile.getParentFolder(), Spin_RenameCounterStart);
				}
			}
		}


		int filesCount = observableList_Files.size();
		int i = 0;

		try {

			for (Store_Files storeFile : observableList_Files) {

				String increment = String.format("(%d / %d)", ++i, filesCount);

				final Store_ReturnedData returnedData = new Store_ReturnedData();
				returnedData.setFileID(storeFile.getFileID());			
				returnedData.setCurrentFile(String.format("Scanning Infos ...     |   %s   |   %s", increment, storeFile.getNameOriginal()));
				returnedData.setLabelStatus("Scanning for infos ...");
				processor.accept(returnedData);


				final String parentFolder = storeFile.getParentFolder();
				final String originalFilePath = storeFile.getFilePath();
				final String originalNameWithoutExt = storeFile.getNameOriginalWithoutExt();
				final String extension = storeFile.getExtension();

				File renamedFile = null;
				boolean isExistsInObsFiles = false;

				try {
					if (unnamedFiles.contains(new File(originalFilePath))) {
						storeFile.setNameRenamed(originalNameWithoutExt);
						storeFile.setReadyForRename(false);
						returnedData.setRenamedName(originalNameWithoutExt);
						returnedData.setReadyForRename(false);
						// processor.accept(returnedData);

					} else {
						String renamedName = "";
						while (renamedFile == null || renamedFile.exists() || isExistsInObsFiles) {
							int fileIncrement = 0;
							if (CheckBox_RenameResetCounterFolder) {
								fileIncrement = parentFoldersMap.get(parentFolder);	
								renamedName = insertNumber(fileIncrement);
							} else {
								renamedName = insertNumber(Spin_RenameCounterStart);
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
							if (CheckBox_RenameResetCounterFolder) {
								fileIncrement = fileIncrement + Spin_RenameCounterIncrementStep;
								parentFoldersMap.put(parentFolder, fileIncrement);
							} else {
								Spin_RenameCounterStart = Spin_RenameCounterStart + Spin_RenameCounterIncrementStep;	
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
					}
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

	private void renameMiscellaneous() {

		HashMap<String, Integer> filesIncrementMap = new HashMap<>();
		List<Store_FilesIncrement> storeFI_List = new ArrayList<>();

		int filesCount = observableList_Files.size();
		int i = 0;

		try {

			for (Store_Files storeFile : observableList_Files) {

				String increment = String.format("(%d / %d)", ++i, filesCount);

				final Store_ReturnedData returnedData = new Store_ReturnedData();
				returnedData.setFileID(storeFile.getFileID());			
				returnedData.setCurrentFile(String.format("Scanning Infos ...     |   %s   |   %s", increment, storeFile.getNameOriginal()));
				returnedData.setLabelStatus("Scanning for infos ...");
				processor.accept(returnedData);


				Store_Files SF = observableList_Files.stream()
						.filter(v -> v.getNameOriginal().equals(storeFile.getNameOriginal()))
						.findFirst()
						.get();


				String originalNameWithoutExt = SF.getNameOriginalWithoutExt();
				String extension = SF.getExtension();
				String filePath = SF.getFilePath();
				String parentFolder = SF.getParentFolder();

				try {
					String renamedName = insertMiscellaneous(filePath, -1);
					String renamedFilePathWithoutExt = Paths.get(parentFolder, renamedName).toString();

					// Create a HashMap containing the number ...
					if (storeFI_List.stream().anyMatch(p -> p.getRenamedFileWithoutExtPath().equals(renamedFilePathWithoutExt))) {
						filesIncrementMap.put(renamedFilePathWithoutExt, filesIncrementMap.getOrDefault(renamedFilePathWithoutExt, 0) + 1);
						renamedName = insertMiscellaneous(filePath, filesIncrementMap.get(renamedFilePathWithoutExt));
					} else {
						filesIncrementMap.put(renamedFilePathWithoutExt, 0);
					}

					//
					Store_FilesIncrement storeFileIncrment = new Store_FilesIncrement();
					storeFileIncrment.setRenamedFileWithoutExtPath(Paths.get(parentFolder, renamedName).toString());
					storeFI_List.add(storeFileIncrment);


					if (extension.isEmpty()) {
						SF.setNameRenamed(renamedName);
					} else {
						if (renamedName.isEmpty()) {
							SF.setNameRenamed(originalNameWithoutExt + "." + extension);
						} else {
							renamedName = renamedName + "." + extension;
							SF.setNameRenamed(renamedName);
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



	private String insertNumber(Integer incrementedNum) {

		if (Spin_RenameCounterFormat == 0) {
			return TF_RenameText + renameSeparatorPosition(String.format("%d", incrementedNum)); 
		} else {
			String numberFormat = String.join("", Collections.nCopies(Spin_RenameCounterFormat, "0"));
			return TF_RenameText + renameSeparatorPosition(String.format("%s", new DecimalFormat(String.format("%s", numberFormat)).format(incrementedNum))); 
		}
	}


	private String renameSeparatorPosition(String theString) {

		String result = "";

		switch (ChoiceBox_RenameSeparator.getSelectedIndex()) {
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
				result =  " - " + theString;
				break;
			case 4 :
				result = " _ " + theString;;
				break;
			case 5 :
				result = " " + theString;;
				break;
			case 6 :
				result = theString;
				break;
			default :
				break;
		}

		return result;
	}


	private String insertMiscellaneous(String fileLocation, int incrementedNum) {

		String result = "";
		Date currentDate = java.util.Calendar.getInstance().getTime();

		if (!ChoiceBox_RenameMiscellaneous.isEmpty()) {
			switch (ChoiceBox_RenameMiscellaneous.getSelectedIndex()) {
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

		if (incrementedNum == -1) {
			return TF_RenameText + renameSeparatorPosition(result); 
		} else {
			return TF_RenameText + renameSeparatorPosition(result) + String.format(" (%d)", incrementedNum); 
		}
	}




	private boolean existsInObservableFiles(String value) {
		return observableList_Files.stream().anyMatch(p -> (Paths.get("/", p.getParentFolder(), p.getNameRenamed()).toString()).equals(value));
	}

}
