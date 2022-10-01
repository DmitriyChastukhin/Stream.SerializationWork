import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class ClientLog {

    private int ProductNum;
    private int amount;

    private List<Integer> productNumLog = new LinkedList<>();
    private List<Integer> amountLog = new LinkedList<>();

    public ClientLog() {
    }

    public void log(int productNum, int amount) {
        productNumLog.add(productNum);
        amountLog.add(amount);
    }

    public void exportAsCSV(File txtFile) {
        try (PrintWriter writer = new PrintWriter(new File(txtFile.toURI()))) {
            writer.append("productNum,amount" + "\n");
            for (int i = 0; i < productNumLog.size(); i++) {
                writer.append(productNumLog.get(i) + "," + amountLog.get(i) + "\n");
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}