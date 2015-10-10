package by.bsu.graphmath.web.jsf;

import by.bsu.graphmath.exception.WolframException;
import by.bsu.graphmath.util.ContextUtils;
import by.bsu.graphmath.util.FileUtils;
import by.bsu.graphmath.util.WolframExecutor;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

@ManagedBean(name = "math")
@ViewScoped
public class MathematicaPage implements Serializable {

    protected static final Logger logger = Logger.getLogger(MathematicaPage.class.getName());
    private static final long serialVersionUID = 5528487989058971589L;
    private Part wolframInputFile;
    private String solution;

    public Part getWolframInputFile() {
        return wolframInputFile;
    }

    public void setWolframInputFile(Part wolframInputFile) {
        this.wolframInputFile = wolframInputFile;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public void setSolution(String[] solution) {
        StringBuilder solutionBuilder = new StringBuilder();
        for (String str : solution) {
            solutionBuilder.append(str);
            solutionBuilder.append("\n");
        }
        this.solution = solutionBuilder.toString();
    }

    public void validateWolframInputFile(FacesContext ctx, UIComponent comp, Object value) {
        FileUtils.validateTextFile(ctx, comp, value);
    }

    public String executeWolframProgram() {
        setSolution("");
        try {
            String inputFileContent = FileUtils.readFileContent(getWolframInputFile());
            setSolution(WolframExecutor.getInstance().execute(inputFileContent));
        } catch (WolframException ex) {
            ContextUtils.addErrorMessage(ex);
            return null;
        }
        return null;
    }

    public boolean isEmptySolution() {
        return solution == null || solution.isEmpty();
    }
}
