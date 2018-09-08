package sketch.m_hospital.com.m_hospital.Models;

/**
 * Created by ANDROID on 8/29/2017.
 */

public class Branch {

    private String branch_id;
    private String main_hospital;
    private String branch_name;
    private String img_url;
    private String latitude;
    private String longitude;
    private String video_file;
    private String video_type;
    private String video_link;

    public Branch(String branch_id , String main_hospital , String name , String img_url , String latitude , String longitude,
                       String video_file , String video_type , String video_link){
        this.branch_id = branch_id;
        this.main_hospital = main_hospital;
        this.branch_name = name;
        this.img_url = img_url;
        this.latitude = latitude;
        this.longitude = longitude;
        this.video_file = video_file;
        this.video_type = video_type;
        this.video_link = video_link;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getMain_hospital() {
        return main_hospital;
    }

    public void setMain_hospital(String main_hospital) {
        this.main_hospital = main_hospital;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getVideo_file() {
        return video_file;
    }

    public void setVideo_file(String video_file) {
        this.video_file = video_file;
    }

    public String getVideo_type() {
        return video_type;
    }

    public void setVideo_type(String video_type) {
        this.video_type = video_type;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }
}
