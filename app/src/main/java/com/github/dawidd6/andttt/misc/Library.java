package com.github.dawidd6.andttt.misc;

public class Library {
    private String name;
    private String author;
    private String license;
    private String url;

    public Library(String name, String author, String license, String url) {
        this.name = name;
        this.author = author;
        this.license = license;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
