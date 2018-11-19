package message.processor.engine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import message.processor.model.IAdjustmentItem;
import message.processor.model.IMessageFactory;
import message.processor.model.IMessageItem;
import message.processor.model.ISaleItem;
import message.processor.model.MessageFormatException;

public class MessageProcessorEngine implements IMessageProcessor {

	private IMessageFactory messageFactory;
	private Map<String, List<ISaleItem>> productToSaleItems = new LinkedHashMap<>();

	private boolean isStopped;

	public MessageProcessorEngine(IMessageFactory messageFactory) {
		this.messageFactory = Objects.requireNonNull(messageFactory);
	}

	@Override
	public void processMessage(String msg) throws MessageFormatException {
		if(isStopped) {
			return;
		}

		IMessageItem msgItem = messageFactory.parse(msg);
		List<ISaleItem> saleItems = productToSaleItems.computeIfAbsent(msgItem.getProduct(), (k) -> new ArrayList<>());
		if(msgItem instanceof ISaleItem) {
			saleItems.add((ISaleItem) msgItem);
		} else if (msgItem instanceof IAdjustmentItem) {
			((IAdjustmentItem) msgItem).process(saleItems);
		} else {
			throw new MessageFormatException("Unkown message type...");
		}
	}

	@Override
	public void stop() {
		isStopped = true;
	}
	
	public String[] getProducts() {
		return productToSaleItems.keySet().toArray(new String[0]);
	}
	
	public ISaleItem[] getSales(String product) {
		if(product == null || product.isEmpty()) {
			return new ISaleItem[0];
		}
		
		List<ISaleItem> items = productToSaleItems.get(product);
		if(items == null) {
			return new ISaleItem[0];
		}
		
		return items.toArray(new ISaleItem[0]);
	}
	
}
