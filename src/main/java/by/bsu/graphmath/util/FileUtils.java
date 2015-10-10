package by.bsu.graphmath.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

public class FileUtils {

    public static void validateTextFile(FacesContext ctx, UIComponent comp, Object value) {
        List<FacesMessage> messages = new ArrayList<>();
        if (value == null) {
            messages.add(new FacesMessage(ContextUtils.getBundledString("message_null_file")));
        } else {
            Part file = (Part) value;
            if (!"text/plain".equals(file.getContentType())) {
                messages.add(new FacesMessage(ContextUtils.getBundledString("message_not_text_file")));
            }
        }
        if (!messages.isEmpty()) {
            throw new ValidatorException(messages);
        }
    }

    public static String readFileContent(Part file) {
        StringBuilder fileContent = new StringBuilder();
        if (file != null) {
            try (Scanner scanner = new Scanner(file.getInputStream())) {
                while (scanner.hasNextLine()) {
                    fileContent.append(scanner.nextLine());
                    fileContent.append("\n");
                }
            } catch (IOException ex) {
                ContextUtils.addErrorMessage(ex);
                fileContent = new StringBuilder();
            }
        }
        return fileContent.toString();
    }
}
