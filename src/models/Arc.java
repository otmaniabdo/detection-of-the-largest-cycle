package models;


public class Arc {
	private int id;
	private int poids;
	private int source;
	private int destination;

	public Arc() {
		super();
	}

	public Arc(int id, int source, int destination) {
		super();
		this.id = id;
		this.source = source;
		this.destination = destination;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public void setPoids(int poids) {
		this.poids = poids;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "Arc [ source=" + source + ", destination=" + destination
				+ "]";
	}

}
