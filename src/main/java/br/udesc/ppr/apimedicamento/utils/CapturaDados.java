package br.udesc.ppr.apimedicamento.utils;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import net.minidev.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CapturaDados {

    private static final String URL_MEDICAMENTOS = "https://dados.anvisa.gov.br/dados/DADOS_ABERTOS_MEDICAMENTOS.csv";
    private static final String URL_PRECO_MEDICAMENTOS = "https://dados.anvisa.gov.br/dados/TA_PRECO_MEDICAMENTO.csv";

    private static List<String[]> readAll(Reader reader, char separator) {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(separator)
                .withQuoteChar('"')
                .withErrorLocale(Locale.getDefault())
                .withIgnoreQuotations(false)
                .build();

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(0)
                .withCSVParser(parser).withVerifyReader(false)
                .build();

        List<String[]> list = new ArrayList<>();
        try {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                list.add(line);
            }
            reader.close();
            csvReader.close();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Método para baixar e ler planilha 1
     * @param update
     * @return
     */
    public static List<JSONObject> readMainFile(boolean update) {
        Reader reader = null;
        if (update)
            downloadData(1);

        try {
            Path path = Paths.get("data_1.csv");
            reader = Files.newBufferedReader(path,StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<JSONObject> lista = new ArrayList<>();
        Map<String,Integer> mapper = new HashMap<>();

        List<String[]> lines = readAll(reader,';');
        String[] header = lines.get(0);
        for (int i = 0; i< header.length ; i++){
            for (MedicamentoColumnMapper mapperValue : MedicamentoColumnMapper.values()){
                if( header[i].equalsIgnoreCase(mapperValue.getNomeColuna()))
                    mapper.put(mapperValue.getNomeChave(),i);
            }
        }
        for (int i = 1; i < lines.size(); i++) {
            JSONObject produto = new JSONObject();
            for (MedicamentoColumnMapper mapperValue : MedicamentoColumnMapper.values()){
                produto.put(mapperValue.getNomeChave(),lines.get(i)[mapper.get(mapperValue.getNomeChave())]);
            }
            lista.add(produto);
        }
        return lista;
    }


    /**
     * Método para baixar e ler planilha 2 - usada para popular o banco
     * @param update
     * @return
     */
    public static List<JSONObject> readSecondaryFile(Boolean update) {
        Reader reader = null;

        if (update)
            downloadData(2);

        try {
            Path path = Paths.get("data_2.csv");
            reader = Files.newBufferedReader(path,StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<JSONObject> lista = new ArrayList<>();
        Map<String,Integer> mapper = new HashMap<>();

        List<String[]> lines = readAll(reader,',');

        String[] header = lines.get(0);
        for (int i = 0; i< header.length ; i++){
            for (PrecoMedicamentoColumnMapper mapperValue : PrecoMedicamentoColumnMapper.values()){
                if( header[i].equalsIgnoreCase(mapperValue.getNomeColuna()))
                    mapper.put(mapperValue.getNomeChave(),i);
            }
        }
        for (int i = 1; i < lines.size(); i++) {

            JSONObject produto = new JSONObject();
            for (PrecoMedicamentoColumnMapper mapperValue : PrecoMedicamentoColumnMapper.values()){
                produto.put(mapperValue.getNomeChave(),lines.get(i)[mapper.get(mapperValue.getNomeChave())]);
            }
            lista.add(produto);
        }
        return lista;
    }


    /**
     * Método
     * @param mode
     */
    private static void downloadData(Integer mode){
        String url = URL_MEDICAMENTOS;
        if (mode == 2)
            url = URL_PRECO_MEDICAMENTOS;

        List<String> header = new ArrayList<>();
        header.add("UTF-8");

        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(url).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream("data_"+mode+".csv");
            FileChannel fileChannel = fileOutputStream.getChannel();
            fileOutputStream.getChannel()
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            fileOutputStream.close();
            if (response.statusCode()!=200)
                return;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
