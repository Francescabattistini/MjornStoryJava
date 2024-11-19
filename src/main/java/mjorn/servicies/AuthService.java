package mjorn.servicies;

import mjorn.entities.User;
import mjorn.exceptions.UnauthorizedException;
import mjorn.payloadDTO.UserLoginDTO;
import mjorn.tools.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    @Autowired
    private UsersService usersService;

    @Autowired
    private JWT jwt;

    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredentialsAndGenerateToken(UserLoginDTO body) {
        // 1. Controllo le credenziali
        // 1.1 Cerco nel DB se esiste un utente con l'email fornita
        User found = this.usersService.findByEmail(body.email());
        // 1.2 Verifico che la password di quell'utente corrisponda a quella fornita
        if (bcrypt.matches(body.password(), found.getPassword())) {
            // 2. Se sono OK --> Genero il token
            String accessToken = jwt.createToken(found);
            // 3. Ritorno il token
            return accessToken;
        } else {
            // 4. Se le credenziali sono errate --> 401 (Unauthorized)
            throw new UnauthorizedException("Credenziali errate!");
        }
    }

}
