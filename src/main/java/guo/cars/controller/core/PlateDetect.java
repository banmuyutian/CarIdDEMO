package guo.cars.controller.core;

import java.util.Vector;

import guo.cars.controller.FunctionControler;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 车牌检测
 */
@Component
public class PlateDetect {
    @Autowired
    private FunctionControler functionControler;
    /**
     * @param src
     * @param resultVec
     *            可能是车牌的图块集合
     * @return the error number
     *         <ul>
     *         <li>0: plate detected successfully;
     *         <li>-1: source Mat is empty;
     *         <li>-2: plate not detected.
     *         </ul>
     */
    public int plateDetect(final Mat src, Vector<Mat> resultVec) {
        Vector<Mat> matVec = plateLocate.plateLocate(src);
        if (0 == matVec.size()) {
            return -1;
        }
        if (0 != plateJudge.plateJudge(matVec, resultVec)) {
            return -2;
        }
        if (getPDDebug()) {
            int size = (int) resultVec.size();
            for (int i = 0; i < size; i++) {
                Mat img = resultVec.get(i);
                String str = "tmp/plate_judge_result_" + Integer.valueOf(i).toString() + ".jpg";
                opencv_imgcodecs.imwrite(str, img);
                functionControler.recordProcess("经过筛选后有可能是车牌的图像",str);
            }
        }

        return 0;
    }

    /**
     * 生活模式与工业模式切换
     * 
     * @param pdLifemode
     */
    public void setPDLifemode(boolean pdLifemode) {
        plateLocate.setLifemode(pdLifemode);
    }

    /**
     * 是否开启测试模式
     */
    public void setTestMode(boolean testMode) {
        plateLocate.setTestMode(testMode);
    }

    /**
     * 获取调试模式状态
     * 
     * @return
     */
    public boolean getPDDebug() {
        return plateLocate.getDebug();
    }

    public void setGaussianBlurSize(int gaussianBlurSize) {
        plateLocate.setGaussianBlurSize(gaussianBlurSize);
    }

    public final int getGaussianBlurSize() {
        return plateLocate.getGaussianBlurSize();
    }

    public void setMorphSizeWidth(int morphSizeWidth) {
        plateLocate.setMorphSizeWidth(morphSizeWidth);
    }

    public final int getMorphSizeWidth() {
        return plateLocate.getMorphSizeWidth();
    }

    public void setMorphSizeHeight(int morphSizeHeight) {
        plateLocate.setMorphSizeHeight(morphSizeHeight);
    }

    public final int getMorphSizeHeight() {
        return plateLocate.getMorphSizeHeight();
    }

    public void setVerifyError(float verifyError) {
        plateLocate.setVerifyError(verifyError);
    }

    public final float getVerifyError() {
        return plateLocate.getVerifyError();
    }

    public void setVerifyAspect(float verifyAspect) {
        plateLocate.setVerifyAspect(verifyAspect);
    }

    public final float getVerifyAspect() {
        return plateLocate.getVerifyAspect();
    }

    public void setVerifyMin(int verifyMin) {
        plateLocate.setVerifyMin(verifyMin);
    }

    public void setVerifyMax(int verifyMax) {
        plateLocate.setVerifyMax(verifyMax);
    }

    public void setJudgeAngle(int judgeAngle) {
        plateLocate.setJudgeAngle(judgeAngle);
    }

    @Autowired
    private PlateLocate plateLocate;
    @Autowired
    private PlateJudge plateJudge;

}
