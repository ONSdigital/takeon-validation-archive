package uk.gov.ons.validation.test;

import org.junit.jupiter.api.Test;
import uk.gov.ons.validation.service.Rule;
import uk.gov.ons.validation.service.RuleFactory;
import uk.gov.ons.validation.service.RuleValuePresent;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RuleFactoryTest {

    @Test
    void givenValuePresentTypeGetCorrectRuleType() {
        Rule rule = new RuleFactory().getRule("ValuePresent",null);
        assertThat(rule, instanceOf(RuleValuePresent.class));
    }

    @Test
    void givenUnknownRuleTypeGetNullObject() {
        Rule rule = new RuleFactory().getRule("ThisTypeDoesntExist",null);
        assertEquals(null, rule);
    }

    @Test
    void givenNullTypeGetNullObject() {
        Rule rule = new RuleFactory().getRule(null,null);
        assertEquals(null, rule);
    }

}