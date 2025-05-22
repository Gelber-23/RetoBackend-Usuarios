package com.course.users.domain.utils.constants;

public class DtoConstants {


    private DtoConstants() {

    }

    // Regular expressions
    public static final String PHONE_REGEX = "^\\+?\\d{10,18}$";
    public static final String ONLY_NUMBERS_REGEX ="^\\d+$";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String FIELD_REQUIRED = "This field is required";
    public static final String FIELD_PHONE_ERROR_MESSAGE = "This field is required";
    public static final String FIELD_ONLY_NUMBER_REQUIRED = "The document must only contain numbers";
    public static final String FIELD_MIN_2_SIZE_REQUIRED ="This field must have at least 2 characters" ;
    public static final String FIELD_BETWEEN_7_10_REQUIRED = "This field must have between 7 and 10 characters";
    public static final String FIELD_EMAIL_INCORRECT_FORMAT = "The email is not in a valid format";
    public static final String FIELD_MUST_BE_MORE_18 = "The email is not in a valid format";

}
