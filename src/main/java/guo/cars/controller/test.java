package guo.cars.controller;

import guo.cars.dao.ShowProcessMapper;
import guo.cars.dao.TestResultMapper;
import guo.cars.domain.ShowProcess;
import guo.cars.domain.TestResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.stream.ImageInputStream;
import javax.validation.constraints.Null;
import java.io.*;
import java.util.List;

/**
 * @description:
 * @author: guoyiming
 **/
@RestController
@Api("test")
public class test {

    @Autowired
    private TestResultMapper testResultMapper;
    @Autowired
    private PlateRecognition plateRecognition;
    @GetMapping("/sqlTest")
    @ApiOperation("/sqlTest")
    public List<TestResult> sqlTest(){
        //showMapper.cleanTable();
        //showProcessMapper.truncate();
        return testResultMapper.selectAll();
    }

    @GetMapping("/startTest")
    @ApiOperation("/startTest")
    public void startTest(){
        List<TestResult> testResultList = testResultMapper.selectAll();
        for (TestResult testResult:testResultList){
            plateRecognition.testRun(testResult);
        }

    }

    @GetMapping("/prepareTest")
    @ApiOperation("/prepareTest")
    public void prepareTest(){
        testResultMapper.truncate();
        File file = new File("src/main/webapp/srcTestImg");
        String[] fileNameList = file.list();
        int i = 0;
        for (String srcFileName:fileNameList){
            try(FileInputStream in = new FileInputStream(new File("src/main/webapp/srcTestImg/" + srcFileName));
                FileOutputStream out = new FileOutputStream(new File("src/main/webapp/testImg/" + i + ".jpg"))){

                TestResult testResult = new TestResult();
                int n = 0;// 每次读取的字节长度
                byte[] bb = new byte[1024];// 存储每次读取的内容
                while ((n = in.read(bb)) != -1) {
                    out.write(bb, 0, n);// 将读取的内容，写入到输出流当中
                }
                String number = srcFileName.split("\\.")[0];
                testResult.setImg(i + ".jpg");
                testResult.setNumber(number);
                testResultMapper.insertSelective(testResult);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        }

    }
}
