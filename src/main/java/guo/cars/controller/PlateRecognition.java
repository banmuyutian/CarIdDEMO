package guo.cars.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Vector;

import guo.cars.controller.core.CharsRecognise;
import guo.cars.controller.core.PlateDetect;
import guo.cars.dao.TestResultMapper;
import guo.cars.dao.carsMapper;
import guo.cars.domain.TestResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 车牌识别
 */
@RestController
@Api("main")
@Log
public class PlateRecognition {
    @Autowired
    private carsMapper carsMapper;
    @Autowired
    private PlateDetect plateDetect;
    @Autowired
    private CharsRecognise cr;
    @Autowired
    private TestResultMapper testResultMapper;


    //plateDetect.setPDLifemode(true);


    /**
     * 单个车牌识别
     *
     * @param mat
     * @return
     */
    public String plateRecognise(Mat mat) {
        Vector<Mat> matVector = new Vector<Mat>(1);
        if (0 == plateDetect.plateDetect(mat, matVector)) {
            if (matVector.size() > 0) {
                return cr.charsRecognise(matVector.get(0));
            }
        }
        return null;
    }

    /**
     * 多车牌识别
     *
     * @param mat
     * @return
     */
    public String[] mutiPlateRecognise(Mat mat) {
        PlateDetect plateDetect = new PlateDetect();
        plateDetect.setPDLifemode(true);
        Vector<Mat> matVector = new Vector<Mat>(10);
        if (0 == plateDetect.plateDetect(mat, matVector)) {
            CharsRecognise cr = new CharsRecognise();
            String[] results = new String[matVector.size()];
            for (int i = 0; i < matVector.size(); ++i) {
                String result = cr.charsRecognise(matVector.get(i));
                results[i] = result;
            }
            return results;
        }
        return null;
    }

    /**
     * 单个车牌识别
     *
     * @return
     */
    public String plateRecognise(String imgPath) {
        Mat src = opencv_imgcodecs.imread(imgPath);
        return plateRecognise(src);
    }

    /**
     * 多车牌识别
     *
     * @return
     */
    public String[] mutiPlateRecognise(String imgPath) {
        Mat src = opencv_imgcodecs.imread(imgPath);
        return mutiPlateRecognise(src);
    }

    @ApiOperation(value = "main")
    @GetMapping("/mainRun")
    public void mainRun(String srcPath) {
        String ret;
        String imgPath = "src/main/webapp/" + srcPath;
        Mat src = opencv_imgcodecs.imread(imgPath);
        ret = plateRecognise(src);
        if ("".equals(ret) || ret == null ){
            ret = "识别失败";
        }
        carsMapper.updateByImg(ret,srcPath);
        log.info("识别完成！本次识别结果为：" + ret);
    }

    /*
    测试时运行
     */
    @ApiOperation(value = "test")
    @GetMapping("/testRun")
    public void testRun(TestResult testResult) {
        int sum = 100;
        int errNum = 0;
        int sumTime = 0;
        long longTime = 0;
        String ret;
        String srcPath = testResult.getImg();
        for (int i = sum; i > 0; i--) {
            String imgPath = "src/main/webapp/testImg/" + srcPath;
            Mat src = opencv_imgcodecs.imread(imgPath);
            long now = System.currentTimeMillis();
            ret = plateRecognise(src);
            //System.err.println(ret);
            long s = System.currentTimeMillis() - now;
            if (s > longTime) {
                longTime = s;
            }
            sumTime += s;
            String targetName = testResult.getNumber();
            if (!targetName.equalsIgnoreCase(ret)) {
                errNum++;
            }
        }
        System.err.println("总数量：" + sum);
        System.err.println("单次最长耗时：" + longTime + "ms");
        BigDecimal errSum = new BigDecimal(errNum);
        BigDecimal sumNum = new BigDecimal(sum);
        BigDecimal c = sumNum.subtract(errSum).divide(sumNum).multiply(new BigDecimal(100));
        System.err.println("总耗时：" + sumTime + "ms,平均处理时长：" + sumTime / sum + "ms,错误数量：" + errNum + "，正确识别率：" + c + "%");
        testResult.setSum(sum);
        testResult.setLongestTime(longTime);
        testResult.setSumTime(Integer.toUnsignedLong(sumTime));
        testResult.setAverageTime(Integer.toUnsignedLong(sumTime / sum));
        testResult.setCorrectRate(c + "%");
        testResult.setImg("testImg/" + srcPath);
        testResultMapper.updateByPrimaryKeySelective(testResult);
    }
}
