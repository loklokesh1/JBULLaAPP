package com.mahammadjabi.jbulla.Models;

import java.util.List;

public class StoryModel
{
    private List<String> images;
    private String name;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StoryModel(List<String> images, String name) {
        this.images = images;
        this.name = name;
    }

}
