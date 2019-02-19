package uk.gov.ons.validation.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.ons.validation.entity.InputData;
import uk.gov.ons.validation.service.RuleValuePresent;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Validation - Value Present tests")
class RuleValuePresentTest {

    @Test
    void GivenStatisticalVariableProvideQuestionFormula() {
        String statisticalVariable = "SH648213vfH";
        String expectedFormula = statisticalVariable + " != ''";
        RuleValuePresent validation = new RuleValuePresent(new InputData.Builder().statisticalVariable(statisticalVariable).build());
        assertEquals(expectedFormula, validation.getStatisticalVariableFormula());
    }

    @Test
    void GivenStatisticalVariableValueProvideValueFormula() {
        String value = "648213";
        String expectedFormula = value + " != ''";
        RuleValuePresent validation = new RuleValuePresent(new InputData.Builder().value(value).build());
        assertEquals(expectedFormula, validation.getValueFormula());
    }

    @Test
    void GivenNonBlankValueTriggerValidation() {
        String value = "648213";
        RuleValuePresent validation = new RuleValuePresent(new InputData.Builder().value(value).build());
        assertThat(validation.run(), is(true));
    }

    @Test
    void GivenBlankValueTriggerValidation() {
        String value = "";
        RuleValuePresent validation = new RuleValuePresent(new InputData.Builder().value(value).build());
        assertThat(validation.run(), is(false));
    }

    @Test
    void GivenStringWithSpacesTriggerValidation() {
        String value = "   ";
        RuleValuePresent validation = new RuleValuePresent(new InputData.Builder().value(value).build());
        assertThat(validation.run(), is(true));
    }

    @Test
    void GivenNullInputDataDoNotTriggerValidation() {
        RuleValuePresent validation = new RuleValuePresent(null);
        assertThat(validation.run(), is(false));
    }

}
