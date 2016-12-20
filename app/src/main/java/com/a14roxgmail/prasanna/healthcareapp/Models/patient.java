package com.a14roxgmail.prasanna.healthcareapp.Models;

/**
 * Created by Prasanna Deshappriya on 12/1/2016.
 */
public class patient {
    private int id;
    private String name;
    private String nic;
    private String district_id;
    private String date_of_birth;
    private String last_edit_date;
    private String flag;

    public patient(String name, String nic, String date_of_birth, String district_id) {
        this.date_of_birth = date_of_birth;
        this.district_id = district_id;
        this.name = name;
        this.nic = nic;
    }

    public patient(String name, String nic, String date_of_birth, String district_id, String last_edit_date, String flag) {
        this.date_of_birth = date_of_birth;
        this.district_id = district_id;
        this.flag = flag;
        this.last_edit_date = last_edit_date;
        this.name = name;
        this.nic = nic;
    }

    public patient(int id, String name,String nic, String date_of_birth, String district_id, String last_edit_date, String flag) {
        this.date_of_birth = date_of_birth;
        this.district_id = district_id;
        this.flag = flag;
        this.last_edit_date = last_edit_date;
        this.name = name;
        this.id = id;
        this.nic = nic;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLast_edit_date() {
        return last_edit_date;
    }

    public void setLast_edit_date(String last_edit_date) {
        this.last_edit_date = last_edit_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }
}
