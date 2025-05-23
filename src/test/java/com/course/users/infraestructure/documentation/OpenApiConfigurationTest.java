package com.course.users.infraestructure.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenApiConfigurationTest {
    private final OpenApiConfiguration config = new OpenApiConfiguration();

    @Test
    void customOpenApi_shouldPopulateInfoAndComponents() {
        String description = "My application description";
        String version     = "2.3.4";

        OpenAPI api = config.customOpenApi(description, version);

        // Components nunca es null
        assertNotNull(api.getComponents());

        // Info
        Info info = api.getInfo();
        assertNotNull(info);
        assertEquals("Hexagonal User API", info.getTitle());
        assertEquals(version,                  info.getVersion());
        assertEquals(description,              info.getDescription());
        assertEquals("http://swagger.io/terms/", info.getTermsOfService());

        // License
        License lic = info.getLicense();
        assertNotNull(lic);
        assertEquals("Apache 2.0",       lic.getName());
        assertEquals("http://springdoc.org", lic.getUrl());
    }

}