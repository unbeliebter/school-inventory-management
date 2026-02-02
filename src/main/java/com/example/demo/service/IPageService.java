package com.example.demo.service;

import java.io.PrintWriter;
import java.util.List;

public interface IPageService<T> {
    List<T> getAll();
    T create(T entity);;
    T getById(String id);
    void deleteById(String id);
    void writeToCsv(List<T> entities, PrintWriter writer);
}

