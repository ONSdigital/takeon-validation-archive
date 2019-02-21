package uk.gov.ons.validation.service;

import uk.gov.ons.validation.entity.InputData;

import java.math.BigDecimal;

/**
 * Validation rule for Value Changes.
 *
 * Can be used for:
 *  Period on Period Movement
 *  Question v Question
 *  Question v Derived Question
 *
 * Compare 2 values. Trigger (return true) if:
 *     If value has increased by the percentage increased parameter AND absolute difference increase
 *     If value has decreased by the percentage decreased parameter AND absolute difference decrease
 *
 * If parameter 'ignoreZero' is set then BOTH of the values must be non-zero for any rules to trigger
 *
 * Missing/blank/invalid values are treated as ZERO
 *
 * @formula:   [ abs(comparisonValue - value) > 'absolute difference increase threshold' AND
 *              100 * (comparisonValue - value)/value > 'percent increase threshold' ]
 *              OR
 *            [ abs(value - comparisonValue) > 'absolute difference decrease threshold' AND
 *               100 * (value - comparisonValue)/comparisonValue > 'percent decrease threshold' ]
 */
public class RuleValueChange implements Rule {

    private final InputData inputData;
    private final BigDecimal absoluteIncreaseThreshold;
    private final BigDecimal absoluteDecreaseThreshold;
    private final BigDecimal percentIncreaseThreshold;
    private final BigDecimal percentDecreaseThreshold;
    private final BigDecimal value;
    private final BigDecimal comparisonValue;

    /**
     * Assumes a pre-populated copy of the standard Validation data class InputData is provided
     * However, the rule will handle null/missing data without error
     *
     * @param sourceInputData
     */
    public RuleValueChange( InputData sourceInputData) {
        inputData = (sourceInputData == null) ? new InputData() : sourceInputData;
        value = safeDefineDecimal(inputData.getValue());
        comparisonValue = safeDefineDecimal(inputData.getComparisonValue());
        percentIncreaseThreshold = safeDefineDecimal(inputData.getPercentIncreaseThreshold());
        percentDecreaseThreshold = safeDefineDecimal(inputData.getPercentDecreaseThreshold());
        absoluteIncreaseThreshold = safeDefineDecimal(inputData.getAbsoluteIncreaseThreshold());
        absoluteDecreaseThreshold = safeDefineDecimal(inputData.getAbsoluteDecreaseThreshold());
    }

    // Ensure we end up with 0 if no (or invalid) values are passed through to this validation rule
    private static BigDecimal safeDefineDecimal(String value) {
        BigDecimal safeDecimal;
        try {
            safeDecimal = new BigDecimal(value);
        } catch (NumberFormatException e) {
            safeDecimal = new BigDecimal(0);
        } catch (NullPointerException e) {
            safeDecimal = new BigDecimal(0);
        }
        return safeDecimal;
    }

    /**
     * Give the variable based formula. i.e. the formula used at definition time and so uses the
     * given statistical variables and the threshold value
     *
     * @return String
     */
    public String getVariableFormula() {
        return getFormula("","");
    }

    /**
     * Give the value based formula. i.e. the formula used at runtime and so uses the given
     * statistical values and threshold values
     *
     * @return String
     */
    public String getValueFormula() {
        return getFormula("","");
    }

    // Shared formula function so we can use the same formula definition for the statistical
    // variables and the values (i.e. at definition and at runtime)
    private String getFormula( String variable, String comparisonVariable) {
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
