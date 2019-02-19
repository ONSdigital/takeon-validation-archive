package uk.gov.ons.validation.service;

public interface Rule {
    String getStatisticalVariableFormula();
    String getValueFormula();
    boolean run();
}
