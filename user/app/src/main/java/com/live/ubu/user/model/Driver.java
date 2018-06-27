package com.live.ubu.user.model;

/**
 * Created by SD on 3/26/2018.
 */

public class Driver {
    private String name, position;
    private int imageId;

    public Driver(String name, String position, int imageId) {
        this.name = name;
        this.position = position;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
