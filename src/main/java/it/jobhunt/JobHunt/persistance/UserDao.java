package it.jobhunt.JobHunt.persistance;

import it.jobhunt.JobHunt.entity.User;
import it.jobhunt.JobHunt.enums.UserRole;
import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.helper.user.UserHelper;

public interface UserDao {

    User get(String email) throws DefaultException;

    User getOrCreate(String email, String password, UserRole role) throws DefaultException;

    User changePassword(String password) throws DefaultException;

    User changePassword(String email, String password) throws DefaultException;

    User changeEmail(String email) throws DefaultException;

    User changeEmail(String actualEmail, String futureEmail) throws DefaultException;

    void editData(String email, UserHelper user) throws DefaultException;
}
