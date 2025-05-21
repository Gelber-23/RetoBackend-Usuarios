package com.course.users.infraestructure.input.res;

import com.course.users.application.dto.request.RoleRequest;
import com.course.users.application.dto.response.RoleResponse;
import com.course.users.application.handler.IRoleHandler;
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
@RequestMapping("/roles/")
@Tag(name = "ROLES", description = "Endpoints for roles")
@RequiredArgsConstructor
public class RoleRestController {

    private final IRoleHandler roleHandler;


    @Operation(summary = "Add a new role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Role created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation errors", content = @Content)
    })
    @PostMapping()
    @PreAuthorize("@permissionService.isAdmin(authentication)")
    public ResponseEntity<Void> saveRole (@Valid @RequestBody RoleRequest roleRequest) {
        roleHandler.saveRole(roleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get role by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation errors", content = @Content)
    })
    @GetMapping("{id}")
    @PreAuthorize("@permissionService.isAdmin(authentication)")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable(value = "id") int id) {
        return  ResponseEntity.ok(roleHandler.getRoleById(id));
    }

    @Operation(summary = "Get all roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All roles returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RoleResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping()
    @PreAuthorize("@permissionService.isAdmin(authentication)")
    public ResponseEntity<List<RoleResponse>> getAllRoles(){
        return ResponseEntity.ok(roleHandler.getAllRoles());
    }



    @Operation(summary = "Delete role by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Role not found", content = @Content)
    })
    @DeleteMapping("{id}")
    @PreAuthorize("@permissionService.isAdmin(authentication)")
    public ResponseEntity<Void> deleteRoleById(@PathVariable(value = "id")int id){
        roleHandler.deleteRoleById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
