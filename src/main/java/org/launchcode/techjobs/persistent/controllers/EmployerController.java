package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("employers")
public class EmployerController {

    @Autowired
    private EmployerRepository employerRepository;
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("employers", employerRepository.findAll());
        return "employers/index";//returns index view
    }
    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        model.addAttribute(new Employer());
        return "employers/add";
    }

    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Employer newEmployer,
                                    Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "employers/add";
        }
        employerRepository.save(newEmployer);//addition
        return "redirect:/employers"; // ADDED EMPLOYERS
    }

    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable int employerId) {

        Optional<Employer> optEmployer = employerRepository.findById(employerId);// TASK 2-4 CONTROLLERS
        if (optEmployer.isPresent()) {
            Employer employer = optEmployer.get(); //REMOVED EMPLOYER
            model.addAttribute("employer", employer);
            return "employers/view";
        } else {
            return "redirect:/employers"; //ADDED EMPLOYERS
        }

    }
}
