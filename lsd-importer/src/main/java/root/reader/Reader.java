package root.reader;

import com.github.miachm.sods.Range;
import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;
import root.entity.LsdExchangeEntity;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Reader {
    private static final String INGESAMT = "insgesamt";
    private static final String AND_MORE = " und mehr";
    private SpreadSheet spreadSheet;
    private Sheet sheet;
    private Range dataRange;
    private Object[][] values;

    public Reader(File file) {
        try {
            spreadSheet = new SpreadSheet(file);
            sheet = spreadSheet.getSheet(0);
            dataRange = sheet.getDataRange();
            values = getValidDataRows();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LsdExchangeEntity read(int i) {
        return LsdExchangeEntity.builder().
                code(extractCellContent(i, 0))
                .description(extractCellContent(i, 1))
                .employeeFrom(extractEmployeeFrom(i, 3))
                .employerTo(extractEmployeeTo(i, 3))
                .level(extractCellContentAsLong(i, 2))
                .corporation(extractCellContentAsLong(i, 4))
                .employee(extractCellContentAsLong(i, 5))
                .employeeDependent(extractCellContentAsLong(i, 6))
                .staffCost(extractCellContentAsLong(i, 7))
                .revenue(extractCellContentAsLong(i, 8))
                .sales(extractCellContentAsLong(i, 9))
                .addedValue(extractCellContentAsLong(i, 10))
                .buys(extractCellContentAsLong(i, 11))
                .buysResale(extractCellContentAsLong(i, 12))
                .outputValue(extractCellContentAsLong(i, 13))
                .operatingSurplus(extractCellContentAsLong(i, 14))
                .investment(extractCellContentAsLong(i, 15))
                .build();
    }

    private Long extractEmployeeFrom(int row, int column) {
        String cellContent = extractCellContent(row, column);
        if (INGESAMT.equals(cellContent)) {
            return null;
        }
        if (cellContent.contains(AND_MORE)) {
            return Long.parseLong(cellContent.replace(AND_MORE, "").trim());
        }
        String[] parts = cellContent.split("-");
        if (parts.length > 0) {
            return Long.parseLong(parts[0].trim());
        }
        return null;
    }

    private Long extractEmployeeTo(int row, int column) {
        String cellContent = extractCellContent(row, column);
        if (INGESAMT.equals(cellContent) || cellContent.contains(AND_MORE)) {
            return null;
        }
        String[] parts = cellContent.split("-");
        if (parts.length > 1) {
            return Long.parseLong(parts[1].trim());
        }

        return null;
    }

    private Long extractCellContentAsLong(int row, int index) {
        String temp = extractCellContent(row, index);
        if (temp == null || "G".equals(temp) || "".equals(temp)) {
            return null;
        }
        return Long.parseLong(temp.trim());
    }

    private String extractCellContent(int row, int column) {
        Object o = values[row][column];
        if (o instanceof Number) {
            return String.format("%.0f", o);
        } else if (o instanceof String) {
            return o.toString().trim();
        }
        return o.toString();
    }

    private boolean cellIsFilled(Object cell) {
        if (cell == null) {
            return false;
        }
        return !cell.toString().trim().isEmpty();
    }

    private Object[][] getValidDataRows() {
        Object[][] values = dataRange.getValues();

        int columns = values[0].length;
        int rows;
        List<Object[]> validValuesList = new ArrayList<>();

        for (int i = 2; i < values.length - 1; i++) {
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

    private Object[][] getValidDataRow() {
        Object[][] values = dataRange.getValues();

        int columns = values[0].length;
        int rows = dataRange.getNumRows() - 3;
        Object[][] validValues = new Object[rows][columns];

        for (int i = 2; i < values.length - 1; i++) {
            for (int j = 0; j < values[i].length; j++) {
                validValues[i - 2][j] = values[i][j];
            }
        }
        return validValues;
    }
}
