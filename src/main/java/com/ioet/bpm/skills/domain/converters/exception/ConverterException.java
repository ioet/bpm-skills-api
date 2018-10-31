package com.ioet.bpm.skills.domain.converters.exception;

public class ConverterException extends RuntimeException {

    public ConverterException(Object objectFailingConversion, Throwable cause) {
        super(ConverterException.buildErrorMessage(objectFailingConversion),cause);
    }

    private static String buildErrorMessage(Object objectFailingConversion) {
        StringBuilder errorMessage = new StringBuilder("There was an error converting ");
        errorMessage.append(objectFailingConversion.getClass())
                .append(":").append(objectFailingConversion.toString());
        return errorMessage.toString();
    }
}
