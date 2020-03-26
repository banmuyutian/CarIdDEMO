package guo.cars.dao;

import guo.cars.domain.cars;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface carsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(cars record);

    int insertSelective(cars record);

    cars selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(cars record);

    int updateByPrimaryKey(cars record);

    List<cars> selectAll();

    @Select("select count(img) from cars_info where img = #{img}")
    int countByImg(@Param("img") String img);

    int updateByImg(@Param("number") String number,@Param("img") String img);
}