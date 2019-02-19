package uk.gov.ons.validation.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OutputData {

   public String preCalculationFormula = null;
   public String valueFormula = null;
   public Boolean triggered = null;
   public Object metaData = null;
   public Object error = "";

    public OutputData(String valueFormula, Boolean triggered, Object metaData, Object error) {
        this.valueFormula = valueFormula;
        this.triggered = triggered;
        this.metaData = metaData;
        this.error = error;
    }

    public OutputData(String preCalculationFormula, Object metaData, Object error) {
        this.preCalculationFormula = preCalculationFormula;
        this.metaData = metaData;
        this.error = error;
    }

    @Override
    public String toString() {
        return "OutputData{" +
                "preCalculationFormula='" + preCalculationFormula + '\'' +
                ", valueFormula='" + valueFormula + '\'' +
                ", triggered=" + triggered +
                ", metaData=" + metaData +
                ", error=" + error +
                '}';
    }

    public String toJson() {
        try {
            return new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "{\"Error\":\"Failure processing JSON\"}";
        }
    }

    public OutputData() {}
}



