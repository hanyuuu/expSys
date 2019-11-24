package expSys.model;

public class Rule implements Comparable<Rule> {
	private Characteristic premise, consequent;

	public Rule(Characteristic premise, Characteristic consequent) {
		this.premise = premise;
		this.consequent = consequent;
	}

	@Override
	public int compareTo(Rule o) {
		int i = this.premise.compareTo(o.getPremise());
		if (i != 0)
			return i;

		return this.consequent.compareTo(o.getConsequent());
	}

	public String getName() {
		return this.getConsequent().getParent().getName() + " = " + this.getConsequent().getName() + " т.к. "
				+ this.getPremise().getParent().getName() + " = " + this.getPremise().getName();
	}

	public Characteristic ruleApplier(Characteristic premise) {
		return (premise.compareTo(this.premise) == 0) ? this.consequent : null;
	}

	public Characteristic getPremise() {
		return premise;
	}

	public Characteristic getConsequent() {
		return consequent;
	}
}
