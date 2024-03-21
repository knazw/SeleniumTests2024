package org.utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import java.io.File;
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

    public static void takeSnapShot(WebDriver webdriver, String fileWithPath) {

        try {
            TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

            File DestFile = new File(fileWithPath);
            FileUtils.copyFile(SrcFile, DestFile);
        }
        catch (Exception ex) {
            log.error(ex.toString());
        }
    }

    public static String createFileNameFromCurrentTime(String prefix, String suffix) {
        String value = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd-hhMMss"));
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
