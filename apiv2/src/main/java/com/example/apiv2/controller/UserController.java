package com.example.apiv2.controller;

import com.example.apiv2.model.UserDtls;
import com.example.apiv2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @ModelAttribute
    private void userDetails(Model m, Principal p) {
        String email = p.getName();
        UserDtls user = userRepo.findByEmail(email);
        m.addAttribute("user", user);
    }

    @GetMapping("/")
    public String home() {
        return "user/home";
    }

    @GetMapping("/changPass")
    public String loadChnagePassword() {
        return "user/editarPassword";
    }

    @PostMapping("/updatePassword")
    public String changePassword(Principal p, @RequestParam("oldPass") String oldPass,
                                 @RequestParam("newPass") String newPass, HttpSession session){
        String email=p.getName();
        UserDtls loginUser = userRepo.findByEmail(email);

        boolean f = passwordEncoder.matches(oldPass, loginUser.getPassword());

        if (f){

            loginUser.setPassword(passwordEncoder.encode(newPass));
            UserDtls updatePasswordUser = userRepo.save(loginUser);
            if (updatePasswordUser!=null){
                    session.setAttribute("msg", "Password cambiada exitosamente");
            }else {
                session.setAttribute("msg", "Hubo un error");
            }

        }else {
            session.setAttribute("msg", "antigua contraseña incorrecta");

        }

        return "redirect:/user/changPass";
    }

}
