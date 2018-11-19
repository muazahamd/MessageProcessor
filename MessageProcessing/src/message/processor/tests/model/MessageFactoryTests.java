package message.processor.tests.model;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import message.processor.model.Adjustment.ADJUSTMENT_OPS;
import message.processor.model.Adjustment;
import message.processor.model.IAdjustmentItem;
import message.processor.model.IMessageItem;
import message.processor.model.ISaleItem;
import message.processor.model.MessageFactory;
import message.processor.model.MessageFormatException;

public class MessageFactoryTests {

	@Test(expected=MessageFormatException.class)
	public void testInvalidOperationType() throws MessageFormatException {
		MessageFactory msgFactory = new MessageFactory();
		// Price is expected to be in the pounds instead of in the pence
		msgFactory.parse("OPS=invalid;ITEM=1;PRODUCT=apple;amount=10p");
	}
	
	@Test(expected=MessageFormatException.class)
	public void testMissingOperationType() throws MessageFormatException {
		MessageFactory msgFactory = new MessageFactory();
		// Price is expected to be in the pounds instead of in the pence
		msgFactory.parse("ITEM=1;PRODUCT=apple;amount=10p");
	}
	
	@Test(expected=MessageFormatException.class)
	public void testInvalidNumberOfItems() throws MessageFormatException {
		MessageFactory msgFactory = new MessageFactory();
		// Price is expected to be in the pounds instead of in the pence
		msgFactory.parse("OPS=SALE;ITEM=0;PRODUCT=apple;amount=10p");
	}
	
	@Test(expected=MessageFormatException.class)
	public void testMissingNumberOfItems() throws MessageFormatException {
		MessageFactory msgFactory = new MessageFactory();
		// Price is expected to be in the pounds instead of in the pence
		msgFactory.parse("OPS=SALE;PRODUCT=apple;amount=10p");
	}
	
	@Test(expected=MessageFormatException.class)
	public void testMissingProduct() throws MessageFormatException {
		MessageFactory msgFactory = new MessageFactory();
		// Price is expected to be in the pounds instead of in the pence
		msgFactory.parse("OPS=SALE;ITEM=1;amount=10p");
	}
	
	@Test(expected=MessageFormatException.class)
	public void testInvalidAmountFormat() throws MessageFormatException {
		MessageFactory msgFactory = new MessageFactory();
		// Price is expected to be in the pounds instead of in the pence
		msgFactory.parse("OPS=SALE;ITEM=1;PRODUCT=apple;amount=10p");
	}
	
	@Test(expected=MessageFormatException.class)
	public void testAmountMissingFromSaleMessage() throws MessageFormatException {
		MessageFactory msgFactory = new MessageFactory();
		// Price is expected to be in the pounds instead of pence
		msgFactory.parse("OPS=SALE;ITEM=1;PRODUCT=apple");
	}
	
	@Test(expected=MessageFormatException.class)
	public void testAmountMissingFromAdjustmentMessage() throws MessageFormatException {
		MessageFactory msgFactory = new MessageFactory();
		// Price is expected to be in the pounds instead of pence
		msgFactory.parse("OPS=SALE;ITEM=1;PRODUCT=apple");
	}
	
	@Test
	public void testTotalAmountInSaleMessage() throws MessageFormatException {
		MessageFactory msgFactory = new MessageFactory();
		// Price is expected to be in the pounds instead of pence
		IMessageItem item = msgFactory.parse("OPS=SALE;ITEM=1;PRODUCT=apple;amount=£2.03");
		testSaleItem((ISaleItem) item, "apple", new BigDecimal("2.03"));
	}
	
	@Test
	public void testTotalAmountInMultiItemsSaleMessage() throws MessageFormatException {
		MessageFactory msgFactory = new MessageFactory();
		// Price is expected to be in the pounds instead of pence
		IMessageItem item = msgFactory.parse("OPS=SALE;ITEM=5;PRODUCT=apple;amount=£2.03");
		testSaleItem((ISaleItem) item, "apple", new BigDecimal("10.15"));
	}
	
	@Test
	public void testAmountInAdjustmentMessage() throws MessageFormatException {
		MessageFactory msgFactory = new MessageFactory();
		// Price is expected to be in the pounds instead of pence
		IMessageItem item = msgFactory.parse("OPS=ADJ;TYPE=ADD;PRODUCT=apple;amount=£1.03");
		testAdjustmentItem((IAdjustmentItem) item, "apple", new Adjustment(new BigDecimal("1.03"), ADJUSTMENT_OPS.ADD));
	}
	
	public static void testSaleItem(ISaleItem item, String eProduct, BigDecimal eTotalValue) {
		Assert.assertEquals(eProduct, item.getProduct());
		Assert.assertEquals(eTotalValue, item.getTotalValue());
		
	}

	public static void testAdjustmentItem(IAdjustmentItem item, String eProduct, Adjustment eAdjustment) {
		Assert.assertEquals(eProduct, item.getProduct());
		Assert.assertEquals(eAdjustment, item.getAdjustment());
	}
}
