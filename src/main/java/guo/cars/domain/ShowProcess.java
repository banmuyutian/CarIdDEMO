package guo.cars.domain;

public class ShowProcess {

    private Integer id;

    private String description;

    private String img;

    public ShowProcess(){}

    public ShowProcess(String description,String img){
        this.description = description;
        this.img = img;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}