package core.models;

import java.util.ArrayList;

public class Repo {
    private String name;
    private String url;
    private int stars;
    private String description;
    private String language;
    private int commitsNum;
    private ArrayList<Contributor> topContributors;

    public Repo() {
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getCommitsNum() {
        return commitsNum;
    }

    public void setCommitsNum(int commitsNum) {
        this.commitsNum = commitsNum;
    }

    public ArrayList<Contributor> getTopContributors() {
        return topContributors;
    }

    public void setTopContributors(ArrayList<Contributor> topContributors) {
        this.topContributors = topContributors;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
