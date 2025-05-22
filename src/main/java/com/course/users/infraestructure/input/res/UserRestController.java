package com.course.users.infraestructure.input.res;

import com.course.users.application.dto.request.UserClientRequest;
import com.course.users.application.dto.request.UserEmployeeRequest;
import com.course.users.application.dto.request.UserRequest;
import com.course.users.application.dto.response.UserResponse;
import com.course.users.application.handler.IUserHandler;

import com.course.users.domain.utils.constants.OpenApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/")
@Tag(name = OpenApiConstants.TITLE_USERS_REST, description = OpenApiConstants.TITLE_DESCRIPTION_USERS_REST)
@RequiredArgsConstructor
public class UserRestController {

    private final IUserHandler userHandler;


    @Operation(summary =  OpenApiConstants.NEW_USER_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = OpenApiConstants.NEW_USER_CREATED, content = @Content),
            @ApiResponse(responseCode = "400", description =  OpenApiConstants.VALIDATIONS_ERRORS_MESSAGE, content = @Content)
    })
    @PostMapping()
    @PreAuthorize("@permissionService.isAdmin(authentication)")
    public ResponseEntity<Void> saveUser (@Valid @RequestBody UserRequest userRequest) {
        userHandler.saveUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = OpenApiConstants.NEW_EMPLOYEE_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = OpenApiConstants.NEW_USER_CREATED, content = @Content),
            @ApiResponse(responseCode = "400", description =OpenApiConstants.VALIDATIONS_ERRORS_MESSAGE, content = @Content)
    })
    @PostMapping("createEmployee")
    @PreAuthorize("@permissionService.isOwner(authentication)")
    public ResponseEntity<Void> saveUserEmployee (@Valid @RequestBody UserEmployeeRequest userEmployeeRequest) {
        userHandler.saveUserEmployee(userEmployeeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = OpenApiConstants.NEW_CLIENT_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = OpenApiConstants.NEW_USER_CREATED, content = @Content),
            @ApiResponse(responseCode = "400", description = OpenApiConstants.VALIDATIONS_ERRORS_MESSAGE, content = @Content)
    })
    @PostMapping("CreateClient")
    public ResponseEntity<Void> saveUserClient (@Valid @RequestBody UserClientRequest userClientRequest) {
        userHandler.saveUserClient(userClientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    @Operation(summary =  OpenApiConstants.GET_USER_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  OpenApiConstants.GET_USER_MESSAGE, content = @Content),
            @ApiResponse(responseCode = "400", description =  OpenApiConstants.VALIDATIONS_ERRORS_MESSAGE, content = @Content)
    })
    @GetMapping("{id}")
    @PreAuthorize("@permissionService.isAdminOrOwner(authentication)")
    public ResponseEntity<UserResponse> getUserById(@PathVariable(value = "id") Long id) {
        return  ResponseEntity.ok(userHandler.getUserById(id));
    }

    @Operation(summary =OpenApiConstants.GET_ALL_USER_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = OpenApiConstants.GET_ALL_USER_MESSAGE,
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "404", description = OpenApiConstants.NO_DATA_MESSAGE, content = @Content)
    })
    @GetMapping()
    @PreAuthorize("@permissionService.isAdmin(authentication)")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userHandler.getAllUsers());
    }



    @Operation(summary = OpenApiConstants.DELETE_USER_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  OpenApiConstants.DELETE_USER_MESSAGE, content = @Content),
            @ApiResponse(responseCode = "404", description =  OpenApiConstants.NO_DATA_MESSAGE, content = @Content)
    })
    @DeleteMapping("{id}")
    @PreAuthorize("@permissionService.isAdmin(authentication)")
    public ResponseEntity<Void> deleteUserById(@PathVariable(value = "id")Long id){
        userHandler.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
