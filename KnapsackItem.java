package knapsack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collector;

import io.jenetics.util.RandomRegistry;

public class KnapsackItem {

    public static List<KnapsackItem> history = new ArrayList<>();

    public double size;
    public double value;

    public KnapsackItem(double size, double value) {
        this.size = size;
        this.value = value;
    }

    protected static KnapsackItem random() {
        Random r = RandomRegistry.getRandom();
        KnapsackItem result = new KnapsackItem(r.nextDouble() * 100, r.nextDouble() * 100);
        history.add(result);
        return result;
    }

    protected static Collector<KnapsackItem, ?, KnapsackItem> toSum() {
        return Collector.of(() -> new double[2], (a, b) -> {
            a[0] += b.size;
            a[1] += b.value;
        } , (a, b) -> {
            a[0] += b[0];
            a[1] += b[1];
            return a;
        } , r -> new KnapsackItem(r[0], r[1]));
    }


    @Override
    public String toString() {
        return "Объект (размер="+size+", стоимость="+value+")";
    }
}
