package toy.arhrina.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.arhrina.board.domain.BoardVO;
import toy.arhrina.board.domain.entity.BoardEntity;
import toy.arhrina.board.repository.BoardRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional // 해당 클래스 내에서 벌어지는 transaction 단위로 dirty checking 수행하도록
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    public List<BoardVO> getAllList() {
        return boardRepository.findAll().stream().map(l -> new BoardVO(l)).collect(Collectors.toList());
    }

    public int save(String sub, String mem) {
        BoardEntity boardEntity = new BoardEntity(sub, mem);
        boardRepository.save(boardEntity);
        if(boardEntity.getId().toString() != null) {
            return 1;
        }
        else
            return 0;
    }

    public BoardVO viewById(String id) {
        BoardEntity boardEntity = boardRepository.findById(Long.valueOf(id)).orElseThrow(IllegalStateException::new);
//        boardEntity.setViews(boardEntity.getViews() + 1);
        boardEntity.addViews();
        BoardVO boardVO = new BoardVO(boardEntity.getId().toString(), boardEntity.getSubject(), boardEntity.getMemo(), boardEntity.getCreateTime().toString(), boardEntity.getCreateBy(), boardEntity.getViews());
        return boardVO;
    }

    public int deletePost(String id) {
        boardRepository.deleteById(Long.valueOf(id));
        if(boardRepository.findById(Long.valueOf(id)).isEmpty()) {
            return 1;
        } else {
            return 0;
        }
    }

    public BoardVO findById(String id) {
        BoardEntity boardEntity = boardRepository.findById(Long.valueOf(id)).orElseThrow(IllegalStateException::new);
        BoardVO boardVO = new BoardVO(boardEntity);
        return boardVO;
    }
}
