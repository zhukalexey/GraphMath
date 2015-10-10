package by.bsu.graphmath.util;

import java.rmi.RemoteException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.*;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public final class ContextUtils {

    private static final Logger logger = Logger.getLogger(ContextUtils.class.getName());
    public static final String BUNDLE_VAR = "bundle";

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public static Application getApplication() {
        return getFacesContext().getApplication();
    }

    public static ExternalContext getExternalContext() {
        return getFacesContext().getExternalContext();
    }

    public static ServletContext getServletContext() {
        return (ServletContext) getExternalContext().getContext();
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) getExternalContext().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) getExternalContext().getResponse();
    }

    public static UIViewRoot getRoot() {
        return getFacesContext().getViewRoot();
    }

    public static HttpSession getHttpSession() {
        return (HttpSession) getExternalContext().getSession(false);
    }

    public static ResourceBundle getBundle() {
        ResourceBundle bundle = getApplication().getResourceBundle(getFacesContext(), BUNDLE_VAR);
        return bundle;
    }

    public static String getBundledString(String key) {
        String bundledString = key;
        try {
            bundledString = getBundle().getString(key);
        } catch (MissingResourceException ex) {
            logger.log(Level.WARNING, "The following key was not found in bundle: {0}", key);
        }
        return bundledString;
    }

    public static void addInfoMessage(String msg) {
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
    }

    public static void addErrorMessage(String clientId, String msg) {
        getFacesContext().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
    }

    public static void addErrorMessage(String msg) {
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
    }

    public static void addErrorMessage(Exception ex) {
        String errorMessage = ex.getMessage();
        FacesMessage facesMessage = null;
        if (ex.getCause() instanceof RemoteException) {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, getBundledString("message_technical_error"), null);
        } else {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, ex.toString());
        }
        getFacesContext().addMessage(null, facesMessage);
        logger.log(Level.SEVERE, "ADDED MESSAGE: " + facesMessage.getSummary(), ex);
    }
}
