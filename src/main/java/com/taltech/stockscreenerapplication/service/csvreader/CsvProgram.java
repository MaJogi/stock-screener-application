package com.taltech.stockscreenerapplication.service.csvreader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CsvProgram {

    // static CsvReader reader = new CsvReader();

//    public static void main(String[] args) throws Exception {
//////        String result = reader.oneByOneExample();
////////        System.out.println(result);
////
////        String fileName = "src/main/resources/numbers.csv";
////        Path myPath = Paths.get(fileName);
////
////        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
////
////        try (Reader br = Files.newBufferedReader(myPath,  StandardCharsets.UTF_8);
////             CSVReader reader = new CSVReaderBuilder(br).withCSVParser(parser)
////                     .build()) {
////
////            List<String[]> rows = reader.readAll();
////
////            for (String[] row : rows) {
////
////                for (String e : row) {
////
////                    System.out.format("%s ", e);
////                }
////
////                System.out.println();
////            }
////        }
////    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvProgram.class);

    public static void main(String[] args) throws Exception {
        CsvReaderAndProcessImpl readerImpl = new CsvReaderAndProcessImpl();
        List<List<List<String>>> result = readerImpl.createReaderAndUseReadingMethod(
                "tkm-2017_q2_CSV_modified_by_frontend.csv");
        LOGGER.info("{}", result.get(0));

        LOGGER.info("--------------START OF INCOME STATEMENT---------------");
        for (List<String> row : result.get(0)) {
            System.out.println(row);
        }
        LOGGER.info("--------------END OF INCOME STATEMENT---------------");

        LOGGER.info("--------------START OF CASHFLOW STATEMENT---------------");
        for (List<String> row : result.get(1)) {
            System.out.println(row);
        }
        LOGGER.info("--------------END OF CASHFLOW STATEMENT---------------");

        LOGGER.info("--------------START OF BALANCE STATEMENT---------------");
        for (List<String> row : result.get(2)) {
            System.out.println(row);
        }
        LOGGER.info("--------------END OF BALANCE STATEMENT---------------");
    }
}
