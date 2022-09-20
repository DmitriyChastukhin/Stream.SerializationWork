import java.io.*;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Basket implements Serializable {
    private String[] products;
    private int[] prices;
    private int[] amount;

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.amount = new int[products.length];
    }

    public void addToCart(int productNum, int amount) {
        this.amount[productNum - 1] += amount;
    }

    public int[] getAmount() {
        return amount;
    }

    //Сериализация
    public void saveBin(File file, Basket basket) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(basket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Десериализация
    public static Basket loadFromBinFile(File file) {
        Basket basket = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            basket = (Basket) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }

    public void printCart() {
        System.out.println("Ваша корзина покупок:");
        {
            IntStream
                    .range(0, products.length)
                    .filter(i -> amount[i] > 0)
                    .forEach(i -> System.out.println((i + 1) + ". " + products[i] + " - " + amount[i] + " шт., " + prices[i] * amount[i] + " руб."));

            Map<Integer, Integer> total;
            total = IntStream.range(0, prices.length)
                    .boxed()
                    .collect(Collectors.toMap(price -> prices[price],
                            t -> amount[t]));
            int totalSum = total.entrySet().stream()
                    .filter(t -> t.getValue() > 0)
                    .mapToInt(i -> i.getValue() * i.getKey())
                    .sum();

            System.out.println("Общая стоимость: " + totalSum + " рублей");
        }
    }
}