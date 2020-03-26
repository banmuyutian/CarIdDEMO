package guo.cars.common;

/**
 * @description:
 * @author: guoyiming
 **/
public class AppException extends Exception {

    private String errMessage;

    public AppException(String errMsg){

        super(errMsg);
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
