package mjorn.controllers;

import mjorn.entities.User;
import mjorn.exceptions.BadRequestException;
import mjorn.payloadDTO.NewUserDTO;
import mjorn.payloadDTO.UserLoginDTO;
import mjorn.payloadDTO.UserLoginResponseDTO;
import mjorn.servicies.AuthService;
import mjorn.servicies.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

//http://localhost:3005/auth
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UsersService usersService;

    //http://localhost:3005/auth/login
    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO body) {
        return new UserLoginResponseDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    //http://localhost:3005/auth/register
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody @Validated NewUserDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        return this.usersService.save(body);
    }
}
