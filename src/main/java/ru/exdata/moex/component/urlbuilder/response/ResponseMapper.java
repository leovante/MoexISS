package ru.exdata.moex.component.urlbuilder.response;

public interface ResponseMapper<T> {

    T map(String content);

}
