package message.processor.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BasicSaleItem implements ISaleItem {
	private String product;
	private long numberOfSoldItems;
	private BigDecimal unitPrice;
	private BigDecimal totalValue;
	private List<Adjustment> adjustments = new ArrayList<>();
	
	public BasicSaleItem(String product, BigDecimal unitPrice) {
		this(product, 1, unitPrice);
	}
	
	public BasicSaleItem(String product, long numberOfSoldItems, BigDecimal unitPrice) {
		if(numberOfSoldItems <= 0 || unitPrice == null || unitPrice.compareTo(new BigDecimal("0")) <= 0) {
			throw new IllegalArgumentException("Number of sold items & unit price should be > 0");
		}
		if(product == null || product.isEmpty()) {
			throw new IllegalArgumentException("The product name should be present");
		}
		this.product = product;
		this.numberOfSoldItems = numberOfSoldItems;
		this.unitPrice = unitPrice;
		this.totalValue = unitPrice.multiply(new BigDecimal(numberOfSoldItems));
	}

	@Override
	public String getProduct() {
		return product;
	}

	public long getNumberOfSoldItems() {
		return numberOfSoldItems;
	}
	
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	
	@Override
	public BigDecimal getTotalValue() {
		return totalValue;
	}
	
	@Override
	public void addAdjustment(Adjustment adjustment) {
		if(adjustment == null) {
			return;
		}
		
		totalValue = adjustment.perform(totalValue);
		adjustments.add(adjustment);
	}

	@Override
	public Adjustment[] getAdjustments() {
		return adjustments.toArray(new Adjustment[0]);
	}
	
}
