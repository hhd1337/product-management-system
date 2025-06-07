package kr.ac.hansung.cse.hellospringdatajpa.controller;


import jakarta.validation.Valid;
import kr.ac.hansung.cse.hellospringdatajpa.dto.RegisterRequestDTO;
import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import kr.ac.hansung.cse.hellospringdatajpa.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = encoder;
    }

    /*@GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // templates/register.html
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return "redirect:/login";
    }*/
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerForm", new RegisterRequestDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(
            @ModelAttribute("registerForm") @Valid RegisterRequestDTO form,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "register"; // 유효성 오류가 있으면 다시 등록 폼으로
        }

        User user = new User();
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setRole(form.getRole());

        userRepository.save(user);
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            Model model) {
        if (error != null) {
            model.addAttribute("loginError", "이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        return "login"; // templates/login.html
    }

    /*@GetMapping("/login")
    public String loginForm() {
        return "login"; // templates/login.html
    }*/
}

