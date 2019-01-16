package jfxFilesRenamer.Forms;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import jfxFilesRenamer.Main;
import jfxFilesRenamer.MiscellaneousMethods;
import jfxFilesRenamer.MyThreadListener;
import jfxFilesRenamer.OS_Detector;
import jfxFilesRenamer.Enumerators.Actions;
import jfxFilesRenamer.Enumerators.LoadingFilesToList;
import jfxFilesRenamer.Enumerators.Validation;
import jfxFilesRenamer.Extensions.CommonExtensions;
import jfxFilesRenamer.Operations.Op_Apply;
import jfxFilesRenamer.Operations.Op_Extensions;
import jfxFilesRenamer.Operations.Op_Insert;
import jfxFilesRenamer.Operations.Op_LoadFilesList;
import jfxFilesRenamer.Operations.Op_Remove;
import jfxFilesRenamer.Operations.Op_Rename;
import jfxFilesRenamer.Operations.Op_Replace;
import jfxFilesRenamer.Stores.Store_Files;


public class ControllerMain implements Initializable {



	//**************************************************************
	//********************* @FXML Declarations *********************
	//**************************************************************
	@FXML private HBox HBox_Insert;
	@FXML private ScrollPane ScrollPane_Files;
	@FXML private TabPane TabPane_Controls;
	@FXML private TableView<Store_Files> TableView_Files;
	@FXML private TableColumn<Store_Files, String> col_ID;
	@FXML private TableColumn<Store_Files, String> col_OriginalName;
	@FXML private TableColumn<Store_Files, String> col_OriginalNameWithoutExt;
	@FXML private TableColumn<Store_Files, String> col_RenamedNane;
	@FXML private TableColumn<Store_Files, String> col_Status;
	@FXML private TableColumn<Store_Files, String> col_ParentFolder;
	@FXML private TableColumn<Store_Files, String> col_Extension;
	@FXML private Button BTN_ScanFolder;
	@FXML private Button BTN_AddFiles;
	@FXML private Button BTN_Apply;
	@FXML private Button BTN_ReplaceText;
	@FXML private Button BTN_ReplaceLettersCase;
	@FXML private Button BTN_ReplaceMultipleOccurrences;
	@FXML private Button BTN_ReplaceMiscellaneous;
	@FXML private Button BTN_RemoveText;
	@FXML private Button BTN_RemoveTextBetween;
	@FXML private Button BTN_RemovePosition;
	@FXML private Button BTN_RemoveExtraSpaces;
	@FXML private Button BTN_RemoveBrackets;
	@FXML private Button BTN_InsertText;
	@FXML private Button BTN_InsertNumber;
	@FXML private Button BTN_InsertMiscellaneous;
	@FXML private Button BTN_RenameNumber;
	@FXML private Button BTN_RenameMiscellaneous;
	@FXML private Button BTN_ExtAdd;
	@FXML private Button BTN_ExtReplace;
	@FXML private Button BTN_ExtRemove;
	@FXML private Button BTN_ExtLettersCase;
	@FXML private TextField TF_ReplaceFind;
	@FXML private TextField TF_ReplaceWith;
	@FXML private TextField TF_ReplaceMultipleOccurrences;
	@FXML private TextField TF_RemoveText;
	@FXML private TextField TF_RemoveTextFrom;
	@FXML private TextField TF_RemoveTextTo;
	@FXML private TextField TF_InsertText;
	@FXML private TextField TF_RenameText;
	@FXML private TextField TF_ExtAdd;
	@FXML private TextField TF_ExtReplace;
	@FXML private TextField TF_ExtReplaceWith;
	@FXML private TextField TF_ExtRemove;
	@FXML private CheckBox CheckBox_ReplaceRegex;
	@FXML private CheckBox CheckBox_ReplaceCaseSensitive;
	@FXML private CheckBox CheckBox_ScanSubFolders;
	@FXML private CheckBox CheckBox_RemoveExtraSpaces;
	@FXML private CheckBox CheckBox_RemoveTextWithinBrackets;
	@FXML private CheckBox CheckBox_InsertResetCounterFolder;
	@FXML private CheckBox CheckBox_RenameResetCounterFolder;
	@FXML private CheckBox CheckBox_ExtReplaceAll;
	@FXML private CheckBox CheckBox_ExtRemoveAll;
	@FXML private Label Label_Status;
	@FXML private ChoiceBox<Object> ChoiceBox_LettersCase;
	@FXML private ChoiceBox<Object> ChoiceBox_ReplaceMiscellaneous;
	@FXML private ChoiceBox<Object> ChoiceBox_Trims;
	@FXML private ChoiceBox<Object> ChoiceBox_Brackets;
	@FXML private ChoiceBox<Object> ChoiceBox_InsertMiscellaneous;
	@FXML private ChoiceBox<Object> ChoiceBox_InsertSeparator;
	@FXML private ChoiceBox<Object> ChoiceBox_RenameMiscellaneous;
	@FXML private ChoiceBox<Object> ChoiceBox_RenameSeparator;
	@FXML private Spinner<Integer> Spin_RemoveFrom;
	@FXML private Spinner<Integer> Spin_RemoveTo;
	@FXML private Spinner<Integer> Spin_InsertAtPosition;
	@FXML private Spinner<Integer> Spin_InsertCounterStart;
	@FXML private Spinner<Integer> Spin_InsertCounterFormat;
	@FXML private Spinner<Integer> Spin_InsertCounterIncrementStep;
	@FXML private Spinner<Integer> Spin_RenameCounterStart;
	@FXML private Spinner<Integer> Spin_RenameCounterFormat;
	@FXML private Spinner<Integer> Spin_RenameCounterIncrementStep;
	@FXML private RadioButton RB_InsertAtBegin;
	@FXML private RadioButton RB_InsertAtEnd;
	@FXML private RadioButton RB_InsertAtPosition;
	@FXML private RadioButton RB_ExtUppercase;
	@FXML private RadioButton RB_ExtLowercase;
	@FXML private MenuItem MenuItem_About;
	@FXML private MenuItem MenuItem_Exit;



	//**************************************************************
	//************************ Declarations ************************
	//**************************************************************
	private ObservableList<Store_Files> observableList_Files = FXCollections.observableArrayList();
	private Tooltip toolTip = new Tooltip();
	// private static boolean isLinux = false;
	public static BooleanProperty isLoadingFiles = new SimpleBooleanProperty(false);
	public static BooleanProperty isRenamingOperation = new SimpleBooleanProperty(false);



	//**************************************************************
	//************************ initialize **************************
	//**************************************************************
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//**************************************************************
		//                     Initialize controls
		//**************************************************************
		initializeControls();	


		//		//**************************************************************
		//		// We put true for the case sensitive while incrementation (Linux & Windows are differents)
		//		//**************************************************************
		//		if (OS_Detector.iS_LINUX) {
		//			isLinux = true;
		//		} else {
		//			isLinux = false;
		//		}



		//**************************************************************
		//                     TableViews Settings
		//**************************************************************
		col_ID.setCellValueFactory(new PropertyValueFactory<>("fileID"));
		col_OriginalName.setCellValueFactory(new PropertyValueFactory<>("nameOriginal"));
		col_OriginalNameWithoutExt.setCellValueFactory(new PropertyValueFactory<>("nameOriginalWithoutExt"));
		col_RenamedNane.setCellValueFactory(new PropertyValueFactory<>("nameRenamed"));
		col_Status.setCellValueFactory(new PropertyValueFactory<>("renamingStatus"));
		col_ParentFolder.setCellValueFactory(new PropertyValueFactory<>("parentFolder"));
		col_Extension.setCellValueFactory(new PropertyValueFactory<>("extension"));



		//**************************************************************
		//                       Controls Actions
		//**************************************************************

		TF_ReplaceWith.textProperty().addListener((observable, oldValue, newValue) -> {	
			verifyIllegalCharacters(TF_ReplaceWith, newValue);
		});

		TF_InsertText.textProperty().addListener((observable, oldValue, newValue) -> {
			verifyIllegalCharacters(TF_InsertText, newValue);
		});

		TF_RenameText.textProperty().addListener((observable, oldValue, newValue) -> {
			verifyIllegalCharacters(TF_RenameText, newValue);
		});

		TF_ExtAdd.textProperty().addListener((observable, oldValue, newValue) -> {
			verifyIllegalCharacters(TF_ExtAdd, newValue);
		});

		TF_ExtReplaceWith.textProperty().addListener((observable, oldValue, newValue) -> {
			verifyIllegalCharacters(TF_ExtReplaceWith, newValue);
		});


		TF_ReplaceMultipleOccurrences.setTextFormatter(new TextFormatter<String>((Change change) -> {
			String newText = change.getControlNewText();
			if (newText.length() > 1) {
				return null ;
			} else {
				return change ;
			}
		}));


		BTN_ScanFolder.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				scanFolder();
			}
		});

		BTN_AddFiles.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				addFilesList();
			}
		});


		BTN_ReplaceText.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controlsActions(Actions.REPLACETEXT);
			}
		});

		BTN_ReplaceLettersCase.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controlsActions(Actions.REPLACELETTERSCASE);
			}
		});

		BTN_ReplaceMultipleOccurrences.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controlsActions(Actions.REPLACEMULTIPLEOCCURENCES);
			}
		});

		BTN_ReplaceMiscellaneous.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controlsActions(Actions.REPLACEMISCELLANEOUS);
			}
		});

		BTN_RemoveText.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controlsActions(Actions.REMOVETEXT);
			}
		});

		BTN_RemoveTextBetween.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controlsActions(Actions.REMOVETEXTBETWEEN);
			}
		});

		BTN_RemovePosition.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controlsActions(Actions.REMOVEPOSITION);
			}
		});

		BTN_RemoveExtraSpaces.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controlsActions(Actions.REMOVEEXTRASPACES);
			}
		});

		BTN_RemoveBrackets.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controlsActions(Actions.REMOVEBRACKETS);
			}
		});

		BTN_InsertText.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controlsActions(Actions.INSERTTEXT);
			}
		});

		BTN_InsertNumber.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controlsActions(Actions.INSERTNUMBER);
			}
		});

		BTN_InsertMiscellaneous.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controlsActions(Actions.INSERTMISCELLANEOUS);
			}
		});

		BTN_RenameNumber.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controlsActions(Actions.RENAMENUMBER);
			}
		});

		BTN_RenameMiscellaneous.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controlsActions(Actions.RENAMEMISCELLANEOUS);
			}
		});

		BTN_ExtAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controlsActions(Actions.EXTADD);
			}
		});

		BTN_ExtReplace.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controlsActions(Actions.EXTREPLACE);
			}
		});

		BTN_ExtRemove.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controlsActions(Actions.EXTREMOVE);
			}
		});

		BTN_ExtLettersCase.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controlsActions(Actions.EXTLETTERSCASE);
			}
		});

		BTN_Apply.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controlsActions(Actions.APPLY);
			}
		});


		Spin_RemoveFrom.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
			if (Spin_RemoveFrom.getValue() > Spin_RemoveTo.getValue()) {
				Spin_RemoveTo.getValueFactory().setValue(Spin_RemoveFrom.getValue());
			}
		});

		Spin_RemoveTo.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
			if (Spin_RemoveFrom.getValue() > Spin_RemoveTo.getValue()) {
				Spin_RemoveTo.getValueFactory().setValue(Spin_RemoveFrom.getValue());
			}
		});


		MenuItem_Exit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});

		MenuItem_About.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				showForm_About();
			}
		});


		//**************************************************************
		//                            Bindings
		//**************************************************************
		bindingControls();

	}





	//**************************************************************
	//************************** Methods ***************************
	//**************************************************************
	public void controlsActions(Actions action) {

		if (!action.equals(Actions.APPLY))
			resetColumnsBeforeRename();

		switch (action) {
			case SCANFOLDER :
				scanFolder();
				break;

			case ADDFILES :
				addFilesList();
				break;

			case APPLY :
				confirmApply();
				break;

			case REPLACETEXT :
				replaceActions(Actions.REPLACETEXT);
				break;

			case REPLACELETTERSCASE :
				replaceActions(Actions.REPLACELETTERSCASE);
				break;

			case REPLACEMULTIPLEOCCURENCES :
				replaceActions(Actions.REPLACEMULTIPLEOCCURENCES);
				break;

			case REPLACEMISCELLANEOUS :
				replaceActions(Actions.REPLACEMISCELLANEOUS);
				break;

			case REMOVETEXT :
				removeActions(Actions.REMOVETEXT);
				break;

			case REMOVETEXTBETWEEN :
				removeActions(Actions.REMOVETEXTBETWEEN);
				break;

			case REMOVEPOSITION :
				removeActions(Actions.REMOVEPOSITION);
				break;

			case REMOVEEXTRASPACES :
				removeActions(Actions.REMOVEEXTRASPACES);
				break;

			case REMOVEBRACKETS :
				removeActions(Actions.REMOVEBRACKETS);
				break;

			case INSERTTEXT :
				insertActions(Actions.INSERTTEXT);
				break;

			case INSERTNUMBER :
				insertActions(Actions.INSERTNUMBER);
				break;

			case INSERTMISCELLANEOUS :
				insertActions(Actions.INSERTMISCELLANEOUS);
				break;

			case RENAMENUMBER :
				renameActions(Actions.RENAMENUMBER);
				break;

			case RENAMEMISCELLANEOUS :
				renameActions(Actions.RENAMEMISCELLANEOUS);
				break;

			case EXTADD :
				extensionActions(Actions.EXTADD);
				break;

			case EXTREPLACE :
				extensionActions(Actions.EXTREPLACE);
				break;

			case EXTREMOVE :
				extensionActions(Actions.EXTREMOVE);
				break;

			case EXTLETTERSCASE :
				extensionActions(Actions.EXTLETTERSCASE);
				break;

			default :
				break;
		}
	}


	private void verifyIllegalCharacters(TextField textField, String str) {

		// Check for illegal characters in the filename
		if (str.isEmpty() || MiscellaneousMethods.isValidName(str)) {
			textField.setStyle("-fx-control-inner-background: white");
			textField.setTooltip(null);
		} else {
			textField.setStyle("-fx-control-inner-background: #FE2E2E");
			toolTip.setText("Illegal characters aren't allowed");
			// Works on Java >= 9
			// toolTip.setShowDelay(Duration.seconds(0));
			// toolTip.setShowDuration(Duration.seconds(99999));
			textField.setTooltip(toolTip);
		}
	}


	private void showForm_About() {
		try {
			FXMLLoader loaderFXML = new FXMLLoader(getClass().getResource("FormAbout.fxml"));
			Parent root = (Parent) loaderFXML.load();
			Stage stage_About = new Stage();
			stage_About.setTitle("About JFX Files Renamer");
			stage_About.getIcons().add(new Image(Main.class.getResourceAsStream("Resources/Images/Logo.png")));
			stage_About.setMaximized(false);
			stage_About.setResizable(false);
			stage_About.setScene(new Scene(root));  
			stage_About.initModality(Modality.APPLICATION_MODAL);
			stage_About.showAndWait();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}




	//**************************************************************
	// Operations
	//**************************************************************

	public void addFilesList() {

		Stage currentStage = (Stage) BTN_ScanFolder.getScene().getWindow();

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Files");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(CommonExtensions.getCollection_ExtensionFilter());

		List<File> selectedFiles = fileChooser.showOpenMultipleDialog(currentStage);

		if (selectedFiles == null || selectedFiles.isEmpty())
			return;


		MyThreadListener threadListner = new MyThreadListener() {
			@Override
			public void threadFinished() {					
				Platform.runLater(new Runnable() {
					@Override
					public void run() {		
						isLoadingFiles.set(false);
						Label_Status.setText("The files are added to files' list successfully.");
						System.out.println("The files are added to files' list successfully.");
					}
				});
			}

			@Override
			public void threadFailed() {				
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						isLoadingFiles.set(false);
						Label_Status.setText("An error is occurred while adding the files.");
						MiscellaneousMethods.createMessageBox(AlertType.WARNING, "Error", "An error is occurred while adding the files", null).showAndWait();
					}
				});
			}
		};

		Op_LoadFilesList loadFilesList = new Op_LoadFilesList(obList -> Platform.runLater(new Runnable() {
			@Override
			public void run() {
				observableList_Files.clear();
				observableList_Files = obList;
				populate_TableViewFiles();
			}
		}));


		loadFilesList.setListener(threadListner);
		loadFilesList.setFilesList(selectedFiles);
		loadFilesList.setScanType(LoadingFilesToList.ADDFILES);
		loadFilesList.setObservableList_Files(observableList_Files);
		isLoadingFiles.set(true);
		Label_Status.setText("Adding Files to the list...");

		Thread thread = new Thread(loadFilesList, "AddingFilesList Thread");
		thread.setDaemon(true);	// للخروج التام عند إقفال البرنامج
		thread.start();
	}


	public void scanFolder() {

		Stage currentStage = (Stage) BTN_ScanFolder.getScene().getWindow();

		DirectoryChooser DirChooser = new DirectoryChooser();
		DirChooser.setTitle("Select a directory");
		// DirChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		DirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		File selectedDirectory = DirChooser.showDialog(currentStage);

		if (selectedDirectory == null || !selectedDirectory.isDirectory() || !selectedDirectory.exists())
			return;

		observableList_Files.clear();


		MyThreadListener threadListner = new MyThreadListener() {
			@Override
			public void threadFinished() {					
				Platform.runLater(new Runnable() {
					@Override
					public void run() {	
						isLoadingFiles.set(false);
						String status = "The files are loaded to the files' list successfully.";
						if (observableList_Files.isEmpty())
							status = "No file founded.";

						Label_Status.setText(status);
						System.out.println(status);
					}
				});
			}

			@Override
			public void threadFailed() {				
				Platform.runLater(new Runnable() {
					@Override
					public void run() {	
						isLoadingFiles.set(false);
						Label_Status.setText("An error is occurred while loaded the files.");
						MiscellaneousMethods.createMessageBox(AlertType.WARNING, "Error", "An error is occurred while adding the files", null).showAndWait();
					}
				});
			}
		};

		Op_LoadFilesList loadFilesList = new Op_LoadFilesList(obList -> Platform.runLater(new Runnable() {
			@Override
			public void run() {
				observableList_Files.clear();
				observableList_Files = obList;
				populate_TableViewFiles();
			}
		}));

		loadFilesList.setListener(threadListner);
		loadFilesList.setScanType(LoadingFilesToList.SCANFOLDER);
		loadFilesList.setScanSubDirectories(CheckBox_ScanSubFolders.isSelected());
		loadFilesList.setFolderPath(selectedDirectory.getAbsolutePath());
		isLoadingFiles.set(true);
		Label_Status.setText("Scanning the folder for Files...");

		Thread thread = new Thread(loadFilesList, "ScanFilesList Thread");
		thread.setDaemon(true);	// للخروج التام عند إقفال البرنامج
		thread.start();
	}


	public void replaceActions(Actions action) {

		MyThreadListener threadListner = new MyThreadListener() {
			@Override
			public void threadFinished() {					
				Platform.runLater(new Runnable() {
					@Override
					public void run() {	
						isRenamingOperation.set(false);
						Label_Status.setText("Ready to apply replacing.");
						System.out.println("Ready to apply replacing.");
					}
				});
			}

			@Override
			public void threadFailed() {				
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						isRenamingOperation.set(false);
						Label_Status.setText("Replacing operation failed.");
						MiscellaneousMethods.createMessageBox(AlertType.WARNING, "Error", "Replacing operation failed.", null).showAndWait();
					}
				});
			}
		};


		Op_Replace op_Replace = new Op_Replace(storeReturnedData -> Platform.runLater(new Runnable() {
			@Override
			public void run() {

				if (storeReturnedData.getSendedTimes() == 0) {
					Label_Status.setText(storeReturnedData.getCurrentFile());

				} else if (storeReturnedData.getSendedTimes() == 1) {
					observableList_Files.stream()
					.filter(x -> x.getFileID() == storeReturnedData.getFileID())
					.map((v) -> {
						v.setNameRenamed(storeReturnedData.getRenamedName());
						v.setReadyForRename(storeReturnedData.isReadyForRename());
						v.setNameValidation(storeReturnedData.getValidation());
						v.setRenamingStatus(storeReturnedData.getValidationMessage());
						return v;
					})
					.forEach(v -> {
						TableView_Files.refresh();
					});
				}
			}
		}));


		op_Replace.setListener(threadListner);
		op_Replace.setAction(action);
		op_Replace.setObservableList_Files(observableList_Files);
		op_Replace.setTF_ReplaceFind(TF_ReplaceFind.getText());
		op_Replace.setTF_ReplaceWith(TF_ReplaceWith.getText());
		op_Replace.setTF_ReplaceMultipleOccurrences(TF_ReplaceMultipleOccurrences.getText());
		op_Replace.setCB_ReplaceRegex(CheckBox_ReplaceRegex.isSelected());
		op_Replace.setCB_ReplaceCaseSensitive(CheckBox_ReplaceCaseSensitive.isSelected());
		op_Replace.setCB_LettersCase(ChoiceBox_LettersCase.getSelectionModel());
		op_Replace.setCB_ReplaceMiscellaneous(ChoiceBox_ReplaceMiscellaneous.getSelectionModel());
		isRenamingOperation.set(true);
		Label_Status.setText("Replacing operation...");

		Thread thread = new Thread(op_Replace, "Replacing Thread");
		thread.setDaemon(true);	// للخروج التام عند إقفال البرنامج
		thread.start();
	}


	public void renameActions(Actions action) {

		MyThreadListener threadListner = new MyThreadListener() {
			@Override
			public void threadFinished() {					
				Platform.runLater(new Runnable() {
					@Override
					public void run() {	
						isRenamingOperation.set(false);
						Label_Status.setText("Ready to apply renaming.");
						System.out.println("Ready to apply renaming.");
					}
				});
			}

			@Override
			public void threadFailed() {				
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						isRenamingOperation.set(false);
						Label_Status.setText("Renaming operation failed.");
						MiscellaneousMethods.createMessageBox(AlertType.WARNING, "Error", "Renaming operation failed.", null).showAndWait();
					}
				});
			}
		};


		Op_Rename op_Rename = new Op_Rename(storeReturnedData -> Platform.runLater(new Runnable() {
			@Override
			public void run() {

				if (storeReturnedData.getSendedTimes() == 0) {
					Label_Status.setText(storeReturnedData.getCurrentFile());

				} else if (storeReturnedData.getSendedTimes() == 1) {
					observableList_Files.stream()
					.filter(x -> x.getFileID() == storeReturnedData.getFileID())
					.map((v) -> {
						v.setNameRenamed(storeReturnedData.getRenamedName());
						v.setReadyForRename(storeReturnedData.isReadyForRename());
						v.setNameValidation(storeReturnedData.getValidation());
						v.setRenamingStatus(storeReturnedData.getValidationMessage());
						return v;
					})
					.forEach(v -> {
						TableView_Files.refresh();
					});
				}
			}
		}));


		op_Rename.setListener(threadListner);
		op_Rename.setAction(action);
		op_Rename.setObservableList_Files(observableList_Files);
		op_Rename.setChoiceBox_RenameMiscellaneous(ChoiceBox_RenameMiscellaneous.getSelectionModel());
		op_Rename.setChoiceBox_RenameSeparator(ChoiceBox_RenameSeparator.getSelectionModel());
		op_Rename.setTF_RenameText(TF_RenameText.getText());
		op_Rename.setSpin_RenameCounterFormat(Spin_RenameCounterFormat.getValue());
		op_Rename.setSpin_RenameCounterIncrementStep(Spin_RenameCounterIncrementStep.getValue());
		op_Rename.setSpin_RenameCounterStart(Spin_RenameCounterStart.getValue());
		op_Rename.setCheckBox_RenameResetCounterFolder(CheckBox_RenameResetCounterFolder.isSelected());
		isRenamingOperation.set(true);
		Label_Status.setText("Renaming operation...");

		Thread thread = new Thread(op_Rename, "Renaming Thread");
		thread.setDaemon(true);	// للخروج التام عند إقفال البرنامج
		thread.start();
	}


	public void insertActions(Actions action) {

		MyThreadListener threadListner = new MyThreadListener() {
			@Override
			public void threadFinished() {					
				Platform.runLater(new Runnable() {
					@Override
					public void run() {		
						isRenamingOperation.set(false);
						Label_Status.setText("Ready to apply inserting.");
						System.out.println("Ready to apply inserting.");
					}
				});
			}

			@Override
			public void threadFailed() {				
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						isRenamingOperation.set(false);
						Label_Status.setText("Inserting operation failed.");
						MiscellaneousMethods.createMessageBox(AlertType.WARNING, "Error", "Inserting operation failed.", null).showAndWait();
					}
				});
			}
		};


		Op_Insert op_Insert = new Op_Insert(storeReturnedData -> Platform.runLater(new Runnable() {
			@Override
			public void run() {

				if (storeReturnedData.getSendedTimes() == 0) {
					Label_Status.setText(storeReturnedData.getCurrentFile());

				} else if (storeReturnedData.getSendedTimes() == 1) {
					observableList_Files.stream()
					.filter(x -> x.getFileID() == storeReturnedData.getFileID())
					.map((v) -> {
						v.setNameRenamed(storeReturnedData.getRenamedName());
						v.setReadyForRename(storeReturnedData.isReadyForRename());
						v.setNameValidation(storeReturnedData.getValidation());
						v.setRenamingStatus(storeReturnedData.getValidationMessage());
						return v;
					})
					.forEach(v -> {
						TableView_Files.refresh();
					});
				}
			}
		}));


		op_Insert.setListener(threadListner);
		op_Insert.setAction(action);
		op_Insert.setObservableList_Files(observableList_Files);
		op_Insert.setSpin_InsertCounterStart(Spin_InsertCounterStart.getValue());
		op_Insert.setSpin_InsertCounterIncrementStep(Spin_InsertCounterIncrementStep.getValue());
		op_Insert.setSpin_InsertCounterFormat(Spin_InsertCounterFormat.getValue());
		op_Insert.setSpin_InsertAtPosition(Spin_InsertAtPosition.getValue());
		op_Insert.setTF_InsertText(TF_InsertText.getText());
		op_Insert.setCB_InsertSeparator(ChoiceBox_InsertSeparator.getSelectionModel());
		op_Insert.setCB_InsertMiscellaneous(ChoiceBox_InsertMiscellaneous.getSelectionModel());
		op_Insert.setCB_InsertResetCounterFolder(CheckBox_InsertResetCounterFolder.isSelected());
		op_Insert.setRB_InsertAtBegin(RB_InsertAtBegin.isSelected());
		op_Insert.setRB_InsertAtEnd(RB_InsertAtEnd.isSelected());
		op_Insert.setRB_InsertAtPosition(RB_InsertAtPosition.isSelected());
		isRenamingOperation.set(true);
		Label_Status.setText("Inserting operation ...");

		Thread thread = new Thread(op_Insert, "Inserting Thread");
		thread.setDaemon(true);	// للخروج التام عند إقفال البرنامج
		thread.start();
	}



	public void removeActions(Actions action) {

		MyThreadListener threadListner = new MyThreadListener() {
			@Override
			public void threadFinished() {					
				Platform.runLater(new Runnable() {
					@Override
					public void run() {		
						isRenamingOperation.set(false);
						Label_Status.setText("Ready to apply removing.");
						System.out.println("Ready to apply removing.");
					}
				});
			}

			@Override
			public void threadFailed() {				
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						isRenamingOperation.set(false);
						Label_Status.setText("Removing operation failed.");
						MiscellaneousMethods.createMessageBox(AlertType.WARNING, "Error", "Removing operation failed.", null).showAndWait();
					}
				});
			}
		};


		Op_Remove op_Remove = new Op_Remove(storeReturnedData -> Platform.runLater(new Runnable() {
			@Override
			public void run() {

				if (storeReturnedData.getSendedTimes() == 0) {
					Label_Status.setText(storeReturnedData.getCurrentFile());

				} else if (storeReturnedData.getSendedTimes() == 1) {
					observableList_Files.stream()
					.filter(x -> x.getFileID() == storeReturnedData.getFileID())
					.map((v) -> {
						v.setNameRenamed(storeReturnedData.getRenamedName());
						v.setReadyForRename(storeReturnedData.isReadyForRename());
						v.setNameValidation(storeReturnedData.getValidation());
						v.setRenamingStatus(storeReturnedData.getValidationMessage());
						return v;
					})
					.forEach(v -> {
						TableView_Files.refresh();
					});
				}
			}
		}));


		op_Remove.setListener(threadListner);
		op_Remove.setAction(action);
		op_Remove.setObservableList_Files(observableList_Files);
		op_Remove.setTF_RemoveText(TF_RemoveText.getText());
		op_Remove.setTF_RemoveTextFrom(TF_RemoveTextFrom.getText());
		op_Remove.setTF_RemoveTextTo(TF_RemoveTextTo.getText());
		op_Remove.setSpin_RemoveFrom(Spin_RemoveFrom.getValue());
		op_Remove.setSpin_RemoveTo(Spin_RemoveTo.getValue());
		op_Remove.setChoiceBox_Trims(ChoiceBox_Trims.getSelectionModel());
		op_Remove.setChoiceBox_Brackets(ChoiceBox_Brackets.getSelectionModel());
		op_Remove.setCheckBox_RemoveExtraSpaces(CheckBox_RemoveExtraSpaces.isSelected());
		op_Remove.setCheckBox_RemoveTextWithinBrackets(CheckBox_RemoveTextWithinBrackets.isSelected());
		isRenamingOperation.set(true);
		Label_Status.setText("Removing operation ...");

		Thread thread = new Thread(op_Remove, "Removing Thread");
		thread.setDaemon(true);	// للخروج التام عند إقفال البرنامج
		thread.start();
	}


	public void extensionActions(Actions action) {

		MyThreadListener threadListner = new MyThreadListener() {
			@Override
			public void threadFinished() {					
				Platform.runLater(new Runnable() {
					@Override
					public void run() {		
						isRenamingOperation.set(false);
						Label_Status.setText("Ready to apply extensions.");
						System.out.println("Ready to apply extensions.");
					}
				});
			}

			@Override
			public void threadFailed() {				
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						isRenamingOperation.set(false);
						Label_Status.setText("Extensions operation failed.");
						MiscellaneousMethods.createMessageBox(AlertType.WARNING, "Error", "Extensions operation failed.", null).showAndWait();
					}
				});
			}
		};

		Op_Extensions op_Extensions = new Op_Extensions(storeReturnedData -> Platform.runLater(new Runnable() {
			@Override
			public void run() {

				if (storeReturnedData.getSendedTimes() == 0) {
					Label_Status.setText(storeReturnedData.getCurrentFile());

				} else if (storeReturnedData.getSendedTimes() == 1) {
					observableList_Files.stream()
					.filter(x -> x.getFileID() == storeReturnedData.getFileID())
					.map((v) -> {
						v.setNameRenamed(storeReturnedData.getRenamedName());
						v.setReadyForRename(storeReturnedData.isReadyForRename());
						v.setNameValidation(storeReturnedData.getValidation());
						v.setRenamingStatus(storeReturnedData.getValidationMessage());
						return v;
					})
					.forEach(v -> {
						TableView_Files.refresh();
					});
				}
			}
		}));


		op_Extensions.setListener(threadListner);
		op_Extensions.setAction(action);
		op_Extensions.setObservableList_Files(observableList_Files);
		op_Extensions.setTF_ExtAdd(TF_ExtAdd.getText());
		op_Extensions.setTF_ExtReplaceWith(TF_ExtReplaceWith.getText());
		op_Extensions.setTF_ExtReplace(TF_ExtReplace.getText());
		op_Extensions.setTF_ExtRemove(TF_ExtRemove.getText());
		op_Extensions.setCheckBox_ExtReplaceAll(CheckBox_ExtReplaceAll.isSelected());
		op_Extensions.setCheckBox_ExtRemoveAll(CheckBox_ExtRemoveAll.isSelected());
		op_Extensions.setRB_ExtUppercase(RB_ExtUppercase.isSelected());
		op_Extensions.setRB_ExtLowercase(RB_ExtLowercase.isSelected());
		isRenamingOperation.set(true);
		Label_Status.setText("Extensions operation ...");

		Thread thread = new Thread(op_Extensions, "Extensions Thread");
		thread.setDaemon(true);	// للخروج التام عند إقفال البرنامج
		thread.start();
	}



	public void confirmApply() {	

		if (observableList_Files.isEmpty()) {
			Label_Status.setText("The Files' list is empty.");
			System.out.println("The Files' list is empty.");
			return;
		}

		if (!observableList_Files.stream().anyMatch(x -> x.isReadyForRename())) {
			Label_Status.setText("No file is ready to be renamed.");
			System.out.println("No file is ready to be renamed.");
			return;
		}


		MyThreadListener threadListner = new MyThreadListener() {
			@Override
			public void threadFinished() {					
				Platform.runLater(new Runnable() {
					@Override
					public void run() {	
						TableView_Files.refresh();
						isRenamingOperation.set(false);
						Label_Status.setText("Renaming applied successfully.");
						System.out.println("Renaming applied successfully.");
					}
				});
			}

			@Override
			public void threadFailed() {				
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						TableView_Files.refresh();
						isRenamingOperation.set(false);
						Label_Status.setText("Apply renaming failed.");
						MiscellaneousMethods.createMessageBox(AlertType.WARNING, "Apply", "Apply renaming failed.", null).showAndWait();
					}
				});
			}
		};


		Op_Apply op_Apply = new Op_Apply(storeFiles  -> Platform.runLater(new Runnable() {
			@Override
			public void run() {
				observableList_Files.stream()
				.filter(x -> x.getFileID().equals(storeFiles.getFileID()))
				.map((v) -> {
					v = storeFiles;
					return v;
				});
			}
		}));


		op_Apply.setListener(threadListner);
		op_Apply.setObservableList_Files(observableList_Files);
		isRenamingOperation.set(true);
		Label_Status.setText("Apply Renaming ...");

		Thread thread = new Thread(op_Apply, "Apply Thread");
		thread.setDaemon(true);	// للخروج التام عند إقفال البرنامج
		thread.start();
	}




	private void populate_TableViewFiles() {

		if (observableList_Files != null && !observableList_Files.isEmpty())
			TableView_Files.setItems(observableList_Files);

		bindingControls();


		col_RenamedNane.setCellFactory(new Callback<TableColumn<Store_Files, String>, TableCell<Store_Files, String>>() {
			@Override
			public TableCell<Store_Files, String> call(TableColumn<Store_Files, String> param) {
				return new TableCell<Store_Files, String>() {

					@Override
					protected void updateItem(String item, boolean empty) {
						if (!empty) {
							int currentIndex = indexProperty().getValue() < 0 ? 0 : indexProperty().getValue();

							Validation validation = param.getTableView().getItems().get(currentIndex).getNameValidation();
							String renamedFilename = param.getTableView().getItems().get(currentIndex).getNameRenamed();

							if (validation.equals(Validation.NOTRENAMED)) {
								setTextFill(Color.BLACK);
								setStyle("");
								param.getTableView().getItems().get(currentIndex).setReadyForRename(false);

							} else if (validation.equals(Validation.VALID)) {
								setTextFill(Color.DARKRED);
								setStyle("-fx-font-weight: bold");
								param.getTableView().getItems().get(currentIndex).setReadyForRename(true);

							} else {
								setTextFill(Color.BLACK);
								setStyle("-fx-font-weight: bold");
								param.getTableView().getItems().get(currentIndex).setReadyForRename(false);
							}

							setText(renamedFilename);
						}
					}
				};
			}
		});


		col_Status.setCellFactory(new Callback<TableColumn<Store_Files, String>, TableCell<Store_Files, String>>() {
			@Override
			public TableCell<Store_Files, String> call(TableColumn<Store_Files, String> param) {
				return new TableCell<Store_Files, String>() {

					@Override
					protected void updateItem(String item, boolean empty) {
						if (!empty) {
							int currentIndex = indexProperty().getValue() < 0 ? 0 : indexProperty().getValue();

							boolean isApplied = param.getTableView().getItems().get(currentIndex).isRenameApplied();
							Validation validation = param.getTableView().getItems().get(currentIndex).getNameValidation();
							String renamingStatus = param.getTableView().getItems().get(currentIndex).getRenamingStatus();

							if (validation.equals(Validation.NOTRENAMED)) {
								setStyle("");
								setText("");

							} else if (validation.equals(Validation.VALID)) {
								setStyle("-fx-background-color: #F1EEC1; -fx-font-weight: bold");
								setText(renamingStatus);

							} else if (isApplied) {
								setStyle("-fx-background-color: #e9e9ff; -fx-font-weight: bold");
								setText("Renaming Applied");

							} else {
								setStyle("-fx-background-color: #EEAEAD; -fx-font-weight: bold");
								setText(renamingStatus);
							} 

							setTextFill(Color.BLACK);
						}
					}
				};
			}
		});

	}






	// ************************************************************************************************** //
	// ************************************************************************************************** //
	// ************************************************************************************************** //

	public void initializeControls() {

		hackTooltipStartTiming(toolTip);


		final Image image_AddFiles = new Image(Main.class.getResource("Resources/Images/Add Files.png").toString(), 40, 40, true, true);
		final Image image_ScanFolder = new Image(Main.class.getResource("Resources/Images/Scan Folder.png").toString(), 40, 40, true, true);
		final Image image_Apply = new Image(Main.class.getResource("Resources/Images/Apply.png").toString(), 40, 40, true, true);

		BTN_AddFiles.setGraphic(new ImageView(image_AddFiles));
		BTN_ScanFolder.setGraphic(new ImageView(image_ScanFolder));
		BTN_Apply.setGraphic(new ImageView(image_Apply));

		BTN_AddFiles.setText("");
		BTN_AddFiles.setTooltip(new Tooltip("Add Files to the list"));
		BTN_AddFiles.setGraphic(new ImageView(image_AddFiles));

		BTN_ScanFolder.setText("");
		BTN_ScanFolder.setTooltip(new Tooltip("Load File to the list"));
		BTN_ScanFolder.setGraphic(new ImageView(image_ScanFolder));

		BTN_Apply.setText("");
		BTN_Apply.setTooltip(new Tooltip("َApply renaming"));
		BTN_Apply.setGraphic(new ImageView(image_Apply));


		CheckBox_ScanSubFolders.setTooltip(new Tooltip("Scan subdirectories also"));


		ChoiceBox_LettersCase.setItems(FXCollections.observableArrayList(
				"Uppercase the first letter", "Uppercase each word's first letter", "Uppercase all letters", new Separator(),
				"Lowercase the first letter", "Lowercase each word's first letter", "Lowercase all letters", new Separator(),
				"Randomcase the first letter", "Randomcase each word's first letter", "Randomcase all letters", "Randomcase each letter", new Separator(),
				"Separate capitalized Text"));

		ChoiceBox_ReplaceMiscellaneous.setItems(FXCollections.observableArrayList("Accents"));

		ChoiceBox_InsertMiscellaneous.setItems(FXCollections.observableArrayList(
				"Date", "Day", "Day name", "Day simplified", "Month", "Month name", "Month simplified",
				"Year", "Time", "Creation date", "Last modification date", "Last access date"));

		ChoiceBox_RenameMiscellaneous.setItems(FXCollections.observableArrayList(
				"Date", "Day", "Day name", "Day simplified", "Month", "Month name", "Month simplified",
				"Year", "Time", "Creation date", "Last modification date", "Last access date"));

		ChoiceBox_InsertSeparator.setItems(FXCollections.observableArrayList("( )", "- -", "_ _", "-", "_", "Space", ""));
		ChoiceBox_InsertSeparator.getSelectionModel().selectFirst();

		ChoiceBox_RenameSeparator.setItems(FXCollections.observableArrayList("( )", "- -", "_ _", "-", "_", "Space", ""));
		ChoiceBox_RenameSeparator.getSelectionModel().selectFirst();

		ChoiceBox_Trims.setItems(FXCollections.observableArrayList("None", new Separator(), "Trim both", "Trim Beginning", "Trim end", new Separator(), "All spaces"));
		ChoiceBox_Brackets.setItems(FXCollections.observableArrayList("All", new Separator(), "()", "{}", "[]", "<>", "⟨⟩", "⸤⸥", "｢｣", "⟦⟧", "〔〕"));



		SpinnerValueFactory<Integer> fromSVF =  new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 800, 1);
		Spin_RemoveFrom.setValueFactory(fromSVF);	

		SpinnerValueFactory<Integer> toSVF =  new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 800, 1);
		Spin_RemoveTo.setValueFactory(toSVF);	

		SpinnerValueFactory<Integer> insertAtPosSVF =  new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 800, 0);
		Spin_InsertAtPosition.setValueFactory(insertAtPosSVF);

		SpinnerValueFactory<Integer> insertNumStartSVF =  new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 800, 0);
		Spin_InsertCounterStart.setValueFactory(insertNumStartSVF);

		SpinnerValueFactory<Integer> insertNumFormatSVF =  new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 0);
		Spin_InsertCounterFormat.setValueFactory(insertNumFormatSVF);

		SpinnerValueFactory<Integer> insertCounterStepSVF =  new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999999, 1);
		Spin_InsertCounterIncrementStep.setValueFactory(insertCounterStepSVF);	

		SpinnerValueFactory<Integer> renameNumStartSVF =  new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 800, 0);
		Spin_RenameCounterStart.setValueFactory(renameNumStartSVF);	

		SpinnerValueFactory<Integer> renameNumFormatSVF =  new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 0);
		Spin_RenameCounterFormat.setValueFactory(renameNumFormatSVF);	

		SpinnerValueFactory<Integer> renameCounterStepSVF =  new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999999, 1);
		Spin_RenameCounterIncrementStep.setValueFactory(renameCounterStepSVF);	



		ToggleGroup radioGroupInsert = new ToggleGroup();
		RB_InsertAtBegin.setToggleGroup(radioGroupInsert);
		RB_InsertAtEnd.setToggleGroup(radioGroupInsert);
		RB_InsertAtPosition.setToggleGroup(radioGroupInsert);
		RB_InsertAtBegin.setSelected(true);

		ToggleGroup radioGroupExtension = new ToggleGroup();
		RB_ExtUppercase.setToggleGroup(radioGroupExtension);
		RB_ExtLowercase.setToggleGroup(radioGroupExtension);
		RB_ExtUppercase.setSelected(true);
	}



	public void bindingControls() {

		BTN_AddFiles.disableProperty().bind(isLoadingFiles
				.or(isRenamingOperation));

		BTN_ScanFolder.disableProperty().bind(isLoadingFiles
				.or(isRenamingOperation));

		BTN_Apply.disableProperty().bind(Bindings.isEmpty(observableList_Files)
				.or(isLoadingFiles)
				.or(isRenamingOperation));

		CheckBox_ScanSubFolders.disableProperty().bind(isLoadingFiles
				.or(isRenamingOperation));

		TableView_Files.disableProperty().bind(Bindings.isEmpty(observableList_Files)
				.or(isLoadingFiles)
				.or(isRenamingOperation));
		
		TabPane_Controls.disableProperty().bind(Bindings.isEmpty(observableList_Files)
				.or(isLoadingFiles)
				.or(isRenamingOperation));


		// ************************************************************************* //

		CheckBox_ReplaceCaseSensitive.disableProperty().bind(Bindings.createBooleanBinding(() -> OS_Detector.iS_WINDOWS));	

		// ************************************************************************* //
		BTN_ReplaceText.disableProperty().bind(Bindings.isEmpty(TF_ReplaceFind.textProperty()));

		BTN_ReplaceLettersCase.disableProperty().bind(ChoiceBox_LettersCase.valueProperty().isNull());

		BTN_ReplaceMultipleOccurrences.disableProperty().bind(Bindings.isEmpty(TF_ReplaceMultipleOccurrences.textProperty()));

		BTN_ReplaceMiscellaneous.disableProperty().bind(ChoiceBox_ReplaceMiscellaneous.valueProperty().isNull());

		// ************************************************************************* //
		BTN_InsertText.disableProperty().bind(Bindings.isEmpty(TF_InsertText.textProperty()));

		BTN_InsertMiscellaneous.disableProperty().bind(ChoiceBox_InsertMiscellaneous.valueProperty().isNull());

		Spin_InsertAtPosition.disableProperty().bind(RB_InsertAtPosition.selectedProperty().not());

		// ************************************************************************* //
		BTN_RenameNumber.disableProperty().bind(Bindings.createBooleanBinding(
				() ->  TF_RenameText.getText().trim().isEmpty(), TF_RenameText.textProperty()));

		BTN_RenameMiscellaneous.disableProperty().bind(ChoiceBox_RenameMiscellaneous.valueProperty().isNull());


		// ************************************************************************* //
		BTN_RemoveText.disableProperty().bind(Bindings.isEmpty(TF_RemoveText.textProperty()));

		BTN_RemoveTextBetween.disableProperty().bind(Bindings.isEmpty(TF_RemoveTextFrom.textProperty())
				.or(Bindings.isEmpty(TF_RemoveTextTo.textProperty())));

		//		BTN_RemoveTextBetween.disableProperty().bind(Bindings.createBooleanBinding(() ->  TF_RemoveTextFrom.getText().trim().isEmpty(), TF_RemoveTextFrom.textProperty())
		//				.or(Bindings.createBooleanBinding(() ->  TF_RemoveTextTo.getText().trim().isEmpty(), TF_RemoveTextTo.textProperty()))
		//				);	

		//		BTN_RemovePosition.disableProperty().bind(Bindings.createBooleanBinding(
		//				() -> Spin_RemoveFrom.getValue() > Spin_RemoveTo.getValue(), Spin_RemoveFrom.valueProperty()));	

		BTN_RemoveExtraSpaces.disableProperty().bind(ChoiceBox_Trims.valueProperty().isNull()
				.and(CheckBox_RemoveExtraSpaces.selectedProperty().not()));

		BTN_RemoveBrackets.disableProperty().bind(ChoiceBox_Brackets.valueProperty().isNull());

		CheckBox_RemoveExtraSpaces.disableProperty().bind(Bindings.createBooleanBinding(
				() -> ChoiceBox_Trims.getSelectionModel().getSelectedIndex() == 6,
				ChoiceBox_Trims.getSelectionModel().selectedIndexProperty())
				);

		// ************************************************************************* //
		BTN_ExtAdd.disableProperty().bind(Bindings.isEmpty(TF_ExtAdd.textProperty()));

		//		BTN_ExtReplace.disableProperty().bind(CheckBox_ExtReplaceAll.selectedProperty().not()
		//				.and(Bindings.isEmpty(TF_ExtReplace.textProperty()))
		//				.or(Bindings.isEmpty(TF_ExtReplaceWith.textProperty())));	

		BTN_ExtReplace.disableProperty().bind(CheckBox_ExtReplaceAll.selectedProperty().not()
				.and(Bindings.createBooleanBinding(() ->  TF_ExtReplace.getText().trim().isEmpty(), TF_ExtReplace.textProperty()))
				.or(Bindings.createBooleanBinding(() ->  TF_ExtReplaceWith.getText().trim().isEmpty(), TF_ExtReplaceWith.textProperty()))
				);		

		BTN_ExtRemove.disableProperty().bind(CheckBox_ExtRemoveAll.selectedProperty().not()
				.and(Bindings.createBooleanBinding(() ->  TF_ExtRemove.getText().trim().isEmpty(), TF_ExtRemove.textProperty())));

		TF_ExtReplace.disableProperty().bind(CheckBox_ExtReplaceAll.selectedProperty());

		TF_ExtRemove.disableProperty().bind(CheckBox_ExtRemoveAll.selectedProperty());

	}


	public BooleanBinding isEitherFieldEmpty() { 
		return Bindings.isEmpty(TF_ExtReplaceWith.textProperty()).or(CheckBox_ExtRemoveAll.selectedProperty());
	}



	private void resetColumnsBeforeRename() {

		// Clear "Renamed" & Status colums contents				
		for (Store_Files store_Files : observableList_Files) {
			store_Files.cancelRenaming();
		}
	}


	public static void hackTooltipStartTiming(Tooltip tooltip) {
		try {
			Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
			fieldBehavior.setAccessible(true);
			Object objBehavior = fieldBehavior.get(tooltip);

			Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
			fieldTimer.setAccessible(true);
			Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

			objTimer.getKeyFrames().clear();
			objTimer.getKeyFrames().add(new KeyFrame(new Duration(250)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
