public class Rover {
	
	private Plateau plateau;
	private Position position;	
	private CardinalPoint cardinalPoint;
	private String name;
	
	public Rover(String name) {
		this.name = name;
	}
	
	public void dropRover(Plateau plateau, String args) {		
		String[] parts = args.split(" ");
		int x = Integer.parseInt(parts[0]);// Character.getNumericValue(parts[0].toCharArray()[0]);
		int y = Integer.parseInt(parts[1]);// Character.getNumericValue(parts[1].toCharArray()[0]);
		CardinalPoint cardinalPoint = ToHeading(parts[2].toCharArray()[0]);
		dropRover(plateau, new Position(x, y), cardinalPoint);
	}

	public void dropRover(Plateau plateau, Position position, CardinalPoint cardinalPoint) {
		if (!position.IsOnPlateau(plateau)) {
			throw new PositionNotOnPlateauException(plateau, position);
		}
		
		if (plateau.isOccupied(position)) {
			throw new RuntimeException("Already occupied by a rover!");
		}
		
		this.plateau = plateau;
		this.position = position;
		this.cardinalPoint = cardinalPoint;
		
		plateau.addRover(this);
	}
	
	public void dropRover(Plateau plateau, int posX, int posY, char heading) {
		dropRover(plateau, new Position(posX, posY), ToHeading(heading));
	}

	public String reportStatus() {		
		StringBuilder sb = new StringBuilder(name);
		sb.append(" ");
		sb.append(reportPosition());
		return sb.toString();
	}

	public String reportPosition() {		
		if (position == null || cardinalPoint == null) {
			return "Not dropped yet.";
		}
		return position.toString() + " " + FromHeading(cardinalPoint);
	}

	public boolean hasPosition(Position pos) {
		return position.isEqual(pos);
	}
	
	public void processInstructions(Direction[] directions) {
		for (Direction i : directions) {
			processInstruction(i);
		}
	}
	
	private void processInstruction(Direction direction) {
		if (position == null || cardinalPoint == null) {
			throw new NotDroppedException();
		}
		
		switch (direction) {
			case LEFT: turnLeft(); break;
			case MOVE: moveForward(); break;
			case RIGHT: turnRight(); break;
			default: throw new RuntimeException("Should not get here!");
		}
	}
	
	private void turnLeft() {		
		switch (cardinalPoint) {
			case EAST: cardinalPoint = CardinalPoint.NORTH; break;
			case NORTH: cardinalPoint = CardinalPoint.WEST; break;
			case SOUTH: cardinalPoint = CardinalPoint.EAST; break;
			case WEST: cardinalPoint = CardinalPoint.SOUTH; break;
			default: throw new RuntimeException("Should not get here!");
		}
	}
	
	private void turnRight() {		
		switch (cardinalPoint) {
			case EAST: cardinalPoint = CardinalPoint.SOUTH; break;
			case NORTH: cardinalPoint = CardinalPoint.EAST; break;
			case SOUTH: cardinalPoint = CardinalPoint.WEST; break;
			case WEST: cardinalPoint = CardinalPoint.NORTH; break;
			default: throw new RuntimeException("Should not get here!");
		}
	}
	
	private void moveForward() {		
		Position newPosition = position.moveForward(cardinalPoint);
		if (!newPosition.IsOnPlateau(plateau)) {
			throw new PositionNotOnPlateauException(plateau, newPosition);
		}
		position = newPosition;
	}
	
	private static CardinalPoint ToHeading(char heading) {
		switch (heading) {
			case 'N': return CardinalPoint.NORTH;
			case 'W': return CardinalPoint.WEST;
			case 'S': return CardinalPoint.SOUTH;
			case 'E': return CardinalPoint.EAST;
			default: throw new RuntimeException("Unsupported character '" + heading + "'!");
		}
	}
	
	private static char FromHeading(CardinalPoint cardinalPoint) {
		switch (cardinalPoint) {
			case NORTH: return 'N';
			case WEST: return 'W';
			case SOUTH: return 'S';
			case EAST: return 'E';
			default: throw new RuntimeException("Unsupported heading '" + cardinalPoint + "'!");
		}
	}
}
