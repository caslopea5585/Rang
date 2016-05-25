package com.example.sangwoon.rang;

public class nutrient_compare  {

    int imgRes;
    String name;
    String content;
    private float ratingStar;


    public nutrient_compare(int imgRes, String name,String content, float ratingStar){
        this.imgRes=imgRes;
        this.name=name;
        this.content=content;
        this.ratingStar=ratingStar;
    }

    public float getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(float ratingStar) {
        this.ratingStar = ratingStar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.name = content;
    }

}