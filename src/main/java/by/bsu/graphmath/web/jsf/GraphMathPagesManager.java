package by.bsu.graphmath.web.jsf;

import by.bsu.graphmath.util.ContextUtils;

public class GraphMathPagesManager {

    public static final String OUTCOME_HOME = "home";
    public static final String OUTCOME_MATHEMATICA = "mathematica";
    public static final String OUTCOME_CONVERTER = "home";

    public static void navigateToPage(String outcome) {
        ContextUtils.getApplication().getNavigationHandler().handleNavigation(ContextUtils.getFacesContext(), null, outcome);
    }
}
