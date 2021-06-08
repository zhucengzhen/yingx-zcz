package com.baizhi.util;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class VideoInterceptUtil {

    /**
     * 获取指定视频的帧并保存为图片至指定目录
     * @param videofilePath  源视频文件路径
     * @param coverfilePath  截取帧的图片存放路径
     * @throws Exception
     */
    public static void fetchFrame(String videofilePath, String coverfilePath)throws Exception {
        long start = System.currentTimeMillis();
        File targetFile = new File(coverfilePath);
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofilePath);
        ff.start();
        int lenght = ff.getLengthInFrames();
        int i = 0;
        Frame f = null;
        while (i < lenght) {
            // 过滤前5帧，避免出现全黑的图片，依自己情况而定
            f = ff.grabFrame();
            if ((i > 5) && (f.image != null)) {
                break;
            }
            i++;
        }
        opencv_core.IplImage img = f.image;
        int owidth = img.width();
        int oheight = img.height();

        // 对截取的帧进行等比例缩放
        int width = 800;
        int height = (int) (((double) width / owidth) * oheight);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH),0, 0, null);

        //使用图片输出流将图片以jpg的格式输出到targetFile的位置
        ImageIO.write(bi, "jpg", targetFile);
        //ff.flush();
        ff.stop();
        System.out.println(System.currentTimeMillis() - start);
    }

    /*public static void main(String[] args) {
        try {
            //要截取的视频地址,截取之后的封面保存的路径
            //Test.fetchFrame("http://yingx-2010.oss-cn-beijing.aliyuncs.com/video/1622447380253-动画.mp4", "C:\\Users\\Administrator\\Desktop\\video\\动画.jpg");

            //Test.fetchFrame("http://yingx-2010.oss-cn-beijing.aliyuncs.com/video/1622447380253-动画.mp4", "C:\\Users\\Administrator\\Desktop\\video\\人民的名义.jpg");

            //1.根据阿里云截取封面
            //2.保存封面到本地
            //3.将本地保存的封面上传阿里云
            //4.删除本地封面

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
