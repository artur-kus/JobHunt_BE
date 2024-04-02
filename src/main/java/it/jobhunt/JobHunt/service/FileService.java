package it.jobhunt.JobHunt.service;

import it.jobhunt.JobHunt.entity.Candidate;
import it.jobhunt.JobHunt.entity.Job;
import it.jobhunt.JobHunt.entity.Response;
import it.jobhunt.JobHunt.enums.UploadFileExtension;
import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.exception.InternalException;
import it.jobhunt.JobHunt.repository.JobRepository;
import it.jobhunt.JobHunt.repository.ResponseRepository;
import it.jobhunt.JobHunt.util.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class FileService {

    @Value("${uploadCvPath}")
    private String uploadCvFolderPath;
    @Value("${jobResponsesTemplatePath}")
    private String jobResponsesTemplatePath;
    @Value("${jobResponsesFolderPath}")
    private String jobResponsesFolderPath;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ResponseRepository responseRepository;

    @PostConstruct
    private void initDirectories() throws IOException {
        Files.createDirectories(Paths.get(uploadCvFolderPath));
        Files.createDirectories(Paths.get(jobResponsesFolderPath));
        Files.createDirectories(Paths.get(jobResponsesFolderPath + "/zip/"));
    }

    public DefaultResponse uploadCv(Long jobId, String email, MultipartFile file) throws IOException, InternalException {
        ValidationUtil.isValidEmail(email);
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new InternalException("Job " + jobId + " doesn't exist."));
        String uploadFilePath = saveUploadFile(file, job.getId());
        Response response = new Response(email, uploadFilePath, job);
        responseRepository.save(response);
        return DefaultResponse.builder()
                .message(String.format("CV has been sent to %s on %s", job.getCompany().getName(), job.getName()))
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    public String saveUploadFile(MultipartFile file, Long jobId) throws InternalException, IOException {
        List<String> extensions = Arrays.stream(UploadFileExtension.values()).map(v -> v.name().toLowerCase()).toList();
        return FileOperation.save(file, createUploadCvFileName(jobId), uploadCvFolderPath, extensions);
    }

    public HttpEntity<ByteArrayResource> downloadJobResults(Long jobId) throws IOException, DefaultException {
        Job job = jobRepository.findByIdAndInitializeResponse(jobId)
                .orElseThrow(() -> new DefaultException("Job " + jobId + " doesn't exist."));
        List<String> filePaths = new ArrayList<>(job.getResponses().stream()
                .map(Response::getCvFilePath)
                .filter(cvFilePath -> !GeneralUtil.isNullOrEmpty(cvFilePath))
                .toList());
        try {
            List<Candidate> candidates = job.getResponses().stream()
                    .map(Response::getCandidate)
                    .filter(Objects::nonNull)
                    .toList();
            String jobResponsesPath = XlsGenerator.generate(getJobResponsesTemplatePath(),
                    createJobResponsesFilePath(job), prepareDataForJobResponses(candidates, filePaths.size()));
            if (jobResponsesPath != null) filePaths.add(jobResponsesPath);
        } catch (Exception ignored) {
            log.error("Cannot prepare job responses file for job " + jobId);
        }
        List<File> files = FileOperation.findFilesByPaths(filePaths);
        if (files.isEmpty()) throw new DefaultException("Cannot find any responses for job id" + jobId);
        String zipPath = FileOperation.zipFiles(files, jobResponsesFolderPath + "/zip/" + createZipName(jobId));
        return FileOperation.download(zipPath);
    }

    private String createJobResponsesFilePath(Job job) {
        return jobResponsesFolderPath + "/job_responses_" + job.getId() +
                new SimpleDateFormat("_HHmm_ddMMyy").format(new Date()) + ".xlsx";
    }

    private Map<String, Object> prepareDataForJobResponses(List<Candidate> candidates, int cvResponseCounter) {
        Map<String, Object> data = new HashMap<>();
        data.put("candidates", candidates);
        data.put("responseByAccount", candidates.size());
        data.put("cvResponse", cvResponseCounter);
        return data;
    }

    public HttpEntity<ByteArrayResource> downloadResult(Long responseId) throws IOException, DefaultException {
        Response response = responseRepository.findByIdAndCvFilePathIsNotNull(responseId)
                .orElseThrow(() -> new DefaultException("Response " + responseId + " doesn't exist"));
        return FileOperation.download(response.getCvFilePath());
    }

    private String createUploadCvFileName(Long jobId) {
        String actualDate = new SimpleDateFormat("ddMMyy_HHmmss").format(new Date());
        return "cv_" + GeneralUtil.leftPad(String.valueOf(jobId), 5, "0") + "_" + actualDate;
    }

    private String createZipName(Long jobId) {
        String actualDate = new SimpleDateFormat("ddMMyyHHmm").format(new Date());
        return "job_cv_response_" + jobId + "_" + actualDate + ".zip";
    }

    private String getJobResponsesTemplatePath() throws DefaultException {
        URL templatePath = getClass().getResource(jobResponsesTemplatePath);
        if (templatePath != null) {
            return templatePath.getPath().substring(1);
        } else throw new DefaultException("Cannot find job responses template file");
    }
}
