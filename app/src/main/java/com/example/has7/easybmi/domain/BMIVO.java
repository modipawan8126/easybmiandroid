package com.example.has7.easybmi.domain;

/**
 * Created by has7 on 6/10/2017.
 */

public class BMIVO {

    private String name;
    private String height;
    private String weight;
    private String bmi;

    public BMIVO() {

    }

    public BMIVO (String name1, String height1, String weight1, String bmi1) {
        name = name1;
        height = height1;
        weight = weight1;
        bmi = bmi1;
    }


    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
