package guo.cars.domain;

public class TestResult {
    private Integer id;

    private String number;

    private Integer sum;

    private Long sumTime;

    private Long longestTime;

    private Long averageTime;

    private String correctRate;

    private String img;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Long getSumTime() {
        return sumTime;
    }

    public void setSumTime(Long sumTime) {
        this.sumTime = sumTime;
    }

    public Long getLongestTime() {
        return longestTime;
    }

    public void setLongestTime(Long longestTime) {
        this.longestTime = longestTime;
    }

    public Long getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(Long averageTime) {
        this.averageTime = averageTime;
    }

    public String getCorrectRate() {
        return correctRate;
    }

    public void setCorrectRate(String correctRate) {
        this.correctRate = correctRate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}