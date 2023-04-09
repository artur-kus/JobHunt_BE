package it.jobhunt.JobHunt.controler;

import it.jobhunt.JobHunt.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:8011")
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadCv(@RequestParam Long jobId, @RequestParam String email, @RequestBody MultipartFile file) {
        try {
            fileService.uploadCv(jobId, email, file);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/response", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> responseByLoggedUser(@RequestParam Long jobId, @RequestParam String email, @RequestBody MultipartFile file) {
        try {
            fileService.uploadCv(jobId, email, file);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/download/cv}")
    public HttpEntity<?> downloadResult(@RequestParam Long responseId) {
        try {
            return fileService.downloadResult(responseId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/download/cv/zip")
    public HttpEntity<?> downloadResults(@RequestParam Long jobId) {
        try {
            return fileService.downloadJobResults(jobId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}