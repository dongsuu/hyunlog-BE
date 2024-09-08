package donghyunlee.hyunlog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CATEGORY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;
    private int sortOrder;

    @Builder
    private Category(String name, int order) {
        this.name = name;
        this.sortOrder = order;
    }

    public static Category createCategory(String name, int order){
        return Category.builder()
                .name(name)
                .order(order)
                .build();
    }
}
