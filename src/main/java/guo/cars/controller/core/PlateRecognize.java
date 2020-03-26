package guo.cars.controller.core;

import org.bytedeco.javacpp.opencv_core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Vector;


/**
 * 车牌识别
 * 
 */
@Component
public class PlateRecognize {

    public int plateRecognize(Mat src, Vector<String> licenseVec) {
        //车牌方块集合
        Vector<Mat> plateVec = new Vector<Mat>();
        int resultPD = plateDetect.plateDetect(src, plateVec);
        if (resultPD == 0) {
            int num = (int) plateVec.size();
            for (int j = 0; j < num; j++) {
                Mat plate = plateVec.get(j);
                //获取车牌颜色
                String plateType = charsRecognise.getPlateType(plate);
                //获取车牌号
                String plateIdentify = charsRecognise.charsRecognise(plate);
                String license = plateType + ":" + plateIdentify;
                licenseVec.add(license);
            }
        }
        return resultPD;
    }

    /**
     * 设置是否开启生活模式
     * @param lifemode
     */
    public void setLifemode(boolean lifemode) {
        plateDetect.setPDLifemode(lifemode);
    }

    /**
     * 是否开启调试模式
     * @param debug
     */
    public void setTestMode(boolean testMode) {
        plateDetect.setTestMode(testMode);
        charsRecognise.setCRDebug(testMode);
    }

    @Autowired
    private PlateDetect plateDetect;
    @Autowired
    private CharsRecognise charsRecognise;
}
