import java.io.File;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static String[] products = {"Молоко", "Яблоки", "Хлеб", "Макароны"};
    static int[] prices = {60, 80, 45, 65};

    static void printProducts() {
        System.out.println("Список возможных товаров для покупки:");
        IntStream intStream = IntStream.range(0, products.length);
        intStream.forEach(i -> System.out.println((i + 1) + ". " + products[i] + " - " + prices[i] + " руб/шт"));
    }

    static void printOptions() {
        System.out.println("Выберите товар и его количество или введите end");
    }

    public static void main(String[] args) {
        File file = new File("basket.txt");
        Scanner scanner = new Scanner(System.in);
        printProducts();
        while (true) {
            Basket basket = file.exists() ? Basket.loadFromTxtFile(file) : new Basket(products, prices);

            printOptions();
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                basket.printCart();
                break;
            }
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println("Нужно вводить два числа через пробел");
                continue;
            }
            try {
                int productNumber = Integer.parseInt(parts[0]);
                int amount = Integer.parseInt(parts[1]);

                if ((productNumber <= 0) || (productNumber > products.length)) {
                    System.out.println("Номер продукта должен быть от 1 до " + products.length);
                    continue;
                }
                if ((basket.getAmount()[productNumber - 1] + amount) < 0) {
                    System.out.println("Количество продуктов в корзине не должно быть меньше 0");
                    continue;
                }
                basket.addToCart(productNumber, amount);
                basket.saveTxt(file);

            } catch (NumberFormatException e) {
                System.out.println("Вы ввели " + input + " Введите два числа или end");
            }
        }
    }
}