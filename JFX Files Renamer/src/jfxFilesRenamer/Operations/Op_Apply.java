package jfxFilesRenamer.Operations;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert.AlertType;
import jfxFilesRenamer.ClassBaseLocation;
import jfxFilesRenamer.Main;
import jfxFilesRenamer.MessageBox;
import jfxFilesRenamer.MiscellaneousMethods;
import jfxFilesRenamer.MyThreadListener;
import jfxFilesRenamer.Enumerators.Validation;
import jfxFilesRenamer.Extensions.AllExtensions;
import jfxFilesRenamer.Stores.Store_Files;


public class Op_Apply implements Runnable {

	//**************************************************************
	//*********************** Declarations *************************
	//**************************************************************

	private final Consumer<Store_Files> processor;
	MyThreadListener listener;

	public static final String applicationLocation = ClassBaseLocation.getBaseLocation(Main.class);
	private ObservableList<Store_Files> observableList_Files;
	private final static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat timeFormatter = new SimpleDateFormat("HH'h'mm");
	private String renamedFileHistory;
	private FileWriter fileWriter;



	//**************************************************************
	//************************ Constructors ************************
	//**************************************************************
	public Op_Apply() {
		super();
		this.processor = null;
		this.renamedFileHistory = "";
		this.fileWriter = null;
	}


	public Op_Apply(Consumer<Store_Files> processor) {
		super();
		this.processor = processor;
	}



	//**************************************************************
	//********************* Getters / Setters **********************
	//**************************************************************

	public MyThreadListener getListener() {
		return this.listener;
	}

	public ObservableList<Store_Files> getObservableList_Files() {
		return observableList_Files;
	}






	public void setListener(MyThreadListener listener) {
		this.listener = listener;
	}

	public void setObservableList_Files(
			ObservableList<Store_Files> observableList_Files) {
		this.observableList_Files = observableList_Files;
	}





	//**************************************************************
	//************************** Methods ***************************
	//**************************************************************

	@Override
	public void run() {

		try {
			MiscellaneousMethods.CreateFolders();

			renamedFileHistory = String.format("%s (%s) (%s).csv",
					Paths.get(File.separator, applicationLocation, "JFX Files Renamer History/History").toString(),
					dateFormatter.format(Calendar.getInstance().getTime()),
					timeFormatter.format(Calendar.getInstance().getTime()));

			fileWriter = new FileWriter(renamedFileHistory);
			SaveCSV.createHeader(fileWriter);

			for (Store_Files store_Files : observableList_Files) {
				if (store_Files.isReadyForRename()) {
					try {	
						Path renamedFilePath = Paths.get("/", store_Files.getParentFolder(), store_Files.getNameRenamed());
						String newExtension = AllExtensions.getExtension(store_Files.getNameRenamed());
						String newFilenameWithoutExt = AllExtensions.getBaseName(store_Files.getNameRenamed());

						// Apply renaming
						SaveCSV.write(fileWriter, store_Files.getFilePath(), renamedFilePath.toString());
						Files.move(Paths.get(store_Files.getFilePath()), renamedFilePath);

						// Update files' list with the new changements
						store_Files.setNameOriginal(store_Files.getNameRenamed());
						store_Files.setNameRenamed(store_Files.getNameRenamed());
						store_Files.setExtension(newExtension);
						store_Files.setNameOriginalWithoutExt(newFilenameWithoutExt);
						store_Files.setFilePath(renamedFilePath.toString());
						store_Files.setRenamingStatus("Rename Applied");
						// store_Files.setNameValidation(Validation.UnRenamed);
						store_Files.setRenameApplied(true);
						store_Files.setReadyForRename(false);

						processor.accept(store_Files);

					} catch (Exception e) {
						store_Files.setNameValidation(Validation.INVALID);
						store_Files.setRenamingStatus("Error while applying rename");
						store_Files.setRenameApplied(false);
						store_Files.setReadyForRename(false);
						e.printStackTrace();
					}
				}
			}

		} catch (Exception e) {
			listener.threadFailed();

		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			
			Platform.runLater(new Runnable(){
				@Override
				public void run() {
					
				MessageBox.createMessageBox(AlertType.INFORMATION,
							"Renaming Hitory",
							null,	
							"The history of renamed files is saved in :" + System.lineSeparator() + renamedFileHistory,
							450,
							100,
							NodeOrientation.INHERIT)
					.showAndWait();
				}
			});
		}

		listener.threadFinished();
	}





}
