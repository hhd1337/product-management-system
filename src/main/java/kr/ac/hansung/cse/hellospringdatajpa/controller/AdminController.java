package kr.ac.hansung.cse.hellospringdatajpa.controller;

import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import kr.ac.hansung.cse.hellospringdatajpa.repo.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')") // 전체 컨트롤러 보호 (권장)
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/approvals")
    public String showPendingAdmins(Model model) {
        List<User> pending = userRepository.findByRole("ROLE_ADMIN_REQUESTED");
        model.addAttribute("pendingAdmins", pending);
        return "admin/approvals";
    }

    @PostMapping("/approve/{id}")
    public String approveAdmin(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setRole("ROLE_ADMIN");
        userRepository.save(user);
        return "redirect:/admin/approvals";
    }

    @GetMapping("/users")
    public String listAllUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin/users"; // templates/admin/users.html
    }

}
