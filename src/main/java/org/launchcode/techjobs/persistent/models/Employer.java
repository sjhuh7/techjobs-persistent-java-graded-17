package org.launchcode.techjobs.persistent.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Employer extends AbstractEntity {
    @NotBlank(message = "Location cannot be left blank")
    @Size(max = 100, message = "Location cannot be longer than 100 characters")
    private String location;

    @OneToMany
    @JoinColumn(name = "employer_id")// name of the column in job table
    private List<Job> jobs = new ArrayList<>();
    //CONSTRUCTOR
    public Employer() {
    }
    //GETTERS AND SETTERS
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
