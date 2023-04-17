package de.stecknitz.backend.core.service.exception;

public class MasterDataException extends RuntimeException {

    public MasterDataException(final String defaultErrorMessage) {
        super(defaultErrorMessage);
    }

}
