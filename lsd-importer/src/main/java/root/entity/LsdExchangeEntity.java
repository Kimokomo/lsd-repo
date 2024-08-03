package root.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class LsdExchangeEntity {
    private String code;
    private String description;
    private Long employeeFrom;
    private Long employerTo;
    private Long level;
    private Long corporation;
    private Long employee;
    private Long employeeDependent;
    private Long staffCost;
    private Long revenue;
    private Long sales;
    private Long addedValue;
    private Long buys;
    private Long buysResale;
    private Long outputValue;
    private Long operatingSurplus;
    private Long investment;
    private Long year;
}
