package message.processor.app;

import java.util.Random;

import message.processor.engine.ConsoleReportGenerator;
import message.processor.engine.IMessageProcessor;
import message.processor.engine.IReportGenerator;
import message.processor.engine.MessageProcessorEngine;
import message.processor.model.MessageFactory;
import message.processor.model.MessageFormatException;

public class MainClass {

	private static Random RANDOM = new Random();

	private IMessageProcessor engine = new MessageProcessorEngine(new MessageFactory());
	private IReportGenerator reportGenerator = new ConsoleReportGenerator(engine);

	private int messageCount = 0;

	public static void main(String[] args) {
		new MainClass().run();
	}

	private void run() {
		while(true) {
			handleMessage(generateMessage());
			messageCount++;

			if(messageCount == 50) {
				System.out.println("\n\n******************** Sale and Adjustment items report ********************");
				reportGenerator.createSaleItemsReport();
				System.out.println("**************************************************************************\n\n");

				// Shutdown the application
				System.out.println("Application is shutting down...");
				break;
			}

			if((messageCount % 10) == 0) {
				System.out.println("\n\n************************** Sold products report **************************");
				reportGenerator.createSoldProductReport();
				System.out.println("**************************************************************************\n\n");
			}
		}
	}

	private void handleMessage(String msg) {
		try {
			System.out.println("Processing message: " + msg);
			engine.processMessage(msg);
		} catch (MessageFormatException e) {
			System.err.println(e.getMessage());
		}
	}

	private String generateMessage() {
		// Test method to generate messages randomly
		if(messageCount == 0) {
			return "OPS=SALE;ITEM=50;PRODUCT=apple;amount=£1.50";
		} else if (messageCount == 1) {
			return "OPS=SALE;ITEM=25;PRODUCT=banana;amount=£3.35";
		} else if (messageCount == 2) {
			return "OPS=SALE;ITEM=25;PRODUCT=orange;amount=£2.75";
		} else if (messageCount == 3) {
			return "OPS=SALE;ITEM=25;PRODUCT=corn;amount=£0.63";
		}

		int nextNum = RANDOM.nextInt(8);
		if(nextNum == 0) {
			return "OPS=SALE;ITEM=15;PRODUCT=apple;amount=£1.50";
		} else if (nextNum == 1) {
			return "OPS=SALE;ITEM=5;PRODUCT=banana;amount=£1.35";
		} else if (nextNum == 2) {
			return "OPS=SALE;ITEM=7;PRODUCT=orange;amount=£1.75";
		} else if (nextNum == 3) {
			return "OPS=SALE;ITEM=8;PRODUCT=corn;amount=£0.63";
		} else if(nextNum == 4) {
			return "ops=adj;type=add;product=apple;amount=£1.50";
		} else if (nextNum == 5) {
			return "ops=adj;type=subtract;product=banana;amount=£0.50";
		} else if (nextNum == 6) {
			return "ops=adj;type=multiply;product=orange;amount=£1.50";
		} else if (nextNum == 7) {
			return "ops=adj;type=add;product=corn;amount=£1.50";
		}
		return "OPS=SALE;ITEM=15;PRODUCT=ketchup;amount=£1.50";
	}

}
