import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import root.config.Config;
import root.entity.LsdEntity;
import root.entity.LsdExchangeEntity;
import root.reader.OdsFileReader;
import root.repo.LsdEntityRepository;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class OdsFileReaderTest {

    @Autowired
    private LsdEntityRepository repository;
    @Autowired
    private OdsFileReader odsFileReader;


    @Test
    public void testExtractYearFromFilename() {
        Long expectedYear = 2022L; // Assuming the test file name includes this year
        assertThat(odsFileReader.getYear()).isEqualTo(expectedYear);
    }

    @Test
    public void testReadAll() {
        List<LsdExchangeEntity> entities = odsFileReader.readAll();
        assertThat(entities).isNotEmpty();

        LsdExchangeEntity entity = entities.get(0);

        assertThat(entity).isNotNull()
                .extracting(LsdExchangeEntity::getCode)
                .isEqualTo("B");
    }

    @Test
    public void testExtractCellContentAsString() {
        String cellContent = odsFileReader.extractCellContentAsString(odsFileReader.getValues()[0], 1);
        assertThat(cellContent).isEqualTo("Bergbau");
    }

    @Test
    public void testExtractCellContentAsLong() {
        Long cellContent = odsFileReader.extractCellContentAsLong(odsFileReader.getValues()[1], 3, true, true);
        assertThat(cellContent).isEqualTo(0);
    }

    @Test
    public void testGetValidDataRows() {
        Object[][] values = odsFileReader.getValidDataRows();
        assertThat(values.length).isEqualTo(679L);
    }

    @Test
    @Transactional
    public void saveFromFileToDatabase() {

        List<LsdExchangeEntity> entities = odsFileReader.readAll();

        // Map each row to an LsdEntity and save it to the database
        entities.stream()
                // .limit(1)
                .map(entity -> LsdEntity.builder()
                        .code(entity.getCode())
                        .description(entity.getDescription())
                        .employeeFrom(entity.getEmployeeFrom())
                        .employerTo(entity.getEmployerTo())
                        .level(entity.getLevel())
                        .corporation(entity.getCorporation())
                        .employee(entity.getEmployee())
                        .employeeDependent(entity.getEmployeeDependent())
                        .staffCost(entity.getStaffCost())
                        .revenue(entity.getRevenue())
                        .sales(entity.getSales())
                        .addedValue(entity.getAddedValue())
                        .buys(entity.getBuys())
                        .buysResale(entity.getBuysResale())
                        .outputValue(entity.getOutputValue())
                        .operatingSurplus(entity.getOperatingSurplus())
                        .investment(entity.getInvestment())
                        .year(entity.getYear())
                        .build())
                .forEach(repository::save);
    }
}
