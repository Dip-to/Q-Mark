package com.example.q_mark.Model;

import java.util.List;

public class Folder {
    private String folderPath;
    private String curUser;
    private String folderName;
    private Boolean isdirectory;
    private Boolean isPdf;
    private Boolean isImage;

    public Boolean getPdf() {
        return isPdf;
    }

    public void setPdf(Boolean pdf) {
        isPdf = pdf;
    }

    public Boolean getImage() {
        return isImage;
    }

    public void setImage(Boolean image) {
        isImage = image;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getCurUser() {
        return curUser;
    }

    public void setCurUser(String curUser) {
        this.curUser = curUser;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public Boolean getIsdirectory() {
        return isdirectory;
    }

    public void setIsdirectory(Boolean isdirectory) {
        this.isdirectory = isdirectory;
    }
}
