import java.util.ArrayList;

public class Instructions {
	private String instructions;
	public Instructions(String instructions) {
		this.instructions = instructions;
	}
	
	public Direction[] getInstructions() {
		ArrayList<Direction> result = new ArrayList<Direction>();
		
		for (char c: instructions.toCharArray()) {
			switch (c) {
				case 'L': result.add(Direction.LEFT); break;
				case 'M': result.add(Direction.MOVE); break;
				case 'R': result.add(Direction.RIGHT); break;
				default: throw new UnknownInstructionException(c);
			}
		}
		
		return result.toArray(new Direction[result.size()]);
	}
}
