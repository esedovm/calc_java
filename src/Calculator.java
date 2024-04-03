import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);


            System.out.println("Введите арифметическое выражение:");
            String input = scanner.nextLine();
            scanner.close();

            try {
                String result = calc(input);
                System.out.println("Результат: " + result);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        public static String calc(String input) throws Exception {
            // Удаляем пробелы из введенной строки
            input = input.replaceAll("\\s", "");

            // Разбиваем введенную строку на числа и оператор
            String[] parts = input.split("[-+*/]");
            if (parts.length != 2) {
                throw new Exception("Неверный формат выражения");
            }

            // Определяем, содержит ли выражение римские цифры
            boolean isRoman = input.matches(".*[IVXLCDM].*");

            // Преобразуем числа из строки в целые числа
            int num1, num2;
            if (isRoman) {
                num1 = romanToArabic(parts[0]);
                num2 = romanToArabic(parts[1]);
            } else {
                num1 = Integer.parseInt(parts[0]);
                num2 = Integer.parseInt(parts[1]);
            }

            // Проверяем диапазон чисел
            if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
                throw new Exception("Число должно быть в диапазоне от 1 до 10");
            }

            // Определяем оператор
            char operator = input.replaceAll("[^+\\-*/]", "").charAt(0);

            // Вычисляем результат
            int result;
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    result = num1 / num2;
                    break;
                default:
                    throw new Exception("Неверный оператор");
            }

            // Если использовались римские числа, возвращаем результат в виде римского числа
            if (isRoman) {
                return arabicToRoman(result);
            }

            return String.valueOf(result);
        }


        // Метод для преобразования строки в число
        public static int parseOperand(String operand, boolean isRoman) throws Exception {
            if (!isRoman) {
                int num;
                try {
                    num = Integer.parseInt(operand);
                    if (num < 1 || num > 10) {
                        throw new Exception("Число должно быть в диапазоне от 1 до 10");
                    }
                } catch (NumberFormatException e) {
                    throw new Exception("Неверный формат числа: " + operand);
                }
                return num;
            } else {
                return romanToArabic(operand);
            }
        }

        // Метод для преобразования римских чисел в арабские
        public static int romanToArabic(String roman) throws Exception {
            Map<Character, Integer> romanMap = new HashMap<>();
            romanMap.put('I', 1);
            romanMap.put('V', 5);
            romanMap.put('X', 10);
            romanMap.put('L', 50);
            romanMap.put('C', 100);
            romanMap.put('D', 500);
            romanMap.put('M', 1000);

            int result = 0;
            int prevValue = 0;

            for (int i = roman.length() - 1; i >= 0; i--) {
                int currentValue = romanMap.getOrDefault(roman.charAt(i), -1);
                if (currentValue == -1) {
                    throw new Exception("Неверный символ в римском числе: " + roman.charAt(i));
                }

                if (currentValue >= prevValue) {
                    result += currentValue;
                } else {
                    result -= currentValue;
                }

                prevValue = currentValue;
            }

            return result;
        }

        // Метод для преобразования арабских чисел в римские
        public static String arabicToRoman(int number) {
            if (number < 1 || number > 10) {
                throw new IllegalArgumentException("Число должно быть в диапазоне от 1 до 10");
            }

            String[] romanSymbols = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
            return romanSymbols[number - 1];

        }
    }



