package com.example.jobportal_signup.controller;

import com.example.jobportal_signup.models.Add;
import com.example.jobportal_signup.repository.AddRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/jobs")
public class AddController {

    @Autowired
    private AddRepository addRepository;

    private final String UPLOAD_DIR =
            System.getProperty("user.dir")
                    + File.separator
                    + "uploads"
                    + File.separator;

    // =========================
    // ADD JOB API
    // =========================

    @PostMapping("/add")
    public ResponseEntity<?> addJob(

            @RequestParam("imageName") MultipartFile file,

            @RequestParam("jobTitle") String jobTitle,

            @RequestParam("location") String location,

            @RequestParam("jobType") String jobType,

            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam("deadline") LocalDate deadline

    ) {

        try {

            // Create Folder
            File uploadFolder = new File(UPLOAD_DIR);

            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            // Unique File Name
            String fileName =
                    System.currentTimeMillis()
                            + "_"
                            + file.getOriginalFilename();

            // File Path
            String filePath = UPLOAD_DIR + fileName;

            // Save File
            file.transferTo(new File(filePath));

            // Save DB
            Add add = new Add();

            add.setJobTitle(jobTitle);
            add.setLocation(location);
            add.setJobType(jobType);
            add.setDeadline(deadline);
            add.setImageName(fileName);

            Add savedAdd = addRepository.save(add);

            return ResponseEntity.ok(savedAdd);

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // =========================
    // GET ALL JOBS
    // =========================

    @GetMapping("/all")
    public ResponseEntity<?> getAllJobs() {

        List<Add> jobs = addRepository.findAll();

        return ResponseEntity.ok(jobs);
    }

    // =========================
    // GET SINGLE JOB
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneJob(@PathVariable Long id) {

        Optional<Add> optionalAdd = addRepository.findById(id);

        if (optionalAdd.isPresent()) {

            return ResponseEntity.ok(optionalAdd.get());

        } else {

            return ResponseEntity.badRequest().body("Job Not Found");
        }
    }

    // =========================
    // DELETE JOB
    // =========================

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {

        Optional<Add> optionalAdd = addRepository.findById(id);

        if (optionalAdd.isPresent()) {

            Add add = optionalAdd.get();

            // Delete Image
            File imageFile = new File(UPLOAD_DIR + add.getImageName());

            if (imageFile.exists()) {
                imageFile.delete();
            }

            // Delete DB Record
            addRepository.deleteById(id);

            return ResponseEntity.ok("Job Deleted Successfully");

        } else {

            return ResponseEntity.badRequest().body("Job Not Found");
        }
    }

    // =========================
    // UPDATE JOB
    // =========================

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateJob(

            @PathVariable Long id,

            @RequestParam(value = "imageName", required = false)
            MultipartFile file,

            @RequestParam("jobTitle")
            String jobTitle,

            @RequestParam("location")
            String location,

            @RequestParam("jobType")
            String jobType,

            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam("deadline")
            LocalDate deadline

    ) {

        try {

            Optional<Add> optionalAdd = addRepository.findById(id);

            if (optionalAdd.isPresent()) {

                Add add = optionalAdd.get();

                // Update Image If New Image Exists
                if (file != null && !file.isEmpty()) {

                    // Delete Old Image
                    File oldImage =
                            new File(UPLOAD_DIR + add.getImageName());

                    if (oldImage.exists()) {
                        oldImage.delete();
                    }

                    // Upload New Image
                    String fileName =
                            System.currentTimeMillis()
                                    + "_"
                                    + file.getOriginalFilename();

                    String filePath = UPLOAD_DIR + fileName;

                    file.transferTo(new File(filePath));

                    add.setImageName(fileName);
                }

                // Update Other Fields
                add.setJobTitle(jobTitle);
                add.setLocation(location);
                add.setJobType(jobType);
                add.setDeadline(deadline);

                Add updatedJob = addRepository.save(add);

                return ResponseEntity.ok(updatedJob);

            } else {

                return ResponseEntity.badRequest().body("Job Not Found");
            }

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}