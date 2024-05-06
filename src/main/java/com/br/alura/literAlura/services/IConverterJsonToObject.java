package com.br.alura.literAlura.services;


public interface IConverterJsonToObject{
    <T> T converterDados(String json, Class<T> classe);
}


//interfaz que recebe um json e lo transforma a um objeto,neste caso é generico,pode ser
// de qualquer classe