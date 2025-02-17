package com.aleffalves.api_simples.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CargoEnum {

    DESENVOLVEDOR("Desenvolvedor"),
    DESIGNER("Designer"),
    SUPORTE("Suporte"),
    TESTER("Tester");

    private final String descricao;

    @JsonValue
    public String getDescricao() {
        return descricao;
    }
}
