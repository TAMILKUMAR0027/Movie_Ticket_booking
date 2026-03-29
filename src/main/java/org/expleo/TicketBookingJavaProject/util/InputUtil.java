package org.expleo.TicketBookingJavaProject.util;

import java.util.Scanner;

public class InputUtil {

    private static Scanner sc = new Scanner(System.in);

    public static String getString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    public static int getInt(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            sc.next();
            System.out.print(prompt);
        }
        return sc.nextInt();
    }

    public static double getDouble(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number.");
            sc.next();
            System.out.print(prompt);
        }
        return sc.nextDouble();
    }

    public static boolean getBoolean(String prompt) {
        System.out.print(prompt + " (yes/no): ");
        String input = sc.nextLine();
        return input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y");
    }

    public static String getChoice(String prompt, String... options) {
        System.out.println(prompt);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.print("Enter choice: ");
        int choice = getInt("");
        if (choice > 0 && choice <= options.length) {
            return options[choice - 1];
        }
        return "";
    }
}
