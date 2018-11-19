package message.processor.engine;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

import message.processor.model.Adjustment;
import message.processor.model.ISaleItem;

public class ConsoleReportGenerator implements IReportGenerator {

	private IMessageProcessor messageProcessor;

	public ConsoleReportGenerator(IMessageProcessor messageProcessor) {
		this.messageProcessor = Objects.requireNonNull(messageProcessor);
	}

	@Override
	public void createSoldProductReport() {
		NumberFormat uk = NumberFormat.getCurrencyInstance(Locale.UK);
		System.out.println("Product\t\t\tItems\t\t\tTotal Price");
		for(String product : messageProcessor.getProducts()) {
			int numberOfItems = 0;
			BigDecimal totalPrice = new BigDecimal("0");
			for(ISaleItem item : messageProcessor.getSales(product)) {
				numberOfItems += item.getNumberOfSoldItems();
				totalPrice = totalPrice.add(item.getTotalValue());
			}
			System.out.println(String.format("%s\t\t\t%d\t\t\t%s", product,
															   numberOfItems,
															   uk.format(totalPrice)));
		}
	}

	@Override
	public void createSaleItemsReport() {
		NumberFormat uk = NumberFormat.getCurrencyInstance(Locale.UK);
		System.out.println("Product\t\t\tItems\t\t\tPrice");
		for(String product : messageProcessor.getProducts()) {
			for(ISaleItem item : messageProcessor.getSales(product)) {
				System.out.println(String.format("%s\t\t\t%d\t\t\t%s", product,
																   item.getNumberOfSoldItems(),
																   uk.format(item.getTotalValue())));
				createAdjustmentReport(item);
			}
		}
	}

	public void createAdjustmentReport(ISaleItem item) {
		if(item == null) {
			return;
		}

		for(Adjustment a : item.getAdjustments()) {
			System.out.println("  -- " + a);
		}
	}

}
