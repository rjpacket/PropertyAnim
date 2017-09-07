package com.taovo.rjp.propertyanim.bullet_screen;

/**
 * Created by Administrator on 2017/9/3.
 */

public class Bullet {
    private String headUrl;
    private String title;
    private int zan;

    public Bullet(String title){
        this.title = title;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }
}
