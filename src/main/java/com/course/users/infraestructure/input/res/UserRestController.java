package com.course.users.infraestructure.input.res;

import com.course.users.application.dto.request.UserEmployeeRequest;
import com.course.users.application.dto.request.UserRequest;
import com.course.users.application.dto.response.UserResponse;
import com.course.users.application.handler.IUserHandler;

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
@Tag(name = "USERS", description = "Endpoints for users")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserHandler userHandler;


    @Operation(summary = "Add a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation errors", content = @Content)
    })
    @PostMapping()
    @PreAuthorize("@permissionService.isAdmin(authentication)")
    public ResponseEntity<Void> saveUser (@Valid @RequestBody UserRequest userRequest) {
        userHandler.saveUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Add a new user employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation errors", content = @Content)
    })
    @PostMapping("createEmployee")
    @PreAuthorize("@permissionService.isOwner(authentication)")
    public ResponseEntity<Void> saveUserEmployee (@Valid @RequestBody UserEmployeeRequest userEmployeeRequest) {
        userHandler.saveUserEmployee(userEmployeeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation errors", content = @Content)
    })
    @GetMapping("{id}")
    @PreAuthorize("@permissionService.isAdminOrOwner(authentication)")
    public ResponseEntity<UserResponse> getUserById(@PathVariable(value = "id") Long id) {
        return  ResponseEntity.ok(userHandler.getUserById(id));
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All users returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping()
    @PreAuthorize("@permissionService.isAdmin(authentication)")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userHandler.getAllUsers());
    }



    @Operation(summary = "Delete user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @DeleteMapping("{id}")
    @PreAuthorize("@permissionService.isAdmin(authentication)")
    public ResponseEntity<Void> deleteUserById(@PathVariable(value = "id")Long id){
        userHandler.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
