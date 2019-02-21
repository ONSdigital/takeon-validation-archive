package uk.gov.ons.validation.test;

import org.junit.jupiter.api.Test;
import uk.gov.ons.validation.entity.InputData;
import uk.gov.ons.validation.service.RuleZeroContinuity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RuleZeroContinuityTest {

    @Test
    void GivenStatisticalVariableProvideQuestionFormula() {
        InputData sourceData = new InputData.Builder().statisticalVariable("q1").comparisonVariable("q2").threshold("2000").build();
        String expectedFormula = "{ [ abs(q1 > 0) AND q2 = 0 ] OR [ abs(q1 = 0) AND q2 > 0 ] } AND abs(q1 - q2 ) > 2000";
        RuleZeroContinuity validation = new RuleZeroContinuity(sourceData);
        assertEquals(expectedFormula, validation.getStatisticalVariableFormula());
    }

    @Test
    void GivenStatisticalVariableValueProvideValueFormula() {
        InputData sourceData = new InputData.Builder().value("6000").comparisonValue("0").threshold("2000").build();
        String expectedFormula = "{ [ abs(6000 > 0) AND 0 = 0 ] OR [ abs(6000 = 0) AND 0 > 0 ] } AND abs(6000 - 0 ) > 2000";
        RuleZeroContinuity validation = new RuleZeroContinuity(sourceData);
        assertEquals(expectedFormula, validation.getValueFormula());
    }

    @Test
    void GivenBlankValueTriggerValidation() {
        InputData sourceData = new InputData.Builder().value("").comparisonValue("").threshold("").build();
        RuleZeroContinuity validation = new RuleZeroContinuity(sourceData);
        assertThat(validation.run(), is(false));
    }


}