package toy.arhrina.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import toy.arhrina.board.repository.BoardRepository;

@Controller(value = "/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @ResponseBody
    @GetMapping("/responsebodyPage")
    public String testPage() {
        return "TEST";
    }

    @GetMapping(value = "/boardList")
    public String viewList() {
        return "board_list";
    }

    @ResponseBody
    @PostMapping(value = "/savePost")
    public String savePost() {
        return "success";
    }
}
