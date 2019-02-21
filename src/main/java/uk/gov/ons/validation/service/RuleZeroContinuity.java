package uk.gov.ons.validation.service;

import uk.gov.ons.validation.entity.InputData;

import java.math.BigDecimal;

/**
 * Validation rule for Zero continuity.
 *
 * Compare 2 values. Trigger if one of the values is ZERO, the other is greater than 0 and the
 * difference between them is greater than the given threshold
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

    public RuleZeroContinuity( InputData sourceInputData) {
        inputData = (sourceInputData == null) ? new InputData() : sourceInputData;
        threshold = safeDefineDecimal(inputData.getThreshold());
        statisticalValue = safeDefineDecimal(inputData.getValue());
        comparisonValue = safeDefineDecimal(inputData.getComparisonValue());
    }

    //
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

    public String getStatisticalVariableFormula() {
        return getFormula(inputData.getStatisticalVariable(), inputData.getComparisonVariable(), inputData.getThreshold());
    }

    public String getValueFormula() {
        return getFormula(statisticalValue.toString(), comparisonValue.toString(), threshold.toString());
    }

    private String getFormula( String formulaVariable, String comparisonVariable, String threshold ) {
        return "{ [ abs(" + formulaVariable + " > 0) AND " + comparisonVariable + " = 0 ] OR" +
                " [ abs(" + formulaVariable + " = 0) AND " + comparisonVariable + " > 0 ] } AND" +
                " abs(" + formulaVariable + " - " + comparisonVariable + " ) > " + threshold;
    }

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
