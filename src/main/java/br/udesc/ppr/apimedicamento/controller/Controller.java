package br.udesc.ppr.apimedicamento.controller;

import net.minidev.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface Controller<T> {
    public List<T> getAll();

    public List<T> insertAll(List<T> list);

    public JSONObject getEstatisticas();
}
