package jfxFilesRenamer.Operations;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfxFilesRenamer.MyThreadListener;
import jfxFilesRenamer.Enumerators.LoadingFilesToList;
import jfxFilesRenamer.Extensions.AllExtensions;
import jfxFilesRenamer.Stores.Store_Files;


public class Op_LoadFilesList implements Runnable {


	//**************************************************************
	//*********************** Declarations *************************
	//**************************************************************

	private final Consumer<ObservableList<Store_Files>> processor;
	MyThreadListener listener;
	private List<File> filesList;
	private String folderPath;
	private Boolean scanSubDirectories;
	private LoadingFilesToList scanType;
	private ObservableList<Store_Files> observableList_Files;


	//**************************************************************
	//************************ Constructors ************************
	//**************************************************************
	public Op_LoadFilesList() {
		super();
		this.processor = null;
		this.filesList = new ArrayList<>();
		this.folderPath = "";
		this.scanSubDirectories = true;
		this.scanType = LoadingFilesToList.NONE;
	}

	public Op_LoadFilesList(Consumer<ObservableList<Store_Files>> processor) {
		super();
		this.processor = processor;
		this.filesList = new ArrayList<>();
		this.folderPath = "";
		this.scanSubDirectories = true;
		this.scanType = LoadingFilesToList.NONE;
	}

	//**************************************************************
	//********************* Setters / getters **********************
	//**************************************************************
	public Consumer<ObservableList<Store_Files>> getProcessor() {
		return processor;
	}

	public MyThreadListener getListener() {
		return this.listener;
	}

	public List<File> getFilesList() {
		return this.filesList;
	}

	public String getFolderPath() {
		return this.folderPath;
	}

	public Boolean getScanSubDirectories() {
		return this.scanSubDirectories;
	}

	public LoadingFilesToList getScanType() {
		return this.scanType;
	}

	public ObservableList<Store_Files> getObservableList_Files() {
		return this.observableList_Files;
	}



	public void setListener(MyThreadListener listener) {
		this.listener = listener;
	}

	public void setFilesList(List<File> filesList) {
		this.filesList = filesList;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public void setScanSubDirectories(Boolean scanSubDirs) {
		this.scanSubDirectories = scanSubDirs;
	}

	public void setScanType(LoadingFilesToList scanType) {
		this.scanType = scanType;
	}

	public void setObservableList_Files(ObservableList<Store_Files> observableList_Files) {
		this.observableList_Files = observableList_Files;
	}


	@Override
	public void run() {

		switch (scanType) {
			case ADDFILES :
				addFiles();
				break;

			case SCANFOLDER :
				scanDirFiles();
				break;

			case NONE :
				// scanDirFiles();
				break;

			default :
				break;
		}

	}




	private void addFiles() {

		List<File> filesPathsList = new ArrayList<>();

		//
		for (int i = 0; i < observableList_Files.size(); i++) {
			filesPathsList.add(Paths.get(observableList_Files.get(i).getFilePath()).toFile());
		}

		observableList_Files.clear();	

		try {
			if (filesList != null || !filesList.isEmpty()) {

				filesPathsList = Stream.concat(
						filesPathsList.stream(),
						filesList.stream())
						.map(x -> x)
						.distinct()
						.collect(Collectors.toList());

				int[] ID_Increment = {0};
				//AtomicInteger ID_Increment = new AtomicInteger(0);
				observableList_Files = filesPathsList.stream()
						.filter(e -> e.exists() && e.isFile())
						.map((x) -> {
							Store_Files storeFile = new Store_Files();
							storeFile.setFileID(Integer.toString(ID_Increment[0]++));
							storeFile.setFilePath(getFilePath(x));
							storeFile.setParentFolder(x.getParent());
							storeFile.setNameOriginal(x.getName());
							storeFile.setNameOriginalWithoutExt(getFilenameWithoutExt(x));
							storeFile.setExtension(getFileExtension(x));
							return storeFile;
						})
						.collect(Collectors.toCollection(FXCollections::observableArrayList));
			}

			//Sort files' List by name
			if (!observableList_Files.isEmpty()) 
				Collections.sort(observableList_Files, comparator_byOriginalName);

		} catch (Exception e) {
			e.printStackTrace();
			listener.threadFailed();
		} finally {
			processor.accept(observableList_Files);
		}

		listener.threadFinished();
	}



	private void scanDirFiles() {

		int[] ID_Increment = {0};
		if (scanSubDirectories) {

			ObservableList<Store_Files> obs_Files = FXCollections.observableArrayList();

			try {
				// Files.walkFileTree(Paths.get(folderPath), Collections.emptySet(), 9999, new SimpleFileVisitor<Path>() {
				Files.walkFileTree(Paths.get(folderPath), new SimpleFileVisitor<Path>() {			
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						if (attrs.isRegularFile()) {
							Store_Files storeFile = new Store_Files();
							storeFile.setFilePath(file.toString());
							obs_Files.add(storeFile);
						}
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
						System.out.println(exc.getMessage());
						return FileVisitResult.CONTINUE;
					}					
				});


				obs_Files.stream()
				.map((x) -> {
					Path filePath = Paths.get(x.getFilePath());
					x.setFileID(Integer.toString(ID_Increment[0]++));
					x.setFilePath(filePath.toString());
					x.setParentFolder(filePath.getParent().toString());
					x.setNameOriginal(filePath.getFileName().toString());
					x.setNameOriginalWithoutExt(getFilenameWithoutExt(filePath));
					x.setExtension(getFileExtension(filePath));
					return x;
				})
				.collect(Collectors.toCollection(FXCollections::observableArrayList));

				//Sort files' List by path
				if (!obs_Files.isEmpty()) 
					Collections.sort(obs_Files, comparator_byPath);

			} catch (AccessDeniedException e) {
				e.printStackTrace();
				listener.threadFailed();
			} catch (IOException e) {
				e.printStackTrace();
				listener.threadFailed();
			} finally {
				processor.accept(obs_Files);
			}

			listener.threadFinished();

		} else {
			ObservableList<Store_Files> obs_Files = FXCollections.observableArrayList();

			try {
				obs_Files = Files.list(Paths.get(folderPath))
						.filter(Files::isRegularFile)
						.map((x) -> {
							Store_Files storeFile = new Store_Files();
							storeFile.setFileID(Integer.toString(ID_Increment[0]++));
							storeFile.setFilePath(x.toString());
							storeFile.setParentFolder(x.getParent().toString());
							storeFile.setNameOriginal(x.getFileName().toString());
							storeFile.setNameOriginalWithoutExt(getFilenameWithoutExt(x));
							storeFile.setExtension(getFileExtension(x));
							return storeFile;
						})
						.collect(Collectors.toCollection(FXCollections::observableArrayList));


				//Sort files' List by path
				if (!obs_Files.isEmpty()) 
					Collections.sort(obs_Files, comparator_byPath);

			} catch (AccessDeniedException e) {
				e.printStackTrace();
				listener.threadFailed();
			} catch (IOException e) {
				e.printStackTrace();
				listener.threadFailed();
			} finally {
				processor.accept(obs_Files);
			}

			listener.threadFinished();
		}
	}




	//Comparator for String, by Original Name
	static Comparator<? super Store_Files> comparator_byOriginalName = new Comparator<Store_Files>() {
		@Override
		public int compare(Store_Files o1, Store_Files o2) {
			return o1.getNameOriginal().compareToIgnoreCase(o2.getNameOriginal());
		}
	};

	//Comparator for String, by Renamed Name
	static Comparator<? super Store_Files> comparator_byRenamedName = new Comparator<Store_Files>() {
		@Override
		public int compare(Store_Files o1, Store_Files o2) {
			return o1.getNameRenamed().compareToIgnoreCase(o2.getNameRenamed());
		}
	};

	//Comparator for String, by Extensions
	static Comparator<? super Store_Files> comparator_byExtensions = new Comparator<Store_Files>() {
		@Override
		public int compare(Store_Files o1, Store_Files o2) {
			return o1.getExtension().compareToIgnoreCase(o2.getExtension());
		}
	};

	//Comparator for String, by Paths
	static Comparator<? super Store_Files> comparator_byPath = new Comparator<Store_Files>() {
		@Override
		public int compare(Store_Files o1, Store_Files o2) {
			return o1.getFilePath().compareToIgnoreCase(o2.getFilePath());
		}
	};

	//Comparator for String, by Parent Folders
	static Comparator<? super Store_Files> comparator_byParentFolders = new Comparator<Store_Files>() {
		@Override
		public int compare(Store_Files o1, Store_Files o2) {
			return o1.getParentFolder().compareToIgnoreCase(o2.getParentFolder());
		}
	};




	private String getFilePath(File file) {

		String filePath = "";

		try {
			filePath = file.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return filePath;
	}


	private String getFileExtension(File file) {

		//      String fileExtension = "";
		//		String fileName = file.getName();
		//		if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
		//			fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

		return AllExtensions.getExtension(getFilePath(file));
	}

	private String getFileExtension(Path filePath) {		
		return AllExtensions.getExtension(getFilePath(filePath.toFile()));
	}


	private String getFilenameWithoutExt(File file) {
		// return file.getName().replaceAll(Pattern.quote("." + AllExtensions.getExtension(getFilePath(file))), "");
		return AllExtensions.getBaseName(file.getName());		
	}

	private String getFilenameWithoutExt(Path filePath) {
		return AllExtensions.getBaseName(filePath.toFile().getName());		
	}


}



