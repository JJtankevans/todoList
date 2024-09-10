package br.com.vitorOliveira.todoList.task;

import br.com.vitorOliveira.todoList.User.UserModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_task")
public class TaskModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private UUID idUser;
    @Column(length = 50)
    private String Title;
    private String description;
    private LocalDateTime startedAt;
    private LocalDateTime finishAt;
    private String priority;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
