import entity.LsdEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestData {
    public static LsdEntity lsdEntity = LsdEntity.builder()
            .code("AA")
            .description("Test")
            .employeeFrom(465L)
            .employerTo(7842L)
            .level(7L)
            .corporation(99L)
            .employee(77L)
            .employeeDependent(45L)
            .staffCost(44L)
            .revenue(12L)
            .sales(1203584L)
            .addedValue(55L)
            .buys(88L)
            .buysResale(145238L)
            .outputValue(4582L)
            .operatingSurplus(4128L)
            .build();

    public static LsdEntity lsdEntity1 = LsdEntity.builder()
            .code("BB")
            .description("Test1")
            .employeeFrom(46875L)
            .employerTo(782L)
            .level(7L)
            .corporation(99L)
            .employee(787L)
            .employeeDependent(45L)
            .staffCost(44L)
            .revenue(12L)
            .sales(184L)
            .addedValue(55L)
            .buys(88L)
            .buysResale(145238L)
            .outputValue(4582L)
            .operatingSurplus(4128L)
            .build();
}
