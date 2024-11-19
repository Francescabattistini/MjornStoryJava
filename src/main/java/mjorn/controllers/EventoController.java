package mjorn.controllers;

import mjorn.entities.Evento;
import mjorn.exceptions.BadRequestException;
import mjorn.payloadDTO.EventoDTO;
import mjorn.servicies.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/eventi")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    //GET http://localhost:3005/eventi
    @GetMapping
    public List<Evento> findAll() {

        return this.eventoService.findAllEventi();
    }

    //POST http://localhost:3005/eventi
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Evento save(@RequestBody @Validated EventoDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        return this.eventoService.saveEvento(body);
    }

    //DELETE http://localhost:3005/eventi/1
    @DeleteMapping("/{eventoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable Long eventoId) {
        this.eventoService.findByIdAndDelete(eventoId);
    }


}
