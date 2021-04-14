package com.taltech.stockscreenerapplication.service.JsonToCsv;

import org.springframework.stereotype.Service;

@Service
public class JsonToCsvConverter {
    /*
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvProgram.class);

    public static void doSomething() throws IOException {
        JsonNode jsonTree = new ObjectMapper().readTree(new File("src/main/resources/json/orderLines.json"));

        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
        JsonNode firstObject = jsonTree.elements().next();
        firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);});
        CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

        CsvMapper csvMapper = new CsvMapper();
        csvMapper.writerFor(JsonNode.class)
                .with(csvSchema)
                .writeValue(new File("src/main/resources/json/orderLines.csv"), jsonTree);

    }

    public static void main(String[] args) throws Exception {
        doSomething();

    }

     */
}
