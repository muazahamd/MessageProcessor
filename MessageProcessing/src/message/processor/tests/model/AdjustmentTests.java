package message.processor.tests.model;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import message.processor.model.Adjustment;
import message.processor.model.Adjustment.ADJUSTMENT_OPS;

public class AdjustmentTests {

	@Test(expected=IllegalArgumentException.class)
	public void testNullAdjustmentValue() {
		new Adjustment(null, ADJUSTMENT_OPS.ADD);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidAdjustmentValue() {
		new Adjustment(new BigDecimal("-2"), ADJUSTMENT_OPS.ADD);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullAdjustmentOpsType() {
		new Adjustment(new BigDecimal("2"), null);
	}
	
	@Test
	public void testAddOperation() {
		Adjustment adj = new Adjustment(new BigDecimal("2"), ADJUSTMENT_OPS.ADD);
		BigDecimal actual = adj.perform(new BigDecimal("3"));
		Assert.assertEquals(new BigDecimal("5"), actual);
	}
	
	@Test
	public void testSubtractOperation() {
		Adjustment adj = new Adjustment(new BigDecimal("3"), ADJUSTMENT_OPS.SUBTRACT);
		BigDecimal actual = adj.perform(new BigDecimal("10"));
		Assert.assertEquals(new BigDecimal("7"), actual);
	}
	
	@Test
	public void testMultiplyOperation() {
		Adjustment adj = new Adjustment(new BigDecimal("3"), ADJUSTMENT_OPS.MULTIPLY);
		BigDecimal actual = adj.perform(new BigDecimal("10"));
		Assert.assertEquals(new BigDecimal("30"), actual);
	}

}
