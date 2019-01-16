package jfxFilesRenamer.Operations;

import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.scene.control.SingleSelectionModel;
import jfxFilesRenamer.MiscellaneousMethods;
import jfxFilesRenamer.MyThreadListener;
import jfxFilesRenamer.OS_Detector;
import jfxFilesRenamer.Enumerators.Actions;
import jfxFilesRenamer.Enumerators.Validation;
import jfxFilesRenamer.Stores.Store_Files;
import jfxFilesRenamer.Stores.Store_FilesIncrement;
import jfxFilesRenamer.Stores.Store_NameValidator;
import jfxFilesRenamer.Stores.Store_ReturnedData;



public class Op_Replace implements Runnable {

	//**************************************************************
	//*********************** Declarations *************************
	//**************************************************************

	private static final String splitCamelCasePattern = String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])");
	private List<Store_FilesIncrement> storeFI_List = new ArrayList<>();
	private HashMap<String, Integer> filesIncrementMap = new HashMap<>();

	private final Consumer<Store_ReturnedData> processor;
	MyThreadListener listener;

	private Actions action;
	private ObservableList<Store_Files> observableList_Files;
	private String TF_ReplaceFind;
	private String TF_ReplaceWith;
	private String TF_ReplaceMultipleOccurrences;
	private boolean CB_ReplaceRegex;
	private boolean CB_ReplaceCaseSensitive;
	private SingleSelectionModel<Object> CB_LettersCase;
	private SingleSelectionModel<Object> CB_ReplaceMiscellaneous;



	//**************************************************************
	//************************ Constructors ************************
	//**************************************************************

	public Op_Replace() {
		super();
		this.processor = null;
		storeFI_List.clear();
		filesIncrementMap.clear();
	}

	public Op_Replace(Consumer<Store_ReturnedData> processor) {
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

	public String getTF_ReplaceFind() {
		return TF_ReplaceFind;
	}

	public String getTF_ReplaceWith() {
		return TF_ReplaceWith;
	}

	public String getTF_ReplaceMultipleOccurrences() {
		return TF_ReplaceMultipleOccurrences;
	}

	public boolean isCB_ReplaceRegex() {
		return CB_ReplaceRegex;
	}

	public boolean isCB_ReplaceCaseSensitive() {
		return CB_ReplaceCaseSensitive;
	}

	public SingleSelectionModel<Object> getCB_LettersCase() {
		return CB_LettersCase;
	}

	public SingleSelectionModel<Object> getCB_ReplaceMiscellaneous() {
		return CB_ReplaceMiscellaneous;
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

	public void setTF_ReplaceFind(String tF_ReplaceFind) {
		TF_ReplaceFind = tF_ReplaceFind;
	}

	public void setTF_ReplaceWith(String tF_ReplaceWith) {
		TF_ReplaceWith = tF_ReplaceWith;
	}

	public void setTF_ReplaceMultipleOccurrences(
			String tF_ReplaceMultipleOccurrences) {
		TF_ReplaceMultipleOccurrences = tF_ReplaceMultipleOccurrences;
	}

	public void setCB_ReplaceRegex(boolean cB_ReplaceRegex) {
		CB_ReplaceRegex = cB_ReplaceRegex;
	}

	public void setCB_ReplaceCaseSensitive(boolean cB_ReplaceCaseSensitive) {
		CB_ReplaceCaseSensitive = cB_ReplaceCaseSensitive;
	}

	public void setCB_LettersCase(SingleSelectionModel<Object> cB_LettersCase) {
		CB_LettersCase = cB_LettersCase;
	}

	public void setCB_ReplaceMiscellaneous(
			SingleSelectionModel<Object> cB_ReplaceMiscellaneous) {
		CB_ReplaceMiscellaneous = cB_ReplaceMiscellaneous;
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
				returnedData.setCurrentFile(String.format("Replacing ...     |   %s   |   %s", increment, storeFile.getNameOriginal()));
				returnedData.setLabelStatus("Replacing ...");
				processor.accept(returnedData);

				try {
					switch (action) {
						case REPLACETEXT :
							renamedName = replaceText(storeFile.getNameOriginalWithoutExt()).trim();
							break;

						case REPLACELETTERSCASE :
							renamedName = replaceLettersCase(storeFile.getNameOriginalWithoutExt());
							break;

						case REPLACEMULTIPLEOCCURENCES :
							renamedName = replaceMultipleOccurrences(storeFile.getNameOriginalWithoutExt());
							break;

						case REPLACEMISCELLANEOUS :
							renamedName = replaceMiscellaneous(storeFile.getNameOriginalWithoutExt());
							break;

						default :
							break;
					}	


					if (renamedName.trim().isEmpty()) {
						storeFile.setNameRenamed("");
						storeFile.setReadyForRename(false);
						returnedData.setRenamedName("");
						returnedData.setReadyForRename(false);
						returnedData.setValidation(Validation.NEWNAMEEMPTY);
						continue;
					}				

					if (!renamedName.equals(storeFile.getNameOriginalWithoutExt())) 
						renamedName = incrementDuplicateNames(storeFile.getParentFolder(), renamedName, storeFile.getNameOriginalWithoutExt(), storeFile.getExtension());

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



	private String replaceText(String originalName) {

		String replacedName = originalName;

		try {
			if (CB_ReplaceRegex && CB_ReplaceCaseSensitive) {
				replacedName = originalName.replaceAll(TF_ReplaceFind, TF_ReplaceWith);

			} else if (CB_ReplaceRegex && !CB_ReplaceCaseSensitive){
				replacedName = originalName.replaceAll("(?i)" + TF_ReplaceFind, TF_ReplaceWith);

			}  else if (!CB_ReplaceRegex && CB_ReplaceCaseSensitive){
				replacedName = originalName.replace(TF_ReplaceFind, TF_ReplaceWith);

			} else {
				replacedName = originalName.replaceAll("(?i)" + Pattern.quote(TF_ReplaceFind), TF_ReplaceWith);
			}
		} catch (PatternSyntaxException e) {
			System.out.println(e.getMessage());
		}

		return replacedName;
	}


	private String replaceLettersCase(String originalName) {

		String result = "";

		if (!CB_LettersCase.isEmpty()) {
			switch (CB_LettersCase.getSelectedIndex()) {
				case 0 :
					result = originalName.substring(0, 1).toUpperCase() + originalName.substring(1);
					break;

				case 1 :
					result = Pattern.compile("\\s").splitAsStream(originalName).map(Op_Replace::upperCase).collect(Collectors.joining(" "));
					break;

				case 2 :
					result = originalName.toUpperCase();
					break;

				case 4 :
					result = originalName.substring(0, 1).toLowerCase() + originalName.substring(1);
					break;

				case 5 :
					result = Pattern.compile("\\s").splitAsStream(originalName).map(Op_Replace::lowerCase).collect(Collectors.joining(" "));
					break;

				case 6 :
					result = originalName.toLowerCase();
					break;

				case 8 :
					if (randomCase() == 0) {
						result = originalName.substring(0, 1).toLowerCase() + originalName.substring(1);	
					} else {
						result = originalName.substring(0, 1).toUpperCase() + originalName.substring(1);		
					}
					break;

				case 9 :
					if (randomCase() == 0) {
						result = Pattern.compile("\\s").splitAsStream(originalName).map(Op_Replace::lowerCase).collect(Collectors.joining(" "));
					} else {
						result = Pattern.compile("\\s").splitAsStream(originalName).map(Op_Replace::upperCase).collect(Collectors.joining(" "));
					}
					break;

				case 10 :					
					for (char ch: originalName.toCharArray()) {
						if (randomCase() == 0) {
							result += Character.toLowerCase(ch);							
						} else {						
							result += Character.toUpperCase(ch);
						}
					}					

					break;

				case 11 :					
					if (randomCase() == 0) {
						result = originalName.toLowerCase();							
					} else {						
						result = originalName.toUpperCase();
					}					

					break;

				case 13 :	
					result = splitCamelCase(originalName);

					break;

				default :
					break;
			}
		}

		return result;
	}


	private String replaceMultipleOccurrences(String originalName) {

		// String regexString = String.format("(?i)%s{2,}", escapeRegExp(TF_ReplaceMultipleOccurrences));
		String regexString = String.format("(?i)%s{2,}", Pattern.quote(TF_ReplaceMultipleOccurrences));
		return Pattern.compile(regexString, Pattern.CASE_INSENSITIVE).matcher(originalName).replaceAll(TF_ReplaceMultipleOccurrences);

	}


	private String replaceMiscellaneous(String originalName) {

		String renamedString = "";

		if (!CB_ReplaceMiscellaneous.isEmpty()) {
			switch (CB_ReplaceMiscellaneous.getSelectedIndex()) {
				case 0 :
					renamedString = Normalizer.normalize(originalName, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
					break;

				default :
					break;
			}
		}

		return renamedString;

	}


	private static String upperCase(String theString) {
		if (theString.equals(""))
			return theString;

		return theString.substring(0, 1).toUpperCase() + theString.substring(1);
	}

	private static String lowerCase(String theString) {
		if (theString.equals(""))
			return theString;

		return theString.substring(0, 1).toLowerCase() + theString.substring(1);
	}

	private static int randomCase() {
		Random r = new Random();
		return r.ints(0, 2).findFirst().getAsInt(); 	
	}





	private static String splitCamelCase(String text) {

		// Way 1
		return Pattern.compile(splitCamelCasePattern).splitAsStream(text).collect(Collectors.joining(" "));	

		//// Way 2
		// return text.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])" )," ");
	}


	private String incrementDuplicateNames(String parentFolder, String renamedName, String originalNameWithoutExt, String extension) {

		Store_FilesIncrement storeFileIncrement = new Store_FilesIncrement();
		String result = ""; 

		if (extension == null || extension.isEmpty()) {
			final String renamedFilePathWithoutExt;
			boolean isExistsInObsFiles = false;

			if (OS_Detector.iS_LINUX) {   // Case Sensitive file names can be a probleme with Windows
				renamedFilePathWithoutExt = Paths.get("/", parentFolder, renamedName).toString();	
				isExistsInObsFiles = observableList_Files.stream().anyMatch(p -> (Paths.get("/", parentFolder, p.getNameRenamed()).toString()).equals(renamedFilePathWithoutExt)
						|| (Paths.get("/", parentFolder, p.getNameOriginalWithoutExt()).toString()).equals(renamedFilePathWithoutExt));
			} else {
				renamedFilePathWithoutExt = Paths.get(parentFolder, renamedName.toLowerCase()).toString();
				isExistsInObsFiles = observableList_Files.stream().anyMatch(p -> (Paths.get("/", parentFolder, p.getNameRenamed()).toString()).equalsIgnoreCase(renamedFilePathWithoutExt)
						|| (Paths.get("/", parentFolder, p.getNameOriginalWithoutExt()).toString()).equalsIgnoreCase(renamedFilePathWithoutExt));
			}	


			// Create a HashMap containing the number ...
			if (isExistsInObsFiles || storeFI_List.stream().anyMatch(p -> p.getRenamedFileWithoutExtPath().equals(renamedFilePathWithoutExt))) {
				filesIncrementMap.put(renamedFilePathWithoutExt, filesIncrementMap.getOrDefault(renamedFilePathWithoutExt, 0) + 1);
				result = String.format("%s (%d)", renamedName, filesIncrementMap.get(renamedFilePathWithoutExt));
			} else {
				filesIncrementMap.put(renamedFilePathWithoutExt, 0);
				result = renamedName;
			}

			//
			storeFileIncrement.setRenamedFileWithoutExtPath(renamedFilePathWithoutExt);


		} else {
			final String renamedFilePathWithExt;
			final boolean isExistsInObsFiles;

			if (OS_Detector.iS_LINUX) {
				renamedFilePathWithExt = String.format("%s.%s", Paths.get(parentFolder, renamedName).toString(), extension);
				isExistsInObsFiles = observableList_Files.stream().anyMatch(p -> (Paths.get("/", parentFolder, p.getNameRenamed()).toString()).equals(renamedFilePathWithExt)
						|| (Paths.get("/", parentFolder, p.getNameOriginal()).toString()).equals(renamedFilePathWithExt));
			} else {
				renamedFilePathWithExt = String.format("%s.%s", Paths.get(parentFolder, renamedName.toLowerCase()).toString(), extension);
				isExistsInObsFiles = observableList_Files.stream().anyMatch(p -> (Paths.get("/", parentFolder, p.getNameRenamed()).toString()).equalsIgnoreCase(renamedFilePathWithExt)
						|| (Paths.get("/", parentFolder, p.getNameOriginal()).toString()).equalsIgnoreCase(renamedFilePathWithExt));
			}	

			// Create a HashMap containing the number ...
			if (isExistsInObsFiles || storeFI_List.stream().anyMatch(p -> p.getRenamedFileWithExtPath().equals(renamedFilePathWithExt))) {
				filesIncrementMap.put(renamedFilePathWithExt, filesIncrementMap.getOrDefault(renamedFilePathWithExt, 0) + 1);
				result = String.format("%s (%d)", renamedName, filesIncrementMap.get(renamedFilePathWithExt));
			} else {
				filesIncrementMap.put(renamedFilePathWithExt, 0);
				result = renamedName;
			}

			//
			storeFileIncrement.setRenamedFileWithExtPath(renamedFilePathWithExt);
		}

		storeFI_List.add(storeFileIncrement);

		return result;
	}

}
