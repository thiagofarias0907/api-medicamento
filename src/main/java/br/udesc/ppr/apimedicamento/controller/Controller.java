package br.udesc.ppr.apimedicamento.controller;

import java.util.List;

public interface Controller<T> {
    public List<T> getAll();
}
