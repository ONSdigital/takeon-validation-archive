package uk.gov.ons.validation.service;

import uk.gov.ons.validation.entity.InputData;

public class RuleValuePresent implements Rule {

    private InputData inputData;

    public RuleValuePresent(InputData sourceInputData) {
        inputData = (sourceInputData == null) ? new InputData() : sourceInputData;
    }

    public String getStatisticalVariableFormula() {
        return getFormula(inputData.getStatisticalVariable());
    }

    public String getValueFormula() {
        return getFormula(inputData.getValue());
    }

    private static String getFormula( String formulaVariable ) {
        return formulaVariable + " != ''";
    }

    public boolean run() {
        return !inputData.getValue().isEmpty();
    }

}
