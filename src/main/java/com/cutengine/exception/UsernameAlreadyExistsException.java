package com.cutengine.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "Uzytkownik o nicku %s juz istnieje";

    public UsernameAlreadyExistsException(String username) {
        super(MESSAGE.formatted(username));
    }
}
