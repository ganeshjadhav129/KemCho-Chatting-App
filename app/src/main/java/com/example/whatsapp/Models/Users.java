package com.example.whatsapp.Models;

public class Users {
    String profilePicPath,userName,mail,password,useId,lastMassage,about;

    public Users(String profilePicPath, String userName, String mail, String password, String useId, String lastMassage) {
        this.profilePicPath = profilePicPath;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.useId = useId;
        this.lastMassage = lastMassage;
    }
    public Users() {

    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getUseId() {
        return useId;
    }

    public void setUseId(String useId) {
        this.useId = useId;
    }

    ////////////////////////////////////////////
    public Users(String userName, String mail, String password) {
        this.userName = userName;
        this.mail = mail;
        this.password = password;
    }

    public String getProfilePicPath() {
        return profilePicPath;
    }

    public void setProfilePicPath(String profilePicPath) {
        this.profilePicPath = profilePicPath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getLastMassage() {
        return lastMassage;
    }

    public void setLastMassage(String lastMassage) {
        this.lastMassage = lastMassage;
    }
}
