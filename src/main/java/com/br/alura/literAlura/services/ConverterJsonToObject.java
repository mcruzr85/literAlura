package com.br.alura.literAlura.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public class ConverterJsonToObject implements IConverterJsonToObject{

    private ObjectMapper mapper = new ObjectMapper();
    @Override
    public <T> T converterDados(String json, Class<T> classe) {
        //para pegar os dados do json e mappearlo a la classe que viene como parametro

        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
              throw new RuntimeException(e);

        }

    }

    @Override
    public <T> List<T> obterLista(String json, Class<T> classe) {
        //para construir una lista usando la clase que se le pasa por parametro
        CollectionType lista = mapper.getTypeFactory()
                .constructCollectionType(List.class, classe);
        try {
            return mapper.readValue(json, lista);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //este lo uso cuando el json esta asi: [{}, {}, {}] => un arreglo de objetos
}
