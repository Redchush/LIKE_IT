package by.epam.like_it.model.bean;

import by.epam.like_it.common_util.ResourceManager;
import by.epam.like_it.model.bean.util_interface.DeletableByBan;
import by.epam.like_it.model.bean.util_interface.RealEntity;
import by.epam.like_it.model.bean.util_interface.TimeDependent;

import java.sql.Timestamp;

public class User implements DeletableByBan, TimeDependent, RealEntity {

     /*not banned*/
    private Long id;
    private Byte roleId;
    private Byte languageId;
    private String login;
    private String password;
    private String email;
    private String lastName;
    private String firstName;
    private String aboutMe;
    private String fotoPath;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private Boolean banned;

    public User() {}

    public User(Long id, Byte roleId, Byte languageId, String login, String password, String email, String lastName, String firstName, String about_me, String foto_path, Timestamp createdDate, Timestamp updatedDate, Boolean banned) {
        this.id = id;
        this.roleId = roleId;
        this.languageId = languageId;
        this.login = login;
        this.password = password;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.aboutMe = about_me;
        this.fotoPath = foto_path;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.banned = banned;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getUserId() {
        return getId();
    }

    @Override
    public void setUserId(Long id) {
        setId(id);
    }

    public Byte getRoleId() {
        return roleId;
    }

    public void setRoleId(Byte roleId) {
        this.roleId = roleId;
    }

    public Byte getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Byte languageId) {
        this.languageId = languageId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getFotoPath() {
        return fotoPath;
    }

    public void setFotoPath(String fotoPath) {
        this.fotoPath = fotoPath;
    }

    @Override
    public void initRequiredDefault() {
        Byte defaultRoleId = 3; /*user*/
        if (roleId == null){
            roleId = defaultRoleId;
        }
        if (banned == null){
            banned = getDefaultCreateBan();
        }
        if (createdDate == null){
            createdDate = getDefaultCreatedDate();
        }
    }

    @Override
    public void initAllDefault() {

       initRequiredDefault();
       if (updatedDate == null){
           updatedDate = getDefaultUpdatedDate();
       }
       if (fotoPath == null){
           fotoPath = ResourceManager.VALIDATION_INFO.getString("User.fotoPath.default");
       }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) {
            return false;
        }
        if (roleId != null ? !roleId.equals(user.roleId) : user.roleId != null) {
            return false;
        }
        if (languageId != null ? !languageId.equals(user.languageId) : user.languageId != null) {
            return false;
        }
        if (login != null ? !login.equals(user.login) : user.login != null) {
            return false;
        }
        if (password != null ? !password.equals(user.password) : user.password != null) {
            return false;
        }
        if (email != null ? !email.equals(user.email) : user.email != null) {
            return false;
        }
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) {
            return false;
        }
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) {
            return false;
        }
        if (aboutMe != null ? !aboutMe.equals(user.aboutMe) : user.aboutMe != null) {
            return false;
        }
        if (fotoPath != null ? !fotoPath.equals(user.fotoPath) : user.fotoPath != null) {
            return false;
        }
        if (createdDate != null ? !createdDate.equals(user.createdDate) : user.createdDate != null) {
            return false;
        }
        //noinspection SimplifiableIfStatement
        if (updatedDate != null ? !updatedDate.equals(user.updatedDate) : user.updatedDate != null) {
            return false;
        }
        return banned != null ? banned.equals(user.banned) : user.banned == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        result = 31 * result + (languageId != null ? languageId.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (aboutMe != null ? aboutMe.hashCode() : 0);
        result = 31 * result + (fotoPath != null ? fotoPath.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (banned != null ? banned.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");

        sb.append("id=").append(id);
        sb.append(", roleId=").append(roleId);
        sb.append(", languageId=").append(languageId);
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", aboutMe='").append(aboutMe).append('\'');
        sb.append(", fotoPath='").append(fotoPath).append('\'');
        sb.append(", createdDate=").append(createdDate);
        sb.append(", updatedDate=").append(updatedDate);
        sb.append(", banned=").append(banned);
        sb.append('}');
        return sb.toString();
    }


}