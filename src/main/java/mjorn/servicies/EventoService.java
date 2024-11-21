package mjorn.servicies;

import mjorn.entities.Evento;
import mjorn.entities.User;
import mjorn.exceptions.NotFoundException;
import mjorn.payloadDTO.EventoDTO;
import mjorn.repositories.EventoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    @Autowired
    private EventoRepo eventoRepo;


    public List<Evento> findAllEventi() {
        return this.eventoRepo.findAll();
    }

    public Evento saveEvento(EventoDTO body, User user) {
        Evento toSave = new Evento();
        toSave.setDataEvento(body.data());
        toSave.setImg(body.img());
        toSave.setLuogo(body.luogo());
        toSave.setNome(body.nome());
        toSave.setTesto(body.testo());
        eventoRepo.save(toSave);
        return toSave;
    }

    public void findByIdAndDelete(Long eventoId) {
        Evento toDelete = eventoRepo.findById(eventoId)
                .orElseThrow(() -> new NotFoundException(eventoId));
        eventoRepo.delete(toDelete);
    }
}
