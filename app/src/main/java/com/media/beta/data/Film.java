package com.media.beta.data;

/**
 * Created by Team 2 on 10/31/2018.
 */

public class Film {

    private String description;
    private String image;
    private String title;
    private String release;
    private String vote;
    private String popular;

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

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getPopular() {
        return popular;
    }

    public void setPopular(String popular) {
        this.popular = popular;
    }

    public Film(String description, String image, String title, String release, String vote, String popular) {
        this.description = description;
        this.image = image;
        this.title = title;
        this.release = release;
        this.vote = vote;
        this.popular = popular;
    }

    public Film() {

    }
}
