package toy.arhrina.board.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import toy.arhrina.board.repository.BoardRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Profile("test")
@SpringBootTest
@Transactional
class AuditingTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    void auditTest() {
        // given
        LocalDateTime localDateTime;
        BoardEntity boardEntity = new BoardEntity("t", "test");
        // when
        boardRepository.save(boardEntity);
        localDateTime = boardEntity.getCreateTime();
        // then
        Assertions.assertEquals(boardEntity.getCreateTime(), localDateTime);
    }
}