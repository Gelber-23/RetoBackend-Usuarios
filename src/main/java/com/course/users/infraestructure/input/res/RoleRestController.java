package com.course.users.infraestructure.input.res;

import com.course.users.application.dto.request.RoleRequest;
import com.course.users.application.dto.response.RoleResponse;
import com.course.users.application.handler.IRoleHandler;
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
@RequestMapping("/roles/")
@Tag(name = OpenApiConstants.TITLE_ROLE_REST, description =OpenApiConstants.TITLE_DESCRIPTION_ROLE_REST)
@RequiredArgsConstructor
public class RoleRestController {

    private final IRoleHandler roleHandler;


    @Operation(summary = OpenApiConstants.NEW_ROLE_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = OpenApiConstants.NEW_ROLE_CREATED, content = @Content),
            @ApiResponse(responseCode = "400", description = OpenApiConstants.VALIDATIONS_ERRORS_MESSAGE, content = @Content)
    })
    @PostMapping()
    @PreAuthorize("@permissionService.isAdmin(authentication)")
    public ResponseEntity<Void> saveRole (@Valid @RequestBody RoleRequest roleRequest) {
        roleHandler.saveRole(roleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary =  OpenApiConstants.GET_ROLE_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  OpenApiConstants.GET_ROLE_MESSAGE, content = @Content),
            @ApiResponse(responseCode = "400", description =  OpenApiConstants.VALIDATIONS_ERRORS_MESSAGE, content = @Content)
    })
    @GetMapping("{id}")
    @PreAuthorize("@permissionService.isAdmin(authentication)")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable(value = "id") int id) {
        return  ResponseEntity.ok(roleHandler.getRoleById(id));
    }

    @Operation(summary =  OpenApiConstants.GET_ALL_ROLES_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  OpenApiConstants.GET_ALL_ROLES_MESSAGE,
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RoleResponse.class)))),
            @ApiResponse(responseCode = "404", description =  OpenApiConstants.NO_DATA_MESSAGE, content = @Content)
    })
    @GetMapping()
    @PreAuthorize("@permissionService.isAdmin(authentication)")
    public ResponseEntity<List<RoleResponse>> getAllRoles(){
        return ResponseEntity.ok(roleHandler.getAllRoles());
    }



    @Operation(summary =OpenApiConstants.DELETE_ROLE_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =OpenApiConstants.DELETE_ROLE_MESSAGE, content = @Content),
            @ApiResponse(responseCode = "404", description = OpenApiConstants.NO_DATA_MESSAGE, content = @Content)
    })
    @DeleteMapping("{id}")
    @PreAuthorize("@permissionService.isAdmin(authentication)")
    public ResponseEntity<Void> deleteRoleById(@PathVariable(value = "id")int id){
        roleHandler.deleteRoleById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
