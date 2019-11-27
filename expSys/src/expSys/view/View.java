package expSys.view;

import java.util.Scanner;

import expSys.engine.Pool;

public class View {

	private Scanner scanner = new Scanner(System.in);
	private int currentChoice = -1;
	private boolean lever;

	private Pool pool = new Pool();

	public void start() {
		while (true) {
			System.out.println("~~~~~~~~~~~~~~~ Меню ~~~~~~~~~~~~~~~");
			System.out.println("~ 1. Выйти из программы            ~");
			System.out.println("~ 2. Вывести все данные            ~");
			System.out.println("~ 3. Добавить СРХ                  ~");
			System.out.println("~ 4. Добавить СРЗХ                 ~");
			System.out.println("~ 5. Добавить правило              ~");
			System.out.println("~ 6. Добавить условие              ~");
			System.out.println("~ 7. Сгенерировать результат       ~");
			System.out.println("~ 8. Получить результат            ~");
			System.out.println("~ 9. Удалить условие               ~");
			System.out.println("~ 10. Удалить правило              ~");
			System.out.println("~ 11. Вывести все условия          ~");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			try {
				currentChoice = scanner.nextInt();
			} catch (java.util.InputMismatchException e) {
				System.out.println("\nВведен не пункт меню!\n");
				scanner.next();
			}

			switch (currentChoice) {
			case 1:
				System.exit(0);
				break;
			case 2:
				pool.printEverything();
				break;
			case 3:
				System.out.println("Вводите построчно названия объектов или -1 для выхода из режима ввода: ");
				scanner.nextLine();
				lever = true;
				while (lever) {
					String line = scanner.nextLine();
					if (line.equalsIgnoreCase("-1")) {
						lever = false;
					} else {
						pool.createEntity(line);
					}
				}
				break;
			case 4:
				pool.createCharacteristic();
				break;
			case 5:
				pool.createRule();
				break;
			case 6:
				pool.addCondition();
				break;
			case 7:
				pool.startTraverse();
				break;
			case 8:
				pool.printTraverseResults();
				break;
			case 9:
				pool.deleteCondition();
				break;
			case 10:
				pool.deleteRule();
				break;
			case 11:
				pool.printAllConditions();
			}
		}
	}
}
