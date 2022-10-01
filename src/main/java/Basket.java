import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Basket {
    private String[] products;
    private int[] prices;
    private int[] amount;

    public Basket() { //objectmapper default-конструктор
    }

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

    public String[] getProducts() {
        return products;
    }

    public void setProducts(String[] products) {
        this.products = products;
    }

    public int[] getPrices() {
        return prices;
    }

    public void setPrices(int[] prices) {
        this.prices = prices;
    }

    // запись  в json-файл
    public void saveJson(File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, new Basket(this.products, this.prices, this.amount));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //восстановление корзины из файла
    public static Basket loadFromJsonFile(File jsonFile) throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        Basket basket = mapper.readValue(jsonFile, Basket.class);
        return basket;
    }

    public void printCart() {
        IntStream
                .range(0, products.length)
                .filter(i -> amount[i] > 0)
                .forEach(i -> System.out.println((i + 1) + ". "
                        + products[i] + " - " + amount[i]
                        + " шт., " + prices[i] * amount[i] + " руб."));
        Map<Integer, Integer> total = IntStream.range(0, prices.length).boxed()
                .collect(Collectors.toMap(price -> prices[price], cnt -> amount[cnt]));
        int totalSum = total.entrySet().stream().
                filter(cnt -> cnt.getValue() > 0)
                .mapToInt(i -> i.getValue() * i.getKey())
                .sum();

        System.out.println("Общая стоимость: " + totalSum + " рублей");
    }
}