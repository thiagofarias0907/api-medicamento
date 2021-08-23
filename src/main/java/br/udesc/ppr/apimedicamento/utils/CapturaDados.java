package br.udesc.ppr.apimedicamento.utils;

import br.udesc.ppr.apimedicamento.entities.Produto;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.FieldMapByName;
import com.opencsv.exceptions.CsvValidationException;
import net.minidev.json.JSONObject;
import org.springframework.web.util.UriBuilder;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
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
            ;

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                System.out.println(line[0]);
                list.add(line);
            }
//            list = csvReader.readAll();
            reader.close();
            csvReader.close();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return list;
    }

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


//        List<Produto> listaProduto = new ArrayList<>();
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
//            String nome = lines.get(i)[mapper.get(MedicamentoColumnMapper.NOME.getNomeChave())];
//            String categoria = lines.get(i)[mapper.get(MedicamentoColumnMapper.CATEGORIA.getNomeChave())];
//            Long registro = Long.parseLong(lines.get(i)[mapper.get(MedicamentoColumnMapper.NUMERO_REGISTRO.getNomeChave())]);
//            listaProduto.add(new Produto(nome,categoria, registro ));

            JSONObject produto = new JSONObject();
            for (MedicamentoColumnMapper mapperValue : MedicamentoColumnMapper.values()){
                produto.put(mapperValue.getNomeChave(),lines.get(i)[mapper.get(mapperValue.getNomeChave())]);
            }
            lista.add(produto);
        }

//        return listaProduto;
        return lista;
    }


    public static List<JSONObject> readSecondaryFile(Boolean update) {
        Reader reader = null;
//        try {
////            URI url = ClassLoader.getSystemResource("src/main/resources/static/DADOS_ABERTOS_MEDICAMENTOS.csv").toURI();
////            Path path = Paths.get("C:/Users/Thiago/Desktop/UDESC/55PPR/api-medicamento/src/main/resources/static/TA_PRECO_MEDICAMENTO.csv");
////            reader = Files.newBufferedReader(path);
////            reader = Files.newBufferedReader(Paths.get(
////                    ClassLoader.getSystemResource("src/main/resources/static/DADOS_ABERTOS_MEDICAMENTOS.csv").toURI()));
////            reader = Files.newBufferedReader(Paths.get("src/main/resources/static/DADOS_ABERTOS_MEDICAMENTOS.csv"));
////        } catch (URISyntaxException e) {
////            e.printStackTrace();
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        if (update)
            downloadData(2);

        try {
            Path path = Paths.get("data_2.csv");
            reader = Files.newBufferedReader(path,StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<JSONObject> lista = new ArrayList<>();
//        List<Produto> listaProduto = new ArrayList<>();
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
//            String nome = lines.get(i)[mapper.get(PrecoMedicamentoColumnMapper.PRODUTO.getNomeChave())];
//            String categoria = lines.get(i)[mapper.get(PrecoMedicamentoColumnMapper.TIPO_DE_PRODUTO.getNomeChave())];
//            String numeroRegistro = lines.get(i)[mapper.get(PrecoMedicamentoColumnMapper.REGISTRO.getNomeChave())];
//            Long registro = 0l;
//            if (!numeroRegistro.matches("\\D+") && !numeroRegistro.isBlank())
//                registro = Long.parseLong(numeroRegistro);
//            listaProduto.add(new Produto(nome,categoria, registro ));

            JSONObject produto = new JSONObject();
            for (PrecoMedicamentoColumnMapper mapperValue : PrecoMedicamentoColumnMapper.values()){
                produto.put(mapperValue.getNomeChave(),lines.get(i)[mapper.get(mapperValue.getNomeChave())]);
            }
            lista.add(produto);
        }
        return lista;
//        return listaProduto;
    }

    public static List<String[]> readTestFile() {
        Reader reader = null;
        try {
//            URI url = ClassLoader.getSystemResource("src/main/resources/static/DADOS_ABERTOS_MEDICAMENTOS.csv").toURI();
            Path path = Paths.get("C:/Users/Thiago/Desktop/UDESC/55PPR/api-medicamento/src/main/resources/static/teste.csv");
            reader = Files.newBufferedReader(path);
//            reader = Files.newBufferedReader(Paths.get(
//                    ClassLoader.getSystemResource("src/main/resources/static/DADOS_ABERTOS_MEDICAMENTOS.csv").toURI()));
//            reader = Files.newBufferedReader(Paths.get("src/main/resources/static/DADOS_ABERTOS_MEDICAMENTOS.csv"));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readAll(reader,',');
    }


    private static void downloadData(Integer mode){
        String url = URL_MEDICAMENTOS;
        if (mode == 2)
            url = URL_PRECO_MEDICAMENTOS;

        List<String> header = new ArrayList<>();
        header.add("UTF-8");

        HttpClient httpClient = HttpClient.newBuilder().build();
//        String url = "https://dados.anvisa.gov.br/dados/TA_PRECO_MEDICAMENTO.csv";
//        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(url)).GET().header("Accept-Encoding", "windows-1252").build();
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


//            System.out.println(response.body());

//            String str = new String(response.body().getBytes( "Cp1252"),StandardCharsets.UTF_8);
//            String str = response.body();
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data_"+mode+".csv")));
//            writer.write(str);
//            writer.close();
//            FileOutputStream outputStream = new FileOutputStream("data_"+mode+".csv");
//            PrintStream stream  = new PrintStream(outputStream, true, StandardCharsets.UTF_8);
//            byte[] strToBytes = str.getBytes();
//            stream.write(strToBytes);
//            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        HttpResponse httpResponse = HttpClient.newBuilder().build().send(httpRequest).body();
    }
//    private static List<Produto> beanBuilderExample(Path path, Class clazz) throws Exception {
//        ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
//        ms.setType(clazz);
//
//        Reader reader = Files.newBufferedReader(path);
//        CsvToBean cb = new CsvToBeanBuilder(reader)
//                .withType(clazz)
//                .withMappingStrategy(ms)
//                .build();
//
//        List result = cb.parse();
//        reader.close();
//        return result;
//    }
}
