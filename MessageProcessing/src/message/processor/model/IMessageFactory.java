package message.processor.model;

public interface IMessageFactory {

	public static final String OPS_ARG = "OPS";
	public static final String OPS_SALE = "SALE";
	public static final String OPS_ADJ = "ADJ";
	
	public static final String ITEM_ARG = "ITEM";
	public static final String PRODUCT_ARG = "PRODUCT";
	public static final String AMOUNT_ARG = "AMOUNT";
	
	public static final String TYPE_ARG = "TYPE";
	public static final String TYPE_ADD = "ADD";
	public static final String TYPE_SUBTRACT = "SUBTRACT";
	public static final String TYPE_MULTIPLY = "MULTIPLY";

	/**
	 * @param message The sale or adjustment message.
	 * @return The appropriate message object.
	 */
	public IMessageItem parse(String message) throws MessageFormatException;
}
