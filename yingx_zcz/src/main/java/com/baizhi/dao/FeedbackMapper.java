package com.baizhi.dao;

import com.baizhi.entity.Feedback;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface FeedbackMapper  extends Mapper<Feedback> {
    List<Feedback> queryByPagess(@Param("page") Integer page, @Param("rows") Integer rows);
    void selectCount();
}