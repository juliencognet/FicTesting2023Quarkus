package com.cgi.fic.tests.cucumber;

import static org.junit.Assert.assertEquals;

import com.cgi.fic.tests.service.Calculator;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class CalculatorSteps {

	// This could have the @Autowired Spring annotation to pull the bean
	// definition from cucumber.xml
	private Calculator calculator;

	private int actualSum;

	@Given("^I have a Calculator$")
	public void intializeCalculator() {
		this.calculator = new Calculator();
	}

	@When("^I add (-?\\d) and (-?\\d)$")
	public void whenIAddTwoNumbers(int firstNumber, int secondNumber) {
		this.actualSum = this.calculator.add(firstNumber, secondNumber);
	}

	@Then("^the sum should be (-?\\d)$")
	public void thenTheSumShouldBe(int expectedSum) {
		assertEquals("The expected sum does not equal the actual sum", expectedSum, this.actualSum);
	}
}