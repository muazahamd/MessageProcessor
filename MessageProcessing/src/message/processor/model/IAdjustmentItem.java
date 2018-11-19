package message.processor.model;

import java.util.List;

public interface IAdjustmentItem extends IMessageItem {

	public Adjustment getAdjustment();
	
	/**
	 * Apply the adjustments to all the given sale items.
	 * 
	 * @param allSales
	 *            All sale items related to the product of this message.
	 */
	public void process(List<ISaleItem> allSales);
}
