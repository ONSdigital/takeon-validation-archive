package uk.gov.ons.validation.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InputData {

    public String statisticalVariable = "";
    public String comparisonVariable;
    public String value = "";
    public String comparisonValue;
    public String threshold;
    public Object metaData = "{}";
    public Object errorMessage;

    public InputData() {}

    public String getStatisticalVariable() {
        return statisticalVariable;
    }

    public String getValue() {
        return value;
    }

    public String getComparisonVariable() {
        return comparisonVariable;
    }

    public String getComparisonValue() {
        return comparisonValue;
    }

    public String getThreshold() {
        return threshold;
    }

    public Object getMetaData() {

        return metaData;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "InputData{" +
                "statisticalVariable='" + statisticalVariable + '\'' +
                ", comparisonVariable='" + comparisonVariable + '\'' +
                ", value='" + value + '\'' +
                ", comparisonValue='" + comparisonValue + '\'' +
                ", threshold='" + threshold + '\'' +
                ", metaData=" + metaData +
                ", errorMessage=" + errorMessage +
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

    // Builder class to allow simpler construction of the InputData class
    public static class Builder {

        private String statisticalVariable = "";
        private String comparisonVariable;
        private String value = "";
        private String comparisonValue;
        private String threshold;
        private Object metaData = "{}";
        private Object errorMessage;

        public Builder(){}

        public Builder statisticalVariable(String statisticalVariable){
            this.statisticalVariable = statisticalVariable; return this;
        }

        public Builder comparisonVariable(String comparisonVariable){
            this.comparisonVariable = comparisonVariable; return this;
        }

        public Builder value(String value){
            this.value = value; return this;
        }

        public Builder comparisonValue(String comparisonValue){
            this.comparisonValue = comparisonValue; return this;
        }

        public Builder threshold(String threshold){
            this.threshold = threshold; return this;
        }

        public Builder metaData(String metaData){
            this.metaData = metaData; return this;
        }

        public Builder errorMessage(String errorMessage){
            this.errorMessage = errorMessage; return this;
        }

        public InputData build(){
            return new InputData(this);
        }
    }

    public InputData(Builder builder) {
        this.statisticalVariable = builder.statisticalVariable;
        this.comparisonVariable = builder.comparisonVariable;
        this.value = builder.value;
        this.comparisonValue = builder.comparisonValue;
        this.threshold = builder.threshold;
        this.metaData = builder.metaData;
        this.errorMessage = builder.errorMessage;
    }
}



