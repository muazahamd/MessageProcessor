package message.processor.engine;

public interface IReportGenerator {

	/**
	 * Generates the reports of all the sold products.
	 */
	public void createSoldProductReport();

	/**
	 * Generates the reports of the sale items reporting their adjustments as well.
	 */
	public void createSaleItemsReport();

}
