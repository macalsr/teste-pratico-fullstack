package com.simplesdental.contatosapi.model;

/**
 * Enumeração representando os possíveis cargos de profissionais.
 */
public enum CargoEnum {
    DESENVOLVEDOR,
    DESIGNER,
    SUPORTE,
    TESTER;

    /**
     * Converte uma string em um valor do enum CargoEnum.
     *
     * @param cargo A string representando o cargo.
     * @return O valor do enum correspondente à string.
     * @throws IllegalArgumentException Se a string não corresponder a nenhum valor do enum.
     */
    public static CargoEnum fromString(String cargo) {
        if (cargo != null) {
            for (CargoEnum c : CargoEnum.values()) {
                if (cargo.equalsIgnoreCase(c.name())) {
                    return c;
                }
            }
        }
        throw new IllegalArgumentException("Cargo inválido: " + cargo);
    }
}
