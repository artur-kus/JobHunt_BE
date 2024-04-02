package it.jobhunt.JobHunt.controler;

import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.exception.InternalException;
import it.jobhunt.JobHunt.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:8011")
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadCv(@RequestParam Long jobId,
                                      @RequestParam String email,
                                      @RequestBody MultipartFile file) throws IOException, InternalException {
        return new ResponseEntity<>(fileService.uploadCv(jobId, email, file), HttpStatus.OK);
    }

    @PostMapping(value = "/download/cv/{responseId}")
    public HttpEntity<?> downloadResult(@PathVariable Long responseId) throws DefaultException, IOException {
        return fileService.downloadResult(responseId);
    }

    @PostMapping(value = "/download/cv/zip/{jobId}")
    public HttpEntity<?> downloadResults(@PathVariable Long jobId) throws DefaultException, IOException {
        return fileService.downloadJobResults(jobId);
    }
}