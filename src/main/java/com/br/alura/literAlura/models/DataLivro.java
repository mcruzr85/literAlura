package com.br.alura.literAlura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataLivro(Integer id,
                        @JsonAlias("title") String titulo,
                        @JsonAlias("authors") List<DataAutor> autores,
                        @JsonAlias("languages") String[] idiomas,
                        @JsonAlias("download_count") Integer downloads,
                        @JsonAlias("formats") DataFormatos formatos)

{
}
