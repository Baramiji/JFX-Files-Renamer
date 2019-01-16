package jfxFilesRenamer.Extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javafx.stage.FileChooser.ExtensionFilter;
import jfxFilesRenamer.Stores.Store_CommonExtension;



public class CommonExtensions {

	//**************************************************************
	//*********************** Declarations *************************
	//**************************************************************
	static List<Store_CommonExtension> extensionsList = new ArrayList<>();



	//**************************************************************
	//*********************** Constructors *************************
	//**************************************************************
	public CommonExtensions() {
		super();
	}



	//**************************************************************
	//************************** Methods ***************************
	//**************************************************************
	static {
		createExtensionList();
	}


	private static void createExtensionList() {
		extensionsList.add(new Store_CommonExtension("Audio", "*.aac"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.aif"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.aiff"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.ape"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.au"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.cda"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.flac"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.gsm"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.iff"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.it"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.m3u"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.m4a"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.mid"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.midi"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.mod"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.mp3"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.mpa"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.ogg"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.pls"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.ra"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.s3m"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.sid"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.wav"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.wma"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.wpl"));
		extensionsList.add(new Store_CommonExtension("Audio", "*.xm"));
		extensionsList.add(new Store_CommonExtension("Compressed", "*.7z"));
		extensionsList.add(new Store_CommonExtension("Compressed", "*.arj"));
		extensionsList.add(new Store_CommonExtension("Compressed", "*.bz2"));
		extensionsList.add(new Store_CommonExtension("Compressed", "*.cab"));
		extensionsList.add(new Store_CommonExtension("Compressed", "*.cbr"));
		extensionsList.add(new Store_CommonExtension("Compressed", "*.deb"));
		extensionsList.add(new Store_CommonExtension("Compressed", "*.gz"));
		extensionsList.add(new Store_CommonExtension("Compressed", "*.pkg"));
		extensionsList.add(new Store_CommonExtension("Compressed", "*.rar"));
		extensionsList.add(new Store_CommonExtension("Compressed", "*.rpm"));
		extensionsList.add(new Store_CommonExtension("Compressed", "*.sit"));
		extensionsList.add(new Store_CommonExtension("Compressed", "*.sitx"));
		extensionsList.add(new Store_CommonExtension("Compressed", "*.tar"));
		extensionsList.add(new Store_CommonExtension("Compressed", "*.tar.gz"));
		extensionsList.add(new Store_CommonExtension("Compressed", "*.z"));
		extensionsList.add(new Store_CommonExtension("Compressed", "*.zip"));
		extensionsList.add(new Store_CommonExtension("Compressed", "*.zipx"));
		extensionsList.add(new Store_CommonExtension("Database", "*.accdb"));
		extensionsList.add(new Store_CommonExtension("Database", "*.db"));
		extensionsList.add(new Store_CommonExtension("Database", "*.dbf"));
		extensionsList.add(new Store_CommonExtension("Database", "*.mdb"));
		extensionsList.add(new Store_CommonExtension("Database", "*.pdb"));
		extensionsList.add(new Store_CommonExtension("Database", "*.sql"));
		extensionsList.add(new Store_CommonExtension("Database", "*.sqlite"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.c"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.cc"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.class"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.clj"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.cpp"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.cs"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.cxx"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.diff"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.dtd"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.el"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.fla"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.go"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.h"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.java"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.lua"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.m"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.m4"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.php"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.pl"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.po"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.py"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.rb"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.rs"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.sh"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.sln"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.swift"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.vb"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.vcxproj"));
		extensionsList.add(new Store_CommonExtension("Developer", "*.xcodeproj"));
		extensionsList.add(new Store_CommonExtension("Document", "*.csv"));
		extensionsList.add(new Store_CommonExtension("Document", "*.doc"));
		extensionsList.add(new Store_CommonExtension("Document", "*.docx"));
		extensionsList.add(new Store_CommonExtension("Document", "*.ebook"));
		extensionsList.add(new Store_CommonExtension("Document", "*.log"));
		extensionsList.add(new Store_CommonExtension("Document", "*.md"));
		extensionsList.add(new Store_CommonExtension("Document", "*.msg"));
		extensionsList.add(new Store_CommonExtension("Document", "*.odp"));
		extensionsList.add(new Store_CommonExtension("Document", "*.ods"));
		extensionsList.add(new Store_CommonExtension("Document", "*.odt"));
		extensionsList.add(new Store_CommonExtension("Document", "*.org"));
		extensionsList.add(new Store_CommonExtension("Document", "*.pages"));
		extensionsList.add(new Store_CommonExtension("Document", "*.pdf"));
		extensionsList.add(new Store_CommonExtension("Document", "*.pps"));
		extensionsList.add(new Store_CommonExtension("Document", "*.ppt"));
		extensionsList.add(new Store_CommonExtension("Document", "*.pptx"));
		extensionsList.add(new Store_CommonExtension("Document", "*.rtf"));
		extensionsList.add(new Store_CommonExtension("Document", "*.tex"));
		extensionsList.add(new Store_CommonExtension("Document", "*.txt"));
		extensionsList.add(new Store_CommonExtension("Document", "*.wks"));
		extensionsList.add(new Store_CommonExtension("Document", "*.wpd"));
		extensionsList.add(new Store_CommonExtension("Document", "*.wps"));
		extensionsList.add(new Store_CommonExtension("Document", "*.xlr"));
		extensionsList.add(new Store_CommonExtension("Document", "*.xls"));
		extensionsList.add(new Store_CommonExtension("Document", "*.xlsx"));
		extensionsList.add(new Store_CommonExtension("Document", "*.xml"));
		extensionsList.add(new Store_CommonExtension("Executable", "*.apk"));
		extensionsList.add(new Store_CommonExtension("Executable", "*.app"));
		extensionsList.add(new Store_CommonExtension("Executable", "*.bat"));
		extensionsList.add(new Store_CommonExtension("Executable", "*.bin"));
		extensionsList.add(new Store_CommonExtension("Executable", "*.cgi"));
		extensionsList.add(new Store_CommonExtension("Executable", "*.com"));
		extensionsList.add(new Store_CommonExtension("Executable", "*.exe"));
		extensionsList.add(new Store_CommonExtension("Executable", "*.gadget"));
		extensionsList.add(new Store_CommonExtension("Executable", "*.jar"));
		extensionsList.add(new Store_CommonExtension("Executable", "*.pl"));
		extensionsList.add(new Store_CommonExtension("Executable", "*.py"));
		extensionsList.add(new Store_CommonExtension("Executable", "*.wsf"));
		extensionsList.add(new Store_CommonExtension("Fonts", "*.eot"));
		extensionsList.add(new Store_CommonExtension("Fonts", "*.fnt"));
		extensionsList.add(new Store_CommonExtension("Fonts", "*.fon"));
		extensionsList.add(new Store_CommonExtension("Fonts", "*.otf"));
		extensionsList.add(new Store_CommonExtension("Fonts", "*.ttf"));
		extensionsList.add(new Store_CommonExtension("Fonts", "*.woff"));
		extensionsList.add(new Store_CommonExtension("Fonts", "*.woff2"));
		extensionsList.add(new Store_CommonExtension("Image", "*.bmp"));
		extensionsList.add(new Store_CommonExtension("Image", "*.dds"));
		extensionsList.add(new Store_CommonExtension("Image", "*.gif"));
		extensionsList.add(new Store_CommonExtension("Image", "*.jpeg"));
		extensionsList.add(new Store_CommonExtension("Image", "*.jpg"));
		extensionsList.add(new Store_CommonExtension("Image", "*.png"));
		extensionsList.add(new Store_CommonExtension("Image", "*.psd"));
		extensionsList.add(new Store_CommonExtension("Image", "*.pspimage"));
		extensionsList.add(new Store_CommonExtension("Image", "*.tga"));
		extensionsList.add(new Store_CommonExtension("Image", "*.thm"));
		extensionsList.add(new Store_CommonExtension("Image", "*.tif"));
		extensionsList.add(new Store_CommonExtension("Image", "*.tiff"));
		extensionsList.add(new Store_CommonExtension("Image", "*.webp"));
		extensionsList.add(new Store_CommonExtension("Image", "*.yuv"));
		extensionsList.add(new Store_CommonExtension("System", "*.bak"));
		extensionsList.add(new Store_CommonExtension("System", "*.cab"));
		extensionsList.add(new Store_CommonExtension("System", "*.cfg"));
		extensionsList.add(new Store_CommonExtension("System", "*.cpl"));
		extensionsList.add(new Store_CommonExtension("System", "*.cur"));
		extensionsList.add(new Store_CommonExtension("System", "*.deskthemepack"));
		extensionsList.add(new Store_CommonExtension("System", "*.dll"));
		extensionsList.add(new Store_CommonExtension("System", "*.dmp"));
		extensionsList.add(new Store_CommonExtension("System", "*.drv"));
		extensionsList.add(new Store_CommonExtension("System", "*.icns"));
		extensionsList.add(new Store_CommonExtension("System", "*.ico"));
		extensionsList.add(new Store_CommonExtension("System", "*.ini"));
		extensionsList.add(new Store_CommonExtension("System", "*.lnk"));
		extensionsList.add(new Store_CommonExtension("System", "*.msi"));
		extensionsList.add(new Store_CommonExtension("System", "*.sys"));
		extensionsList.add(new Store_CommonExtension("System", "*.tmp"));
		extensionsList.add(new Store_CommonExtension("Video", "*.3g2"));
		extensionsList.add(new Store_CommonExtension("Video", "*.3gp"));
		extensionsList.add(new Store_CommonExtension("Video", "*.3gp2"));
		extensionsList.add(new Store_CommonExtension("Video", "*.3gpp"));
		extensionsList.add(new Store_CommonExtension("Video", "*.amv"));
		extensionsList.add(new Store_CommonExtension("Video", "*.asf"));
		extensionsList.add(new Store_CommonExtension("Video", "*.avi"));
		extensionsList.add(new Store_CommonExtension("Video", "*.divx"));
		extensionsList.add(new Store_CommonExtension("Video", "*.dv"));
		extensionsList.add(new Store_CommonExtension("Video", "*.flv"));
		extensionsList.add(new Store_CommonExtension("Video", "*.h264"));
		extensionsList.add(new Store_CommonExtension("Video", "*.m1v"));
		extensionsList.add(new Store_CommonExtension("Video", "*.m2t"));
		extensionsList.add(new Store_CommonExtension("Video", "*.m2ts"));
		extensionsList.add(new Store_CommonExtension("Video", "*.m2v"));
		extensionsList.add(new Store_CommonExtension("Video", "*.m4v"));
		extensionsList.add(new Store_CommonExtension("Video", "*.mkv"));
		extensionsList.add(new Store_CommonExtension("Video", "*.mov"));
		extensionsList.add(new Store_CommonExtension("Video", "*.mp2"));
		extensionsList.add(new Store_CommonExtension("Video", "*.mp2v"));
		extensionsList.add(new Store_CommonExtension("Video", "*.mp4"));
		extensionsList.add(new Store_CommonExtension("Video", "*.mp4v"));
		extensionsList.add(new Store_CommonExtension("Video", "*.mpa"));
		extensionsList.add(new Store_CommonExtension("Video", "*.mpe"));
		extensionsList.add(new Store_CommonExtension("Video", "*.mpeg"));
		extensionsList.add(new Store_CommonExtension("Video", "*.mpeg1"));
		extensionsList.add(new Store_CommonExtension("Video", "*.mpeg2"));
		extensionsList.add(new Store_CommonExtension("Video", "*.mpeg4"));
		extensionsList.add(new Store_CommonExtension("Video", "*.mpg"));
		extensionsList.add(new Store_CommonExtension("Video", "*.mpv2"));
		extensionsList.add(new Store_CommonExtension("Video", "*.mts"));
		extensionsList.add(new Store_CommonExtension("Video", "*.nsv"));
		extensionsList.add(new Store_CommonExtension("Video", "*.nuv"));
		extensionsList.add(new Store_CommonExtension("Video", "*.ogg"));
		extensionsList.add(new Store_CommonExtension("Video", "*.ogm"));
		extensionsList.add(new Store_CommonExtension("Video", "*.ogv"));
		extensionsList.add(new Store_CommonExtension("Video", "*.ogx"));
		extensionsList.add(new Store_CommonExtension("Video", "*.ps"));
		extensionsList.add(new Store_CommonExtension("Video", "*.rec"));
		extensionsList.add(new Store_CommonExtension("Video", "*.rm"));
		extensionsList.add(new Store_CommonExtension("Video", "*.rmbv "));
		extensionsList.add(new Store_CommonExtension("Video", "*.rmvb"));
		extensionsList.add(new Store_CommonExtension("Video", "*.rv"));
		extensionsList.add(new Store_CommonExtension("Video", "*.swf"));
		extensionsList.add(new Store_CommonExtension("Video", "*.tod"));
		extensionsList.add(new Store_CommonExtension("Video", "*.ts"));
		extensionsList.add(new Store_CommonExtension("Video", "*.tts"));
		extensionsList.add(new Store_CommonExtension("Video", "*.vi"));
		extensionsList.add(new Store_CommonExtension("Video", "*.vob"));
		extensionsList.add(new Store_CommonExtension("Video", "*.vro"));
		extensionsList.add(new Store_CommonExtension("Video", "*.webm"));
		extensionsList.add(new Store_CommonExtension("Video", "*.wmv"));
		extensionsList.add(new Store_CommonExtension("Web", "*.asp"));
		extensionsList.add(new Store_CommonExtension("Web", "*.aspx"));
		extensionsList.add(new Store_CommonExtension("Web", "*.cer"));
		extensionsList.add(new Store_CommonExtension("Web", "*.cfm"));
		extensionsList.add(new Store_CommonExtension("Web", "*.cgi"));
		extensionsList.add(new Store_CommonExtension("Web", "*.csr"));
		extensionsList.add(new Store_CommonExtension("Web", "*.css"));
		extensionsList.add(new Store_CommonExtension("Web", "*.dcr"));
		extensionsList.add(new Store_CommonExtension("Web", "*.htm"));
		extensionsList.add(new Store_CommonExtension("Web", "*.html"));
		extensionsList.add(new Store_CommonExtension("Web", "*.js"));
		extensionsList.add(new Store_CommonExtension("Web", "*.jsp"));
		extensionsList.add(new Store_CommonExtension("Web", "*.part"));
		extensionsList.add(new Store_CommonExtension("Web", "*.php"));
		extensionsList.add(new Store_CommonExtension("Web", "*.pl"));
		extensionsList.add(new Store_CommonExtension("Web", "*.py"));
		extensionsList.add(new Store_CommonExtension("Web", "*.rss"));
		extensionsList.add(new Store_CommonExtension("Web", "*.xhtml"));
	}





	public static ExtensionFilter getExtFilter_All() {
		return new ExtensionFilter("All Files", "*.*");
	}

	public static ExtensionFilter getExtFilter_Audio() {
		return getExtensionFilter("Audio");
	}

	public static ExtensionFilter getExtFilter_Video() {
		return getExtensionFilter("Video");
	}
	
	public static ExtensionFilter getExtFilter_Image() {
		return getExtensionFilter("Image");
	}
	
	public static ExtensionFilter getExtFilter_Document() {
		return getExtensionFilter("Document");
	}
	
	public static ExtensionFilter getExtFilter_Database() {
		return getExtensionFilter("Database");
	}

	public static ExtensionFilter getExtFilter_Executable() {
		return getExtensionFilter("Executable");
	}

	public static ExtensionFilter getExtFilter_Web() {
		return getExtensionFilter("Web");
	}
		
	public static ExtensionFilter getExtFilter_Fonts() {
		return getExtensionFilter("Fonts");
	}
	
	public static ExtensionFilter getExtFilter_System() {
		return getExtensionFilter("System");
	}
	
	public static ExtensionFilter getExtFilter_Compressed() {
		return getExtensionFilter("Compressed");
	}
	
	public static ExtensionFilter getExtFilter_Developer() {
		return getExtensionFilter("Developer");
	}

	
	public static Collection<ExtensionFilter> getCollection_ExtensionFilter() {	
		
		Collection<ExtensionFilter> collection_ExtensionFilter = new ArrayList<>();
		
		collection_ExtensionFilter.add(CommonExtensions.getExtFilter_All());
		collection_ExtensionFilter.add(CommonExtensions.getExtFilter_Audio());
		collection_ExtensionFilter.add(CommonExtensions.getExtFilter_Video());
		collection_ExtensionFilter.add(CommonExtensions.getExtFilter_Image());
		collection_ExtensionFilter.add(CommonExtensions.getExtFilter_Document());
		collection_ExtensionFilter.add(CommonExtensions.getExtFilter_Compressed());
		collection_ExtensionFilter.add(CommonExtensions.getExtFilter_Executable());
		collection_ExtensionFilter.add(CommonExtensions.getExtFilter_Database());
		collection_ExtensionFilter.add(CommonExtensions.getExtFilter_Web());
		collection_ExtensionFilter.add(CommonExtensions.getExtFilter_Fonts());
		collection_ExtensionFilter.add(CommonExtensions.getExtFilter_System());
		collection_ExtensionFilter.add(CommonExtensions.getExtFilter_Developer());
		
		return collection_ExtensionFilter;
	}
	
	
	
	private static ExtensionFilter getExtensionFilter(String type) {	
		
		String extensions = extensionsList.stream()
				.filter(x -> x.getType().equals(type))
				.map(x -> "\"" + x.getExtension() + "\"")
				.collect(Collectors.joining(", "));
		
		return new ExtensionFilter(type, extensions);
	}
	

}







