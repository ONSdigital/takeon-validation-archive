package uk.gov.ons.validation.service;

import uk.gov.ons.validation.entity.InputData;

public class RuleFactory {

    /**
     * Creates a new instance of a validation class for the given text based description of a rule
     *
     * @param ruleType A text/string description that matches one of the expected validation rules
     * @param inputData Used by the instantiated validation rule
     * @return A concrete instance of a class implementing the Rule interface. Null object otherwise.
     */
    public static Rule getRule(String ruleType, InputData inputData){
        if (ruleType == null) return null;

        if (ruleType.equalsIgnoreCase("ValuePresent")) {
            return new RuleValuePresent(inputData);
        } else if (ruleType.equalsIgnoreCase("ZeroContinuity")) {
            return new RuleZeroContinuity(inputData);
        } else if (ruleType.equalsIgnoreCase("RuleLargeValue")) {
            return new RuleLargeValue(inputData);
        } else if (ruleType.equalsIgnoreCase("RuleValueChange")) {
            return new RuleValueChange(inputData);
        }

        return null;
    }
}
