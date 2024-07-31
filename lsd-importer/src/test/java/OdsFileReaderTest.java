import entity.LsdEntity;
import entity.LsdExchangeEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import reader.OdsFileReader;
import repo.LsdEntityRepository;
import repo.LsdEntityRepositoryImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class OdsFileReaderTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private LsdEntityRepository repository;
    private OdsFileReader odsFileReader;

    private static final String TEST_FILE_PATH = "src/test/resources/G_UNT2_Statistisches_Unternehmen_Hauptergebnisse_nach_Beschaeftigtengroessenklassen_2022.ods";

    @Before

    public void setUp() throws IOException {
        emf = Persistence.createEntityManagerFactory("myJpaUnit");
        em = emf.createEntityManager();
        File testFile = new File(TEST_FILE_PATH);
        odsFileReader = new OdsFileReader(testFile);
        repository = new LsdEntityRepositoryImpl(em);
    }

    @After
    public void tearDown() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }

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
    public void saveFromFileToDatabase() {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
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

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); // Print the exception for debugging
        }
    }
}
