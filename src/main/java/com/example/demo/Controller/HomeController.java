package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Student;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String home() {
        return "redirect:/products";
    }

    @GetMapping("/demo")
    public String demoPage(Model model) {
        Student student = new Student(1L, "Nguyễn Văn A");
        model.addAttribute("student", student);
        model.addAttribute("message", "Welcome Thymeleaf!");
        return "demo";
    }
}
