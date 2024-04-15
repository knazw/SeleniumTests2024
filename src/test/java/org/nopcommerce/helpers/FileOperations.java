package org.nopcommerce.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.nopcommerce.model.User;
import org.slf4j.Logger;

import java.io.*;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class FileOperations {

    static final Logger log = getLogger(lookup().lookupClass());

    public static void writeToFile(String fileName, String text) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(text);
        }
        catch (IOException ioException) {
            log.error(ioException.toString());
        }
    }

    public static String readFromFile(String fileName) {
        String data = "";

        try {
            File file = new File(fileName);
            InputStream inputStream = new FileInputStream(file);
            data = readFromInputStream(inputStream);
        }
        catch (IOException ioException) {
            log.error(ioException.toString());
        }

        return data;
    }

    private static String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();

        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        catch (Exception ex) {
            log.error(ex.toString());
        }
        return resultStringBuilder.toString();
    }

    public static void writeToFile(Object object, String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JSR310Module());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try {
            objectMapper.writeValue(new File(fileName), object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
