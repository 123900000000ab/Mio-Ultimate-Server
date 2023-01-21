package com.mio.launcher;

import android.content.Context;
import android.os.Environment;
import cosine.boat.LauncherConfig;

public class MioInfo {
    public static LauncherConfig config;
    public static String DIR_MAIN = Environment.getExternalStorageDirectory() + "/MioLauncher";
    public static String DIR_GAME = DIR_MAIN + "/.minecraft";
    public static String DIR_TEMP = DIR_MAIN + "/temp";
    public static String DIR_ASSETS = DIR_GAME + "/assets";
    public static String DIR_OBJECTS = DIR_ASSETS + "/objects";
    public static String DIR_INDEXES = DIR_ASSETS+ "/indexes";
    public static String DIR_VERSIONS = DIR_GAME + "/versions";
    public static String DIR_LIBRARIES = DIR_GAME + "/libraries";
    public static String DIR_GAMEDIR_JSON = DIR_MAIN + "/gamedir.json";
    public String DIR_DATA;
    public MioInfo() {}
    public MioInfo(Context context) {
        DIR_DATA = context.getFilesDir().getAbsolutePath().replace("/files","");
    }
    public static void setPath(String s){
        DIR_MAIN = Environment.getExternalStorageDirectory() + "/MioLauncher";
        DIR_GAME = DIR_MAIN + "/.minecraft";
        DIR_TEMP = DIR_MAIN + "/temp";
        DIR_ASSETS = DIR_GAME + "/assets";
        DIR_OBJECTS = DIR_ASSETS + "/objects";
        DIR_INDEXES = DIR_ASSETS+ "/indexes";
        DIR_VERSIONS = DIR_GAME + "/versions";
        DIR_LIBRARIES = DIR_GAME + "/libraries";
    }
}
