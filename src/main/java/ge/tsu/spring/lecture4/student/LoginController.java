package ge.tsu.spring.lecture4.student;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@RestController
public class LoginController {

    @PostMapping("/login")
    void login(@RequestParam String username, @RequestParam String password, HttpServletResponse httpServletResponse){
        String valid_username = "username";
        String valid_password = "password";

        if (valid_username.equals(username) && valid_password.equals(password)) {
            Cookie cookie = new Cookie("signed", "yes");
            httpServletResponse.addCookie(cookie);
        }else{
            Cookie cookie = new Cookie("signed", "no");
            httpServletResponse.addCookie(cookie);
        }
    }

    @PostMapping("/logout")
    void logout(HttpServletResponse httpServletResponse){
        Cookie cookie = new Cookie("signed", "no");
        httpServletResponse.addCookie(cookie);
    }
}
