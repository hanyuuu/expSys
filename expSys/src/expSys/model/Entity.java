package expSys.model;

public class Entity implements Comparable<Entity> {
	private String name;

	public Entity(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Entity entity) {
		return this.name.compareTo(entity.getName());
	}
}
