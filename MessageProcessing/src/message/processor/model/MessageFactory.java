package message.processor.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import message.processor.model.Adjustment.ADJUSTMENT_OPS;

public class MessageFactory implements IMessageFactory {

	@Override
	public IMessageItem parse(String message) throws MessageFormatException {
		if(message == null || message.isEmpty()) {
			return null;
		}

		boolean isSale = false;
		boolean isAdjustment = false;
		String product = null;
		int numberOfItems = 0;
		BigDecimal amount = null;
		ADJUSTMENT_OPS adjustmentOps = null;

		for(String s : message.split(";")) {
			String[] arg = s.trim().split("=");
			if(arg.length != 2) {
				continue;
			}
			
			String key = arg[0].trim();
			String val = arg[1].trim();
			if(IMessageFactory.OPS_ARG.equalsIgnoreCase(key)) {
				isSale = IMessageFactory.OPS_SALE.equalsIgnoreCase(val);
				isAdjustment = IMessageFactory.OPS_ADJ.equalsIgnoreCase(val);
				if(!isSale && !isAdjustment) {
					String msg = String.format("Sale message contains illegal value for '%s', valid values are %s & %s.",
												IMessageFactory.OPS_ARG, IMessageFactory.OPS_SALE, IMessageFactory.OPS_ADJ);
					throw new MessageFormatException(msg);
				}
			} else if (IMessageFactory.ITEM_ARG.equalsIgnoreCase(key)) {
				try {
					numberOfItems = Integer.parseUnsignedInt(val);
				} catch (NumberFormatException e) {
					// An exception will be thrown below
				}
				if(numberOfItems <= 0) {
					String msg = String.format("Sale message contains illegal value for '%s', it should be > 0.",
												IMessageFactory.ITEM_ARG);
					throw new MessageFormatException(msg);
				}
			} else if (IMessageFactory.PRODUCT_ARG.equalsIgnoreCase(key)) {
				if(val.isEmpty()) {
					String msg = String.format("Sale message contains illegal value for '%s', it shouldn't be empty.",
												IMessageFactory.PRODUCT_ARG);
					throw new MessageFormatException(msg);
				}
				product = val;
			} else if (IMessageFactory.AMOUNT_ARG.equalsIgnoreCase(key)) {
				NumberFormat uk = NumberFormat.getCurrencyInstance(Locale.UK);
				if(uk instanceof DecimalFormat) {
					((DecimalFormat) uk).setParseBigDecimal(true);
				}
				try {
					Number n = uk.parse(val);
					if(n instanceof BigDecimal) {
						amount = (BigDecimal) n;
					} else {
						amount = new BigDecimal(n.longValue());
					}
				} catch(ParseException e) {
					// An exception will be thrown below
				}
				if(amount == null || amount.compareTo(new BigDecimal("0")) <= 0) {
					String msg = String.format("Sale message contains illegal value for '%s', it should follow the format %s.",
												IMessageFactory.AMOUNT_ARG, uk.format(99.99));
					throw new MessageFormatException(msg);
				}
			} else if(IMessageFactory.TYPE_ARG.equalsIgnoreCase(key)) {
				if(IMessageFactory.TYPE_ADD.equalsIgnoreCase(val)) {
					adjustmentOps = ADJUSTMENT_OPS.ADD;
				} else if(IMessageFactory.TYPE_SUBTRACT.equalsIgnoreCase(val)) {
					adjustmentOps = ADJUSTMENT_OPS.SUBTRACT;
				} else if(IMessageFactory.TYPE_MULTIPLY.equalsIgnoreCase(val)) {
					adjustmentOps = ADJUSTMENT_OPS.MULTIPLY;
				} else {
					String msg = String.format("Sale message contains illegal value for '%s', valid values are %s, %s & %s.",
												IMessageFactory.TYPE_ARG, IMessageFactory.TYPE_ADD, IMessageFactory.TYPE_SUBTRACT,
												IMessageFactory.TYPE_MULTIPLY);
					throw new MessageFormatException(msg);
				}
			}
		}
		
		// First check arguments which are required for both the operations
		if(product == null || product.isEmpty()) {
			String msg = String.format("Sale message does not contain the value for '%s', it should be present.",
										IMessageFactory.PRODUCT_ARG);
			throw new MessageFormatException(msg);
		}
		if(amount == null) {
			String msg = String.format("Sale message does not contain the value for '%s', it should be present.",
										IMessageFactory.AMOUNT_ARG);
			throw new MessageFormatException(msg);
		}
		if(isSale) {
			if(numberOfItems <= 0) {
				String msg = String.format("Sale message contains illegal value for '%s', it should be > 0.",
											IMessageFactory.ITEM_ARG);
				throw new MessageFormatException(msg);
			}
			return new BasicSaleItem(product, numberOfItems, amount);
		} else if(isAdjustment) {
			if(adjustmentOps == null) {
				String msg = String.format("Sale message does not contain the value for '%s', valid values are %s, %s & %s.",
											IMessageFactory.TYPE_ARG, IMessageFactory.TYPE_ADD, IMessageFactory.TYPE_SUBTRACT,
											IMessageFactory.TYPE_MULTIPLY);
				throw new MessageFormatException(msg);
			}
			return new AdjustmentItem(product, new Adjustment(amount, adjustmentOps));
		} else {
			String msg = String.format("Sale message doesn't contain the value for '%s', valid values are %s & %s.",
										IMessageFactory.OPS_ARG, IMessageFactory.OPS_SALE, IMessageFactory.OPS_ADJ);
			throw new MessageFormatException(msg);
		}
	}
}
