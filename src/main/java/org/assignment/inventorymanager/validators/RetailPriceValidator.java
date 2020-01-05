package org.assignment.inventorymanager.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

/**
 * <p>
 * This functionality validates transaction amount for empty/null or invalid
 * value format.
 * </p>
 * 
 * @author Arshal Jain
 *
 */
@Component
public class RetailPriceValidator implements ConstraintValidator<RetailPriceConstraint, Double> {

	@Override
	public void initialize(RetailPriceConstraint amountContraint) {
		// Do Nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
	 * javax.validation.ConstraintValidatorContext)
	 * 
	 * - throws InvalidTransactionRequestException for empty/null amount
	 * 
	 * - throws UnparsableTransactionException for invalid amount value
	 */
	@Override
	public boolean isValid(Double price, ConstraintValidatorContext context) {
		try {
			return price != null && price > 0;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

}
