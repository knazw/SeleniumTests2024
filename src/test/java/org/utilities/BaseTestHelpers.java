package org.utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class BaseTestHelpers {
    static final Logger log = getLogger(lookup().lookupClass());
    public static String getBrowser(String input) {
        // [number] chrome, [number] firefox
        String[] array = input.split("] ");
        if(array.length == 2) {
            return array[1];
        }
        return null;
    }

    public static byte[] takeSnapShot(WebDriver webdriver, String fileWithPath) {

        try {
            TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
            byte[] fileBytes = scrShot.getScreenshotAs(OutputType.BYTES);

            File destFile = new File(fileWithPath);

            Files.write(destFile.toPath(), fileBytes);
            return fileBytes;
        }
        catch (Exception ex) {
            log.error(ex.toString());
        }
        return null;
    }

    public static String createFileNameFromCurrentTime(String prefix, String suffix) {
        String value = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd-HHmmssSSS"));
        String result = "";
        if(prefix != null && prefix.length() > 0) {
            result = prefix + "-" + value;
        }
        else {
            result = value;
        }
        if(suffix != null && suffix.length() > 0) {
            result = result + "-" + suffix;
        }

        return result;

    }
}
