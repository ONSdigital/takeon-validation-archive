package uk.gov.ons.validation.test;

import org.junit.jupiter.api.Test;
import uk.gov.ons.validation.entity.InputData;
import uk.gov.ons.validation.service.RuleZeroContinuity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RuleZeroContinuityTest {

    @Test
    void givenVariablesProvideQuestionFormula() {
        InputData sourceData = new InputData.Builder().statisticalVariable("q1").comparisonVariable("q2").threshold("2000").build();
        String expectedFormula = "{ [ abs(q1 > 0) AND q2 = 0 ] OR [ abs(q1 = 0) AND q2 > 0 ] } AND abs(q1 - q2 ) > 2000";
        RuleZeroContinuity validation = new RuleZeroContinuity(sourceData);
        assertEquals(expectedFormula, validation.getStatisticalVariableFormula());
    }

    @Test
    void givenValuesProvideValueFormula() {
        InputData sourceData = new InputData.Builder().value("6000").comparisonValue("0").threshold("2000").build();
        String expectedFormula = "{ [ abs(6000 > 0) AND 0 = 0 ] OR [ abs(6000 = 0) AND 0 > 0 ] } AND abs(6000 - 0 ) > 2000";
        RuleZeroContinuity validation = new RuleZeroContinuity(sourceData);
        assertEquals(expectedFormula, validation.getValueFormula());
    }

    @Test
    void givenBlankVariablesProvideValueFormula() {
        InputData sourceData = new InputData.Builder().value("").comparisonValue("").threshold("").build();
        String expectedFormula = "{ [ abs(0 > 0) AND 0 = 0 ] OR [ abs(0 = 0) AND 0 > 0 ] } AND abs(0 - 0 ) > 0";
        RuleZeroContinuity validation = new RuleZeroContinuity(sourceData);
        assertEquals(expectedFormula, validation.getValueFormula());
    }

    @Test
    void givenBlankValueDoNotTriggerValidation() {
        InputData sourceData = new InputData.Builder().value("").comparisonValue("").threshold("").build();
        RuleZeroContinuity validation = new RuleZeroContinuity(sourceData);
        assertThat(validation.run(), is(false));
    }

    @Test
    void givenNullInputDoNotTriggerValidation() {
        RuleZeroContinuity validation = new RuleZeroContinuity(null);
        assertThat(validation.run(), is(false));
    }

    @Test
    void givenNullValuesDoNotTriggerValidation() {
        InputData sourceData = new InputData.Builder().value(null).comparisonValue(null).threshold(null).build();
        RuleZeroContinuity validation = new RuleZeroContinuity(sourceData);
        assertThat(validation.run(), is(false));
    }

    @Test
    void givenZeroToLargerValueTriggerValidation() {
        InputData sourceData = new InputData.Builder().value("0").comparisonValue("500.0001").threshold("500").build();
        RuleZeroContinuity validation = new RuleZeroContinuity(sourceData);
        assertThat(validation.run(), is(true));
    }

    @Test
    void givenZeroToLargerValueExactThresholdDoesNotTriggerValidation() {
        InputData sourceData = new InputData.Builder().value("0").comparisonValue("500.0001").threshold("500.0001").build();
        RuleZeroContinuity validation = new RuleZeroContinuity(sourceData);
        assertThat(validation.run(), is(false));
    }

    @Test
    void givenZeroToVeryLargeValueTriggerValidation() {
        InputData sourceData = new InputData.Builder().value("0").comparisonValue("-12345678901234567890.0001").threshold("100000000000000000").build();
        RuleZeroContinuity validation = new RuleZeroContinuity(sourceData);
        assertThat(validation.run(), is(true));
    }

    @Test
    void givenBothZeroDoesNotTriggerValidation() {
        InputData sourceData = new InputData.Builder().value("0").comparisonValue("0").threshold("0").build();
        RuleZeroContinuity validation = new RuleZeroContinuity(sourceData);
        assertThat(validation.run(), is(false));
    }

    @Test
    void givenBothNonZeroDoesNotTriggerValidation() {
        InputData sourceData = new InputData.Builder().value("1000").comparisonValue("-100").threshold("500").build();
        RuleZeroContinuity validation = new RuleZeroContinuity(sourceData);
        assertThat(validation.run(), is(false));
    }
}