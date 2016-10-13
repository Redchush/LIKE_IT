package by.epam.like_it.service.validator.impl;

import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.vo.db_vo.PhotoVo;
import by.epam.like_it.model.vo.system_vo.error_bean.InvalidInfo;
import by.epam.like_it.service.validator.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class PhotoValidator implements Validator<PhotoVo> {

    private static PhotoValidator instance;

    private final List<String> allowedExt;

    private PhotoValidator(){
        allowedExt = new ArrayList<>();
        allowedExt.add(".png");
        allowedExt.add(".jpeg");
        allowedExt.add(".jpg");
    }

    public static PhotoValidator getInstance(){

        if (instance == null)
            synchronized (PhotoValidator.class){
                if (instance == null)
                    instance = new PhotoValidator();
            }
        return instance;
    }

    @Override
    public void isValidForCreate(PhotoVo entity) throws ValidationInfoException {
         isValidForUpdate(entity);
    }

    @Override
    public void isValidForUpdate(PhotoVo entity) throws ValidationInfoException {
        if ( !checkHasPhoto(entity) || !checkValidExt(entity))
            throw new ValidationInfoException(new InvalidInfo("User", Collections.singletonList("fotoPath")));
    }

    private boolean checkHasPhoto(PhotoVo vo){
        byte[] newPhoto = vo.getNewPhoto();
        return !(newPhoto == null || newPhoto.length == 0);
    }

    private boolean checkValidExt(PhotoVo vo){
        String extension = findExtension(vo);
        if (extension.isEmpty() || extension.length() < 3){
            return false;
        } else {
            boolean result = false;
            for(String allowed: allowedExt){
                if (extension.equalsIgnoreCase(allowed)){
                    vo.setExtension(extension.toLowerCase());
                    result = true;
                    break;
                }
            }
            return result;
        }
    }

    private String findExtension(PhotoVo vo){
        String photoName = vo.getPhotoName();
        int i = photoName.lastIndexOf(".");
        return photoName.substring(i, photoName.length());
    }

    public List<String> getAllowedExt() {
        return allowedExt;
    }

    public String getDefaultPhotoPath(){
        return NAVIGATOR.getDefault("User", "fotoPath");
    }
}

