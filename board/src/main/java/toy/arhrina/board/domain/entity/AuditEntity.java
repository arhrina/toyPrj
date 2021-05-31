package toy.arhrina.board.domain.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass // 상속시에 field만을 받아갈 수 있게 해준다. 정확하게 entity는 아닌 클래스
public class AuditEntity {
    @CreatedDate // data jpa 사용시
    @Column(updatable = false)
    private LocalDateTime createTime;



    // pure jpa 사용시
//    @PrePersist
//    @PostPersist
//    public void prePersist() {
//        LocalDateTime now = LocalDateTime.now();
//        createTime = now;
//    }

//    @PreUpdate
//    @PostUpdate
//    public void preUpdate() {
//        LocalDateTime now = LocalDateTime.now();
//        updateTime = now;
//    }

    @CreatedBy
    @Column(updatable = false)
    private String createBy;
}
