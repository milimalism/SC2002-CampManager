package sc2002.campmanager.camp;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.csv.*;


/**
 * CSV utilities class
 */
public class CsvUtils {
    private static final ClassLoader classLoader = CsvUtils.class.getClassLoader();

    /**
     * Read CSV file
     * @param filePath path to CSV file
     * @return list of CSV records
     * @throws IOException if file not found
     */
    public static List<CSVRecord> readCsv(String filePath) throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            CSVFormat format = CSVFormat.DEFAULT
                    .builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build();
            CSVParser parser = new CSVParser(reader, format);
            return parser.getRecords();
        }
    }

    /**
     * Read CSV file
     * @param filePath path to CSV file 
     * @param data data to be written
     * @param headers headers of CSV file
     * @throws IOException if file not found
     */
    public static void writeCsvWithArrayOfData(String filePath, List<List<String>> data, String[] headers) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT
                .builder()
                .setHeader(headers)
                .build();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
             CSVPrinter printer = new CSVPrinter(writer, format)) {
            for (List<String> record : data) {
                printer.printRecord(record);
            }
        }
    }

    /**
     * write CSV file
     * @param filePath path to CSV file
     * @param data data to be written
     * @param headers headers of CSV file
     * @throws IOException if file not found
     */
    public static void writeCsv(String filePath, List<String> data, String[] headers) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT
                .builder()
                .setHeader(headers)
                .build();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
             CSVPrinter printer = new CSVPrinter(writer, format)) {
            printer.printRecord(data);
        }
    }
}
