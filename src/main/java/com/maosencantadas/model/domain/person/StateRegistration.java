package com.maosencantadas.model.domain.person;

import lombok.Getter;

@Getter
public enum StateRegistration {
    ISENTO("Isento"),
    CONTRIBUINTE("Contribuinte"),
    NAO_CONTRIBUINTE("Não Contribuinte");

    private final String description;

    StateRegistration(String description) {
        this.description = description;
    }

}
