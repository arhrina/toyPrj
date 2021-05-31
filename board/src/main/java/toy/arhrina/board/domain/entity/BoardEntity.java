package toy.arhrina.board.domain.entity;

import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity(name = "BOARD")
@Getter // getter 꼭 필요함
public class BoardEntity extends AuditEntity { // 상속받아 사용하면 audit 완성

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String subject;
    private String memo;

    @ColumnDefault("0")
    private int views;

    public BoardEntity(String subject, String memo) {
        super();
        this.subject = subject;
        this.memo = memo;
    }

    public BoardEntity() {

    }

    public int addViews() {
        this.views++;
        return this.views;
    }
}
