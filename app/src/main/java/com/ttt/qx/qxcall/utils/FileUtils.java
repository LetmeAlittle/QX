package com.ttt.qx.qxcall.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.media.ExifInterface;
import android.os.Environment;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wyd on 2016/3/17.
 */
public class FileUtils {
    /**
     * 将assets中的文件拷贝到sdcard中，
     *
     * @param ctx                  上下文
     * @param sourceFileName       源文件名称 包含后缀名
     * @param purposeFileName      目标文件名称 包含后缀名
     * @param purposeFileDirectory 目标文件所在路径 包含文件分隔符
     * @throws IOException 异常类型
     */
    public static void copyFromAssetsSourceDirToTargetDir(Context ctx, String sourceFileName, String purposeFileName, String purposeFileDirectory) throws IOException {
        {
            InputStream myInput;
            File fileDir = new File(purposeFileDirectory);
            File fileName = null;
            if (!fileDir.exists()) {//如果当前文件夹不存在则创建
                fileDir.mkdirs();
            }
            fileName = new File(fileDir, purposeFileName);
            OutputStream myOutput = new FileOutputStream(fileName);//拷贝到指定位置
            myInput = ctx.getAssets().open(sourceFileName);
            byte[] buffer = new byte[1024];
            int length = myInput.read(buffer);
            while (length > 0) {
                myOutput.write(buffer, 0, length);
                length = myInput.read(buffer);
            }
            myOutput.flush();
            myInput.close();
            myOutput.close();
        }
    }

    /**
     * 获取文件对象集合的每个文件对象名称
     *
     * @param files  指定文件数组
     * @param suffix 指定文件后缀名
     * @return 返回规范文件买名称
     */
    public static ArrayList<String> getFileName(File[] files, String suffix) {
        ArrayList<String> nameList = new ArrayList<>();
        if (files != null) {// 先判断目录是否为空，否则会报空指针
            for (File file : files) {
                if (file.isDirectory()) {//如果是目录不做处理
                } else {//如果是文件进行处理
                    String fileName = file.getName();
                    if (fileName.endsWith(suffix)) {
                        nameList.add(fileName.substring(0, fileName.lastIndexOf(".")));
                    }
                }
            }
        }
        return nameList;
    }

    /**
     * 获取文件对象的每个文件对象名称
     *
     * @param file   指定文件
     * @param suffix 指定文件后缀名
     * @return 返回规范文件名称
     */
    public static String getFileName(File file, String suffix) {
        String name = "";
        if (file != null) {// 先判断目录是否为空，否则会报空指针
            if (file.isDirectory()) {//如果是目录不做处理
            } else {//如果是文件进行处理
                String fileName = file.getName();
                if (fileName.endsWith(suffix)) {
                    name = fileName.substring(0, fileName.lastIndexOf("."));
                }
            }

        }
        return name;
    }

    /**
     * 将将目标文件拷贝到sdcard中，
     *
     * @param ctx                  上下文
     * @param sourceFileDicAndName 要拷贝的文件名称全路径包含文件名
     * @param purposeFileName      目标文件名称
     * @param purposeFileDirectory 目标文件所在路径不包含文件名
     * @throws IOException 异常类型
     */
    public static void copyFromSourceDirToTargetDir(Context ctx, String sourceFileDicAndName, String purposeFileName, String purposeFileDirectory) throws IOException {
        {
            InputStream myInput = null;
            OutputStream myOutput = null;
            try {
                File fileDir = new File(purposeFileDirectory);
                File fileName = null;
                if (!fileDir.exists()) {//如果当前文件夹不存在则创建文件夹以及相应的子文件夹
                    fileDir.mkdirs();
                }
                fileName = new File(fileDir, purposeFileName);
                myOutput = new FileOutputStream(fileName);//拷贝到指定位置
                File sourceFile = new File(sourceFileDicAndName);
                if (sourceFile.exists()) {
                    myInput = new FileInputStream(sourceFile);
                } else {
                    return;
                }
                byte[] buffer = new byte[1024];
                int length = myInput.read(buffer);
                while (length > 0) {
                    myOutput.write(buffer, 0, length);
                    length = myInput.read(buffer);
                }
                myOutput.flush();
            } catch (Exception e) {

            } finally {
                if (myInput != null) {
                    myInput.close();
                }
                if (myOutput != null) {
                    myOutput.close();
                }
            }
        }
    }
    /**
     * 文件拷贝
     *
     * @param fromFile
     * @param toFile
     * @return
     */
    public static int CopySdcardFile(String fromFile, String toFile) {

        try {
            InputStream fisFrom = new FileInputStream(fromFile);
            OutputStream fosTo = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fisFrom.read(bt)) > 0) {
                fosTo.write(bt, 0, c);
            }
            fisFrom.close();
            fosTo.close();
            return 0;

        } catch (Exception ex) {
            return -1;
        }
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        ;
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 纠正图片
     * @param picFileNamePath
     */
    public static void correctPic(String picFileNamePath) {
        //判断图片是否进行了旋转，是，纠正，否则不做处理。
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = 1;
        /**
         * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转
         */
        int degree = readPictureDegree(picFileNamePath);
        Bitmap bitmap = BitmapFactory.decodeFile(picFileNamePath, bitmapOptions);
        /**
         * 把图片旋转为正的方向
         */
        bitmap = rotingImageView(degree, bitmap);
        //同时将纠正过的图片保存到源文件中
        saveBitmap(bitmap, picFileNamePath);
    }

    /**
     * 根据指定的文件前缀以及文件所在路径，计算文件名称后索引
     * 0/.../../.../房屋照片1.jpg,0/.../../.../房屋照片2.jpg
     *
     * @param imgBasePath 文件所在路径
     * @param reg         文件前缀（如：房屋照片）
     * @return
     */
    public static int getFileNameIndex(String imgBasePath, String reg) {
        int i = 0;
        File fileDic = new File(imgBasePath);
        String[] fileNames = fileDic.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.contains(reg);
            }
        });
        if (fileNames != null && fileNames.length > 0) {
            Arrays.sort(fileNames);//房屋照片1.jpg
            String fileName = fileNames[fileNames.length - 1];
            //获取数字字符串
            String index = fileName.substring(reg.length(), fileName.lastIndexOf("."));
            i = Integer.parseInt(index) + 1;
        } else {
            i++;
        }
        return i;
    }

    /**
     * 文件拷贝
     *
     * @param fisFrom
     * @param toFile
     * @return
     */
    public static int CopySdcardFile(InputStream fisFrom, File toFile) {

        try {
            OutputStream fosTo = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fisFrom.read(bt)) > 0) {
                fosTo.write(bt, 0, c);
            }
            fisFrom.close();
            fosTo.close();
            return 0;

        } catch (Exception ex) {
            return -1;
        }
    }

    /**
     * 将将目标文件拷贝到sdcard中，
     *
     * @param ctx                  上下文
     * @param sourceFileDicAndName 要拷贝的文件名称全路径包含文件名
     * @param purposeFileName      目标文件名称
     * @param purposeFileDirectory 目标文件所在路径不包含文件名
     * @throws IOException 异常类型
     */
    public static void copyFromAssetsToTargetDir(Context ctx, String sourceFileDicAndName, String purposeFileName, String purposeFileDirectory) throws IOException {
        {
            InputStream myInput = null;
            OutputStream myOutput = null;
            try {
                File fileDir = new File(purposeFileDirectory);
                File fileName = null;
                if (!fileDir.exists()) {//如果当前文件夹不存在则创建文件夹以及相应的子文件夹
                    fileDir.mkdirs();
                }
                fileName = new File(fileDir, purposeFileName);
                myOutput = new FileOutputStream(fileName);//拷贝到指定位置
                myInput = ctx.getResources().getAssets().open(sourceFileDicAndName);
                byte[] buffer = new byte[1024];
                int length = myInput.read(buffer);
                while (length > 0) {
                    myOutput.write(buffer, 0, length);
                    length = myInput.read(buffer);
                }
                myOutput.flush();
            } catch (Exception e) {

            } finally {
                if (myInput != null) {
                    myInput.close();
                }
                if (myOutput != null) {
                    myOutput.close();
                }
            }
        }
    }

    /**
     * 将将目标文件拷贝到sdcard中，
     *
     * @param inputStream          要拷贝的文件名称全路径包含文件名
     * @param purposeFileName      目标文件名称
     * @param purposeFileDirectory 目标文件所在路径不包含文件名
     * @throws IOException 异常类型
     */
    public static void copyFromSourceDirToTargetDir(InputStream inputStream, String purposeFileName, String purposeFileDirectory) throws IOException {
        {
            File fileDir = new File(purposeFileDirectory);
            if (!fileDir.exists()) {//如果当前文件夹不存在则创建文件夹以及相应的子文件夹
                fileDir.mkdirs();
            }
            //创建文件之前进行删除
            File deleteName = new File(purposeFileDirectory + purposeFileName);
            if (deleteName.exists()) {
                deleteName.delete();//删除当前工程目录下的指定文件
            }
            OutputStream myOutput = new FileOutputStream(deleteName);//拷贝到指定位置
            byte[] buffer = new byte[1024];
            if (inputStream != null) {
                int length = inputStream.read(buffer);
                while (length > 0) {
                    myOutput.write(buffer, 0, length);
                    length = inputStream.read(buffer);
                }
                myOutput.flush();
                inputStream.close();
            }
            myOutput.close();
        }
    }

    public static boolean createBasicDirectory(String basicDirectory) {
        boolean flag = false;
        File fileDir = new File(basicDirectory);
        if (!fileDir.exists()) {//如果当前文件夹不存在则创建
            flag = fileDir.mkdir();
        }
        return flag;
    }

    /**
     * 将assets中所有文件写入sdcard中
     *
     * @param ctx      上下文
     * @param assetDir assets文件夹
     * @param dir      目标路径
     */
    private void CopyAssets(Context ctx, String assetDir, String dir) {
        String[] files;
        try {
            // 获得Assets一共有几个文件
            files = ctx.getResources().getAssets().list(assetDir);
        } catch (IOException e1) {
            return;
        }
        File mWorkingPath = new File(dir);
        // 如果文件路径不存在
        if (!mWorkingPath.exists()) {
            // 创建文件夹
            if (!mWorkingPath.mkdirs()) {
                // 文件夹创建不成功时调用
            }
        }
        for (int i = 0; i < files.length; i++) {
            try {
                // 获得每个文件的名字
                String fileName = files[i];
                // 根据路径判断是文件夹还是文件
                if (!fileName.contains(".")) {
                    if (0 == assetDir.length()) {
                        CopyAssets(ctx, fileName, dir + fileName + "/");
                    } else {
                        CopyAssets(ctx, assetDir + "/" + fileName, dir + "/"
                                + fileName + "/");
                    }
                    continue;
                }
                File outFile = new File(mWorkingPath, fileName);
                if (outFile.exists())
                    outFile.delete();
                InputStream in = null;
                if (0 != assetDir.length())
                    in = ctx.getAssets().open(assetDir + "/" + fileName);
                else
                    in = ctx.getAssets().open(fileName);
                OutputStream out = new FileOutputStream(outFile);
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取指定文件夹下的所有文件对象
     *
     * @param directory 文件夹全路径
     * @return
     */
    public static File[] getFiles(String directory) {
        File[] files = null;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File path = new File(directory);// 获得要查询的SD卡路径
            files = path.listFiles();// 读取
        }
        return files;
    }

    /**
     * 删除指定文件
     *
     * @param file 要删除的文件对象
     */

    public static boolean deleteSDFile(File file) {
        if ((file == null) || !file.exists() || file.isDirectory()) {
            return false;
        }
        return file.delete();
    }

    /**
     * 删除仅包含文件的文件夹
     *
     * @param fileName 文件夹全路径
     */
    public static void deleteDirectory(String fileName) {
        File fileNameDic = new File(fileName);
        //删除文件
        File[] files = getFiles(fileName);//注意获取的全部是文件而不是文件夹
        if (files != null && files.length > 0) {
            for (File file : files) {//逐个删除
                if (file != null && file.exists()) {
                    deleteSDFile(file);
                }
            }
        }
        //删除文件夹
        if (fileNameDic != null && fileNameDic.exists()) {
            fileNameDic.delete();
        }
        fileNameDic.delete();
    }

    /**
     * 删除文件夹
     *
     * @param folderPath 文件路径
     */
    public static void delFolder(String folderPath) {

        try {

            delAllFile(folderPath); //删除完里面所有内容

            String filePath = folderPath;

            filePath = filePath.toString();

            File myFilePath = new File(filePath);

            myFilePath.delete(); //删除空文件夹

        } catch (Exception e) {

            System.out.println("删除文件夹操作出错");

            e.printStackTrace();

        }

    }

    /**
     * 根据shp文件全路径拷贝相应的文件到指定位置
     *
     * @param destFileDic 目标文件路径以及文件名不包含后缀名，如sd/dwer/fileName(.后缀名，不包含)
     * @param layerPath   原shp文件全路径名，包含后缀名.shp
     */
    public static void shpFileCopy(String layerPath, String destFileDic) {
        destFileDic.replace(".shp", "");
        String type = layerPath.substring(layerPath.lastIndexOf("."), layerPath.length());
        if (".shp".equals(type)) {//如果是shp文件，将于该shp文件相关的文件一并拷贝到指定文件夹
            copyFile(layerPath, destFileDic + ".shp");
            copyFile(layerPath.replace(".shp", ".shx"), destFileDic + ".shx");
            copyFile(layerPath.replace(".shp", ".dbf"), destFileDic + ".dbf");
            copyFile(layerPath.replace(".shp", ".prj"), destFileDic + ".prj");
            copyFile(layerPath.replace(".shp", ".cpg"), destFileDic + ".cpg");
        }
    }

    /**
     * 从assets资源模板文件中拷贝到指定文件路径下，
     * 生成完整shp文件以及所需文件
     *
     * @param ctx              上下文
     * @param baseFile         目标文件所在根目录
     * @param dataFileDirName  目标文件名不包含后缀名
     * @param sourceFileName   资源模板文件名不包含后缀名
     * @param currentSprString 当前空间参考对象唯一字符串
     * @throws IOException
     */
    public static void copyFile(Context ctx, String baseFile, String dataFileDirName, String sourceFileName, String currentSprString) {
        try {
            copyFromSourceDirToTargetDir(ctx.getAssets().open(sourceFileName + ".shp"), dataFileDirName + ".shp", baseFile);
            copyFromSourceDirToTargetDir(ctx.getAssets().open(sourceFileName + ".shx"), dataFileDirName + ".shx", baseFile);
            copyFromSourceDirToTargetDir(ctx.getAssets().open(sourceFileName + ".dbf"), dataFileDirName + ".dbf", baseFile);
            copyFromSourceDirToTargetDir(StringToStream.getStringStream(currentSprString), dataFileDirName + ".prj", baseFile);
            copyFromSourceDirToTargetDir(ctx.getAssets().open(sourceFileName + ".cpg"), dataFileDirName + ".cpg", baseFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拷贝文件
     *
     * @param sourceFile
     * @param destFile
     */
    private static void copyFile(String sourceFile, String destFile) {
        File file = new File(destFile);
        if (file.exists() && file.isFile()) {//如果目标文件已经
            file.delete();
        }
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            //首先创建一个输入流，去拷贝的文件中去读取内容
            fileInputStream = new FileInputStream(sourceFile);//原路径
            //创建输出流，将读出的数据写入到文件中
            fileOutputStream = new FileOutputStream(destFile);//目标路径
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fileInputStream.read(bytes)) > 0) {
                fileOutputStream.write(bytes, 0, len);
                fileOutputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除指定文件夹下的所有文件
     *
     * @param path
     */
    public static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
            }
        }
    }

    /**
     * 根据文件输入流，获取文件内容字符串
     *
     * @param inputStream
     */
    public static String getFileContent(InputStream inputStream) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        byte[] bytes = new byte[1024];
        int len;
        while ((len = inputStream.read(bytes)) != -1) {
            stringBuffer.append(new String(bytes, 0, len, "utf-8"));
        }
        return stringBuffer.toString();
    }

    /**
     * 保存bitmap到本地
     *
     * @param bmp
     * @param path
     */
    public static void saveBitmap(Bitmap bmp, String path) {
        File file = new File(path);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * InputStrem 转byte[]
     *
     * @param in
     * @return
     * @throws Exception
     */
    public static byte[] readStreamToBytes(InputStream in) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 8];
        int length = -1;
        while ((length = in.read(buffer)) != -1) {
            out.write(buffer, 0, length);
        }
        out.flush();
        byte[] result = out.toByteArray();
        in.close();
        out.close();
        return result;
    }

    /**
     * 写入文件
     *
     * @param in
     * @param file
     */
    public static void writeFile(InputStream in, File file) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (file != null && file.exists())
            file.delete();

        FileOutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[1024 * 128];
        int len = -1;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.flush();
        out.close();
        in.close();

    }

    /**
     * 得到Bitmap的byte
     *
     * @return
     * @author YOLANDA
     */
    public static byte[] bmpToByteArray(Bitmap bmp) {
        if (bmp == null)
            return null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 80, output);

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    /*
    * 根据view来生成bitmap图片，可用于截图功能
    */
    public static Bitmap getViewBitmap(View v) {

        v.clearFocus(); //

        v.setPressed(false); //
        // 能画缓存就返回false

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }

        v.buildDrawingCache();

        Bitmap cacheBitmap = v.getDrawingCache();

        if (cacheBitmap == null) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        // Restore the view

        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;

    }
}
