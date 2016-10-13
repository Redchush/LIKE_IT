package by.epam.like_it.model.vo.db_vo;


import by.epam.like_it.common_util.ResourceManager;
import by.epam.like_it.model.bean.User;

import java.io.Serializable;
import java.util.Arrays;

public class PhotoVo implements Serializable{

    private static final String defaultPhotoPath = ResourceManager.VALIDATION_INFO.getString("User.fotoPath.default");

    private String prevPhoto;
    private String realPath;
    private String photoName;
    private String extension;
    private byte[] newPhoto;
    private User user;

    public String getPrevPhoto() {
        return prevPhoto;
    }

    public void setPrevPhoto(String prevPhoto) {
        this.prevPhoto = prevPhoto;
    }

    public byte[] getNewPhoto() {
        return newPhoto;
    }

    public void setNewPhoto(byte[] newPhoto) {
        this.newPhoto = newPhoto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static String getDefaultPhotoPath() {
        return defaultPhotoPath;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }


    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }


    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PhotoVo{");

        sb.append("prevPhoto='").append(prevPhoto).append('\'');
        sb.append(", realPath='").append(realPath).append('\'');
        sb.append(", photoName='").append(photoName).append('\'');
        sb.append(", newPhoto=").append(Arrays.toString(newPhoto));
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}
