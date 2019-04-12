package uk.gov.ons.validation.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uk.gov.ons.validation.service.Runner;

@Api(value = "Validation Controller", description = "Main entry point for Validation API")
@RestController
@RequestMapping(value = "/validation")
public class ValidationController {

    // Invocation examples:
    // curl -H "Content-Type: application/json" -X GET -d '{"value":""}' http://localhost:00000/validation/valuepresent
    // curl -H "Content-Type: application/json" -X GET -d '{"value":"234235","value2":, "value3", "threshold":}' http://localhost:00000/validation/valuepresent
    // curl -H "Content-Type: application/json" -X GET -d '{"value":"Bananas"}' http://localhost:00000/validation/valuepresent
    // curl -H "Content-Type: application/json" -X GET -d '{"value":"","metaData":{"instance":"0","reference":"12345678901","period":"201212"}}' http://localhost:00000/validation/valuepresent

    @ApiOperation(value = "Run Validation: Value Present", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Response given to Validation call"/*, response = OutputData.class*/),
            @ApiResponse(code = 404, message = "Validation does not exist"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @PostMapping(value = "/valuepresent", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String valuePresent(@RequestBody String inputJson) {
        System.out.println(inputJson);
        String output = new Runner(inputJson,"ValuePresent").ParseAndRun();
        System.out.println(output);
        return output;
    }

    // Invocation example:
    // curl -H "Content-Type: application/json" -X GET -d {\"variable\":\"q1234\"} http://localhost:00000/validation/valuepresentformula

    @ApiOperation(value = "Validation formula: Value Present", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Formula response given to Validation call"/*, response = OutputData.class*/),
            @ApiResponse(code = 404, message = "Validation does not exist"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(value = "/valuepresentformula", produces = MediaType.APPLICATION_JSON_VALUE, consumes = "application/json")
    public String valuePresentFormula(@RequestBody String inputJson) {
        return new Runner(inputJson,"ValuePresent").getStatisticalVariableFormula();
    }

}