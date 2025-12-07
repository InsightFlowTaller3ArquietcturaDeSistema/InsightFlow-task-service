package com.insightflow.tasks_service.controller;
import com.insightflow.tasks_service.dto.TaskDTOs.*;
import com.insightflow.tasks_service.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDateTime;
/**
 * Controlador REST para la gestión de tareas.
 * Proporciona endpoints para crear, actualizar, obtener y eliminar tareas.
 */
@Slf4j
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Endpoints para la gestión de tareas")
@CrossOrigin(origins = "*")
public class TaskController {
    private final TaskService taskService;

    /**
     * Crea una nueva tarea.
     * @param createTaskRequest DTO con los datos necesarios para crear la tarea.
     * @return DTO con los datos de la tarea creada.
     */
    @Operation(summary = "Crear una nueva tarea", description = "Crea una nueva tarea asociada a un documento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarea creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<SuccessResponse<TaskResponse>> createTask(
            @Parameter(description = "Datos para crear la tarea", required = true)
            @Valid @RequestBody CreateTaskRequest createTaskRequest) {
        log.info("Recibida solicitud para crear una nueva tarea");
        TaskResponse taskResponse = taskService.createTask(createTaskRequest);
        SuccessResponse<TaskResponse> successResponse = SuccessResponse.<TaskResponse>builder()
                .timestamp(LocalDateTime.now())
                .message("Tarea creada exitosamente")
                .data(taskResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    /**
     * GET /document/{documentId}/tasks
     * Obtiene todas las tareas asociadas a un documento específico.
     * @param documentId El ID del documento.
     * @return Lista de tareas asociadas al documento.
     */
    @GetMapping("/document/{documentId}/tasks")
    @Operation(summary = "Obtener tareas por ID de documento", description = "Obtiene todas las tareas asociadas a un documento específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tareas obtenidas exitosamente"),
            @ApiResponse(responseCode = "404", description = "Documento no encontrado")
    })
    public ResponseEntity<SuccessResponse<List<TaskResponse>>> getTasksByDocumentId(
            @Parameter(description = "ID del documento")
            @PathVariable String documentId) {
        log.info("Recibida solicitud para obtener tareas del documento ID: {}", documentId);
        List<TaskResponse> tasks = taskService.getTasksByDocumentId(documentId);
        SuccessResponse<List<TaskResponse>> successResponse = SuccessResponse.<List<TaskResponse>>builder()
                .timestamp(LocalDateTime.now())
                .message("Tareas obtenidas exitosamente")
                .data(tasks)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    /**
     * GET /{id}
     * Obtiene una tarea especifica por su ID.
     * @param id El ID de la tarea.
     * @return DTO con los datos de la tarea.
     */    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener tarea por ID", description = "Obtiene una tarea específica por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarea obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    public ResponseEntity<SuccessResponse<TaskResponse>> getTaskById(
            @Parameter(description = "ID de la tarea")
            @PathVariable String id) {
        log.info("Recibida solicitud para obtener tarea con ID: {}", id);
        TaskResponse taskResponse = taskService.getTaskById(id);
        SuccessResponse<TaskResponse> successResponse = SuccessResponse.<TaskResponse>builder()
                .timestamp(LocalDateTime.now())
                .message("Tarea obtenida exitosamente")
                .data(taskResponse)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    /**
     * PUT /{id}/status
     * Actualiza el estado de una tarea existente.
     * @param id El ID de la tarea a actualizar.
     * @param request DTO con el nuevo estado de la tarea.
     * @return DTO con los datos de la tarea actualizada.
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "Actualizar estado de la tarea", description = "Actualiza el estado de una tarea existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado de la tarea actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<SuccessResponse<TaskResponse>> updateTaskStatus(
            @Parameter(description = "ID de la tarea")
            @PathVariable String id,
            @Valid @RequestBody UpdateTaskStatusRequest request) {
        log.info("Recibida solicitud para actualizar el estado de la tarea con ID: {}", id);
        TaskResponse updatedTask = taskService.updateTaskStatus(id, request);
        SuccessResponse<TaskResponse> successResponse = SuccessResponse.<TaskResponse>builder()
                .timestamp(LocalDateTime.now())
                .message("Estado de la tarea actualizado exitosamente")
                .data(updatedTask)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    /**
     * PATCH /{id}
     * Actualiza los detalles de una tarea existente.
     * @param id El ID de la tarea a actualizar.
     * @param request DTO con los nuevos datos de la tarea.
     * @return DTO con los datos de la tarea actualizada.
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar tarea", description = "Actualiza los detalles de una tarea existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarea actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<SuccessResponse<TaskResponse>> updateTask(
            @Parameter(description = "ID de la tarea")
            @PathVariable String id,
            @Valid @RequestBody UpdateTaskRequest request) {
        log.info("Recibida solicitud para actualizar la tarea con ID: {}", id);
        TaskResponse updatedTask = taskService.updateTask(id, request);
        SuccessResponse<TaskResponse> successResponse = SuccessResponse.<TaskResponse>builder()
                .timestamp(LocalDateTime.now())
                .message("Tarea actualizada exitosamente")
                .data(updatedTask)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    /**
     * DELETE /{id}
     * Elimina lógicamente una tarea del sistema.
     * @param id El ID de la tarea a eliminar.
     * @return Respuesta de éxito sin datos.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tarea", description = "Elimina lógicamente una tarea del sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarea eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    public ResponseEntity<SuccessResponse<Void>> deleteTask(
            @Parameter(description = "ID de la tarea")
            @PathVariable String id) {
        log.info("Recibida solicitud para eliminar la tarea con ID: {}", id);
        taskService.deleteTask(id);
        SuccessResponse<Void> successResponse = SuccessResponse.<Void>builder()
                .timestamp(LocalDateTime.now())
                .message("Tarea eliminada exitosamente")
                .data(null)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    /**
     * GET /tasks
     * Obtiene todas las tareas del sistema.
     * @return Lista de todas las tareas.
     */
    @GetMapping("/tasks")
    @Operation(summary = "Obtener todas las tareas", description = "Obtiene todas las tareas del sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tareas obtenidas exitosamente")
    })
    public ResponseEntity<SuccessResponse<List<TaskResponse>>> getAllTasks() {
        log.info("Recibida solicitud para obtener todas las tareas");
        List<TaskResponse> tasks = taskService.getAllTasks();
        SuccessResponse<List<TaskResponse>> successResponse = SuccessResponse.<List<TaskResponse>>builder()
                .timestamp(LocalDateTime.now())
                .message("Tareas obtenidas exitosamente")
                .data(tasks)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    /**
     * GET /users/{userId}/tasks
     * Obtiene todas las tareas asignadas a un usuario específico.
     * @param userId El ID del usuario asignado.
     * @return Lista de tareas asignadas al usuario.
     */
    @GetMapping("/users/{userId}/tasks")
    @Operation(summary = "Obtener tareas por ID de usuario asignado", description = "Obtiene todas las tareas asignadas a un usuario específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tareas obtenidas exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<SuccessResponse<List<TaskResponse>>> getTasksByAssignedUserId(
            @Parameter(description = "ID del usuario asignado")
            @PathVariable String userId) {
        log.info("Recibida solicitud para obtener tareas del usuario asignado ID: {}", userId);
        List<TaskResponse> tasks = taskService.getTasksByAssignedUserId(userId);
        SuccessResponse<List<TaskResponse>> successResponse = SuccessResponse.<List<TaskResponse>>builder()
                .timestamp(LocalDateTime.now())
                .message("Tareas obtenidas exitosamente")
                .data(tasks)
                .build();
        return ResponseEntity.ok(successResponse);
    }
}
