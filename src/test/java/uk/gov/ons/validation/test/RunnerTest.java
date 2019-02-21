package uk.gov.ons.validation.test;

import org.junit.jupiter.api.Test;
import uk.gov.ons.validation.service.Runner;

import static org.junit.jupiter.api.Assertions.*;

class RunnerTest {

    @Test
    void givenValidJsonWithoutMetadataTriggersValidationAndOutputsJsonBlankMeta() {
        String sourceJson = "{\"variable\":\"Q3451a\",\"value\":\"\"}";
        String jsonOutput = new Runner(sourceJson,"ValuePresent").ParseAndRun();
        assertEquals("{\"valueFormula\":\" != ''\",\"triggered\":false,\"metaData\":\"{}\"}",jsonOutput);
    }

    @Test
    void givenNullJsonOutputDefaultJson() {
        String jsonOutput = new Runner(null,"ValuePresent").ParseAndRun();
        assertEquals("{\"valueFormula\":\" != ''\",\"triggered\":false,\"metaData\":\"{}\"}",jsonOutput);
    }

    @Test
    void givenValidInputJsonThenValidationTriggersAndOutputsJson() {
        String sourceJson = "{\"value\":\"45\"}";
        String jsonOutput = new Runner(sourceJson,"ValuePresent").ParseAndRun();
        assertEquals("{\"valueFormula\":\"45 != ''\",\"triggered\":true,\"metaData\":\"{}\"}", jsonOutput);
    }

    @Test
    void givenValidInputJsonAndMetadataValidationNotTriggeredAndOutputsJsonWithMeta() {
        String metaData = "{\"instance\":\"0\",\"reference\":\"12345678901\",\"period\":\"201212\"}";
        String sourceJson = "{\"value\":\"\",\"metaData\":" + metaData + "}";
        String jsonOutput = new Runner(sourceJson,"ValuePresent").ParseAndRun();
        assertEquals("{\"valueFormula\":\" != ''\",\"triggered\":false,\"metaData\":" + metaData + "}", jsonOutput);
    }

    @Test
    void givenMalformedJSONReturnsGoodJsonStructureWithErrors() {
        String badJson = "{\"value\":\"4.2\",\"metaData\":{\"par\":}}";
        String expectedJsonOutput = "{\"valueFormula\":\" != ''\",\"triggered\":false,\"metaData\":\"{}\",\"error\":\"Error mapping source JSON\"}";
        String jsonOutput = new Runner(badJson,"ValuePresent").ParseAndRun();
        assertEquals(expectedJsonOutput, jsonOutput);
    }

    @Test
    void givenValidJsonGetStatisticalFormulaAndOutputJson(){
        String validJson = "{\"variable\":\"q205\"}";
        String output = new Runner(validJson,"ValuePresent").getStatisticalVariableFormula();
        assertEquals("{\"preCalculationFormula\":\"q205 != ''\",\"metaData\":\"{}\"}",output);
    }

    @Test
    void givenNullJsonGetStatisticalFormulaAndOutputDefaultJson(){
        String output = new Runner(null,"ValuePresent").getStatisticalVariableFormula();
        assertEquals("{\"preCalculationFormula\":\" != ''\",\"metaData\":\"{}\"}",output);
    }

}