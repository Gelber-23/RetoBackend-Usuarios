package com.course.users.domain.utils.constants;

public class OpenApiConstants {


    private OpenApiConstants() {

    }

    public static final String VALIDATIONS_ERRORS_MESSAGE = "Validation errors";
    public static final String NO_DATA_MESSAGE = "No data found";


    // USERS CONSTANTS API
    public static final String TITLE_USERS_REST = "USERS";
    public static final String TITLE_DESCRIPTION_USERS_REST = "Endpoints for users";

    public static final String NEW_USER_TITLE = "Add a new user";
    public static final String NEW_USER_CREATED = "User created";

    public static final String NEW_EMPLOYEE_TITLE = "Add a new user employee";

    public static final String NEW_CLIENT_TITLE = "Add a new user client";

    public static final String GET_USER_TITLE = "Get user by ID";
    public static final String GET_USER_MESSAGE = "User foundr";


    public static final String GET_ALL_USER_TITLE = "Get all users";
    public static final String GET_ALL_USER_MESSAGE= "All users returned";

    public static final String DELETE_USER_TITLE = "Delete user by ID";
    public static final String DELETE_USER_MESSAGE= "User deleted";


    // ROLE CONSTANTS API
    public static final String TITLE_ROLE_REST = "ROLES";
    public static final String TITLE_DESCRIPTION_ROLE_REST = "Endpoints for roles";

    public static final String NEW_ROLE_TITLE = "Add a new role";
    public static final String NEW_ROLE_CREATED = "Role created";


    public static final String GET_ROLE_TITLE = "Get role by ID";
    public static final String GET_ROLE_MESSAGE = "Role found";


    public static final String GET_ALL_ROLES_TITLE = "Get all roles";
    public static final String GET_ALL_ROLES_MESSAGE= "All roles returned";

    public static final String DELETE_ROLE_TITLE = "Delete role by ID";
    public static final String DELETE_ROLE_MESSAGE= "Role deleted";

}
