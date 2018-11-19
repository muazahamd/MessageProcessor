package message.processor.tests.engine;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import message.processor.engine.MessageProcessorEngine;
import message.processor.model.ISaleItem;
import message.processor.model.MessageFactory;
import message.processor.model.MessageFormatException;

public class MessageProcessorEngineTests {

	@Test(expected=NullPointerException.class)
	public void testNullMessageFactory() {
		new MessageProcessorEngine(null);
	}
	
	@Test
	public void testProcessMessage() throws MessageFormatException {
		MessageProcessorEngine engine = new MessageProcessorEngine(new MessageFactory());
		Assert.assertEquals(0, engine.getProducts().length);

		engine.processMessage("OPS=SALE;ITEM=5;PRODUCT=apple;amount=£2.50");
		Assert.assertArrayEquals(new String[] {"apple"}, engine.getProducts());

		ISaleItem[] items = engine.getSales("apple");
		Assert.assertEquals(1, items.length);
		Assert.assertEquals(new BigDecimal("12.50"), items[0].getTotalValue());
		
		engine.processMessage("ops=adj;type=add;PRODUCT=apple;amount=£2.50");
		Assert.assertArrayEquals(new String[] {"apple"}, engine.getProducts());

		items = engine.getSales("apple");
		Assert.assertEquals(1, items.length);
		Assert.assertEquals(new BigDecimal("15.00"), items[0].getTotalValue());
		
		engine.processMessage("OPS=SALE;ITEM=1;PRODUCT=apple;amount=£5.00");
		Assert.assertArrayEquals(new String[] {"apple"}, engine.getProducts());

		items = engine.getSales("apple");
		Assert.assertEquals(2, items.length);
		Assert.assertEquals(new BigDecimal("15.00"), items[0].getTotalValue());
		Assert.assertEquals(new BigDecimal("5.00"), items[1].getTotalValue());
		
		engine.processMessage("ops=adj;type=subtract;PRODUCT=apple;amount=£2.50");
		Assert.assertArrayEquals(new String[] {"apple"}, engine.getProducts());

		items = engine.getSales("apple");
		Assert.assertEquals(2, items.length);
		Assert.assertEquals(new BigDecimal("12.50"), items[0].getTotalValue());
		Assert.assertEquals(new BigDecimal("2.50"), items[1].getTotalValue());
	}

}
