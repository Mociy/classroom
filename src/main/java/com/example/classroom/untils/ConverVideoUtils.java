package com.example.classroom.untils;


import java.io.*;

import java.util.ArrayList;

import java.util.List;



/**

 *

 * @Title: ConverVideoUtils.java

 * @Package:com.resource.mytools

 * @Description:(2.转码和截图功能)

 * @see:接收Contants实体的路径

 * @author:Zoutao

 * @date:2018-7-15

 * @version:V1.0

 */



public class ConverVideoUtils {


    private String videofolder = Contants.videofolder; 		// 别的格式视频的目录

    private static String targetfolder = Contants.targetfolder; 	// flv视频的目录

    private static String ffmpegpath = Contants.ffmpegpath;		 // ffmpeg.exe的目录





    /**

     * 检查文件类型

     * @return

     */

    public static int checkContentType(String type) {





        // 如果是ffmpeg能解析的格式:(asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等)

        if (type.equals("avi")) {

            return 0;

        } else if (type.equals("mpg")) {

            return 0;

        } else if (type.equals("wmv")) {

            return 0;

        } else if (type.equals("3gp")) {

            return 0;

        } else if (type.equals("mov")) {

            return 0;

        } else if (type.equals("mp4")) {

            return 0;

        } else if (type.equals("asf")) {

            return 0;

        } else if (type.equals("asx")) {

            return 0;

        } else if (type.equals("flv")) {

            return 0;

        }else if (type.equals("mkv")) {

            return 0;

        }



        System.out.println("上传视频格式异常");

        return 9;

    }





    /**

     * 转换为指定格式--zoutao

     * ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）


     * @param targetExtension 目标格式后缀名 .xxx


     * @return

     */

    public static boolean processVideoFormat(String video_id, String targetExtension,String type) {//压缩视频

        String oldPath="/usr/videos/"+video_id+"."+type;

        System.out.println("调用了ffmpeg.exe工具");
        //先确保保存转码后的视频的文件夹存在
        File TempFile = new File(targetfolder);
        if (TempFile.exists()) {
            if (TempFile.isDirectory()) {
                System.out.println("该文件夹存在。");
            }else {
                System.out.println("同名的文件存在，不能创建文件夹。");
            }
        }else {
            System.out.println("文件夹不存在，创建该文件夹。");
            TempFile.mkdir();
        }
        List<String> commend = new ArrayList<String>();
        commend.add(ffmpegpath);		 //ffmpeg.exe工具地址
        commend.add("-i");
        commend.add(oldPath);			//源视频路径
        commend.add("-vf");             //视频分辨率处理
        commend.add("scale=800:450");   //16:9
        commend.add("-vcodec");
        commend.add("h263");  //
        commend.add("-ab");		//新增4条
        commend.add("128");      //高品质:128 低品质:64
        commend.add("-acodec");
        commend.add("copy");      //音频编码器：原libmp3lame
        commend.add("-ac");
        commend.add("2");       //原1
        commend.add("-ar");
        commend.add("22050");   //音频采样率22.05kHz
        commend.add("-r");
        commend.add("29.97");  //高品质:29.97 低品质:15
        commend.add("-c:v");
        commend.add("libx264");	//视频编码器：视频是h.264编码格式
        commend.add("-strict");
        commend.add("-2");
        commend.add(targetfolder + video_id + targetExtension);  // //转码后的路径+名称，是指定后缀的视频
        //打印命令--zoutao
        StringBuffer test = new StringBuffer();
        for (int i = 0; i < commend.size(); i++) {
            test.append(commend.get(i) + " ");
        }
        System.out.println("ffmpeg输入的命令:"+test);
        try {
            //多线程处理加快速度-解决rmvb数据丢失builder名称要相同
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            Process p = builder.start();   //多线程处理加快速度-解决数据丢失
            final InputStream is1 = p.getInputStream();
            final InputStream is2 = p.getErrorStream();
            new Thread() {

                public void run() {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(is1));
                    try {
                        String lineB = null;
                        while ((lineB = br.readLine()) != null) {
                            if (lineB != null)
                                System.out.println(lineB);    //打印mencoder转换过程代码
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }.start();
            new Thread() {

                public void run() {
                    BufferedReader br2 = new BufferedReader(
                            new InputStreamReader(is2));
                    try {
                        String lineC = null;
                        while ((lineC = br2.readLine()) != null) {
                            if (lineC != null)
                                System.out.println(lineC);    //打印mencoder转换过程代码
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }.start();
            p.waitFor();		//进程等待机制，必须要有，否则不生成mp4！！！

            System.out.println("生成mp4视频为:"+targetfolder+ video_id + ".mp4");

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}


