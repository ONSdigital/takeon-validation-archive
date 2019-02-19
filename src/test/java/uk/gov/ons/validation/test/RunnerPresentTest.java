package uk.gov.ons.validation.test;

import org.junit.jupiter.api.Test;
import uk.gov.ons.validation.service.Runner;

import static org.junit.jupiter.api.Assertions.*;

class RunnerPresentTest {

    @Test
    void FullyPopulatedInputJsonTriggersValidationAndOutputsJsonBlankMeta() {

        // Setup
        String sourceJson = "{\"statisticalVariable\":\"Q3451a\",\"value\":\"\"}";
        String expectedJsonOutput = "{\"valueFormula\":\" != ''\",\"triggered\":false,\"metaData\":\"{}\"}";

        // Run
        String jsonOutput = new Runner(sourceJson,"ValuePresent").ParseAndRun();

        assertEquals(expectedJsonOutput,jsonOutput);
    }

    @Test
    void NullInputJsonOutputsJsonBlankMeta() {

        // Setup
        String expectedJsonOutput = "{\"valueFormula\":\" != ''\",\"triggered\":false,\"metaData\":\"{}\"}";

        // Run
        String jsonOutput = new Runner(null,"ValuePresent").ParseAndRun();

        assertEquals(expectedJsonOutput,jsonOutput);
    }

    @Test
    void FullyPopulatedInputJsonDoesTriggerValidationAndOutputsJsonNoMeta() {

        // Setup
        String sourceJson = "{\"value\":\"45\"}";
        String expectedJsonOutput = "{\"valueFormula\":\"45 != ''\",\"triggered\":true,\"metaData\":\"{}\"}";

        // Run
        String jsonOutput = new Runner(sourceJson,"ValuePresent").ParseAndRun();

        assertEquals(expectedJsonOutput, jsonOutput);
    }

    @Test
    void FullyPopulatedInputJsonDoesNotTriggerValidationAndOutputsJsonWithMeta() {

        // Setup
        String metaData = "{\"instance\":\"0\",\"reference\":\"12345678901\",\"period\":\"201212\"}";
        String sourceJson = "{\"value\":\"\",\"metaData\":" + metaData + "}";
        String expectedJsonOutput = "{\"valueFormula\":\" != ''\",\"triggered\":false,\"metaData\":" + metaData + "}";

        // Run
        String jsonOutput = new Runner(sourceJson,"ValuePresent").ParseAndRun();

        assertEquals(expectedJsonOutput, jsonOutput);
    }

    @Test
    void GivenMalformedJSONReturnsGoodJsonStructureWithErrors() {

        // Setup
        String badJson = "{\"value\":\"4.2\",\"metaData\":{\"par\":}}";
        String expectedJsonOutput = "{\"valueFormula\":\"  != ''\",\"triggered\":true,\"metaData\":\"{}\",\"error\":\"Error mapping source JSON\"}";

        // Run
        String jsonOutput = new Runner(badJson,"ValuePresent").ParseAndRun();

        assertEquals(expectedJsonOutput, jsonOutput);

    }


}