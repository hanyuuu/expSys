package expSys.model;

public class Characteristic implements Comparable<Characteristic> {
	private String name;
	private Entity parent;

	public Characteristic(String name, Entity parent) {
		this.name = name;
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public Entity getParent() {
		return parent;
	}

	@Override
	public int compareTo(Characteristic characteristic) {
		int i = this.parent.compareTo(characteristic.getParent());
		if (i != 0)
			return i;
		return this.name.compareTo(characteristic.getName());
	}
}
