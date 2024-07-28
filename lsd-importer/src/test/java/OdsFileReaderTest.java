import entity.LsdExchangeEntity;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import reader.OdsFileReader;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class OdsFileReaderTest {

    private OdsFileReader odsFileReader;
    private static final String TEST_FILE_PATH = "src/test/resources/G_UNT2_Statistisches_Unternehmen_Hauptergebnisse_nach_Beschaeftigtengroessenklassen_2022.ods";

    @Before
    public void setUp() throws IOException {
        File testFile = new File(TEST_FILE_PATH);
        odsFileReader = new OdsFileReader(testFile);
    }

    @Test
    public void testExtractYearFromFilename() {
        Long expectedYear = 2022L; // Assuming the test file name includes this year
        assertThat(odsFileReader.getYear()).isEqualTo(expectedYear);
    }

    @Test
    public void testRead() {
        LsdExchangeEntity entity = odsFileReader.read(0); // Read the first valid row

        assertThat(entity).isNotNull()
                .extracting(LsdExchangeEntity::getCode)
                .isEqualTo("B");
    }

    @Test
    public void testExtractCellContentAsString() {
        String cellContent = odsFileReader.extractCellContentAsString(0, 1); // Adjust row and column indices as necessary
        assertThat(cellContent).isEqualTo("Bergbau");
    }

    @Test
    public void testExtractCellContentAsLong() {
        Long cellContent = odsFileReader.extractCellContentAsLong(1, 3, true, true); // Adjust row and column indices as necessary
        assertThat(cellContent).isEqualTo(0);
    }

    @Test
    public void testGetValidDataRows() {
        Object[][] values = odsFileReader.getValidDataRows();
        assertThat(values.length).isEqualTo(679L);
    }

}
