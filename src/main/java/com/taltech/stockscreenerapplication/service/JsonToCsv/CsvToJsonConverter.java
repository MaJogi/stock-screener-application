package com.taltech.stockscreenerapplication.service.JsonToCsv;

import org.springframework.stereotype.Service;


@Service
public class CsvToJsonConverter {
    /*
    public static void doSomething() throws IOException {
        CsvSchema orderLineSchema = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<OrderLine> orderLines = csvMapper.readerFor(OrderLine.class)
                .with(orderLineSchema)
                .readValues(new File("src/main/resources/json/orderLines.csv"));

        new ObjectMapper()
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .writeValue(new File("src/main/resources/json/orderLinesFromCsv.json"), orderLines.readAll());

    }

    public static void main(String[] args) throws Exception {
        doSomething();
    }

     */
}
