package com.petproject.security;

import com.petproject.logger.C2FLogger;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.ZoneOffset.UTC;

@Getter
@EqualsAndHashCode
@ToString
public final class DecodedToken {

    private final C2FLogger logger = new C2FLogger();
    private final Integer userId;
    private final List<String> permissions;
    private final Boolean active;
    private final LocalDateTime expired;

    public DecodedToken(final JWTConsumer jwtConsumer) {
        if (jwtConsumer.validate()) {
            expired = LocalDateTime.ofInstant(Instant.ofEpochSecond(jwtConsumer.getFieldAsLong("exp")), UTC);
            jwtConsumer.setFieldAsCurrentObject("data");
            userId = jwtConsumer.getFieldAsInt("id");
            permissions = new ArrayList<>();
            active = true;

//        Not implemented in token yet
//        permissions = asList(jwtConsumer.getFieldAsArrayOfString("lastUsedPermissions"));
//        active = jwtConsumer.getFieldAsInt("userIsActive") == 1;
        } else {
            final String invalidReason = jwtConsumer.getInvalidReason();
            logger.error(null, C2FLogger.LogType.SYSTEM, () -> invalidReason);
            switch (invalidReason) {
                case "The token is expired":
                    throw new CredentialsExpiredException(invalidReason);
                default:
                    throw new BadCredentialsException(invalidReason);
            }
        }
    }

}
