package message.processor.model;

import java.math.BigDecimal;

public interface ISaleItem extends IMessageItem {

	/**
	 * @return The number of items sold as part of this sale.
	 */
	public long getNumberOfSoldItems();
	
	/**
	 * @return The unit price of the product in this sale.
	 */
	public BigDecimal getUnitPrice();
	
	/**
	 * @return The total value of the sale after all adjustments.
	 */
	public BigDecimal getTotalValue();
	
	/**
	 * Apply the given adjustment to this sale.
	 * 
	 * @param adjustment
	 *            The adjustment to be applied.
	 */
	public void addAdjustment(Adjustment adjustment);
	
	/**
	 * @return All the adjustments made to this sale
	 */
	public Adjustment[] getAdjustments();

}
