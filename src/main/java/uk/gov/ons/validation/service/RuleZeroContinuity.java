package uk.gov.ons.validation.service;

import uk.gov.ons.validation.entity.InputData;
import java.math.BigDecimal;

/**
 * Validation rule for Zero continuity.
 *
 * Compare 2 values. Trigger if one of the values is ZERO, the other is greater than 0 and the
 * difference between them is greater than the given threshold
 *
 * Missing/blank/invalid values are treated as ZERO
 *
 * @formula: abs(formulaVariable - comparisonVariable) > threshold AND
 *           [ abs(formulaVariable > 0) AND comparisonVariable = 0 ]
 *           OR
 *           [ abs(formulaVariable = 0) AND comparisonVariable > 0 ]
 */
public class RuleZeroContinuity implements Rule {

    private final InputData inputData;
    private final BigDecimal threshold;
    private final BigDecimal statisticalValue;
    private final BigDecimal comparisonValue;

    /**
     * Assumes a pre-populated copy of the standard Validation data class InputData is provided
     * However, the rule will handle null/missing data without error
     *
     * @param sourceInputData
     */
    public RuleZeroContinuity( InputData sourceInputData) {
        inputData = (sourceInputData == null) ? new InputData() : sourceInputData;
        threshold = safeDefineDecimal(inputData.getThreshold());
        statisticalValue = safeDefineDecimal(inputData.getValue());
        comparisonValue = safeDefineDecimal(inputData.getComparisonValue());
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
    public String getStatisticalVariableFormula() {
        return getFormula(inputData.getStatisticalVariable(), inputData.getComparisonVariable(), inputData.getThreshold());
    }

    /**
     * Give the value based formula. i.e. the formula used at runtime and so uses the given
     * statistical values and threshold value
     *
     * @return String
     */
    public String getValueFormula() {
        return getFormula(statisticalValue.toString(), comparisonValue.toString(), threshold.toString());
    }


    private String getFormula( String formulaVariable, String comparisonVariable, String threshold ) {
        return "{ [ abs(" + formulaVariable + " > 0) AND " + comparisonVariable + " = 0 ] OR" +
                " [ abs(" + formulaVariable + " = 0) AND " + comparisonVariable + " > 0 ] } AND" +
                " abs(" + formulaVariable + " - " + comparisonVariable + " ) > " + threshold;
    }

    /**
     * For the given values and threshold return true if rule is triggered, false otherwise
     *
     * @return boolean
     */
    public boolean run() {
        BigDecimal difference = statisticalValue.subtract(comparisonValue).abs();
        if ((oneValueOnlyIsZero(statisticalValue, comparisonValue)) && difference.compareTo(threshold) > 0 ) {
            return true;
        }
        return false;
    }

    private static boolean oneValueOnlyIsZero(BigDecimal value1, BigDecimal value2) {
        if (((value1.compareTo(BigDecimal.ZERO) != 0) && (value2.compareTo(BigDecimal.ZERO) == 0)) ||
            ((value2.compareTo(BigDecimal.ZERO) != 0) && (value1.compareTo(BigDecimal.ZERO) == 0))) {
            return true;
        }
        return false;
    }

}
