package it.jobhunt.JobHunt.util;

import lombok.extern.slf4j.Slf4j;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

@Service
@Slf4j
public class XlsGenerator {

    public static String generate(String templatePath, String resultFilePath, Map<String, Object> data) {
        OutputStream outputStream = null;
        try {
            File templateFile = new File(templatePath);
            outputStream = new FileOutputStream(resultFilePath);
            Context context = new Context(data);
            JxlsHelper.getInstance().processTemplate(new FileInputStream(templateFile), outputStream, context);
            return resultFilePath;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Xlsx generator - error while generation");
            return null;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error("Xlsx generator - error while close stream");
            }
        }
    }
}