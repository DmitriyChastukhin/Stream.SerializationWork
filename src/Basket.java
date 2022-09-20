import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Basket {
    private final String[] products;
    private final int[] prices;
    private final int[] amount;

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.amount = new int[products.length];
    }
    public Basket(String[] products, int[] prices, int[] amount) {
        this.products = products;
        this.prices = prices;
        this.amount = amount;
    }
    public void addToCart(int productNum, int amount) {
        this.amount[productNum - 1] += amount;
    }

    public int[] getAmount() {
        return amount;
    }

    protected void saveTxt(File file) {
        try (PrintWriter saveTxt = new PrintWriter(file)) {
            for (int i = 0; i < products.length; i++) {
                saveTxt.print(products[i] + " " + prices[i]+ " " + amount[i] + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
        try (BufferedReader buff = new BufferedReader(new FileReader(textFile))) {
            String s;
            List<String> products = new ArrayList<>();
            List<Integer> prices = new ArrayList<>();
            List<Integer> amount = new ArrayList<>();
            while ((s = buff.readLine()) != null) {
                String[] parts = s.split(" ");
                products.add(parts[0]);
                prices.add(Integer.parseInt(parts[1]));
                amount.add(Integer.parseInt(parts[2]));
            }
            String[] name = products.toArray(new String[0]);
            int[] price = prices.stream().mapToInt(i -> i).toArray();
            int[] amounts = amount.stream().mapToInt(i -> i).toArray();
            buff.close();

            return new Basket(name, price, amounts);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void printCart() {
        System.out.println("Ваша корзина покупок:");{
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