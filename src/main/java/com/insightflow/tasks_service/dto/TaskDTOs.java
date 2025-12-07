package com.insightflow.tasks_service.dto;

import com.insightflow.tasks_service.model.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Data Transfer Objects (DTOs) para la entidad Task.
 */
public class TaskDTOs {

    /**
     * DTO para la creación de una nueva tarea.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateTaskRequest {
        @NotBlank(message = "El documentId no puede estar vacío")
        private String documentId;
        @NotBlank(message = "El título no puede estar vacío")
        private String title;
        private String description;
        @NotBlank(message = "El estado no puede estar vacío")
        private String status;
        @NotBlank(message = "El usario asignado no puede estar vacío")
        private String assignedUserId;
        private String priority;
        @NotNull(message = "La fecha de vencimiento no puede estar vacía")
        private LocalDateTime dueDate;
    }

    /**
     * DTO para la actualización de una tarea existente.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateTaskRequest {
        private String title;
        private String description;
        private String status;
        private String assignedUserId;
        private String priority;
        private LocalDateTime dueDate;
    }

    /**
     * DTO para actualizar solo el estado de una tarea.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateTaskStatusRequest {
        @NotBlank(message = "El nuevo estado no puede estar vacío")
        private String status;
    }

    /**
     * DTO para la respuesta que contiene los detalles de una tarea.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TaskResponse {
        private String id;
        private String documentId;
        private String title;
        private String description;
        private Task.TaskStatus status;
        private String statusDisplayName;
        private String assignedUserId;
        private Task.TaskPriority priority;
        private String priorityDisplayName;
        private LocalDateTime dueDate;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private boolean active;

        /**
         * Convierte una entidad Task a un DTO TaskResponse.
         */
        public static TaskResponse fromEntity(Task task) {
            Task.TaskStatus taskStatus = Task.TaskStatus.valueOf(task.getStatus());
            Task.TaskPriority taskPriority = Task.TaskPriority.valueOf(task.getPriority());
            return TaskResponse.builder()
                    .id(task.getId())
                    .documentId(task.getDocumentId())
                    .title(task.getTitle())
                    .description(task.getDescription())
                    .status(taskStatus)
                    .assignedUserId(task.getAssignedUserId())
                    .priority(taskPriority)
                    .dueDate(task.getDueDate())
                    .createdAt(task.getCreatedAt())
                    .updatedAt(task.getUpdatedAt())
                    .active(task.isActive())
                    .statusDisplayName(taskStatus.getDisplayName())
                    .priorityDisplayName(taskPriority.getDisplayName())
                    .build();
        }
    }

    /**
     * DTO para respuesta de error
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorResponse {
        private String message;
        private int status;
        private LocalDateTime timestamp;
        private String path;
    }

    /**
     * DTO para respuesta de éxito 
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SuccessResponse<T> {
        private String message;
        private T data;
        private LocalDateTime timestamp;
    }
}