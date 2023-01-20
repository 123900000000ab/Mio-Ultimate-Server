package com.mio.appStaticVariable;

import android.os.Environment;

public class FolderPath {
    public static String LAUNCHER_DIR = Environment.getExternalStorageDirectory() + "/MioLauncher";
    public static String DEFAULT_GAME_DIR = LAUNCHER_DIR + "/.minecraft";
}
