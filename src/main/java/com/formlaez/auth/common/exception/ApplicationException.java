package com.formlaez.auth.common.exception;

import lombok.Getter;

/**
 * @author khanhspring
 */
@Getter
public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(Throwable throwable) {
        super(throwable);
    }
}
