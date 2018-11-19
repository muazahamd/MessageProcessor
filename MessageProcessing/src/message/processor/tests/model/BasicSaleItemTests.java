package message.processor.tests.model;

import java.math.BigDecimal;

import org.junit.Test;

import message.processor.model.Adjustment;
import message.processor.model.Adjustment.ADJUSTMENT_OPS;
import message.processor.model.BasicSaleItem;
import message.processor.model.ISaleItem;

public class BasicSaleItemTests {

	@Test(expected=IllegalArgumentException.class)
	public void testNullProduct() {
		new BasicSaleItem(null, new BigDecimal("1"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEmptyProduct() {
		new BasicSaleItem("", new BigDecimal("1"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidNumberOfSoldItems() {
		new BasicSaleItem("apple", 0, new BigDecimal("1"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidUnitPrice() {
		new BasicSaleItem("apple", 1, new BigDecimal("-1"));
	}
	
	@Test
	public void testTotalValue() {
		ISaleItem item = new BasicSaleItem("apple", 2, new BigDecimal("5"));
		MessageFactoryTests.testSaleItem(item, "apple", new BigDecimal("10"));
	}
	
	@Test
	public void testAddAdjustment() {
		ISaleItem item = new BasicSaleItem("apple", 2, new BigDecimal("5"));
		MessageFactoryTests.testSaleItem(item, "apple", new BigDecimal("10"));
		
		item.addAdjustment(new Adjustment(new BigDecimal("5"), ADJUSTMENT_OPS.ADD));
		MessageFactoryTests.testSaleItem(item, "apple", new BigDecimal("15"));
		
		item.addAdjustment(new Adjustment(new BigDecimal("3"), ADJUSTMENT_OPS.SUBTRACT));
		MessageFactoryTests.testSaleItem(item, "apple", new BigDecimal("12"));
		
		item.addAdjustment(new Adjustment(new BigDecimal("2"), ADJUSTMENT_OPS.MULTIPLY));
		MessageFactoryTests.testSaleItem(item, "apple", new BigDecimal("24"));
	}
	
}
