package message.processor.model;

import java.util.List;
import java.util.Objects;

public class AdjustmentItem implements IAdjustmentItem {
	
	public String product;
	public Adjustment adjustment;

	public AdjustmentItem(String product, Adjustment adjustment) {
		this.product = Objects.requireNonNull(product);
		this.adjustment = Objects.requireNonNull(adjustment);
	}

	@Override
	public String getProduct() {
		return product;
	}

	@Override
	public Adjustment getAdjustment() {
		return new Adjustment(adjustment);
	}
	
	@Override
	public void process(List<ISaleItem> allSales) {
		allSales.forEach(e -> {
			e.addAdjustment(adjustment);
		});
	}

}
