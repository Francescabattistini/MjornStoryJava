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
        toSave.setData(body.data());
        toSave.setImg(body.img());
        toSave.setLuogo(body.luogo());
        toSave.setNome(body.nome());
        toSave.setTesto(body.testo());
        toSave.setImg(
                body.img() != null && !body.img().isEmpty()
                        ? body.img()
                        : "https://community.velvetmag.it/wp-content/uploads/2023/12/Giochi-di-ruolo-da-tavolo-community-velvetmag-20231214.jpg"
        );
        eventoRepo.save(toSave);
        return toSave;
    }

    public void findByIdAndDelete(Long eventoId) {
        Evento toDelete = eventoRepo.findById(eventoId)
                .orElseThrow(() -> new NotFoundException(eventoId));
        eventoRepo.delete(toDelete);
    }
}
