package de.stecknitz.backend.core.service.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorConstants {

    public static final String USER_NOT_FOUND = "User could not be found.";

    public static final String DEPOT_ALREADY_EXISTS = "Depot does already exist.";

    public static final String USER_ALREADY_EXISTS = "User already exists.";

}
