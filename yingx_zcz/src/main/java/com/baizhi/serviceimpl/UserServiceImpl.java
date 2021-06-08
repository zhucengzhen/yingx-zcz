package com.baizhi.serviceimpl;

import com.baizhi.dao.UserMapper;
import com.baizhi.entity.User;
import com.baizhi.entity.UserExample;
import com.baizhi.service.UserService;
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
public class UserServiceImpl implements UserService {
    @Resource
    HttpServletRequest request;
    @Resource
    private UserMapper userMapper;


    @Override
    public HashMap<String, Object> queryAllPage(Integer page, Integer rows) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("page",page);

        //创建查询条件对象
        UserExample example = new UserExample();

        //查询数据总条数
        int records = userMapper.selectCountByExample(example);
        map.put("records",records);

        //总页数=总条数/每页展示条数  除不尽+1
        int total = records%rows==0?records/rows:records/rows+1;
        map.put("total",total);

        //分页查询数据  参数:条件,分页对象
        List<User> users = userMapper.selectByExampleAndRowBounds(example, new RowBounds((page - 1) * rows, rows));
        map.put("rows",users);

        return map;
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public HashMap<String, Object> add(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setRegistTime(new Date());
        user.setStatus("1");
        user.setWechat(user.getPhone());
        userMapper.insert(user);
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",user.getId());
        return map;
    }

    @Override
    public HashMap<String, Object> delete(User user) {
        HashMap<String,Object> map=new HashMap<>();
        try {
            //根据id查询用户信息
             User users = userMapper.selectOne(user);
             //根据id删除数据
            userMapper.deleteByPrimaryKey(user.getId());
            //获取文件绝对路径  根据相对路径获取绝对路径
            String realPath = request.getSession().getServletContext().getRealPath("/upload/headImg");
            //获取文件名
             String headImg = users.getHeadImg();
             //删除文件
            File file = new File(realPath, headImg);
            //判断file存在并且是一个文件
            if(file.exists()&&file.isFile()){
                 boolean delete = file.delete();
                 if(delete)map.put("message","删除成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("message","文件删除失败");

        }
        return map;
    }

    @Override
    public HashMap<String, Object> fileUpload(MultipartFile headImg, String userId) {
        System.out.println(userId);
        HashMap<String,Object>map =new HashMap<>();
        //获取文件名
        try{
            String filename = headImg.getOriginalFilename();
            //文件名拼接时间戳
            String newName=new Date().getTime()+"-"+filename;

            String realPath = request.getSession().getServletContext().getRealPath("/upload/headImg");
            //判断文件夹是否存在，不存在创建文件夹
             File file = new File(realPath);
             if(!file.exists()){
                 file.mkdirs();
             }
             //文件上传
            headImg.transferTo(new File(realPath,newName));
             map.put("message","文件上传成功");
            User user = new User();
            user.setId(userId);
            user.setHeadImg(newName);
            //4.修改文件信息
            userMapper.updateByPrimaryKeySelective(user);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("message","文件上传失败!!!");
        }
        return map;
    }


}
