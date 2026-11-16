package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import com.jcaa.usersmanagement.infrastructure.config.OpenApiConfig;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateStudentRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateStudentRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.ApiErrorResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.StudentRestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Contrato OpenAPI / Swagger para {@link StudentRestController}.
 *
 * <p>Toda la documentación de la API vive aquí para mantener el controlador limpio y enfocado
 * únicamente en la lógica de despacho.
 */
@Tag(
        name = "Students",
        description = "Gestión de estudiantes: crear, consultar, actualizar y eliminar.")
@SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
public interface StudentRestControllerDocs {

    @Operation(
            summary = "Crear estudiante",
            description =
                    "Registra un nuevo estudiante. El ID debe ser único y el correo no puede estar en uso. "
                            + "Requiere rol ADMIN.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Estudiante creado exitosamente.",
                    content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StudentRestResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos.",
                    content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Ya existe un estudiante con ese correo electrónico.",
                    content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    StudentRestResponse create(@Valid @RequestBody CreateStudentRestRequest request);

    @Operation(
            summary = "Listar estudiantes",
            description = "Retorna todos los estudiantes ordenados por apellido y nombre.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de estudiantes. Puede ser vacía.",
                    content =
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = StudentRestResponse.class))))
    })
    @SuppressWarnings("unused") // Invocado por Spring MVC y leído por springdoc mediante reflexión.
    List<StudentRestResponse> getAll();

    @Operation(
            summary = "Obtener estudiante por ID",
            description = "Retorna los datos de un estudiante identificado por su ID único.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Estudiante encontrado.",
                    content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StudentRestResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe ningún estudiante con el ID proporcionado.",
                    content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    StudentRestResponse getById(
            @Parameter(description = "ID único del estudiante.", example = "std-001", required = true)
            @PathVariable
            String id);

    @Operation(
            summary = "Actualizar estudiante",
            description =
                    "Actualiza los datos de un estudiante existente. Si se cambia el correo, no debe estar "
                            + "en uso por otro estudiante. Requiere rol ADMIN.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Estudiante actualizado exitosamente.",
                    content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StudentRestResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos.",
                    content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe ningún estudiante con el ID proporcionado.",
                    content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "El nuevo correo ya está en uso por otro estudiante.",
                    content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    StudentRestResponse update(
            @Parameter(
                    description = "ID único del estudiante a actualizar.",
                    example = "std-001",
                    required = true)
            @PathVariable
            String id,
            @Valid @RequestBody UpdateStudentRestRequest request);

    @Operation(
            summary = "Eliminar estudiante",
            description = "Elimina permanentemente un estudiante por su ID. Requiere rol ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estudiante eliminado exitosamente."),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe ningún estudiante con el ID proporcionado.",
                    content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    void delete(
            @Parameter(
                    description = "ID único del estudiante a eliminar.",
                    example = "std-001",
                    required = true)
            @PathVariable
            String id);
}
