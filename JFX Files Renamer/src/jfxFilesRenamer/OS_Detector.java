package jfxFilesRenamer;

public class OS_Detector {


	private static String OS_NAME = System.getProperty("os.name").toLowerCase();
	
	
    public static final boolean iS_WINDOWS = OS_Match("Windows");
    public static final boolean iS_LINUX = OS_Match("Linux");
    public static final boolean iS_AIX = OS_Match("AIX");
    public static final boolean iS_HP_UX = OS_Match("HP-UX");
    public static final boolean iS_IRIX = OS_Match("Irix");
    public static final boolean iS_SOLARIS = OS_Match("Solaris");
    public static final boolean iS_SUN_OS = OS_Match("SunOS");
    public static final boolean iS_FREE_BSD = OS_Match("FreeBSD");
    public static final boolean iS_OPEN_BSD = OS_Match("OpenBSD");
    public static final boolean iS_NET_BSD = OS_Match("NetBSD");
    public static final boolean iS_MAC_OSX = OS_Match("Mac OS X");
    public static final boolean iS_MAC = OS_Match("Mac");
    public static final boolean iS_400 = OS_Match("OS/400");
    public static final boolean iS_OS2 = OS_Match("OS/2");
    public static final boolean iS_UNIX = iS_LINUX || iS_HP_UX || iS_IRIX || iS_AIX || iS_MAC_OSX
                || iS_SOLARIS || iS_SUN_OS || iS_FREE_BSD || iS_OPEN_BSD || iS_NET_BSD;
    
    
	
   private static boolean OS_Match(final String osNamePrefix) {
        if (OS_NAME == null) {
            return false;
        }
        return OS_NAME.startsWith(osNamePrefix.toLowerCase());
    }
 
	
	public static String getOS(){
		if (iS_WINDOWS) {
			return "Windows";
		} else if (iS_LINUX) {
			return "Linux";
		} else if (iS_AIX) {
			return "AIX";
		} else if (iS_HP_UX) {
			return "HP-UX";
		} else if (iS_IRIX) {
			return "Irix";
		} else if (iS_SOLARIS) {
			return "Solaris";
		} else if (iS_SUN_OS) {
			return "SunOS";
		} else if (iS_FREE_BSD) {
			return "FreeBSD";
		} else if (iS_OPEN_BSD) {
			return "OpenBSD";
		} else if (iS_NET_BSD) {
			return "NetBSD";
		} else if (iS_MAC_OSX) {
			return "Mac OS X";
		} else if (iS_MAC) {
			return "Mac";
		} else if (iS_400) {
			return "OS/400";
		} else if (iS_OS2) {
			return "OS/2";
		} else if (iS_UNIX) {
			return "Unix";
		} else {
			return "Unknown Operating System";
		}
	}

}
