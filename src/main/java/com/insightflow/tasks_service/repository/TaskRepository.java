package com.insightflow.tasks_service.repository;

import com.insightflow.tasks_service.model.Task;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Repositorio para la gestión de tareas.
 * Implementa operaciones CRUD utilizando una estructura de datos en memoria.
 * Los datos se almacenan en un ConcurrentHashMap para garantizar la seguridad en entornos concurrentes.
 * Los datos se reinician cada vez que se reinicia la aplicación.
 */

@Repository
public class TaskRepository {
    /**
     * Almacén en memoria de las tareas.
     * La clave es el ID de la tarea y el valor es la entidad Task.
     */
    private final Map<String, Task> taskStore = new ConcurrentHashMap<>();
    /**
     * Guarda una nueva tarea en el repositorio.
     *
     * @param task La tarea a guardar.
     * @return La tarea guardada.
     */
    public Task save(Task task) {
        taskStore.put(task.getId(), task);
        return task;
    }

    /**
     * Busca una tarea por su ID.
     *
     * @param id El ID de la tarea.
     * @return Un Optional que contiene la tarea si se encuentra, o vacío si no.
     */
    public Optional<Task> findById(String id) {
        return Optional.ofNullable(taskStore.get(id));
    }

    /**
     * Busca todas las tareas activas asociadas a un documento específico.
     * @param documentId El ID del documento.
     * @return Una lista de todas las tareas activas.
     */
    public List<Task> findByDocumentId(String documentId) {
        return taskStore.values().stream()
                .filter(task -> task.getDocumentId().equals(documentId) && task.isActive())
                .filter(Task::isActive)
                .sorted(Comparator.comparing(Task::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }
    /**
     * Busca todas las tareas activas en el repositorio.
     * @return Una lista de todas las tareas activas.
     */
    public List<Task> findAll() {
        return taskStore.values().stream()
                .filter(Task::isActive)
                .sorted(Comparator.comparing(Task::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Busca tareas por el ID del usuario asignado.
     * @param assignedUserId El ID del usuario asignado.
     * @return Una lista de tareas asignadas al usuario especificado.
     */
    public List<Task> findByAssignedUserId(String assignedUserId) {
        return taskStore.values().stream()
                .filter(task -> task.getAssignedUserId().equals(assignedUserId) && task.isActive())
                .sorted(Comparator.comparing(Task::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }
    /**
     * Busca tareas por estado
     * @param status El estado de la tarea.
     * @return Una lista de tareas con el estado especificado.
     */
    public List<Task> findByStatus(Task.TaskStatus status) {
        return taskStore.values().stream()
                .filter(task -> task.getStatus().equals(status) && task.isActive())
                .sorted(Comparator.comparing(Task::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Actualiza una tarea existente en el repositorio.
     * @param task La tarea con los datos actualizados.
     * @return La tarea actualizada.
     */
    public Task update(Task task) {
        taskStore.put(task.getId(), task);
        return task;
    }

    /**
     * Elimina lógicamente una tarea del repositorio.
     * @param id El ID de la tarea a eliminar.
     */
    public void deleteById(String id) {
        Task task = taskStore.get(id);
        if (task != null) {
            task.setActive(false);
            task.setUpdatedAt(java.time.LocalDateTime.now());
            taskStore.put(id, task);
        }
    }

    /**
     * Verifica si existe una tarea con el ID especificado.
     * @param id El ID de la tarea.
     * @return true si la tarea existe, false en caso contrario.
     */
    public boolean existsById(String id) {
        return taskStore.containsKey(id);
    }

    /**
     * Cuenta el número total de tareas activas en el repositorio.
     * @return El número total de tareas activas.
     */
    public long count() {
        return taskStore.values().stream()
                .filter(Task::isActive)
                .count();
    }

    /**
     * Elimina todas las tareas del repositorio.
     */
    public void clear() {
        taskStore.clear();
    }
}