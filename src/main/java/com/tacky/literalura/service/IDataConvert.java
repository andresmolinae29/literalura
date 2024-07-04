package com.tacky.literalura.service;

public interface IDataConvert {
    <T> T convert(String json, Class<T> clazz);
}
