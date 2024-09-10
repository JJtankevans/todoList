package br.com.vitorOliveira.todoList.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity createTask(@RequestBody TaskModel task) {

        if(task.getTitle().length() > 50) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Titulo maior que 50 caracteres");
        }
        System.out.println("Chegou no controller");
        TaskModel taskCreated = this.taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }
}
