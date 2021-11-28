package com.guigu.apitest.other;


import com.guigu.apitest.source.beans.Sensor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ljy
 * @date 2021-9-30 16:37
 * @description
 */
public class Test {
    public static void main(String[] args) {
        //RecDir(args[0]);
        //testTrim();

        /*Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = dateFormat.format(date);
        System.out.println(format);*/

        String a ="0036000099";
        System.out.println(a.substring(0,4));
    }

    public static void testTrim(){
        String str = "abc ";
        System.out.println(str.length());
        str = str.trim();
        System.out.println(str+str.length());
    }

    /**
     * @param fileDir
     * @deprecated  递归查询目录下所有文件
     */
    public static void RecDir(String fileDir){
        /**
         * 1.查询文件夹下的所有文件
         * 2.判断是否有文件
         *  2.1无文件夹，返回null
         *  2.2有文件夹，判断文件夹是否是目录
         *      2.2.1是目录：将路径添加到路径List
         *      2.2.1不是目录：添加文件的目录
         */
        List<File> fileList = new ArrayList<File>();
        File file = new File(fileDir);
        File[] files = file.listFiles();
        if(null==files) return ;
        // 遍历，目录下的所有文件
        for (File f : files) {
            if (f.isFile()) {
                fileList.add(f);
            } else if (f.isDirectory()) {
                System.out.println(f.getAbsolutePath());
                RecDir(f.getAbsolutePath());
            }
        }
        for (File f1 : fileList) {
            System.out.println(f1.getPath());
        }
    }
}
