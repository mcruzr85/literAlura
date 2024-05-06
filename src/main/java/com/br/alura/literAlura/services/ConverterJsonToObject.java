package com.br.alura.literAlura.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
}
