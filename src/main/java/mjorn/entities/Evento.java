package mjorn.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "eventi")
public class Evento {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data")
    private String data;

    @Column(name = "luogo")
    private String luogo;

    @Column(name = "testo")
    private String testo;

    @Column(name = "img")
    private String img;
    //TODO numero posti da implementare
    //@Column(name="numeroposti")
    //private int numeroposti;

    @Column(name = "data_inserimento")
    @CreationTimestamp
    private LocalDate dataInserimento;

    public Evento(String nome, String data, String luogo, String testo, String img, LocalDate dataInserimento) {
        this.nome = nome;
        this.data = data;
        this.luogo = luogo;
        this.testo = testo;
        this.img = img;
        this.dataInserimento = dataInserimento;
    }
}

