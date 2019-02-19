package uk.gov.ons.validation.test;

import org.junit.jupiter.api.Test;
import uk.gov.ons.validation.service.InputDataParser;
import uk.gov.ons.validation.entity.InputData;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InputDataParserTest {

    @Test
    void GivenNullJsonReturnValidInputDataWithErrorMessage() {
        InputDataParser validationParser = new InputDataParser(null);
        InputData inputData = validationParser.parse();
        assertNotNull(inputData);
        assertEquals("{}",inputData.metaData);
        assertEquals("Error processing source JSON (NULL)",inputData.errorMessage);
    }

    @Test
    void GivenPopulatedValidJsonInputDataIsPopulatedAsExpected() {
        String metaData = "{\"instance\":\"0\",\"reference\":\"12345678901\",\"period\":\"201212\"}";
        String sourceJson = "{\"statisticalVariable\":\"Q3451a\",\"value\":\"12345\",\"metaData\":" + metaData + "}";

        InputDataParser validationParser = new InputDataParser(sourceJson);
        InputData inputData = validationParser.parse();

        assertEquals("Q3451a",inputData.getStatisticalVariable());
        assertEquals("12345",inputData.getValue());
    }

    @Test
    void GivenValidButUnexpectedJsonAttributesIgnoredThem() {
        String metaData = "{\"instance\":\"0\",\"reference\":\"12345678901\",\"period\":\"201212\"}";
        String sourceJson = "{\"toast\":\"fish\",\"value\":\"12345\",\"metaData\":" + metaData + "}";

        InputDataParser validationParser = new InputDataParser(sourceJson);
        InputData inputData = validationParser.parse();

        assertEquals("12345",inputData.getValue());
        assertEquals(null,inputData.errorMessage);
    }

}

/*
        // Setup
        String metaData = "{\"statisticalVariable\":\"Q3451a\",\"instance\":\"0\",\"reference\":\"12345678901\",\"period\":\"201212\"}";
        String sourceJson = "{\"toast:\":\"fish\",\"value\":\"12345\",\"metaData\":" + metaData + "}";
        String expectedJson = "{\"value\":\"12345\",\"metaData\":" + metaData + "}";

        // Setup
        String metaData = "{\"statisticalVariable\":\"Q3451a\",\"instance\":\"0\",\"reference\":\"12345678901\",\"period\":\"201212\"}";
        String expectedJson = "{\"value\":\"12345\",\"metaData\":" + metaData + "}";
 */