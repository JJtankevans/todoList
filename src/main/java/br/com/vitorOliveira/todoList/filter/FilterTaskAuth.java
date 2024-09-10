package br.com.vitorOliveira.todoList.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.vitorOliveira.todoList.User.IUserRepository;
import br.com.vitorOliveira.todoList.User.UserModel;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;


@Component
public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String serveletPath = request.getServletPath();
        if(serveletPath.equals("/tasks/")) {
            //Pegar a atutenticação(usuario e senha)
            String auth = request.getHeader("Authorization");

            String authEncoded = auth.substring("Basic".length()).trim();

            byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

            String authString = new String(authDecoded);

            String[] credentials = authString.split(":");

            String username =credentials[0];
            String password =credentials[1];
            System.out.println(username);
            System.out.println(password);

            //Validar usuario
            UserModel user = userRepository.findByUsername(username.trim());

            if(user == null) {
                System.out.println("Entrou no if do ero do username");
                response.sendError(401);
            } else {
                //Validar a senha
                BCrypt.Result verifyPassword = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                System.out.println(verifyPassword.verified);
                if(verifyPassword.verified) {
                    //Segue viagem
                    filterChain.doFilter(request, response);
                } else {
                    System.out.println("Entrou no else do ero da password");
                    response.sendError(401);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
