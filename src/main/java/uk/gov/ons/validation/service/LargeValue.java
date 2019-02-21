package uk.gov.ons.validation.service;

import uk.gov.ons.validation.entity.InputData;

/**
 * Validation rule for Large Values.
 *
 * Compare the given value against the given threshold. Trigger if the value is larger than the
 * threshold, false otherwise
 *
 * Missing/blank/invalid values are treated as ZERO
 *
 * @formula: Variable > threshold
 */
public class LargeValue implements Rule {

    private final InputData inputData;

    /**
     * Assumes a pre-populated copy of the standard Validation data class InputData is provided
     * However, the rule will handle null/missing data without error
     *
     * @param sourceInputData
     */
    public LargeValue( InputData sourceInputData) {
        inputData = (sourceInputData == null) ? new InputData() : sourceInputData;
    }

    /**
     * Give the variable based formula. i.e. the formula used at definition time and so uses the
     * given statistical variable and the threshold value
     *
     * @return String
     */
    public String getStatisticalVariableFormula() {
        return getFormula("","");
    }

    /**
     * Give the value based formula. i.e. the formula used at runtime and so uses the given
     * statistical value and threshold value
     *
     * @return String
     */
    public String getValueFormula() {
        return getFormula("","");
    }

    // Shared formula function so we can use the same formula definition for the statistical
    // variables and the values (i.e. at definition and at runtime)
    private String getFormula(String Variable, String threshold ) {
        return "Formula";
    }

    /**
     * For the given values and threshold return true if rule is triggered, false otherwise
     *
     * @return boolean
     */
    public boolean run() {
        return false;
    }

}
