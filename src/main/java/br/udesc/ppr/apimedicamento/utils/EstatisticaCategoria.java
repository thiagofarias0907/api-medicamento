package br.udesc.ppr.apimedicamento.utils;

import java.util.*;

public class EstatisticaCategoria {

    private static Float getFrequencia(String target, List<String> data){
        float freq = 0f;
        for (String value : data){
            if (value.equalsIgnoreCase(target))
                freq++;
        }
        return freq;
    }

    private static Float getFrequenciaPalavras(String target, List<String> data){
        float freq = 0f;
        for (String token : tokenizer(data)){
            if (token.equalsIgnoreCase(target))
                freq++;
        }

        return freq;
    }

    private static Set<String> tokenizer(List<String> data){
        Set<String> tokensSet = new HashSet<>();
        for (String value : data) {
            String[] tokens = value.split(" ");
            for (String token : tokens) {
                tokensSet.add(token);
            }
        }
        return tokensSet;
    }

    public static Map<String, Float> getEstatisticas(List<String>  categoryValues){
        Map<String, Float> statistics = new HashMap<>();
        for (String value : categoryValues ){
            if (statistics.containsKey(value))
                continue;
            statistics.put(value,getFrequencia(value, categoryValues));
        }

        return statistics;
    }
    public static Map<String, Float> getEstatisticasPalavras(List<String>  categoryValues){
        Map<String, Float> statistics = new HashMap<>();
        Set<String> allWordsSet = tokenizer(categoryValues);
        for (String value : allWordsSet ){
            statistics.put(value,getFrequenciaPalavras(value, (List<String>) allWordsSet));
        }
        return statistics;
    }
}
