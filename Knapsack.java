package knapsack;

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;
import java.util.Arrays;
import java.util.stream.Stream;
import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;

//The main class.
public class Knapsack {
    public static void main(String[] args) {
        int nItems = 7;
        double ksSize = nItems * 100.0 / 3.0;

        KnapsackFF ff = new KnapsackFF(Stream.generate(KnapsackItem::random)
                .limit(nItems)
                .toArray(KnapsackItem[]::new), ksSize);

        System.out.println("Все объекты:");
        for(KnapsackItem item: KnapsackItem.history){
            System.out.println(item);
        }

        System.out.println("\nРазмер рюкзака:"+ksSize);

        Engine<BitGene, Double> engine = Engine.builder(ff, BitChromosome.of(nItems, 0.5))
                .populationSize(500)
                .survivorsSelector(new TournamentSelector<>(5))
                .offspringSelector(new RouletteWheelSelector<>())
                .alterers(new Mutator<>(0.115), new SinglePointCrossover<>(0.16))
                .build();

        EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();

        Phenotype<BitGene, Double> best = engine.stream()
                .limit(1000)
                .peek(statistics)
                .collect(toBestPhenotype());

       // System.out.println(statistics);
        System.out.println("\n"+best);

        System.out.println("\nОбъекты, которые надо взять:");
        for(int i=0; i<KnapsackItem.history.size(); i++){
            if(best.getGenotype().getChromosome().getGene(i).booleanValue()){
                System.out.println(KnapsackItem.history.get(i));
            }
        }
    }
}
