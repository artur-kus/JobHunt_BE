package it.jobhunt.JobHunt.util;

import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.exception.InternalException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileOperation {

    public static String save(MultipartFile file, String fileName, String path, List<String> fileExtensions) throws InternalException, IOException {
        String extension = FileUtils.getExtension(file.getOriginalFilename());
        String uploadPath = path + fileName + "." + extension;
        if (fileExtensions.contains(extension)) {
            return save(file, uploadPath, true);
        } else
            throw new InternalException("File extension is invalid. Choose correct one. Expected - " + fileExtensions + "");
    }

    private static String save(MultipartFile file, String path, Boolean pathWithFilename) {
        try {
            Path savePath = Paths.get(path);
            Files.copy(file.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING);
            return (pathWithFilename)
                    ? savePath.toString()
                    : savePath + "/" + file.getOriginalFilename();
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public static HttpEntity<ByteArrayResource> download(String path) throws IOException, DefaultException {
        return new HttpEntity<>(getByteArrayResource(path), getHeaders(path, getMediaType(path)));
    }

    private static ByteArrayResource getByteArrayResource(String path) throws IOException {
        return new ByteArrayResource(Files.readAllBytes(getFile(path).toPath()));
    }

    private static HttpHeaders getHeaders(String path, MediaType mediaType) {
        File file = getFile(path);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(mediaType);
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment");
        header.set("filename", file.getName());
        return header;
    }

    private static MediaType getMediaType(String path) throws DefaultException {
        int lastIndexOf = path.lastIndexOf(".") + 1;
        String fileExtension = path.substring(lastIndexOf).toLowerCase();
        return switch (fileExtension) {
            case "jpg":
                yield new MediaType("image", "jpg");
            case "jpeg":
                yield new MediaType("image", "jpeg");
            case "pdf":
                yield new MediaType("application", "pdf");
            case "zip":
                yield new MediaType("application", "zip");
            case "doc":
                yield new MediaType("application", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            default:
                throw new DefaultException("Cannot process that extension of file.");
        };
    }

    private static File getFile(String path) {
        return new File(path);
    }

    private static List<File> findFiles(String searchDirectory, PathMatcher matcher) throws IOException {
        try (Stream<Path> walk = Files.walk(Paths.get(searchDirectory))) {
            return walk.filter(matcher::matches).map(Path::toFile).collect(Collectors.toList());
        }
    }

    public static List<File> findFilesByPaths(List<String> paths) {
        return paths.stream()
                .map(File::new)
                .flatMap(file -> {
                    if (file.isFile()) {
                        return Stream.of(file);
                    } else {
                        File[] files = file.listFiles();
                        return (files == null) ? Stream.empty() : Arrays.stream(files).filter(File::isFile);
                    }
                }).collect(Collectors.toList());
    }

    public static String zipFiles(List<File> files, String toPath) throws IOException {
        FileOutputStream fos = new FileOutputStream(toPath);
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        for (File file : files) {
            FileInputStream fis = new FileInputStream(file);
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();
        return toPath;
    }
}