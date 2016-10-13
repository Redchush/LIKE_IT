package by.epam.like_it.controller.command.impl.command_with_restriction.edit;


import by.epam.like_it.common_util.AuthSecurityConstants;
import by.epam.like_it.controller.command.impl.AbstractPostAuthCommand;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.exception.command.InvalidDataCommandException;
import by.epam.like_it.exception.service.action.ServiceActionDetectableException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.db_vo.PhotoVo;
import by.epam.like_it.service.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SaveNewPhoto extends AbstractPostAuthCommand {

    private static final String fotoRoot = "resources\\user_foto\\";
    private static final String ACTION_DEL_VAL = "delete";
    private static final String ACTION_LOAD_VAL = "load";

    private static final int MAX_PHOTO_SIZE = 1024*1024;

    private static SaveNewPhoto instance;

    private SaveNewPhoto(){}

    public static SaveNewPhoto getInstance(){

        if (instance == null)
            synchronized (SaveNewPhoto.class){
                if (instance == null)
                    instance = new SaveNewPhoto();
            }
        return instance;
    }

    /*Filter not see the command so don't put attribute in request and who eq to null*/
    @Override
    public String reallyExecute(HttpServletRequest request, HttpServletResponse response, Long who)
            throws ServiceSystemException, CommandException, ValidationInfoException, ServiceActionDetectableException {
        HttpSession session = request.getSession();
//        Long who = (Long) session.getAttribute(AuthSecurityConstants.USER_ID_ATTR);
        User user = new User();
        user.setId(who);
        String prevFotoPath = (String) session.getAttribute(AuthSecurityConstants.PHOTO_PATH);
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    String fileName = item.getName();
                    String contentType = item.getContentType();
                    boolean isInMemory = item.isInMemory();
                    long sizeInBytes = item.getSize();
                    if (sizeInBytes > MAX_PHOTO_SIZE || sizeInBytes < 1){
                        MSG_HANDLER.handleFailedValidation(request, User.class, "fotoPath");
                        throw new InvalidDataCommandException("Invalid size of photo", getPrevUrl(request));
                    }
                    byte[] bytes = item.get();
                    String realPath = request.getServletContext().getRealPath("/");
                    PhotoVo vo = new PhotoVo();
                    vo.setPrevPhoto(prevFotoPath);
                    vo.setRealPath(realPath);
                    vo.setNewPhoto(bytes);
                    vo.setUser(user);
                    vo.setPhotoName(fileName);

                    UserService userService = SERVICE_FACTORY.getUserService();
                    String s = userService.updatePhoto(vo);
                    if (s !=null){
                        session.setAttribute(AuthSecurityConstants.PHOTO_PATH, s);
                    }
                }
            }
        } catch (FileUploadException e) {
            throw new CommandException("Cannot parse multipart request", e);
        }
        return null;
    }

}
