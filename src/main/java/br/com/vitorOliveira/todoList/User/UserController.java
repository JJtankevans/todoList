package br.com.vitorOliveira.todoList.User;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired //Essa anotarion gerencia o ciclo de vida da interface usada
    private IUserRepository userRepository;
    @PostMapping("/")
    public ResponseEntity createUser(@RequestBody UserModel user) {
        UserModel userExists = userRepository.findByUsername(user.getUsername());
        if(userExists != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe!");
        }

        //Encrypta a senha
        var passwordHashed = BCrypt.withDefaults()
                .hashToString(12, user.getPassword().toCharArray());
        //Define a senha do usuario com a encryptada
        user.setPassword(passwordHashed);

        //Cria o usuario na base de dados
        UserModel userCreated = this.userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
