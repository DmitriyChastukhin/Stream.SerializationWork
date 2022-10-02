import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static String[] products = {"Молоко", "Яблоки", "Хлеб", "Макароны"};
    static int[] prices = {60, 80, 45, 65};

    static void printItems() {
        System.out.println("Список возможных товаров для покупки:");
        IntStream
                .range(0, products.length)
                .forEach(i -> System.out.println((i + 1) + ". "
                        + products[i] + " - "
                        + prices[i] + " руб/шт"));
    }

    static void printOptions() {
        System.out.println("Выберите товар и его количество или введите \"end\"");
    }

    public static void main(String[] args) throws IOException, ParseException, ParserConfigurationException, SAXException {
        File file = new File("basket.json"); //создание файла
        Scanner scanner = new Scanner(System.in);

        ShopConfig config = new ShopConfig();
        Basket basket = new Basket(products, prices);
        var log = new ClientLog(); // журнал записи ввода пользователя
        config.loadBasket();
        if (config.isLoad()) {
            if (config.isFormat()) {
                basket = Basket.loadFromJsonFile(config.getBasketFile());
            } else basket = Basket.loadFromTxtFile(config.getBasketFile());
        }
        printItems();

        while (true) {
            printOptions();
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                basket.printCart();
                break;
            }

            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println("Нужно ввести два числа через пробел, вы ввели: " + input + ". Попробуйте снова");
                continue;
            }
            try {
                int productNumber = Integer.parseInt(parts[0]);
                int amount = Integer.parseInt(parts[1]);
                if ((productNumber <= 0) || (productNumber > products.length)) {
                    System.out.println("Вы ввели недопустимое число!");
                    System.out.println("Номер продукта должен быть от 1 до " + products.length);
                    continue;
                }
                if ((basket.getAmount()[productNumber - 1] + amount) < 0) {
                    System.out.println("Количество товара в корзине не должно быть меньше 0!");
                    continue;
                }
                basket.addToCart(productNumber, amount); //добавляем продукт в корзину
                config.logCompletion();
                if (config.isLog()) {
                    log.log(productNumber, amount); //записываем выбор в журнал
                    log.exportAsCSV(config.getLogFile());
                } //выгружаем журнал в csv

            } catch (NumberFormatException e) {
                System.out.println("Некорректный символ. Вы ввели " + input + " Введите два числа или end");
            }
        }
        config.saveBasket();
        if (config.isSave()) {
            if (config.isSaveFormat()) {
                basket.saveJson(config.getSaveFile());
            } else {
                basket.saveTxt(config.getSaveFile());
            }
        }
    }
}