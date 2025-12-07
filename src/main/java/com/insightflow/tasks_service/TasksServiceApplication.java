package com.insightflow.tasks_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Clase principal para iniciar la aplicación Spring Boot de Tasks Service.
 */ 
@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Tasks Service API",
		version = "1.0",
		description = "Microservicio para la gestión de tareas asociadas a documentos.",
		contact = @Contact(
			name = "InsightFlow Support",
			email = "support@insightflow.com"
		)
	),
	servers = {
		@Server(url = "http://localhost:8080", description = "Servidor local"),
		@Server(url = "https://task-service-5dmf.onrender.com", description = "Servidor de producción")
	}
)
public class TasksServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(TasksServiceApplication.class, args);
		System.out.println("Microservicio iniciado.");
		System.out.println("Swagger UI disponible en: http://localhost:8080/swagger-ui.html");
		System.out.println("Documentación de la API disponible en: http://localhost:8080/v3/api-docs");
	}
}