package com.insightflow.tasks_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/**
 * Entidad que representa una tarea en el sistema.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    /**
     * Identificador de la tarea
     */
    private String id;
    /**
     * Identificador del documento asociado a la tarea
     */
    private String documentId;
    /**
     * Título de la tarea
     */
    private String title;
    /**
     * Descripción de la tarea
     */
    private String description;
    /**
     * Estado de la tarea (por ejemplo, PENDING, IN_PROGRESS, COMPLETED)
     */
    private String status;
    /**
     * Identificador del usuario asignado a la tarea
     */
    private String assignedUserId;
    /**
     * Nivel de prioridad de la tarea (por ejemplo, LOW, MEDIUM, HIGH)
     */
    private String priority;
    /**
     * Fecha y hora de vencimiento de la tarea
     */
    private LocalDateTime dueDate;
    /**
     * Timestamp cuando la tarea fue creada
     */
    private LocalDateTime createdAt;
    /**
     * Timestamp cuando la tarea fue actualizada por última vez
     */
    private LocalDateTime updatedAt;
    /**
     * Indicador de si la tarea está eliminada lógicamente
     */
    private boolean active;

    /**
     * Estados de las tareas
     */
    public enum TaskStatus {
        PENDING("Pendiente"),
        IN_PROGRESS("En progreso"),
        COMPLETED("Completada");

        private final String displayName;

        TaskStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
    /**
     * Niveles de prioridad de las tareas
     */
    public enum TaskPriority {
        LOW("Baja"),
        MEDIUM("Media"),
        HIGH("Alta");

        private final String displayName;

        TaskPriority(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

}