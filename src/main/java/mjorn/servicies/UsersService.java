package mjorn.servicies;

import mjorn.entities.User;
import mjorn.exceptions.BadRequestException;
import mjorn.exceptions.NotFoundException;
import mjorn.payloadDTO.NewUserDTO;
import mjorn.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    public User save(NewUserDTO body) {
        // 1. Verifico che l'email non sia già in uso
        this.usersRepository.findByEmail(body.email()).ifPresent(
                user -> {
                    throw new BadRequestException("Email " + body.email() + " già in uso!");
                }
        );
        User newUser = new User(body.name(), body.surname(), body.email(), bcrypt.encode(body.password()));
        User savedUser = this.usersRepository.save(newUser);

        return savedUser;
    }

    public Page<User> findAll(int page, int size, String sortBy) {
        if (size > 20)
            size = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.usersRepository.findAll(pageable);
    }

    public User findById(Long userId) {
        return this.usersRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public User findByIdAndUpdate(Long userId, NewUserDTO body) {
        // 1. Cerco l'utente nel db
        User found = this.findById(userId);

        // 2. Controllo se l'email nuova è già in uso
        if (!found.getEmail().equals(body.email())) {
            this.usersRepository.findByEmail(body.email()).ifPresent(
                    // 1.1 Se trovo uno user con quell'indirizzo triggera un errore
                    user -> {
                        throw new BadRequestException("Email " + body.email() + " già in uso!");
                    }
            );
        }

        // 3. Modifico l'utente trovato nel db
        found.setName(body.name());
        found.setSurname(body.surname());
        found.setEmail(body.email());
        found.setPassword(body.password());

        // 4. Risalvo l'utente
        return this.usersRepository.save(found);
    }

    public void findByIdAndDelete(Long userId) {
        User found = this.findById(userId);
        this.usersRepository.delete(found);
    }

    public User findByEmail(String email) {
        return this.usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con email " + email + " non è stato trovato"));
    }


}
