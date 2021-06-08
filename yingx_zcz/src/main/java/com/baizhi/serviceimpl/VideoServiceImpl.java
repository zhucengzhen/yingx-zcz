package com.baizhi.serviceimpl;

import com.baizhi.dao.VideoMapper;
import com.baizhi.entity.Video;
import com.baizhi.entity.VideoExample;
import com.baizhi.service.VideoService;
import com.baizhi.util.AliyunOSSUtil;
import com.baizhi.util.VideoInterceptUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {
    @Resource
    private VideoMapper videoMapper;
    @Resource
    private HttpServletRequest request;
    @Override
    public HashMap<String, Object> queryAllPage(Integer page, Integer rows) {
        HashMap<String,Object>map=new HashMap<>();
        map.put("page",page);
        //创建查询条件对象
         VideoExample example = new VideoExample();
         //查询数据总条数
         int records = videoMapper.selectCountByExample(example);
         map.put("records",records);
         //总页数
         int total = records / rows == 0 ? records / rows : records / rows + 1;
         map.put("total",total);
         //分页查询
         List<Video> videos = videoMapper.selectByExampleAndRowBounds(example, new RowBounds((page - 1) * rows, rows));
       map.put("rows",videos);
        return map;
    }

    @Override
    public void update(Video video) {
        if(video.getVideoPath()==""){
            video.setVideoPath(null);
        }
        videoMapper.updateByPrimaryKeySelective(video);


    }

    @Override
    public HashMap<String, Object> add(Video video) {
        video.setId(UUID.randomUUID().toString());
        video.setCateId("1");
        video.setGroupId("2");
        video.setUserId("3");
        video.setUploadTime(new Date());
        videoMapper.insert(video);
        HashMap<String,Object>map=new HashMap<>();
        map.put("videoId",video.getId());
        return map;
    }

    @Override
    public HashMap<Object, Object> delete(Video video) {
         HashMap<Object, Object> map = new HashMap<>();
         try{
             //根据id查询用户信息
             Video videos = videoMapper.selectOne(video);
             //根据id删除数据
             videoMapper.deleteByPrimaryKey(video.getId());
             //获取路径
              String realPath = request.getSession().getServletContext().getRealPath("/upload/video");
              //获取文件名
              String videoPath = videos.getVideoPath();
              File file = new File(realPath,videoPath);
              if(file.exists()&&file.isFile()){
                   boolean delete = file.delete();
                   if(delete) map.put("message","文件删除成功");
              }


         }catch (Exception e){
             e.printStackTrace();
             map.put("message","删除文件失败");
         }
        return map;
    }

    @Override
    public HashMap<String, Object> fileUploadAliyuns(MultipartFile videoPath, String videoId) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            //1.获取文件名  动画.mp4
            String filename = videoPath.getOriginalFilename();
            //文件名拼接时间戳  1622446770298-动画.mp4
            String newName=new Date().getTime()+"-"+filename;

            //拼接文件夹   video/1622446770297-动画.mp4
            String objectName="video/"+newName;

            //2.文件上传至阿里云
            /**2.将文件上传至阿里云
             *将文件以转为字节数组上传至阿里云
             *  参数:
             *  bucketName(String):指定存储空间名
             *  fileName(String):文件名
             *  headImg(MultipartFile):MultipartFile类型的文件
             * */
            AliyunOSSUtil.uploadByteFileAliyun("zhuc",objectName,videoPath);

            //根据相对路径获取绝对路径
            String realPath = request.getSession().getServletContext().getRealPath("/upload/video");

            //判断文件夹是否存在不存在创建文件夹
            File file = new File(realPath);
            if(!file.exists()){
                file.mkdirs();
            }

            //截取视频名拼接图片名  0:1622446770298-动画    1:mp4
            String[] split = newName.split("\\.");
            //获取名字
            String names=split[0];
            //拼接封面名
            String coverName=names+".jpg";

            //拼接视频封面的本地路径
            String coverPath=realPath+"/"+coverName;
            System.out.println("封面的路径:"+coverPath);

            //拼接阿里云视频地址
            String videoPaths="http://zhuc.oss-cn-beijing.aliyuncs.com/"+objectName;

            /**3.根据阿里云的视频截取一张封面
             * 获取指定视频的帧并保存为图片至指定目录
             * @param videofilePath  源视频文件路径
             * @param coverfilePath  截取帧的图片存放路径
             * @throws Exception
             */
            VideoInterceptUtil.fetchFrame(videoPaths,coverPath);

            /**4.将封面存储到阿里云   文件上传
             *将本地文件上传至阿里云
             *  参数:
             *  bucketName:指定存储空间名
             *  fileName:文件名
             *  localPath:本地文件路径
             * */
            AliyunOSSUtil.uploadlocalFileAliyun("zhuc","cover/"+coverName,coverPath);

            //5.删除本地封面
            File files = new File(realPath, coverName);
            if(files.exists()&& files.isFile()){
                boolean delete = files.delete(); //删除本地文件
                System.out.println("删除文件:"+delete);
            }


            //5.修改视频封面的路径
            Video video = new Video();
            video.setId(videoId);
            video.setVideoPath(videoPaths);
            video.setCoverPath("http://zhuc.oss-cn-beijing.aliyuncs.com/"+"cover/"+coverName);

            //4.修改文件信息
            videoMapper.updateByPrimaryKeySelective(video);

            map.put("message","文件上传成功!!!");
        } catch (Exception exception) {
            exception.printStackTrace();
            map.put("message","文件上传失败!!!");
        }
        return map;
    }

}
