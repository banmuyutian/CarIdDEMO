package guo.cars.controller;

import guo.cars.common.AppException;
import guo.cars.dao.ShowProcessMapper;
import guo.cars.dao.TestResultMapper;
import guo.cars.dao.carsMapper;
import guo.cars.domain.ShowProcess;
import guo.cars.domain.TestResult;
import guo.cars.domain.cars;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author: guoyiming
 **/
@RestController
public class FunctionControler {
    @Autowired
    private carsMapper carsMapper;
    @Autowired
    private ShowProcessMapper showProcessMapper;
    @Autowired
    private PlateRecognition plateRecognition;
    @Autowired
    private TestResultMapper testResultMapper;

    @GetMapping("/getCarsDate")
    public Map getCarsDate(@RequestParam("page") int page,@RequestParam("rows") int rows){

        Map resultmap = new HashMap();
        List<cars> carsList = carsMapper.selectAll();
        resultmap.put("total",carsList.size());
        resultmap.put("rows",carsList);
        return resultmap;
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value="/onlineupload")
    public Map<String,String> onlineupload(@RequestParam("onlinefilename") MultipartFile file,
                                           HttpServletRequest request) {
        Map<String,String> map=new HashMap<>();
        String fileName = file.getOriginalFilename();
        System.out.println("fileName:"+fileName);
        String filePath="src\\main\\webapp\\img\\";
        System.out.println("filePath: "+filePath);

        try {
            uploadFile.uploadFile(file.getBytes(), filePath, fileName);
            cars newCar = new cars();
            newCar.setBehaviour("进入");
            newCar.setImg("img/" + fileName );
            newCar.setInTime(new Date());
            if(carsMapper.countByImg(newCar.getImg()) != 0){
                throw new AppException("请勿重复上传图片！");
            }
            carsMapper.insertSelective(newCar);
            showProcessMapper.truncate();
            plateRecognition.mainRun(newCar.getImg());
            File src = new File("tmp");
            File dst = new File("src/main/webapp/tmp");
            org.apache.commons.io.FileUtils.copyDirectory(src,dst);
        } catch (AppException | IOException e) {
            map.put("error","false");
            return map;
        }

        if(file==null || file.isEmpty()){
            map.put("error","false");
        }else {
            map.put("success","true");
            System.out.println("file + success!");
        }
        return map;
    }

    /**
     * 记录步骤
     */
    @RequestMapping("/recordProcess")
    public void recordProcess(String descrption,String img){
        ShowProcess showProcess = new ShowProcess(descrption,img);
        showProcessMapper.insert(showProcess);
    }

    /**
     * 获取步骤
     */
    @GetMapping("/getProcess")
    public Map getProcess(){
        Map resultmap = new HashMap();
        List<ShowProcess> processes = showProcessMapper.selectAll();
        resultmap.put("total",1);
        resultmap.put("rows",processes);
        return resultmap;
    }

    /**
     * 获取测试结果
     */
    @GetMapping("/getTestResult")
    public Map getTestResult(){
        Map resultmap = new HashMap();
        List<TestResult> testResultList = testResultMapper.selectAll();
        resultmap.put("total",1);
        resultmap.put("rows",testResultList);
        return resultmap;
    }
}
