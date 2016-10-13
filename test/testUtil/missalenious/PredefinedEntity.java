package testUtil.missalenious;


import by.epam.like_it.model.bean.User;

import java.sql.Timestamp;
import java.util.Calendar;

public class PredefinedEntity {


    public static final User userTestedWithBan;

    public static final User userTestedWithoutBan;

    public static final User userToUpdate;

    static {
        long id = 1;
        String login = "lara";
        String password = "123456";
        String email = "lara@mail.ru";
        byte role = (byte) 3;

        String lastName = null;
        String firstName = null;

        Calendar cal = Calendar.getInstance();
        cal.set(2016, 8, 14, 16, 14, 56);
        cal.set(Calendar.MILLISECOND, 0);
        Timestamp createdDate = new Timestamp(cal.getTimeInMillis());
        Timestamp udatedDate = null;
        boolean isBanned = false;

        userTestedWithBan = new User(id, role, (byte) 0, login, password, email, lastName,
                firstName, null, null, createdDate, udatedDate, true);

        userTestedWithoutBan = new User(id, role, (byte) 0, login, password, email, lastName,
                firstName, null, null, createdDate, udatedDate, false);

// public UserTO(Long id, Long roleId, Integer languageId, String login, String password, String email, String lastName, String firstName, String about_me, String foto_path, Timestamp createdDate, Timestamp updatedDate, Boolean banned)
        id = 2L;
        login = "kennni";
        password = "123456";
        email = "kenni@yahoo.com";
        //month counted from 0!!!!
        cal.set(2016, 2, 15, 14, 33, 4);
        createdDate = new Timestamp(cal.getTimeInMillis());
        userToUpdate = new User(id, role, (byte) 2, login, password, email, lastName,
                firstName, null, null, createdDate, udatedDate, false);
    }
}
