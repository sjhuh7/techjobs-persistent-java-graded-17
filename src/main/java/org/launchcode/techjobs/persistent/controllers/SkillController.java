package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Optional;


@Controller
@RequestMapping("skills")//Base path for skill routes
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    //Displays the list of all of the skills
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("skills", skillRepository.findAll());
        return "skills/index";//returns index view
    }
    //Displays the form to add a new skill
    @GetMapping("add")
    public String displayAddSkillForm(Model model) {
        model.addAttribute(new Skill());
        return "skills/add";//returns add view
    }
    //Handles adding a new skill
    @PostMapping("add")
    public String processAddSkillForm(@ModelAttribute @Validated Skill newSkill,
                                      Errors errors, Model model) {
        if(errors.hasErrors()) {
            return "skills/add";
        }
        skillRepository.save(newSkill);
        return "redirect:/skills";// redirect to skills index
    }
    @GetMapping("view/{skillId}")
    public String displayViewSkill(Model model, @PathVariable int skillId) { //CHANGED THIS TO STRING FROM INT BECAUSE IT WAS EXPECTING STRING COME BACK LATER
        Optional<Skill> optSkill = skillRepository.findById(skillId);

        if(optSkill.isPresent()) {
            Skill skill = optSkill.get();
            model.addAttribute("skill", skill);
            return "skills/view";
        } else {
            return "redirect:/skills";
        }
    }
}
