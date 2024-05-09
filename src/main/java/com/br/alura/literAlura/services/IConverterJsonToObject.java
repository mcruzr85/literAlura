package com.br.alura.literAlura.services;


import java.util.List;

public interface IConverterJsonToObject{
    <T> T converterDados(String json, Class<T> classe);
    <T> List<T> obterLista (String json, Class<T> classe);
}


//interfaz que recebe um json e lo transforma a um objeto,neste caso é generico,pode ser
// de qualquer classe