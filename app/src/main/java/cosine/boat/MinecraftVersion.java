package cosine.boat;

import android.util.ArrayMap;
import java.util.*;
import com.google.gson.*;
import com.mio.launcher.MioInfo;
import com.mio.launcher.MioUtils;

import java.io.*;

public class MinecraftVersion {

	public class AssetsIndex {
		public String id;
		public String sha1;
		public int size;
		public int totalSize;
		public String url;
	}
	public class Download {
		public String path;
		public String sha1;
		public int size;
		public String url;
	}
	public AssetsIndex assetIndex;
	public String assets;

	public HashMap<String, Download> downloads;
	public String id;

	public class Library {
		public String name;
		public HashMap<String, Download> downloads;
	}

	public Library libraries[];

	public String mainClass;
	public String minecraftArguments;
	public int minimumLauncherVersion;
	public String releaseTime;
	public String time;
	public String type;

	//minimumLauncherVersion >= 21
    public Arguments arguments;

	// forge
	public String inheritsFrom;





	public String minecraftPath;
    public static MinecraftVersion tempMCV;
	public static MinecraftVersion fromDirectory(File file) {
		try {
			tempMCV=new MinecraftVersion();
			if (!new File(file, file.getName() + ".json").exists()){
				return  null;
			}
			String json = new String(Utils.readFile(new File(file, file.getName() + ".json")), "UTF-8");
			MinecraftVersion result = new Gson().fromJson(json, MinecraftVersion.class);
			if (new File(file, file.getName() + ".jar").exists()) {
				result.minecraftPath = new File(file, file.getName() + ".jar").getAbsolutePath();
			} else {
				result.minecraftPath = "";
			}

			if (result.inheritsFrom != null && !result.inheritsFrom.equals("")) {
				if (!new File(MioInfo.DIR_VERSIONS,result.inheritsFrom).exists()){
					return result;
				}
				MinecraftVersion self = result;
				result = MinecraftVersion.fromDirectory(new File(file.getParentFile(), self.inheritsFrom));
				if (self.inheritsFrom != null) {
					result.inheritsFrom = self.inheritsFrom;
				}
				if (self.assetIndex != null) {
					result.assetIndex = self.assetIndex;
				}
				if (self.assets != null && !self.assets.equals("")) {
					result.assets = self.assets;
				}
				if (self.downloads != null && !self.downloads.isEmpty()) {

					if (result.downloads == null) {
						result.downloads = new HashMap<String, Download>();
					}

					for (Map.Entry<String, Download> e : self.downloads.entrySet()) {
						result.downloads.put(e.getKey(), e.getValue());
					}
				}
				if (self.libraries != null && self.libraries.length > 0) {
					Library newLibs[] = new Library[result.libraries.length + self.libraries.length];
					int i = 0;
					for (Library lib : self.libraries) {
						newLibs[i] = lib;
						i++;
					}
					for (Library lib : result.libraries) {
						newLibs[i] = lib;
						i++;
					}


					result.libraries = newLibs;
				}

				if (self.mainClass != null && !self.mainClass.equals("")) {
					result.mainClass = self.mainClass;
				}
				if (self.minecraftArguments != null && !self.minecraftArguments.equals("")) {
					result.minecraftArguments = self.minecraftArguments;
				}
				if (self.arguments != null && !self.arguments.equals("")) {
					tempMCV.arguments=self.arguments;
				}
				if (self.minimumLauncherVersion > result.minimumLauncherVersion) {
					result.minimumLauncherVersion = self.minimumLauncherVersion;
				}
				if (self.releaseTime != null && !self.releaseTime.equals("")) {
					result.releaseTime = self.releaseTime;
				}
				if (self.time != null && !self.time.equals("")) {
					result.time = self.time;
				}
				if (self.type != null && !self.type.equals("")) {
					result.type = self.type;
				}

				if (self.minecraftPath != null && !self.minecraftPath.equals("")) {
					result.minecraftPath = self.minecraftPath;
				}

			}

			return result;

		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public String getClassPath(LauncherConfig config) {
		String cp = "";
		int count = 0;

		String libraries_path = MioInfo.DIR_LIBRARIES;

		for (Library lib : this.libraries) {
			if (lib.name == null || lib.name.equals("")||lib.name.contains("net.java.jinput")||lib.name.contains("org.lwjgl")||lib.name.contains("platform")) {
				continue;
			}


			String path = new File(libraries_path,MioUtils.parseLibNameToPath(lib.name)).getAbsolutePath();


			if (count > 0) {
				cp = cp + ":";
			}
			cp = cp + path;
			count++;
		}
		if (count > 0) {
			cp = ":" + cp;
		}
		cp = minecraftPath + cp;

		return cp;
	}

	public List<String> getLibraries() {
		List<String> libs=new ArrayList<>();
		for (Library lib : this.libraries) {
			if (lib.name == null || lib.name.equals("") || lib.name.contains("net.java.jinput") || lib.name.contains("org.lwjgl")||lib.name.contains("platform")) {
				continue;
			}
			libs.add(MioUtils.parseLibNameToPath(lib.name));
		}
		return libs;
	}
	private Map<String,String> SHAs;
	public String getSHA1(String libName){
		if (SHAs==null){
			SHAs=new ArrayMap<>();
			for (Library lib : this.libraries) {
				if (lib.name == null || lib.name.equals("") || lib.name.contains("net.java.jinput") || lib.name.contains("org.lwjgl")||lib.name.contains("platform")) {
					continue;
				}
				String sha1;
				try {
					sha1=lib.downloads.get("artifact").sha1;
				}catch (Exception e){
					continue;
				}
				SHAs.put(MioUtils.parseLibNameToPath(lib.name),sha1);
			}
		}
		return SHAs.get(libName);
	}
	public String[] getMinecraftArguments(LauncherConfig config,boolean isHighVer) {
        String test = "";
        if(isHighVer){
            Object[] objs= this.arguments.game;
            for(int i=0;i<objs.length;i++){
                if(objs[i] instanceof String){
                    test+=objs[i].toString()+" ";
//					System.out.println(objs[i]);
                }
            }
			if(tempMCV.arguments!=null){
				objs= tempMCV.arguments.game;
				for(int i=0;i<objs.length;i++){
					if(objs[i] instanceof String){
						test+=objs[i].toString()+" ";
//					System.out.println(objs[i]);
					}
				}
			}
			
        }else{
			test= new String(this.minecraftArguments);
		}
		String result = "";

		int state = 0;
		int start = 0;
		int stop = 0;
		for (int i = 0; i < test.length(); i++) {
			if (state == 0) {
				if (test.charAt(i) != '$') {
					result = result + test.charAt(i);

				} else {
					if (i + 1 < test.length() && test.charAt(i + 1) == '{') {
						state = 1;
						start = i;
					} else {
						result = result + test.charAt(i);
					}

				}
				continue;
			} else {
				if (test.charAt(i) == '}') {
					stop = i;

					String key = test.substring(start + 2, stop);

					String value = "";

					if (key != null && key.equals("version_name")) {
						value = id;
					} else if(key!=null&&key.equals("version_type")){
                        value=type;
                    }else if (key != null && key.equals("assets_index_name")) {


						if (assetIndex != null) {
							value = assetIndex.id;
						} else {
							value = assets;
						}

					}else {
						if(key !=null&&key.equals("game_directory")){
							value=config.get("currentVersion");
						}else{
							value = config.get(key);
						}
						
					}
					result = result + value;
					i = stop;
					state = 0;
				}
			}
		}
//		System.out.println(result);
		return result.split(" ");
	}
    //参考mcinabox
    public class Arguments{
        private Object[] game;
        public Object[] getGame() { return game; }
    }
}
