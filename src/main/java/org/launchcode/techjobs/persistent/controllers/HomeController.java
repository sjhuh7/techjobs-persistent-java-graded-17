package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private SkillRepository skillRepository;

    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("title", "MyJobs");

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
	model.addAttribute("title", "Add Job");
        model.addAttribute("employers", employerRepository.findAll()); //ADDED THE LIST OF EMPLOYERS
        model.addAttribute(new Job());
        model.addAttribute("skills", skillRepository.findAll());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                       Errors errors, Model model, @RequestParam int employerId, @RequestParam List<Integer> skills) {

        if (errors.hasErrors()) {
	    model.addAttribute("title", "Add Job");
        model.addAttribute("employers", employerRepository.findAll()); //ADDS EMPLOYERS AGAIN ON ERROR
            model.addAttribute("skills", skillRepository.findAll());
            return "add";
        }

        Employer selectedEmployer = employerRepository.findById(employerId).orElse(null);
        if(selectedEmployer !=null) {
            newJob.setEmployer(selectedEmployer);
        }
        List<Skill> skillsObjs = (List<Skill>) skillRepository.findAllById(skills);
        newJob.setSkills(skillsObjs);
        jobRepository.save(newJob);
        return "redirect:"; //Returns to index page after submitting
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
        Optional<Job> result = jobRepository.findById(jobId);
        if(result.isPresent()) {
            model.addAttribute("job", result.get());
            return "view";
        } else {
            model.addAttribute("title", "Job not found");
            return "redirect:/";
        }
//        model.addAttribute("job", jobRepository.findById(jobId).orElse(null));

    }

}
