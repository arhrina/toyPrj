package toy.arhrina.board.domain;

import lombok.*;
import toy.arhrina.board.domain.entity.BoardEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardVO {
    private String id;
    private String subject;
    private String memo;
    private String createdDate;
    private String createdBy;
    private int views;

    public BoardVO(BoardEntity bEntity) {
        this.id = bEntity.getId().toString();
        this.subject = bEntity.getSubject();
        this.memo = bEntity.getMemo();
        this.createdDate = bEntity.getCreateTime().toString();
        this.createdBy = bEntity.getCreateBy();
        this.views = bEntity.getViews();
    }
}
