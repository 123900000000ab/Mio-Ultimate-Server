package com.mio.appStaticVariable;

import android.content.Context;
import android.os.Environment;

public class FolderPath {
    public static String defaultMioLauncherDir_Public = Environment.getExternalStorageDirectory() + "/MioLauncher";
    //默认游戏资源文件存放路径(公有目录)
    public static String defaultGameDir_Public = defaultMioLauncherDir_Public + "/.minecraft";
    //游戏资源文件存放路径(私有目录)
    public static String gameDir_Private;
    //运行库解压后所在目录
    public static String runtimeDir_Internal;
    public FolderPath() {}
    public FolderPath(Context context){
        gameDir_Private = context.getExternalFilesDir(".minecraft").getAbsolutePath();
        runtimeDir_Internal = context.getDir("runtime",Context.MODE_PRIVATE).getAbsolutePath();
    }
}