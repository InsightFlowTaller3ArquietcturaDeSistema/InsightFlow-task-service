package com.insightflow.tasks_service.service;

import com.insightflow.tasks_service.dto.TaskDTOs.*;
import com.insightflow.tasks_service.model.Task;
import com.insightflow.tasks_service.repository.TaskRepository;
import com.insightflow.tasks_service.exception.TaskNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de tareas.
 * Implementa la lógica de negocio para crear, actualizar, obtener y eliminar tareas (CRUD).
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    /**
     * Crea una nueva tarea.
     * @param createTaskRequest DTO con los datos necesarios para crear la tarea.
     * @return DTO con los datos de la tarea creada.
     */
    public TaskResponse createTask(CreateTaskRequest createTaskRequest) {
        log.info("Creando una nueva tarea para el documento ID: {}", createTaskRequest.getDocumentId());

        String priority = createTaskRequest.getPriority() != null ? createTaskRequest.getPriority().toUpperCase() : "MEDIUM";

        Task task = Task.builder()
                .id(UUID.randomUUID().toString())
                .documentId(createTaskRequest.getDocumentId())
                .title(createTaskRequest.getTitle())
                .description(createTaskRequest.getDescription())
                .status(createTaskRequest.getStatus())
                .assignedUserId(createTaskRequest.getAssignedUserId())
                .priority(priority)
                .dueDate(createTaskRequest.getDueDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .active(true)
                .build();
        Task savedTask = taskRepository.save(task);
        log.info("Tarea creada con ID: {}", savedTask.getId());
        return TaskResponse.fromEntity(savedTask);
    }

    /**
     * Obtiene todas las tareas asociadas a un documento específico.
     * @param documentId El ID del documento.
     * @return Lista de tareas asociadas al documento.
     */
    public List<TaskResponse> getTasksByDocumentId(String documentId) {
        log.info("Obteniendo tareas para el documento ID: {}", documentId);
        List<Task> tasks = taskRepository.findByDocumentId(documentId);
        return tasks.stream()
                .map(TaskResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /** 
     * Obtiene una tarea especifica por su ID.
     * @param id El ID de la tarea.
     * @return DTO con los datos de la tarea.
     * @throws TaskNotFoundException Si la tarea no existe.
     */
    public TaskResponse getTaskById(String id) {
        log.info("Obteniendo tarea con ID: {}", id);
        Task task = taskRepository.findById(id)
                .filter(Task::isActive)
                .orElseThrow(() -> {
                    log.error("Tarea con ID {} no encontrada", id);
                    return new TaskNotFoundException("Tarea con ID " + id + " no encontrada");
                });
        log.info("Tarea con ID {} obtenida exitosamente", id);
        return TaskResponse.fromEntity(task);
    }

    /**
     * Actualiza el estado de una tarea existente.
     * @param id El ID de la tarea a actualizar.
     * @param request DTO con el nuevo estado de la tarea.
     * @return DTO con los datos de la tarea actualizada.
     * @throws TaskNotFoundException Si la tarea no existe.
     */
    public TaskResponse updateTaskStatus(String id, UpdateTaskStatusRequest request) {
        log.info("Actualizando estado de la tarea con ID: {}", id);
        Task task = taskRepository.findById(id)
                .filter(Task::isActive)
                .orElseThrow(() -> {
                    log.error("Tarea con ID {} no encontrada para actualización", id);
                    return new TaskNotFoundException("Tarea con ID " + id + " no encontrada");
                });
        task.setStatus(request.getStatus());
        task.setUpdatedAt(LocalDateTime.now());
        Task updatedTask = taskRepository.update(task);
        log.info("Estado de la tarea con ID {} actualizado a {}", id, request.getStatus());
        return TaskResponse.fromEntity(updatedTask);
    }

    /**
     * Actualiza los detalles de una tarea existente.
     * @param id El ID de la tarea a actualizar.
     * @param request DTO con los nuevos datos de la tarea.
     * @return DTO con los datos de la tarea actualizada.
     */

    public TaskResponse updateTask(String id, UpdateTaskRequest request) {
        log.info("Actualizando tarea con ID: {}", id);
        Task task = taskRepository.findById(id)
                .filter(Task::isActive)
                .orElseThrow(() -> {
                    log.error("Tarea con ID {} no encontrada para actualización", id);
                    return new TaskNotFoundException("Tarea con ID " + id + " no encontrada");
                });

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        if (request.getAssignedUserId() != null) {
            task.setAssignedUserId(request.getAssignedUserId());
        }
        if (request.getPriority() != null) {
            task.setPriority(request.getPriority().toUpperCase());
        }
        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }
        task.setUpdatedAt(LocalDateTime.now());
        Task updatedTask = taskRepository.update(task);
        log.info("Tarea con ID {} actualizada exitosamente", id);
        return TaskResponse.fromEntity(updatedTask);
    }
    /**
     * Elimina lógicamente una tarea por su ID.
     * @param id El ID de la tarea a eliminar.
     * @throws TaskNotFoundException Si la tarea no existe.
     */
    public void deleteTask(String id) {
        log.info("Eliminando tarea con ID: {}", id);
        Task task = taskRepository.findById(id)
                .filter(Task::isActive)
                .orElseThrow(() -> {
                    log.error("Tarea con ID {} no encontrada para eliminación", id);
                    return new TaskNotFoundException("Tarea con ID " + id + " no encontrada");
                });
        taskRepository.deleteById(id);
        log.info("Tarea con ID {} eliminada exitosamente", id);
    }

    /**
     * Obtiene todas las tareas.
     * @return Lista de todas las tareas.
     */
    public List<TaskResponse> getAllTasks() {
        log.info("Obteniendo todas las tareas");
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(TaskResponse::fromEntity)
                .collect(Collectors.toList());
    }
    /**
     * Obtiene todas las tareas asignadas a un usuario específico.
     * @param assignedUserId El ID del usuario asignado.
     * @return Lista de tareas asignadas al usuario.
     */

    public List<TaskResponse> getTasksByAssignedUserId(String assignedUserId) {
        log.info("Obteniendo tareas para el usuario asignado ID: {}", assignedUserId);
        List<Task> tasks = taskRepository.findByAssignedUserId(assignedUserId);
        return tasks.stream()
                .map(TaskResponse::fromEntity)
                .collect(Collectors.toList());
    }

}