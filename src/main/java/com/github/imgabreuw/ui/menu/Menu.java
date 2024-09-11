package com.github.imgabreuw.ui.menu;

import com.github.imgabreuw.ui.options.MenuOption;
import lombok.Data;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

@Data
public class Menu {

    private final Map<Integer, MenuOption> options;

    private Integer exitOption;

    public Menu(Map<Integer, MenuOption> options) {
        this.options = options;
        this.exitOption = options.keySet().stream().max(Comparator.naturalOrder()).orElse(0) + 1;
    }

    public void display() {
        try (Scanner scanner = new Scanner(System.in)) {
            int option;

            do {
                System.out.println("""
                        -------------------------- MENU --------------------------
                        1. Entrada da expressão aritmética na notação infixa.
                        2. Criação da árvore binária de expressão aritmética.
                        3. Exibição da árvore binária de expressão aritmética.
                        4. Cálculo da expressão (realizando o percurso da árvore).
                        5. Encerramento do programa.
                        ----------------------------------------------------------
                        """);

                System.out.print("Opção: ");
                option = readOption(scanner);

                if (option == -1) {
                    System.out.println("Opção inválida. Tente novamente.");
                    continue;
                }

                Optional
                        .ofNullable(options.get(option))
                        .ifPresent(optionMenu -> optionMenu.execute(scanner));
            } while (option != exitOption);
        } catch (Exception e) {
            System.out.println("Unexpected error. " + e.getMessage());
        } finally {
            System.out.println("Fim do programa.");
        }
    }

    private int readOption(Scanner scanner) {
        try {
            String rawOption = scanner.next();
            int option = Integer.parseInt(rawOption);
            scanner.nextLine();

            if (!options.containsKey(option) && option != exitOption) {
                return -1;
            }

            return option;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
