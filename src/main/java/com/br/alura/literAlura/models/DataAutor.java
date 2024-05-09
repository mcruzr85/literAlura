package com.br.alura.literAlura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataAutor(@JsonAlias( "name" ) String nome,
                  @JsonAlias("birth_year") Integer anoNac,
                  @JsonAlias("death_year") Integer anoMor){

}


