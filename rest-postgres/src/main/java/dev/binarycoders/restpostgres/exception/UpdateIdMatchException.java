package dev.binarycoders.restpostgres.exception;

public class UpdateIdMatchException extends RuntimeException {
    public UpdateIdMatchException() {
        super("ID in path and body must be the same");
    }
}
