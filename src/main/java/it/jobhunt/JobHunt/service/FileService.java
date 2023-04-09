package it.jobhunt.JobHunt.service;

import it.jobhunt.JobHunt.entity.Job;
import it.jobhunt.JobHunt.entity.Response;
import it.jobhunt.JobHunt.enums.UploadFileExtension;
import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.exception.InternalException;
import it.jobhunt.JobHunt.repository.JobRepository;
import it.jobhunt.JobHunt.repository.ResponseRepository;
import it.jobhunt.JobHunt.util.FileOperation;
import it.jobhunt.JobHunt.util.GeneralUtil;
import it.jobhunt.JobHunt.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class FileService {

    @Value("${uploadCvPath}")
    private String uploadCvFolderPath;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ResponseRepository responseRepository;


    public void uploadCv(Long jobId, String email, MultipartFile file) throws IOException, InternalException {
        ValidationUtil.isValidEmail(email);
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new InternalException("Job " + jobId + " doesn't exist."));
        String uploadFilePath = saveUploadFile(file, job.getId());
        Response response = new Response(email, uploadFilePath);
        job.setResponses(List.of(response));
        jobRepository.save(job);
    }

    public String saveUploadFile(MultipartFile file, Long jobId) throws InternalException, IOException {
        List<String> extensions = Arrays.stream(UploadFileExtension.values()).map(v -> v.name().toLowerCase()).toList();
        return FileOperation.save(file, createUploadCvFileName(jobId), uploadCvFolderPath, extensions);
    }

    public HttpEntity<ByteArrayResource> downloadJobResults(Long jobId) throws IOException, DefaultException {
        Job job = jobRepository.findByIdAndInitializeResponse(jobId)
                .orElseThrow(() -> new DefaultException("Job " + jobId + " doesn't exist."));
        List<String> cvPaths = job.getResponses().stream()
                .map(Response::getCvFilePath)
                .filter(cvFilePath -> !GeneralUtil.isNullOrEmpty(cvFilePath)).toList();
        List<File> files = FileOperation.findFilesByPaths(cvPaths);
        if (files.isEmpty()) throw new DefaultException("Cannot find any responses for job id" + jobId);
        String zipPath = FileOperation.zipFiles(files, uploadCvFolderPath + "/zip/" + createZipName(jobId));
        return FileOperation.download(zipPath);
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
}
