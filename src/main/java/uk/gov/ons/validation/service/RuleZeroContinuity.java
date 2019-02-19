package uk.gov.ons.validation.service;

import uk.gov.ons.validation.entity.InputData;

import java.math.BigDecimal;

public class RuleZeroContinuity implements Rule {

    private InputData inputData;

    public RuleZeroContinuity( InputData sourceInputData) {
        inputData = sourceInputData;
    }

    public String getStatisticalVariableFormula() {
        return getFormula(inputData.getStatisticalVariable(), inputData.getComparisonVariable(), inputData.getThreshold());
    }

    public String getValueFormula() {
        return getFormula(inputData.getValue(), inputData.getComparisonValue(), inputData.getThreshold());
    }

    // { [ abs(formulaVariable > 0) AND comparisonVariable = 0 ] OR
    //   [ abs(formulaVariable = 0) AND comparisonVariable > 0 ] } AND
    //   abs(formulaVariable - comparisonVariable) > threshold
    private String getFormula( String formulaVariable, String comparisonVariable, String threshold ) {
        return "{ [ abs(" + formulaVariable + " > 0) AND " + comparisonVariable + " = 0 ] OR " +
                " [ abs(" + formulaVariable + " = 0) AND " + comparisonVariable + " > 0 ] } AND " +
                " abs(" + formulaVariable + " - " + comparisonVariable + " ) > " + threshold;
    }

    // We use decimal here rather than float/double to prevent issues with accuracy and numerical estimation
    // || = AND --- && = OR
    public boolean run() {
        BigDecimal threshold = new BigDecimal(inputData.getThreshold());
        BigDecimal value1 = new BigDecimal(inputData.getValue());
        BigDecimal value2 = new BigDecimal(inputData.getComparisonValue());
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
