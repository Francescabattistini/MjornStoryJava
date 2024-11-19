package mjorn.controllers;

import mjorn.entities.User;
import mjorn.exceptions.BadRequestException;
import mjorn.payloadDTO.NewUserDTO;
import mjorn.servicies.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping

    public Page<User> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String sortBy) {
        return this.usersService.findAll(page, size, sortBy);
    }

    // ************************************************* /ME ENDPOINTS ***********************************************
    // tramite @AuthenticationPrincipal. Grazie a questo Principal quindi
    // possiamo andare ad implementare tutta una serie di endpoint "personali", cioè endpoint per leggere il proprio profilo, cambiare i propri
    // dati oppure anche cancellare se stessi. Inoltre grazie al Principal potremo in futuro anche andare ad effettuare dei controlli, es:
    // endpoint per cancellare un record di cui sono proprietario, devo fare una verifica che il proprietario corrisponda al Principal

    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return currentAuthenticatedUser;
    }

    @PutMapping("/me")
    public User updateProfile(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody @Validated NewUserDTO body) {
        return this.usersService.findByIdAndUpdate(currentAuthenticatedUser.getId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        this.usersService.findByIdAndDelete(currentAuthenticatedUser.getId());
    }

    @GetMapping("/{userId}")
    public User findById(@PathVariable long userId) {
        return this.usersService.findById(userId);
    }

    @PutMapping("/{userId}")

    public User findByIdAndUpdate(@PathVariable long userId, @RequestBody @Validated NewUserDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        return this.usersService.findByIdAndUpdate(userId, body);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable long userId) {
        this.usersService.findByIdAndDelete(userId);
    }

}
