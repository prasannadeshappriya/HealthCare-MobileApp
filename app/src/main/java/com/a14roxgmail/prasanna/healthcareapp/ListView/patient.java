package com.a14roxgmail.prasanna.healthcareapp.ListView;

/**
 * Created by Prasanna Deshappriya on 12/1/2016.
 */
public class patient {
    private String name;
    private String nic;
    private String city;
    private int id;

    public patient(String city, int id, String name, String nic) {
        this.city = city;
        this.id = id;
        this.name = name;
        this.nic = nic;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
