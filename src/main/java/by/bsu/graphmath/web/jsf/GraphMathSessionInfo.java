package by.bsu.graphmath.web.jsf;

import by.bsu.graphmath.util.ApplicationProperties;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = GraphMathSessionInfo.BEAN_BINDING)
@SessionScoped
public final class GraphMathSessionInfo {

    protected static final Logger logger = Logger.getLogger(GraphMathSessionInfo.class.getName());
    public static final String BEAN_BINDING = "sessionInfo";
    public static final String JQUERY_INPUT_DATE_FORMAT = "dd/mm/yy";
    public static final String INPUT_DATE_FORMAT = "dd/MM/yyyy";
    public static final String OUTPUT_DATE_FORMAT = "dd/MM/yyyy";
    public static final String OUTPUT_DATETIME_FORMAT = "dd/MM/yyyy HH:mm";
    public static final String EMAIL_ADDRESS_REGEX_PATTERN = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)";
    public static final String HIGHCHART_DATE_FORMAT = "%d/%m/%Y";
    public static final String CONTEST_OUTPUT_DATE_FORMAT = "MMM d, yyyy";
    private ApplicationProperties applicationProperties;
    private static final String KEY_PROJECTSTAGE = "projectStage";
    private String projectStage;

    public GraphMathSessionInfo() {
    }

    public String getHttpAplicationUrl() {
        String httpApplicationUrl = getApplicationUrl();
        return (httpApplicationUrl == null) ? null : "http://" + httpApplicationUrl + "/";
    }

    public String getApplicationUrl() {
        return "localhost:8080";
    }

    public String getJqueryInputDateFormat() {
        return JQUERY_INPUT_DATE_FORMAT;
    }

    public String getInputDateFormat() {
        return INPUT_DATE_FORMAT;
    }

    public String getOutputDateFormat() {
        return OUTPUT_DATE_FORMAT;
    }

    public String getEmailRegexPattern() {
        return EMAIL_ADDRESS_REGEX_PATTERN;
    }

    public String getHighchartDateFormat() {
        return HIGHCHART_DATE_FORMAT;
    }

    public String getContestOutputDateFormat() {
        return CONTEST_OUTPUT_DATE_FORMAT;
    }

    public void setApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public String getProjectStage() {
        if (projectStage == null) {
            projectStage = applicationProperties.getProperty(KEY_PROJECTSTAGE);
            if (projectStage == null) {
                logger.warning("Project stage of application is not found");
                projectStage = "";
            }
        }
        return projectStage;
    }

    public boolean isDevelopment() {
        return "Development".equals(getProjectStage());
    }
}