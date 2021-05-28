package toy.arhrina.board.domain;

import lombok.*;
import toy.arhrina.board.domain.entity.BoardEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardVO {
    private String subject;
    private String memo;
    private String createdDate;

    public BoardVO(BoardEntity bEntity) {
        this.subject = bEntity.getSubject();
        this.memo = bEntity.getMemo();
    }
//    private String createdUser;
}
