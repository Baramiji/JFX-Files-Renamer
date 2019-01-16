package jfxFilesRenamer.Operations;

import java.util.function.Consumer;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;
import javafx.scene.control.SingleSelectionModel;
import jfxFilesRenamer.MiscellaneousMethods;
import jfxFilesRenamer.MyThreadListener;
import jfxFilesRenamer.Enumerators.Actions;
import jfxFilesRenamer.Enumerators.Validation;
import jfxFilesRenamer.Stores.Store_Files;
import jfxFilesRenamer.Stores.Store_NameValidator;
import jfxFilesRenamer.Stores.Store_ReturnedData;

public class Op_Remove implements Runnable {

	//**************************************************************
	//*********************** Declarations *************************
	//**************************************************************

	private final Consumer<Store_ReturnedData> processor;
	MyThreadListener listener;

	private Actions action;
	private ObservableList<Store_Files> observableList_Files;
	private String TF_RemoveText;
	private String TF_RemoveTextFrom;
	private String TF_RemoveTextTo;
	private int Spin_RemoveFrom;
	private int Spin_RemoveTo;
	private SingleSelectionModel<Object> ChoiceBox_Trims;
	boolean CheckBox_RemoveExtraSpaces;
	private SingleSelectionModel<Object> ChoiceBox_Brackets;
	boolean CheckBox_RemoveTextWithinBrackets;


	//**************************************************************
	//************************ Constructors ************************
	//**************************************************************
	public Op_Remove() {
		super();
		this.processor = null;
	}


	public Op_Remove(Consumer<Store_ReturnedData> processor) {
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

	public String getTF_RemoveText() {
		return TF_RemoveText;
	}

	public String getTF_RemoveTextFrom() {
		return TF_RemoveTextFrom;
	}

	public String getTF_RemoveTextTo() {
		return TF_RemoveTextTo;
	}

	public int getSpin_RemoveFrom() {
		return Spin_RemoveFrom;
	}

	public int getSpin_RemoveTo() {
		return Spin_RemoveTo;
	}

	public SingleSelectionModel<Object> getChoiceBox_Trims() {
		return ChoiceBox_Trims;
	}

	public boolean isCheckBox_RemoveExtraSpaces() {
		return CheckBox_RemoveExtraSpaces;
	}

	public SingleSelectionModel<Object> getChoiceBox_Brackets() {
		return ChoiceBox_Brackets;
	}

	public boolean isCheckBox_RemoveTextWithinBrackets() {
		return CheckBox_RemoveTextWithinBrackets;
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

	public void setTF_RemoveText(String tF_RemoveText) {
		TF_RemoveText = tF_RemoveText;
	}

	public void setTF_RemoveTextFrom(String tF_RemoveTextFrom) {
		TF_RemoveTextFrom = tF_RemoveTextFrom;
	}

	public void setTF_RemoveTextTo(String tF_RemoveTextTo) {
		TF_RemoveTextTo = tF_RemoveTextTo;
	}

	public void setSpin_RemoveFrom(int spin_RemoveFrom) {
		Spin_RemoveFrom = spin_RemoveFrom;
	}

	public void setSpin_RemoveTo(int spin_RemoveTo) {
		Spin_RemoveTo = spin_RemoveTo;
	}

	public void setChoiceBox_Trims(SingleSelectionModel<Object> choiceBox_Trims) {
		ChoiceBox_Trims = choiceBox_Trims;
	}

	public void setCheckBox_RemoveExtraSpaces(boolean checkBox_RemoveExtraSpaces) {
		CheckBox_RemoveExtraSpaces = checkBox_RemoveExtraSpaces;
	}

	public void setChoiceBox_Brackets(
			SingleSelectionModel<Object> choiceBox_Brackets) {
		ChoiceBox_Brackets = choiceBox_Brackets;
	}

	public void setCheckBox_RemoveTextWithinBrackets(
			boolean checkBox_RemoveTextWithinBrackets) {
		CheckBox_RemoveTextWithinBrackets = checkBox_RemoveTextWithinBrackets;
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


				try {
					switch (action) {
						case REMOVETEXT :
							renamedName = removeText(storeFile.getNameOriginalWithoutExt()).trim();
							break;

						case REMOVETEXTBETWEEN :
							renamedName = removeTextBetween(storeFile.getNameOriginalWithoutExt(),
									TF_RemoveTextFrom,
									TF_RemoveTextTo).trim();
							break;

						case REMOVEPOSITION :
							renamedName = removeTextFromTo(storeFile.getNameOriginalWithoutExt(),
									Spin_RemoveFrom,
									Spin_RemoveTo).trim();
							break;

						case REMOVEEXTRASPACES :
							renamedName = removeSpaces(storeFile.getNameOriginalWithoutExt());
							break;

						case REMOVEBRACKETS :
							renamedName = removeBrackets(storeFile.getNameOriginalWithoutExt()).trim();
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

				}  catch (Exception e) {
					//	returnedData.setRenamedName(originalNameWithoutExt);
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



	private String removeText(String originalName) {
		return originalName.replaceAll("(?i)" + TF_RemoveText, "");
	}


	private String removeTextBetween(String originalName, String previousString, String nextString) {
		String regexString = String.format("(?i)(?<=%s)(.*?)(?=%s)", TF_RemoveTextFrom, TF_RemoveTextTo);
		return Pattern.compile(regexString, Pattern.CASE_INSENSITIVE).matcher(originalName).replaceAll("");
	}


	private String removeTextFromTo(String originalName, int startPos, int endPos) {

		String renamedString = "";

		if (startPos > endPos) {
			System.out.println("The start position exceeds the end position!");
			return originalName;

		} else {		
			StringBuilder sb = new StringBuilder(originalName);

			if (endPos >= originalName.length() && startPos <= originalName.length()) {
				renamedString =	sb.delete(startPos - 1, originalName.length()).toString();
			} else if (startPos >= originalName.length()){

			} else if (startPos == endPos){
				renamedString = sb.deleteCharAt(startPos -1).toString();
			} else {
				renamedString = sb.delete(startPos - 1, endPos).toString();
			}

			return renamedString;
		}
	}


	private String removeSpaces(String originalName) {

		String result = "";

		// Remove spaces in the begining or the end or on both sides
		if (!ChoiceBox_Trims.isEmpty()) {
			switch (ChoiceBox_Trims.getSelectedIndex()) {
				case 0 :
					// Remove extraspaces
					if (CheckBox_RemoveExtraSpaces){
						result = result.replaceAll("[ ]+", " ");
					}
					break;
				case 2 :
					result = originalName.trim();
					// Remove extraspaces
					if (CheckBox_RemoveExtraSpaces){
						result = result.replaceAll("[ ]+", " ");
					}
					break;
				case 3 :
					result = originalName.replaceAll("^\\s+", "");
					// Remove extraspaces
					if (CheckBox_RemoveExtraSpaces){
						result = result.replaceAll("[ ]+", " ");
					}
					break;
				case 4 :
					result = originalName.replaceAll("\\s+$", "");
					// Remove extraspaces
					if (CheckBox_RemoveExtraSpaces){
						result = result.replaceAll("[ ]+", " ");
					}
					break;
				case 6 :
					result = originalName.replaceAll(" ", "");
					break;
				default :
					break;
			}
		}

		return result;
	}


	private String removeBrackets(String originalName) {

		if (!ChoiceBox_Brackets.isEmpty()) {

			String result = "";
			String bracketsType_Start = "";
			String bracketsType_End = "";
			String betweenBrackets = "\\%s.*?\\%s ?";
			String brackets = "[\\%s\\%s]";

			switch (ChoiceBox_Brackets.getSelectedIndex()) {
				case 0 :
					if (CheckBox_RemoveTextWithinBrackets) {
						result = originalName.replaceAll("\\(.*?\\) ?|\\{.*?\\} ?|\\[.*?\\] ?|\\<.*?\\> ?|\\⟨.*?\\⟩ ?|\\⸤.*?\\⸥ ?|\\｢.*?\\｣ ?|\\⟦.*?\\⟧ ?|\\〔.*?\\〕 ?", "");
					} else {
						result = originalName.replaceAll("[\\(\\)]|[\\{\\}]|[\\[\\]]|[\\<\\>]|[\\⟨\\⟩]|[\\⸤\\⸥]|[\\｢\\｣]|[\\⟦\\⟧]|[\\〔\\〕]", "");
					}

					break;
				case 2 :
					bracketsType_Start = "(";
					bracketsType_End = ")";
					break;
				case 3 :
					bracketsType_Start = "{";
					bracketsType_End = "}";
					break;
				case 4 :
					bracketsType_Start = "[";
					bracketsType_End = "]";
					break;
				case 5 :
					bracketsType_Start = "<";
					bracketsType_End = ">";
					break;
				case 6 :
					bracketsType_Start = "⟨";
					bracketsType_End = "⟩";
					break;
				case 7 :
					bracketsType_Start = "⸤";
					bracketsType_End = "⸥";
					break;
				case 8 :
					bracketsType_Start = "｢";
					bracketsType_End = "｣";
					break;
				case 9 :
					bracketsType_Start = "⟦";
					bracketsType_End = "⟧";
					break;
				case 10 :
					bracketsType_Start = "〔";
					bracketsType_End = "〕";
					break;

				default :
					break;
			}

			if (ChoiceBox_Brackets.getSelectedIndex() != 0) {
				if (CheckBox_RemoveTextWithinBrackets) {
					result = originalName.replaceAll(String.format(betweenBrackets, bracketsType_Start, bracketsType_End), "");
				} else {
					result = originalName.replaceAll(String.format(brackets, bracketsType_Start, bracketsType_End), "");
				}
			}

			return result;
		}

		return originalName;
	}

}
