package services;

import models.Cell;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CSVTools {
    public static List<List<String>> readCSV(File file) throws IOException {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        }
        return records;
    }

    public static void saveCSV(File file, ArrayList<ArrayList<Cell>> data, boolean saveValues) throws IOException {
        if (file.createNewFile()) {
            try (FileWriter writer = new FileWriter(file)) {
                Stream<String> values;
                for (List<Cell> row : data) {
                    if (saveValues) {
                        values = row.stream().skip(1).map(Cell::toString);
                    } else {
                        values = row.stream().skip(1).map(Cell::getExpression);
                    }
                    writer.write(values.collect(Collectors.joining(",", "", ",\n")));
                }
            }
        } else {
            throw new IOException("File already exists");
        }
    }
}
