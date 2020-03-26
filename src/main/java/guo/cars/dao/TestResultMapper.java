package guo.cars.dao;

import guo.cars.domain.TestResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TestResultMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TestResult record);

    int insertSelective(TestResult record);

    TestResult selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TestResult record);

    int updateByPrimaryKey(TestResult record);

    List<TestResult> selectAll();

    @Update("truncate table test_result")
    void truncate();
}