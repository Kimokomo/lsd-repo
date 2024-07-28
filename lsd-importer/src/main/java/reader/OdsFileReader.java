package reader;

import com.github.miachm.sods.Range;
import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;
import entity.LsdExchangeEntity;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OdsFileReader {

    private static final int START_ROW = 2;
    private static final int FILENAME_YEAR_START_INDEX = 84;
    private static final int FILENAME_YEAR_END_INDEX = 88;
    private static final String INSGESAMT = "insgesamt";
    private static final String AND_MORE = "mehr";
    private final SpreadSheet spreadSheet;
    private final Sheet sheet;
    private final Range dataRange;
    private final Object[][] values;
    private final String fileName;
    private final Long year;

    public OdsFileReader(File file) {
        try {
            spreadSheet = new SpreadSheet(file);
            sheet = spreadSheet.getSheet(0);
            fileName = file.getName();
            dataRange = sheet.getDataRange();
            values = getValidDataRows();
            year = extractYearFromFilename();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<LsdExchangeEntity> readAll() {
        return Arrays.stream(values)
                .map(row -> LsdExchangeEntity.builder()
                        .code(extractCellContentAsString(row, 0))
                        .description(extractCellContentAsString(row, 1))
                        .employeeFrom(extractCellContentAsLong(row, 3, true, true))
                        .employerTo(extractCellContentAsLong(row, 3, true, false))
                        .level(extractCellContentAsLong(row, 2, false, false))
                        .corporation(extractCellContentAsLong(row, 4, false, false))
                        .employee(extractCellContentAsLong(row, 5, false, false))
                        .employeeDependent(extractCellContentAsLong(row, 6, false, false))
                        .staffCost(extractCellContentAsLong(row, 7, false, false))
                        .revenue(extractCellContentAsLong(row, 8, false, false))
                        .sales(extractCellContentAsLong(row, 9, false, false))
                        .addedValue(extractCellContentAsLong(row, 10, false, false))
                        .buys(extractCellContentAsLong(row, 11, false, false))
                        .buysResale(extractCellContentAsLong(row, 12, false, false))
                        .outputValue(extractCellContentAsLong(row, 13, false, false))
                        .operatingSurplus(extractCellContentAsLong(row, 14, false, false))
                        .investment(extractCellContentAsLong(row, 15, false, false))
                        .year(year)
                        .build())
                .collect(Collectors.toList());
    }


    public Long extractCellContentAsLong(Object[] row, int column, boolean isEmployeeFromTo, boolean isFrom) {
        String cellContent = extractCellContentAsString(row, column);
        if (cellContent == null || "G".equals(cellContent) || cellContent.isEmpty() || INSGESAMT.equals(cellContent)) {
            return null;
        }
        if (isEmployeeFromTo) {
            String[] parts = cellContent.split(" ");

            if (isFrom) {
                return Long.parseLong(parts[0].trim());
            }

            if (parts[2].contains(AND_MORE)) {
                return null;
            } else {
                return Long.parseLong(parts[2].trim());
            }
        }
        return Long.parseLong(cellContent.trim());
    }

    public String extractCellContentAsString(Object[] row, int column) {
        Object o = row[column];
        if (o instanceof Number) {
            return String.format("%.0f", o);
        } else if (o instanceof String) {
            return o.toString().trim();
        }
        return o.toString();
    }
    private Long extractYearFromFilename() {
        return Long.parseLong(fileName.substring(FILENAME_YEAR_START_INDEX, FILENAME_YEAR_END_INDEX));
    }

    private boolean cellIsFilled(Object cell) {
        if (cell == null) {
            return false;
        }
        return !cell.toString().trim().isEmpty();
    }

    public Object[][] getValidDataRows() {
        Object[][] values = dataRange.getValues();

        int columns = values[0].length;
        int rows;
        List<Object[]> validValuesList = new ArrayList<>();

        for (int i = START_ROW; i < values.length - 1; i++) {
            if (cellIsFilled(values[i][0])) {
                Object[] row = new Object[columns];
                for (int j = 0; j < values[i].length; j++) {
                    row[j] = values[i][j];
                }
                validValuesList.add(row);
            }
        }

        rows = validValuesList.size();
        Object[][] validValues = new Object[rows][columns];
        for (int i = 0; i < validValuesList.size(); i++) {
            validValues[i] = validValuesList.get(i);
        }

        return validValues;
    }
}
