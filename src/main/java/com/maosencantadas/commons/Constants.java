package com.maosencantadas.commons;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String BRAZILIAN_DATE = "dd/MM/yyyy HH:mm:ss";
    public static final String FROM_EMAIL = "noreplay@maosencantadas.art.br";
    public static final String SEND_EMAIL = "send-email";
    // Change this to your production URL
    public static final String PERSON_NATURAL = "NATURAL";
    public static final String PERSON_LEGAL = "LEGAL";
}
