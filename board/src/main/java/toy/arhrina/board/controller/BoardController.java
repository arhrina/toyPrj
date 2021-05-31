package toy.arhrina.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toy.arhrina.board.domain.BoardVO;
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
    public String savePost(@RequestParam(required = false, name = "subject") String sub, @RequestParam(required = false, name = "memo") String mem) {
        int i = boardService.save(sub, mem);
        if(i > 0) {
            return "redirect:/board/list";
        }
        else {
            return "redirect:/board/error";
        }
    }

    @GetMapping(value = "/view")
    public String view(@RequestParam(required = true, name = "id") String id, Model model) throws Exception {
        BoardVO boardVO = boardService.viewById(id);
        model.addAttribute("data", boardVO);
        return "/board/view";
    }

    @GetMapping(value = "/delete")
    public String delPost(@RequestParam(required = true, name = "id") String id) {
        int i = boardService.deletePost(id);
        if(i == 1) {
            return "redirect:/board/list";
        }
        else {
            return "/error/500";
        }
    }

    @GetMapping(value = "/modify")
    public String modifyPost(@RequestParam(required = true, name = "id") String id, Model model) {
        BoardVO boardVO = boardService.findById(id);
        model.addAttribute("data", boardVO);
        return "/board/write";
    }
}
