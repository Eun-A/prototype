package ac.kr.jejunu.web;

import ac.kr.jejunu.common.entity.User;
import ac.kr.jejunu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Boobby on 17-5-22.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public String register(ModelMap modelMap) {
        User user = new User();
        modelMap.addAttribute("user", user);
        return "register";
    }

    @RequestMapping(value = "/registerProcessing", method = RequestMethod.POST)
    public String registerProcessing(@ModelAttribute("user") User user) {
        try {
            userService.register(user);
        } catch (DataIntegrityViolationException e) {
            return "redirect:/register?error";
        }

        return "redirect:/login?registerSuccessful";
    }

    @RequestMapping(path = "/mypage", produces = "text/html")
    public String mypage(@AuthenticationPrincipal(expression = "user") User user, ModelMap modelMap) {
        modelMap.addAttribute("login_user", user);
        return "mypage";
    }
}
