package mjorn.exceptions;

import java.time.LocalDateTime;

public record ErrorsResponseDTO(String message, LocalDateTime timestamp) {
}

