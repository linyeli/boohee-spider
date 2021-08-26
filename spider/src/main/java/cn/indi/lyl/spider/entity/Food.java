package cn.indi.lyl.spider.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
@Data
@Entity
@Table(name="food", indexes = {
        @Index(name="id", columnList = "id", unique = true)
})
@EntityListeners(AuditingEntityListener.class)
public class Food {
    @Id
    @GeneratedValue(generator = "idGeneratorConfig", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(
            name = "idGeneratorConfig",
            strategy = "cn.indi.lyl.spider.utils.IdGeneratorConfig"
    )
    private Long id;
    @Column
    private String category;
    @Column
    private String name;
    @Column
    private String alias;
    @Column
    private String unit;
    @Column
    private String calorie;
    @Column
    private String carbohydrate;
    @Column
    private String fat;
    @Column
    private String protein;
    @Column
    private String cellulose;
}
