package message.processor.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class Adjustment {
	public enum ADJUSTMENT_OPS {ADD, SUBTRACT, MULTIPLY };
	
	private final BigDecimal adjustment;
	private final ADJUSTMENT_OPS operation;

	public Adjustment(Adjustment from) {
		this(new BigDecimal(from.adjustment.toString()), from.operation);
	}
	
	public Adjustment(BigDecimal adjustment, ADJUSTMENT_OPS operation) {
		if(adjustment == null || adjustment.compareTo(new BigDecimal("0")) <= 0 || operation == null) {
			throw new IllegalArgumentException();
		}
		
		this.adjustment = adjustment;
		this.operation = operation;
	}
	
	public BigDecimal perform(BigDecimal value) {
		switch(operation) {
			case ADD:
				return value.add(adjustment);
			case SUBTRACT:
				return value.subtract(adjustment);
			case MULTIPLY:
				return value.multiply(adjustment);
		}
		return value;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adjustment == null) ? 0 : adjustment.hashCode());
		result = prime * result + ((operation == null) ? 0 : operation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Adjustment other = (Adjustment) obj;
		return Objects.equals(adjustment, other.adjustment) && Objects.equals(operation, other.operation);
	}

	@Override
	public String toString() {
		NumberFormat uk = NumberFormat.getCurrencyInstance(Locale.UK);
		switch(operation) {
			case ADD:
				return String.format("Added the %s amount", uk.format(adjustment));
			case SUBTRACT:
				return String.format("Subtracted the %s amount", uk.format(adjustment));
			case MULTIPLY:
				return String.format("Multiplied by the %s amount", uk.format(adjustment));
		}
		return super.toString();
	}
	
}
