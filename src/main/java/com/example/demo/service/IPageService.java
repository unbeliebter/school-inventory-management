package com.example.demo.service;

import java.io.PrintWriter;
import java.util.List;

public interface IPageService<T, TR> {
    List<T> getAll();
    T create(T entity);
    T getById(String id);
    void deleteById(String id);
    void writeToCsv(List<T> entities, PrintWriter writer);
    List<T> getFilteredAsList(TR request);
}

