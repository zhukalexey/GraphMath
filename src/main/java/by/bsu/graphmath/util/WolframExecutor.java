package by.bsu.graphmath.util;

import by.bsu.graphmath.exception.WolframException;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WolframExecutor {

    private static final Logger logger = Logger.getLogger(WolframExecutor.class.getName());
    private static volatile WolframExecutor instance;
    private ApplicationProperties appProperties = new ApplicationProperties();
    private KernelLink kernelLink;

    private WolframExecutor() throws WolframException {
        try {
            String jLinkDir = appProperties.getProperty("jLinkDir");
            String kernelPath = appProperties.getProperty("wolframKernelPath");
            System.setProperty("com.wolfram.jlink.libdir", jLinkDir);
            String[] launchArguments = {"-linkmode", "launch", "-linkname", kernelPath};

            kernelLink = MathLinkFactory.createKernelLink(launchArguments);
            kernelLink.discardAnswer();
        } catch (MathLinkException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new WolframException(ex);
        }
    }

    public static WolframExecutor getInstance() throws WolframException {
        WolframExecutor localInstance = instance;
        if (localInstance == null) {
            synchronized (WolframExecutor.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new WolframExecutor();
                }
            }
        }
        return localInstance;
    }

    public String execute(String programText) throws WolframException {
        String solution = "";
        try {
            if (kernelLink != null) {
                solution = kernelLink.evaluateToOutputForm(getPreparedSvgProgram(programText), 0);
                kernelLink.flush();
                return Base64.encode(cleanUpSvgResult(solution).getBytes());
            }

        } catch (MathLinkException ex) {
            throw new WolframException(ex);
        }
        return solution;
    }

    private String getPreparedSvgProgram(String source) {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("ExportString[").append(source).append(", \"SVG\"]");
        return resultBuilder.toString();
    }

    private String cleanUpSvgResult(String svgResult) {
        return svgResult.substring(svgResult.indexOf("<svg"));
    }
}
