package uk.gov.ons.validation.service;

import uk.gov.ons.validation.entity.InputData;

/**
 * Validation rule for Value Present.
 *
 * If the given value is not empty then this rule triggers and returns true, false otherwise
 *
 * Missing/invalid values are treated as blank
 *
 * @formula: formulaVariable != ""
 */
public class RuleValuePresent implements Rule {

    private InputData inputData;

    /**
     * Assumes a pre-populated copy of the standard Validation data class InputData is provided
     * However, the rule will handle null/missing data without error
     *
     * Requires variable and value from InputData
     *
     * @param sourceInputData
     */
    public RuleValuePresent(InputData sourceInputData) {
        inputData = (sourceInputData == null) ? new InputData() : sourceInputData;
    }

    public String getVariableFormula() {
        return getFormula(inputData.getVariable());
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
