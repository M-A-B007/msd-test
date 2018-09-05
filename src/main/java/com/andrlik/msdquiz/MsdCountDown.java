package com.andrlik.msdquiz;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Michal Andrlik (andrlik@avast.com)
 * @since 9/5/2018
 * <p>
 * Counts backwards from value provided by user to 1 and prints:
 * "Agile" if the number is divisible by 5,
 * "Software" if the number is divisible by 3,
 * "Testing" if the number is divisible by both,
 * or prints just the number if none of those cases are true.
 **/
public class MsdCountDown {

    private static final String PROVIDE_INTEGER = "Please provide an integer number greater than zero:";
    private static final String CONFIRM = "Ok. The integer %d has been entered. ";
    private static final String ERROR_INPUT = "This does not seems like integer value. Aborting program.";
    private static final String NOT_POSITIVE_INPUT = "Oops, the initial number should be greater than zero. There is nothing to do.";
    private static final String DIVISIBLE_BY_5_TEXT = "Agile";
    private static final String DIVISIBLE_BY_3_TEXT = "Software";
    private static final String DIVISIBLE_BY_BOTH_TEXT = "Testing";

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int startNumber;

        System.out.print(PROVIDE_INTEGER);
        try {
            startNumber = scan.nextInt();
        } catch (NoSuchElementException | IllegalStateException e) {
            System.out.println(ERROR_INPUT);
            return;
        }

        System.out.println(String.format(CONFIRM, startNumber));

        countDown(startNumber);
    }

    private static void countDown(int startInt) {
        if (startInt <= 0) {
            System.out.println(NOT_POSITIVE_INPUT);
        }

        for (int i = startInt; i > 0; i--) {
            boolean divisibleBy5 = 0 == (i % 5);
            boolean divisibleBy3 = 0 == (i % 3);

            if (divisibleBy5) {
                System.out.println(DIVISIBLE_BY_5_TEXT);
            }

            if (divisibleBy3) {
                System.out.println(DIVISIBLE_BY_3_TEXT);
            }

            if (divisibleBy3 && divisibleBy5) {
                System.out.println(DIVISIBLE_BY_BOTH_TEXT);
            }
            if (!(divisibleBy3 || divisibleBy5)) {
                System.out.println(i);
            }
        }
    }
}
