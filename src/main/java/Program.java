import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class Program {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(in);
		String dimensions = scanner.nextLine();
		Plateau plateau = createPlateauFromDimensions(dimensions);
		
		int i = 0;
		while (true) {
			i++;
			
			String name = "Rover " + i + ":";
			String dropInfo = scanner.nextLine();

			try {
				Rover rover = dropRover(name, plateau, dropInfo);
				String instructions = scanner.nextLine();

				Direction[] instructionsCollection = new Instructions(instructions).getInstructions();
				rover.processInstructions(instructionsCollection);
				out.println("Report " + rover.reportStatus());
			} catch (Exception ex) {
				out.println(ex.getMessage());
			}
		}
		
		//scanner.close();
	}
	
	private static Rover dropRover(String id, Plateau plateau, String dropInfo) {
		Rover rover = new Rover(id);
		rover.dropRover(plateau,  dropInfo);
		return rover;
	}

	private static Plateau createPlateauFromDimensions(String dimensions) {
		String[] parts = dimensions.split(" ");
		int dimX = Integer.parseInt(parts[0]);
		int dimY = Integer.parseInt(parts[1]);
		return new Plateau(dimX, dimY);
	}
}
