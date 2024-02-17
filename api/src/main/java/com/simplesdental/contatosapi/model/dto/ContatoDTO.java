package com.simplesdental.contatosapi.model.dto;

import com.simplesdental.contatosapi.model.Contato;
import com.simplesdental.contatosapi.model.Profissional;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContatoDTO {

    private Integer id;

    private String nome;

    private String contato;

    private ProfissionalDTO profissional;

    private LocalDateTime createdDate;


    public void filterFields(List<String> fields) {
        Map<String,Object> filteredFields = new HashMap<>();
        for(String field : fields){
            switch (field){
                case "nome":
                    filteredFields.put("nome", this.getNome());
                    break;
                case "contato":
                    filteredFields.put("contato", this.getContato());
                    break;
                case "profissional":
                    if(this.getProfissional() != null){
                        filteredFields.put("profissional", this.getProfissional().getId());
                    }
                    break;
                default:
                    break;
            }
        }
        this.clearFields();
        this.setNome((String) filteredFields.get("nome"));
        this.setContato((String) filteredFields.get("contato"));

        Integer profissionalId = (Integer) filteredFields.get("profissional");
        if(profissionalId != null){
            Profissional profissional = new Profissional();
            profissional.setId(profissionalId);
            this.setProfissional(this.profissional);
        }
    }
    private void clearFields(){
        this.setId(null);
        this.setNome(null);
        this.setContato(null);
        this.setProfissional(null);
    }



    public Contato toModel(){
        return Contato.builder()
                .id(this.id)
                .contato(this.contato)
                .profissional(this.profissional.toModel())
                .nome(this.nome).build();
    }
}
