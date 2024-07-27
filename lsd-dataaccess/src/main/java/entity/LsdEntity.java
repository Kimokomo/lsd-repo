package entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lsd_entity", schema = "lsd")
@Entity
public class LsdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private LocalDateTime tstamp;


}
