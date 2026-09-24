package com.cutengine.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "Uzytkownik z adresem email %s juz istnieje";

    public EmailAlreadyExistsException(String email) {
        super(MESSAGE.formatted(email));
    }
}
