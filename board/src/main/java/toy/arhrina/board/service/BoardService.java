package toy.arhrina.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toy.arhrina.board.domain.BoardVO;
import toy.arhrina.board.domain.entity.BoardEntity;
import toy.arhrina.board.repository.BoardRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    public List<BoardVO> getAllList() {
        return boardRepository.findAll().stream().map(l -> new BoardVO(l)).collect(Collectors.toList());
    }
}
