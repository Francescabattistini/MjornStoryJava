package mjorn.payloadDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EventoDTO(

        @NotBlank(message = "Il nome non può essere vuota.")
        @Size(max = 100, message = "La ragione sociale deve avere massimo 100 caratteri.")
        String nome,

        @NotBlank(message = "La data dell'evento non può essere vuota.")
        String data,

        @NotBlank(message = "Il luogo non può essere vuoto.")
        @Size(max = 100, message = "Il luogo deve avere massimo 100 caratteri.")
        String luogo,

        @NotBlank(message = "Il testo non può essere vuota.")
        @Size(max = 100, message = "Il testo deve avere massimo 100 caratteri.")
        String testo,

        String img
) {
}
