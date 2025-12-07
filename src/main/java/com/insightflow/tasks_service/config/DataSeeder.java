package com.insightflow.tasks_service.config;
import com.insightflow.tasks_service.model.Task;
import com.insightflow.tasks_service.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.UUID;

/**
* Componente para sembrar datos iniciales en la base de datos al iniciar la aplicación.
*/
@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final TaskRepository taskRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Iniciando el sembrado de datos...");

        taskRepository.clear();

        createSampleTasks();
        
        log.info("Sembrado de datos completado.");
        log.info("Total de tareas sembradas: {}", taskRepository.count());
    }
    private void createSampleTasks() {
        String doc1 = UUID.randomUUID().toString();
        String doc2 = UUID.randomUUID().toString();
        String doc3 = UUID.randomUUID().toString();
        String user1 = UUID.randomUUID().toString();
        String user2 = UUID.randomUUID().toString();
        String user3 = UUID.randomUUID().toString();
        

        Task task1 = Task.builder()
                .id(UUID.randomUUID().toString())
                .documentId(doc1)
                .title("Revisar el informe financiero")
                .description("Verificar los datos del informe financiero del Q2.")
                .status("PENDING")
                .assignedUserId(user1)
                .priority("HIGH")
                .dueDate(LocalDateTime.now().plusDays(3))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .active(true)
                .build();
        Task task2 = Task.builder()
                .id(UUID.randomUUID().toString())
                .documentId(doc1)
                .title("Actualizar la presentación de ventas")
                .description("Incluir los últimos datos de ventas en la presentación.")
                .status("IN_PROGRESS")
                .assignedUserId(user2)
                .priority("MEDIUM")
                .dueDate(LocalDateTime.now().plusDays(5))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .active(true)
                .build();

        Task task3 = Task.builder()
                .id(UUID.randomUUID().toString())
                .documentId(doc2)
                .title("Organizar la reunión de equipo")
                .description("Coordinar una reunión para discutir el proyecto X.")
                .status("COMPLETED")
                .assignedUserId(user3)
                .priority("LOW")
                .dueDate(LocalDateTime.now().plusDays(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .active(true)
                .build();

        Task task4 = Task.builder()
                .id(UUID.randomUUID().toString())
                .documentId(doc3)
                .title("Redactar el informe de progreso")
                .description("Crear un informe detallado sobre el progreso del proyecto Y.")
                .status("PENDING")
                .assignedUserId(user1)
                .priority("HIGH")
                .dueDate(LocalDateTime.now().plusDays(7))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .active(true)
                .build();
        
        Task task5 = Task.builder()
                .id(UUID.randomUUID().toString())
                .documentId(doc3)
                .title("Diseñar el nuevo logo de la empresa")
                .description("Crear un diseño moderno y atractivo para el logo.")
                .status("IN_PROGRESS")
                .assignedUserId(user2)
                .priority("MEDIUM")
                .dueDate(LocalDateTime.now().plusDays(10))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .active(true)
                .build();
        Task task6 = Task.builder()
                .id(UUID.randomUUID().toString())
                .documentId(doc2)
                .title("Planificar la campaña de marketing")
                .description("Desarrollar una estrategia para la próxima campaña de marketing.")
                .status("PENDING")
                .assignedUserId(user3)
                .priority("HIGH")
                .dueDate(LocalDateTime.now().plusDays(14))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .active(true)
                .build();
        
        Task task7 = Task.builder()
                .id(UUID.randomUUID().toString())
                .documentId(doc1)
                .title("Configurar el servidor de desarrollo")
                .description("Instalar y configurar el servidor para el entorno de desarrollo.")
                .status("COMPLETED")
                .assignedUserId(user1)
                .priority("LOW")
                .dueDate(LocalDateTime.now().plusDays(2))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .active(true)
                .build();

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);
        taskRepository.save(task4);
        taskRepository.save(task5);
        taskRepository.save(task6);
        taskRepository.save(task7);

        log.info("Tareas de muestra creadas y guardadas en el repositorio.");

    }
}