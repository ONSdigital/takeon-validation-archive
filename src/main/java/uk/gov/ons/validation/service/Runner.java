package uk.gov.ons.validation.service;

import uk.gov.ons.validation.entity.InputData;
import uk.gov.ons.validation.entity.OutputData;

public class Runner {

    private final String sourceJson;
    private final String ruleType;

    public Runner(String sourceJson, String ruleType) {
        this.sourceJson = (sourceJson == null) ? "{}" : sourceJson; // Coalesce null input to empty Json
        this.ruleType = (ruleType == null) ? "{}" : ruleType; // Coalesce null input to empty String
    }

    public String ParseAndRun(){
        InputData inputData = new InputDataParser(sourceJson).parse();
        Rule validationRule = new RuleFactory().getRule(ruleType, inputData);
        OutputData outputData = runValidation(validationRule,inputData);
        return outputData.toJson();
    }

    public static OutputData runValidation(Rule rule, InputData inputData){
        Boolean isTriggered = rule.run();
        String valueFormula = rule.getValueFormula();
        return new OutputData(valueFormula,isTriggered,inputData.getMetaData(),inputData.getErrorMessage());
    }

    public String getStatisticalVariableFormula() {
        InputData inputData = new InputDataParser(sourceJson).parse();
        Rule validationRule = new RuleFactory().getRule(ruleType, inputData);
        OutputData outputData = new OutputData(validationRule.getStatisticalVariableFormula(),inputData.getMetaData(),inputData.getErrorMessage());
        return outputData.toJson();
    }

}
