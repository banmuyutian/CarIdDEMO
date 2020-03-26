package guo.cars.dao;

import guo.cars.domain.ShowProcess;
import org.apache.catalina.LifecycleState;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ShowProcessMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShowProcess record);

    int insertSelective(ShowProcess record);

    ShowProcess selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShowProcess record);

    int updateByPrimaryKey(ShowProcess record);

    @Update("truncate table show_process")
    void truncate();

    @Select("select * from show_process")
    List<ShowProcess> selectAll();
}