package uk.gov.ons.validation.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InputData {

    public String variable = "";
    public String comparisonVariable;
    public String value = "";
    public String comparisonValue;
    public String threshold;
    public String absoluteIncreaseThreshold;
    public String absoluteDecreaseThreshold;
    public String percentIncreaseThreshold;
    public String percentDecreaseThreshold;
    public Object metaData = "{}";
    public Object errorMessage;

    public InputData() {}

    public String getVariable() {
        return variable;
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

    public String getAbsoluteIncreaseThreshold() {
        return absoluteIncreaseThreshold;
    }

    public String getAbsoluteDecreaseThreshold() {
        return absoluteDecreaseThreshold;
    }

    public String getPercentIncreaseThreshold() {
        return percentIncreaseThreshold;
    }

    public String getPercentDecreaseThreshold() {
        return percentDecreaseThreshold;
    }

    @Override
    public String toString() {
        return "InputData{" +
                "variable='" + variable + '\'' +
                ", comparisonVariable='" + comparisonVariable + '\'' +
                ", value='" + value + '\'' +
                ", comparisonValue='" + comparisonValue + '\'' +
                ", threshold='" + threshold + '\'' +
                ", absoluteIncreaseThreshold='" + absoluteIncreaseThreshold + '\'' +
                ", absoluteDecreaseThreshold='" + absoluteDecreaseThreshold + '\'' +
                ", percentIncreaseThreshold='" + percentIncreaseThreshold + '\'' +
                ", percentDecreaseThreshold='" + percentDecreaseThreshold + '\'' +
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
        private String absoluteIncreaseThreshold;
        private String absoluteDecreaseThreshold;
        private String percentIncreaseThreshold;
        private String percentDecreaseThreshold;
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

        public Builder absoluteIncreaseThreshold(String absoluteIncreaseThreshold){
            this.absoluteIncreaseThreshold = absoluteIncreaseThreshold; return this;
        }

        public Builder absoluteDecreaseThreshold(String absoluteDecreaseThreshold){
            this.absoluteDecreaseThreshold = absoluteDecreaseThreshold; return this;
        }

        public Builder percentIncreaseThreshold(String percentIncreaseThreshold){
            this.percentIncreaseThreshold = percentIncreaseThreshold; return this;
        }

        public Builder percentDecreaseThreshold(String percentDecreaseThreshold){
            this.percentDecreaseThreshold = percentDecreaseThreshold; return this;
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
        this.variable = builder.statisticalVariable;
        this.comparisonVariable = builder.comparisonVariable;
        this.value = builder.value;
        this.comparisonValue = builder.comparisonValue;
        this.threshold = builder.threshold;
        this.absoluteIncreaseThreshold= builder.absoluteIncreaseThreshold;
        this.absoluteDecreaseThreshold= builder.absoluteDecreaseThreshold;
        this.percentIncreaseThreshold= builder.percentIncreaseThreshold;
        this.percentDecreaseThreshold= builder.percentDecreaseThreshold;
        this.metaData = builder.metaData;
        this.errorMessage = builder.errorMessage;
    }
}



