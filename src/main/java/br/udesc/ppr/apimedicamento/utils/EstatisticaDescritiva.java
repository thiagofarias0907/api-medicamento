package br.udesc.ppr.apimedicamento.utils;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.HashMap;
import java.util.Map;

public class EstatisticaDescritiva  {


    public static Map<String, Float> getEstatisticas(double[] valuesArray){
        DescriptiveStatistics descriptiveStatistics =  new DescriptiveStatistics(valuesArray);
        for (double value : valuesArray)
            descriptiveStatistics.addValue(value);

        Map<String, Float> statistics = new HashMap<>();
        statistics.put("quantidade"     , (float) valuesArray.length);
        statistics.put("media"          , (float) descriptiveStatistics.getMean());
        statistics.put("minimo"         , (float) descriptiveStatistics.getMin());
        statistics.put("maximo"         , (float) descriptiveStatistics.getMax());
        statistics.put("mediana"        , (float) descriptiveStatistics.getPercentile(50));
        statistics.put("primeiroQuartil", (float) descriptiveStatistics.getPercentile(25));
        statistics.put("terceiroQuartil", (float) descriptiveStatistics.getPercentile(75));
        statistics.put("desvioPadrao"   , (float) descriptiveStatistics.getStandardDeviation());


        return statistics;
    }


}
