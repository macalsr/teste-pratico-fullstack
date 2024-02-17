package com.simplesdental.contatosapi.model;

public enum CargoEnum {
    DESENVOLVEDOR,
    DESIGNER,
    SUPORTE,
    TESTER;

    public static CargoEnum fromString(String cargo){
        if(cargo != null){
            for(CargoEnum c : CargoEnum.values()){
                if(cargo.equalsIgnoreCase(c.name())){
                    return c;
                }
            }
        }
        throw new IllegalArgumentException("Cargo inválido: " + cargo);
    }
}
