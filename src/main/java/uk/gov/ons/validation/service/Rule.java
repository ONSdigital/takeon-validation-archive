package uk.gov.ons.validation.service;

public interface Rule {
    /**
     * Return the validation rule logic/formula as a String. This is the formula used at definition
     * time and so uses the given statistical variables and parameter values
     *
     * @return A string containing the formula
     */
    String getStatisticalVariableFormula();

    /**
     * Return the validation rule logic/formula as a String. This is the formula used at runtime and
     * so uses the given statistical values and parameter values
     *
     * @return A string containing the formula
     */
    String getValueFormula();

    /**
     * Run the formula within the validation rule. Returns true if the rule conditions are met (i.e.
     * triggered), false otherwise.
     *
     * @return true or false
     */
    boolean run();
}
