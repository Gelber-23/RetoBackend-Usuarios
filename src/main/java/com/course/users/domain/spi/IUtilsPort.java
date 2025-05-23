package com.course.users.domain.spi;

import java.util.Date;

public interface IUtilsPort {

    String encrypPassword (String password) ;

    Boolean isLegal (Date date);

}
