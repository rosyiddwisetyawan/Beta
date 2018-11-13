package com.media.beta.data;

/**
 * Created by Team 2 on 9/6/2018.
 */

public class Politik {
    private String description;
    private String image;
    private String title;
    private String sumber;
    private String link;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSumber() {
        return sumber;
    }

    public void setSumber(String sumber) {
        this.sumber = sumber;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Politik(String description, String image, String title, String sumber, String link) {
        this.description = description;
        this.image = image;
        this.title = title;
        this.sumber = sumber;
        this.link = link;
    }

    public Politik() {

    }
}
