package uk.gov.ons.validation.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import uk.gov.ons.validation.entity.InputData;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class InputDataParser {

    private final String sourceJson;

    public InputDataParser(String sourceJson) {
        this.sourceJson = sourceJson;
    }

    public InputData parse() {
        InputData inputData;
        try {
            inputData = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                                          .readValue(sourceJson, InputData.class);
        } catch (JsonParseException e) {
            inputData = generateEmptyInputDataWithErrorDetails("Error parsing source JSON");
        } catch (JsonMappingException e) {
            inputData = generateEmptyInputDataWithErrorDetails("Error mapping source JSON");
        } catch (IOException e) {
            inputData = generateEmptyInputDataWithErrorDetails("Error processing source JSON (IO)");
        } catch (NullPointerException e) {
            inputData = generateEmptyInputDataWithErrorDetails("Error processing source JSON (NULL)");
        }
        return inputData;
    }

    private static InputData generateEmptyInputDataWithErrorDetails(String errorMessage) {
        return new InputData.Builder().metaData("{}").errorMessage(errorMessage).build();
    }

    @Override
    public String toString() {
        return "InputDataParser{" +
                "sourceJson=" + sourceJson +
                '}';
    }
}
