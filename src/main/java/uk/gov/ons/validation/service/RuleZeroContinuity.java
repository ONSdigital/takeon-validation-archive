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
    private final BigDecimal value1;
    private final BigDecimal value2;

    public RuleZeroContinuity( InputData sourceInputData) {
        inputData = (sourceInputData == null) ? new InputData() : sourceInputData;
        threshold = safeDefineDecimal(inputData.getThreshold());
        value1 = safeDefineDecimal(inputData.getValue());
        value2 = safeDefineDecimal(inputData.getComparisonValue());
    }

    private BigDecimal safeDefineDecimal(String value) {
        BigDecimal safeDecimal;
        try {
            safeDecimal = new BigDecimal(inputData.getThreshold());
        }
        catch (NumberFormatException e) {
            safeDecimal = new BigDecimal(0);
        }
        return safeDecimal;
    }

    public String getStatisticalVariableFormula() {
        return getFormula(inputData.getStatisticalVariable(), inputData.getComparisonVariable(), inputData.getThreshold());
    }

    public String getValueFormula() {
        return getFormula(inputData.getValue(), inputData.getComparisonValue(), inputData.getThreshold());
    }

    private String getFormula( String formulaVariable, String comparisonVariable, String threshold ) {
        return "{ [ abs(" + formulaVariable + " > 0) AND " + comparisonVariable + " = 0 ] OR" +
                " [ abs(" + formulaVariable + " = 0) AND " + comparisonVariable + " > 0 ] } AND" +
                " abs(" + formulaVariable + " - " + comparisonVariable + " ) > " + threshold;
    }

    // We use decimal here rather than float/double to reduce accuracy/estimation issues
    // || => AND --- && => OR
    public boolean run() {
        BigDecimal difference = value1.subtract(value2);

        boolean triggered = false;
        if ((( value1.abs().compareTo(BigDecimal.ZERO) > 0 && value2.abs().compareTo(BigDecimal.ZERO) == 0 ) ||
             ( value1.abs().compareTo(BigDecimal.ZERO) == 0 && value2.abs().compareTo(BigDecimal.ZERO) > 0 )) &&
               difference.compareTo(threshold) > 0 )
        {
            triggered = true;
        }

        return triggered;
    }

}
