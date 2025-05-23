package com.course.users.domain.usercase;

import com.course.users.domain.api.IUserServicePort;
import com.course.users.domain.exeption.UserValidationException;
import com.course.users.domain.model.User;
import com.course.users.domain.spi.IUserPersistencePort;
import com.course.users.domain.spi.IUtilsPort;
import com.course.users.domain.utils.constants.DtoConstants;
import com.course.users.domain.utils.constants.ValuesConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IUtilsPort utilsPort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IUtilsPort utilsPort) {
        this.userPersistencePort = userPersistencePort;
        this.utilsPort = utilsPort;
    }

    @Override
    public void saveUser(User user) {
        saveWithRole(user ,ValuesConstants.ID_ROLE_OWNER );
    }

    @Override
    public void saveEmployee(User user) {
        saveWithRole(user ,ValuesConstants.ID_ROLE_EMPLOYEE );
    }

    @Override
    public void saveClient(User user) {
        saveWithRole(user ,ValuesConstants.ID_ROLE_CLIENT );
    }

    @Override
    public User getUserById(Long id) {
        return userPersistencePort.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userPersistencePort.getAllUsers();
    }

    @Override
    public void deleteUserById(Long id) {
        userPersistencePort.deleteUserById(id);
    }

    private void saveWithRole(User user, Integer roleId) {

        user.setPassword(utilsPort.encrypPassword(user.getPassword()));

        user.setIdRole(Objects.requireNonNullElse(roleId, ValuesConstants.ID_ROLE_CLIENT));
        validUser(user);
        userPersistencePort.saveUser(user);
    }

    private void  validUser( User user) {

        List<String> errors = new ArrayList<>();

        if (user.getName() == null || user.getName().isBlank()) {
            errors.add(DtoConstants.FIELD_REQUIRED);
        }


        if (user.getLastName() == null || user.getLastName().isBlank()) {
            errors.add(DtoConstants.FIELD_REQUIRED);
        }


        String doc = user.getDocumentNumber();
        if (doc == null || doc.isBlank() || !Pattern.matches(DtoConstants.ONLY_NUMBERS_REGEX, doc)) {
            errors.add(DtoConstants.FIELD_ONLY_NUMBER_REQUIRED);
        }

        String phone = user.getPhone();
        if (phone == null || phone.isBlank() || !Pattern.matches(DtoConstants.PHONE_REGEX, phone)) {
            errors.add(DtoConstants.FIELD_PHONE_ERROR_MESSAGE);
        }

        if( user.getIdRole() == ValuesConstants.ID_ROLE_OWNER){
            Date birthdate = user.getBirthdate();
            if (birthdate == null || !utilsPort.isLegal(birthdate)) {
                errors.add(DtoConstants.FIELD_MUST_BE_MORE_18);
            }
        }

        String mail = user.getEmail();
        if (mail == null || mail.isBlank() || !mail.matches(DtoConstants.EMAIL_REGEX)) {
            errors.add(DtoConstants.FIELD_EMAIL_INCORRECT_FORMAT);
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            errors.add(DtoConstants.FIELD_REQUIRED);
        }


        if (user.getIdRole() == 0) {
            errors.add(DtoConstants.FIELD_REQUIRED);
        }

        if (!errors.isEmpty()) {
            throw new UserValidationException(errors);
        }
    }
}
