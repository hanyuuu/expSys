package expSys.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import expSys.model.Characteristic;
import expSys.model.Entity;
import expSys.model.Rule;

public class Pool {
	private Scanner scanner = new Scanner(System.in);
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Characteristic> characteristics = new ArrayList<Characteristic>();
	private List<Rule> rules = new ArrayList<Rule>();
	private List<Characteristic> transitionalCharacteristics = new ArrayList<Characteristic>();
	private List<Characteristic> resultCharacteristics = new ArrayList<Characteristic>();
	private List<Characteristic> conditions = new ArrayList<Characteristic>();
	private List<String> opinionList = new ArrayList<String>();

	public void deleteRule() {
		if (!rules.isEmpty()) {
			int ruleIndex = -1;
			boolean ruleIsNotChosen = true;
			while (ruleIsNotChosen) {
				System.out.println("Выберите правила для удаления:");
				for (int i = 0; i < rules.size(); i++) {
					System.out.println((i + 1) + ". " + rules.get(i).getPremise().getParent().getName() + " = "
							+ rules.get(i).getPremise().getName() + " => "
							+ rules.get(i).getConsequent().getParent().getName() + " = "
							+ rules.get(i).getConsequent().getName());
				}
				try {
					ruleIndex = scanner.nextInt();
					rules.get(ruleIndex - 1);
				} catch (java.util.InputMismatchException e1) {
					System.out.println("Введен не индекс!");
					continue;
				} catch (java.lang.IndexOutOfBoundsException e2) {
					System.out.println("Правила с таким индексом не существует!");
					continue;
				}
				ruleIsNotChosen = false;
			}
			List<Rule> temporaryRuleList = new ArrayList<Rule>();
			for (Rule current : rules) {
				if (!(current.compareTo(rules.get(ruleIndex)) == 0)) {
					temporaryRuleList.add(current);
				} else {
					System.out.println("Удалено!");
				}
			}
			this.rules = temporaryRuleList;
		} else {
			System.out.println("Невозможно удалить правила! Ни одного правила не было добавлено!");
		}
	}

	public void deleteCondition() {
		if (!conditions.isEmpty()) {
			String returnMessage = "";
			int conditionIndex = this.choseConditionIndex("Выберите условие:");
			returnMessage = (conditionIndex != -1) ? "Выбрано!" : "Произошла ошибка при выборе условия!";
			System.out.println(returnMessage);
			Characteristic conditionToBeDeleted = this.conditions.get(conditionIndex);
			List<Characteristic> temporary = new ArrayList<Characteristic>();
			for (Characteristic current : conditions) {
				if (!(current.compareTo(conditionToBeDeleted) == 0)) {
					temporary.add(current);
				}
			}
			this.conditions = temporary;
		} else {
			System.out.println("Добавьте хотя бы одно условие!");
		}
	}

	public void addCondition() {
		if (!characteristics.isEmpty()) {
			String returnMessage = "";
			int conditionIndex = this.choseCharacteristicIndex("Выберите условие:");
			returnMessage = (conditionIndex != -1) ? "Выбрано!" : "Произошла ошибка при выборе условия!";
			System.out.println(returnMessage);
			conditions.add(characteristics.get(conditionIndex));
			System.out.println("Текущие условия:");
			for (Characteristic condition : conditions) {
				System.out.println(condition.getParent().getName() + " = " + condition.getName());
			}
		} else {
			System.out.println("Добавьте хотя бы одну характеристику!");
		}
	}

	public void startTraverse() {
		this.transitionalCharacteristics.clear();
		this.resultCharacteristics.clear();
		this.opinionList.clear();
		for (Characteristic condition : conditions) {
			this.recursiveTraverse(condition, true);
		}
	}

	public void printTraverseResults() {
		System.out.println("Промежуточные:");
		for (Characteristic transitional : transitionalCharacteristics) {
			System.out.println(transitional.getParent().getName() + " = " + transitional.getName());
		}
		System.out.println("Результирующие:");
		for (Characteristic result : resultCharacteristics) {
			System.out.println(result.getParent().getName() + " = " + result.getName());
		}
		System.out.println("Экспертное мнение:");
		for (String opinion : this.opinionList) {
			System.out.println(opinion);
		}
	}

	public void createEntity(String name) {
		int x = addEntity(name);
		if (x == 1) {
			System.out.println("Объект добавлен!");
		} else if (x == -1) {
			System.out.println("Объект с таким названием уже существует!");
		}
	}

	public void createCharacteristic() {
		if (!entities.isEmpty()) {
			int entityIndex = 0;
			boolean entityIsNotChosen = true;
			while (entityIsNotChosen) {
				System.out.println("\nВыберите объект:");
				for (int i = 0; i < entities.size(); i++) {
					System.out.println((i + 1) + ". " + entities.get(i).getName());
				}
				try {
					entityIndex = scanner.nextInt();
					entities.get(entityIndex - 1);
				} catch (java.util.InputMismatchException e1) {
					System.out.println("Введен не индекс!");
					continue;
				} catch (java.lang.IndexOutOfBoundsException e2) {
					System.out.println("Объекта с таким индексом не существует!");
					continue;
				}
				entityIsNotChosen = false;
			}
			System.out.println("Вводите построчно характеристики объекта '" + entities.get(entityIndex - 1).getName()
					+ "' или -1 для выхода из режима ввода: ");
			scanner.nextLine();
			boolean lever = true;
			while (lever) {
				String line = scanner.nextLine();
				if (line.equalsIgnoreCase("-1")) {
					lever = false;
				} else {
					int x = addCharacteristic(line, entities.get(entityIndex - 1));
					if (x == 1) {
						System.out.println("Характеристика добавлена!");
					} else if (x == -1) {
						System.out.println("Характеристика с таким названием уже существует!");
					}
				}
			}
		} else {
			System.out.println("Для того чтобы добавить характеристику, создайте хотя бы один объект!");
		}
	}

	public void createRule() {
		if (!characteristics.isEmpty()) {
			String returnMessage = "";
			int premiseIndex = this.choseCharacteristicIndex("Выберите предпосылку:");
			returnMessage = (premiseIndex != -1) ? "Выбрано!"
					: "Произошла ошибка при выборе характеристики-предпосылки!";
			System.out.println(returnMessage);
			int consequentIndex = this.choseCharacteristicIndex("Выберите консеквент:");
			returnMessage = (consequentIndex != -1) ? "Выбрано!"
					: "Произошла ошибка при выбора характеристики-консеквента!";
			System.out.println(returnMessage);
			this.addRule(characteristics.get(premiseIndex), characteristics.get(consequentIndex));
		} else {
			System.out.println("Для того чтобы добавить правило, создайте хотя бы одну характеристику объекта!");
		}
	}

	private int choseConditionIndex(String message) {
		int conditionIndex = -1;
		boolean conditionIsNotChosen = true;
		while (conditionIsNotChosen) {
			System.out.println(message);
			for (int i = 0; i < conditions.size(); i++) {
				System.out.println(
						(i + 1) + ". " + conditions.get(i).getParent().getName() + " = " + conditions.get(i).getName());
			}
			try {
				conditionIndex = scanner.nextInt();
				conditions.get(conditionIndex - 1);
			} catch (java.util.InputMismatchException e1) {
				System.out.println("Введен не индекс!");
				continue;
			} catch (java.lang.IndexOutOfBoundsException e2) {
				System.out.println("Условия с таким индексом не существует!");
				continue;
			}
			conditionIsNotChosen = false;
			return (conditionIndex - 1);
		}
		return -1;
	}

	private int choseCharacteristicIndex(String message) {
		int characteristicIndex = -1;
		boolean characteristicIsNotChosen = true;
		while (characteristicIsNotChosen) {
			System.out.println(message);
			for (int i = 0; i < characteristics.size(); i++) {
				System.out.println((i + 1) + ". " + characteristics.get(i).getParent().getName() + " = "
						+ characteristics.get(i).getName());
			}
			try {
				characteristicIndex = scanner.nextInt();
				characteristics.get(characteristicIndex - 1);
			} catch (java.util.InputMismatchException e1) {
				System.out.println("Введен не индекс!");
				continue;
			} catch (java.lang.IndexOutOfBoundsException e2) {
				System.out.println(
						"Характеристики с таким индексом не существует! Характеристика не может быть выбрана!");
				continue;
			}
			characteristicIsNotChosen = false;
			return (characteristicIndex - 1);
		}
		return -1;
	}

	private int addEntity(String name) {
		Entity temporary = new Entity(name);
		for (Entity current : entities) {
			if (temporary.compareTo(current) == 0) {
				return -1;
			}
		}
		entities.add(temporary);
		return 1;
	}

	private int addCharacteristic(String name, Entity entity) {
		Characteristic temporary = new Characteristic(name, entity);
		for (Characteristic current : characteristics) {
			if (temporary.compareTo(current) == 0) {
				return -1; // -1, если характеристика с таким названием уже существует
			}
		}
		characteristics.add(temporary);
		Collections.sort(characteristics);
		return 1; // 1, если характеристика добавлена в список
	}

	public int addRule(Characteristic premise, Characteristic consequent) {
		if (premise.compareTo(consequent) == 0) {
			System.out.println("Правило является тавтологией! Предпосылка не может быть равна консеквенту!");
			return -1;
		}
		Rule rule = new Rule(consequent, premise);
		for (Rule current : rules) {
			if (rule.compareTo(current) == 0) {
				System.out.println("Обратное правило уже существует!");
				return -1;
			}
		}
		rule = new Rule(premise, consequent);
		for (Rule current : rules) {
			if (rule.compareTo(current) == 0) {
				System.out.println("Правило уже существует!");
				return -1;
			}
		}
		rules.add(rule);
		return 1;
	}

	public void recursiveTraverse(Characteristic choice, boolean isFirst) {
		List<Characteristic> current = consequentGetter(choice);
		if (current != null) {
			for (Characteristic temporary : current) {
				if (transitionalCharacteristics.contains(temporary)) {
					if (!isFirst)
						resultCharacteristics.add(choice);
				} else {
					if (!isFirst)
						transitionalCharacteristics.add(choice);
					recursiveTraverse(temporary, false);
				}
			}
		} else {
			if (!isFirst) {
				resultCharacteristics.add(choice);
			}
		}
	}
	/*
	 * public void recursiveTraverse(Characteristic choice, boolean isFirst) {
	 * List<Characteristic> current = consequentGetter(choice); if (current != null)
	 * { for (Characteristic temporary : current) { if
	 * (transitionalCharacteristics.contains(temporary)) { if (!isFirst)
	 * resultCharacteristics.add(choice); } else { if (!isFirst)
	 * transitionalCharacteristics.add(choice); recursiveTraverse(temporary, false);
	 * } } } else { if (!isFirst) resultCharacteristics.add(choice); } }
	 */

	public List<Characteristic> consequentGetter(Characteristic x) {
		List<Characteristic> result = new ArrayList<Characteristic>();
		for (Rule rule : rules) {
			if (rule.getPremise().compareTo(x) == 0) {
				result.add(rule.getConsequent());
				if (!this.opinionList.contains(rule.getName())) {
					opinionList.add(rule.getName());
				}
			}
		}
		return (result.size() == 0) ? null : result; // если есть элементы – null, если их нет – лист консеквентов
	}

	public void printEverything() {
		if (!entities.isEmpty()) {
			System.out.println("СРХ и СРЗХ:");
			for (int i = 0; i < entities.size(); i++) {
				System.out.println((i + 1) + ". " + entities.get(i).getName());
				for (Characteristic current : characteristics) {
					if (current.getParent().compareTo(entities.get(i)) == 0) {
						System.out.println("	" + current.getName());
					}
				}
			}
		} else {
			System.out.println("Ни одного объекта не добавлено.");
		}
		if (!rules.isEmpty()) {
			System.out.println("Правила:");
			for (int i = 0; i < rules.size(); i++) {
				System.out.println((i + 1) + ". " + rules.get(i).getPremise().getParent().getName() + " = "
						+ rules.get(i).getPremise().getName() + " => "
						+ rules.get(i).getConsequent().getParent().getName() + " = "
						+ rules.get(i).getConsequent().getName());
			}
		} else {
			System.out.println("Ни одного правила не добавлено.");
		}
	}
}
