package com.br.alura.literAlura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataLivro(@JsonAlias("title") String titulo,
                        @JsonAlias("download_count") String downloads,
                        @JsonAlias("bookshelves") String[] temas) {
}
