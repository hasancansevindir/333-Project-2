import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CAN_SEVINDIR_S12525 {
	private static final String HEX_REGEX = "\\p{XDigit}+";
	private static final Scanner SCANNER = new Scanner(System.in);

	private static String input(String message) {
		System.out.print(message);
		return SCANNER.nextLine();
	}

	private static void assertThat(boolean condition, String message) {
		if (condition)
			return;
		System.err.println(message);
		System.exit(-1);
	}

	public static void main(String[] args) {
		String amountStr = input("Enter how many outlets and lamps there will be: ");

		assertThat(amountStr.matches("\\d+"), "Input is not a valid positive integer number. Exiting...");

		int amount = Integer.parseInt(amountStr);
		String outletInput = input("Enter outlets: ");
		String[] outletArray = outletInput.split("\\s+");

		assertThat(outletArray.length == amount, "Amount entered and the actual amount of outlets do not match. Exiting...");

		for (String outlet : outletArray)
			assertThat(
					outlet.matches(HEX_REGEX) && outlet.length() == 2,
					"All outlets must be 2 digit positive hexadecimal numbers. Exiting..."
			);

		String lampInput = input("Enter lamps: ");
		String[] lampArray = lampInput.split("\\s+");

		assertThat(lampArray.length == amount, "Amount entered and the actual amount of lamps do not match. Exiting...");

		for (String lamp : lampArray)
			assertThat(
					lamp.matches(HEX_REGEX) && lamp.length() == 2,
					"All lamps must be 2 digit positive hexadecimal numbers. Exiting..."
			);

		HashMap<String, Integer> lampDict = new HashMap<>();
		for (int i = 0; i < lampArray.length; i++)
			lampDict.put(lampArray[i], i);

		for (String outlet : outletArray)
			assertThat(lampDict.containsKey(outlet), "Values of outlets and lamps do not match. Exiting...");

		int[] indexArray = new int[outletArray.length];
		for (int i = 0; i < indexArray.length; i++)
			indexArray[i] = lampDict.get(outletArray[i]);

		ArrayList<Integer> longestSequence = new ArrayList<>();
		ArrayList<Integer> currentSequence = new ArrayList<>();
		currentSequence.add(indexArray[0]);

		for (int i = 1; i < indexArray.length; i++) {
			int num = indexArray[i];

			if (num > currentSequence.get(currentSequence.size() - 1))
				currentSequence.add(num);
			else if (currentSequence.size() > longestSequence.size()) {
				longestSequence = new ArrayList<>(currentSequence);
				currentSequence = new ArrayList<>();
				currentSequence.add(num);
			}
		}

		if (currentSequence.size() > longestSequence.size())
			longestSequence = currentSequence;

		System.out.println(longestSequence.size());
		for (int index : longestSequence)
			System.out.print(lampArray[index] + " ");
	}
}