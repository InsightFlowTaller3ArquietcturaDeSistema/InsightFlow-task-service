package com.insightflow.tasks_service.exception;

/**
 * Excepción lanzada cuando una tarea no es encontrada en el repositorio.
 */
public class TaskNotFoundException extends RuntimeException {
    /**
     * Constructor de la excepción.
     * @param message Mensaje de error que describe la causa de la excepción.
     */
    public TaskNotFoundException(String message) {
        super(message);
    }
    /**
     * Constructor de la excepción con un mensaje predeterminado.
     * @param message Mensaje descriptivo del erorr.
     * @param cause Causa raíz del error.
     */
    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}