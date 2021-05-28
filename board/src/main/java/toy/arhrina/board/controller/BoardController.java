package toy.arhrina.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import toy.arhrina.board.service.BoardService;

@RequestMapping(value = "/board")
@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @ResponseBody
    @GetMapping("/responsebodyPage")
    public String testPage() {
        return "TEST";
    }

    @GetMapping(value = "/list")
    public String viewList(Model model) {
        model.addAttribute("boardText", "무슨 게시판일까요?");
        model.addAttribute("list", boardService.getAllList());
        return "/board/list";
    }

    @GetMapping(value = "/write")
    public String write() {
        return "/board/write";
    }

    @PostMapping(value = "/savePost")
    public String savePost() {
        return "success";
    }

    @PostMapping(value = "/modifyPost")
    public String modifyPost() {
        return "success";
    }
}
