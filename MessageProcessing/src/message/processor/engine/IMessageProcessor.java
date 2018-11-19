package message.processor.engine;

import message.processor.model.ISaleItem;
import message.processor.model.MessageFormatException;

public interface IMessageProcessor {

	/**
	 * Process the given message.
	 * 
	 * @param msg
	 *            The sale/adjust message to process.
	 */
	public void processMessage(String msg) throws MessageFormatException;
	
	/**
	 * Stops the message processing.
	 */
	public void stop();
	
	/**
	 * @return The products which are sold.
	 */
	public String[] getProducts();
	
	/**
	 * @param product
	 *            The product whose sale items to get.
	 * @return The sale items for the given product.
	 */
	public ISaleItem[] getSales(String product);
}
