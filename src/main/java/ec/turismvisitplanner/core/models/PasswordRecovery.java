package ec.turismvisitplanner.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "password_recovery")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRecovery {
    @Id
    private String id;
    @DBRef
    private User user;
    private String recoveryCode;
    private Instant expirationTime;
}
