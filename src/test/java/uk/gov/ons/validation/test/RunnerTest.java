package uk.gov.ons.validation.test;

import org.junit.jupiter.api.Test;
import uk.gov.ons.validation.service.Runner;

import static org.junit.jupiter.api.Assertions.*;

class RunnerTest {

    @Test
    void FullyPopulatedInputJsonTriggersValidationAndOutputsJsonBlankMeta() {
        String sourceJson = "{\"statisticalVariable\":\"Q3451a\",\"value\":\"\"}";
        String expectedJsonOutput = "{\"valueFormula\":\" != ''\",\"triggered\":false,\"metaData\":\"{}\"}";
        String jsonOutput = new Runner(sourceJson,"ValuePresent").ParseAndRun();
        assertEquals(expectedJsonOutput,jsonOutput);
    }

    @Test
    void NullInputJsonOutputsJsonBlankMeta() {
        String expectedJsonOutput = "{\"valueFormula\":\" != ''\",\"triggered\":false,\"metaData\":\"{}\"}";
        String jsonOutput = new Runner(null,"ValuePresent").ParseAndRun();
        assertEquals(expectedJsonOutput,jsonOutput);
    }

    @Test
    void FullyPopulatedInputJsonDoesTriggerValidationAndOutputsJsonNoMeta() {
        String sourceJson = "{\"value\":\"45\"}";
        String expectedJsonOutput = "{\"valueFormula\":\"45 != ''\",\"triggered\":true,\"metaData\":\"{}\"}";
        String jsonOutput = new Runner(sourceJson,"ValuePresent").ParseAndRun();
        assertEquals(expectedJsonOutput, jsonOutput);
    }

    @Test
    void FullyPopulatedInputJsonDoesNotTriggerValidationAndOutputsJsonWithMeta() {
        String metaData = "{\"instance\":\"0\",\"reference\":\"12345678901\",\"period\":\"201212\"}";
        String sourceJson = "{\"value\":\"\",\"metaData\":" + metaData + "}";
        String expectedJsonOutput = "{\"valueFormula\":\" != ''\",\"triggered\":false,\"metaData\":" + metaData + "}";

        String jsonOutput = new Runner(sourceJson,"ValuePresent").ParseAndRun();

        assertEquals(expectedJsonOutput, jsonOutput);
    }

    @Test
    void GivenMalformedJSONReturnsGoodJsonStructureWithErrors() {
        String badJson = "{\"value\":\"4.2\",\"metaData\":{\"par\":}}";
        String expectedJsonOutput = "{\"valueFormula\":\" != ''\",\"triggered\":false,\"metaData\":\"{}\",\"error\":\"Error mapping source JSON\"}";
        String jsonOutput = new Runner(badJson,"ValuePresent").ParseAndRun();
        assertEquals(expectedJsonOutput, jsonOutput);
    }

    @Test
    void givenValidJsonGetStatisticalFormula(){
        String emptyJson = "{\"statisticalVariable\":\"q205\"}";
        String output = new Runner(emptyJson,"ValuePresent").getStatisticalVariableFormula();
        assertEquals("{\"preCalculationFormula\":\"q205 != ''\",\"metaData\":\"{}\"}",output);
    }

}