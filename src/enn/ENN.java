package enn;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
/**
 * @author aftab
 */
public class ENN {

    private static int noOfInd;
    double dataSet[][];
    double G;
    static Random random;

    public static void main(String[] args) throws IOException {
        ENN enn = new ENN(100);
    }

    public ENN(int noOfInd) throws FileNotFoundException, IOException {
        ENN.noOfInd = noOfInd;
        MyCSV myCSV = new MyCSV("E://NN-DATA.csv");
        dataSet = myCSV.readCSV();
        G = 1;
        random = new Random(1);

        Generation generation = new Generation(noOfInd);
        for (int i = 0; i < noOfInd; i++) {
            generation.neuralNetworks[i] = new NeuralNetwork();
            generation.neuralNetworks[i].test(dataSet);
        }
        NeuralNetwork best = generation.neuralNetworks[0];

        while (best.fitness < dataSet.length) {
            Generation children = generation.createChildren(50);
            for (int i = 0; i < 20; i++) {
                children.neuralNetworks[i].test(dataSet);
            }
            generation = Generation.chooseBestBinaryTournament(children, generation);
            best = generation.neuralNetworks[0];
            System.out.println("Generation no = " + G + "\t and best NN has fittness" + best.toString());
            G++;
        }
    }

    private static class Generation {

        public NeuralNetwork[] neuralNetworks;

       private static Generation chooseBestBinaryTournament(Generation parents, Generation children) {

            Generation all = new Generation(parents.neuralNetworks.length + children.neuralNetworks.length);
            int i;
            for (i = 0; i < parents.neuralNetworks.length; i++) {
                all.neuralNetworks[i] = parents.neuralNetworks[i];
            }
            for (int j = 0; j < children.neuralNetworks.length; j++, i++) {
                all.neuralNetworks[i] = children.neuralNetworks[j];
            }

            Generation best = new Generation(parents.neuralNetworks.length);

            for (i = 0; i < best.neuralNetworks.length; i++) {
                int p1 = random.nextInt(all.neuralNetworks.length);
                int p2 = random.nextInt(all.neuralNetworks.length);

                while (p1 == p2) {
                    p1 = random.nextInt(all.neuralNetworks.length);
                    p2 = random.nextInt(all.neuralNetworks.length);
                }

                if (all.neuralNetworks[p1].fitness > all.neuralNetworks[p2].fitness) {
                    best.neuralNetworks[i] = all.neuralNetworks[p1];
                } else {
                    best.neuralNetworks[i] = all.neuralNetworks[p2];
                }
            }
            
            return best;

        }

        public static Generation chooseBest(Generation parents, Generation children) {
            Generation all = new Generation(parents.neuralNetworks.length + children.neuralNetworks.length);
            int i;
            for (i = 0; i < parents.neuralNetworks.length; i++) {
                all.neuralNetworks[i] = parents.neuralNetworks[i];
            }
            for (int j = 0; j < children.neuralNetworks.length; j++, i++) {
                all.neuralNetworks[i] = children.neuralNetworks[j];
            }

            for (i = 0; i < all.neuralNetworks.length; i++) {
                for (int j = i; j < all.neuralNetworks.length; j++) {
                    if (all.neuralNetworks[i].fitness < all.neuralNetworks[j].fitness) {
                        NeuralNetwork ind = all.neuralNetworks[i];
                        all.neuralNetworks[i] = all.neuralNetworks[j];
                        all.neuralNetworks[j] = ind;
                    }
                }
            }

            Generation best = new Generation(parents.neuralNetworks.length);
            for (i = 0; i < best.neuralNetworks.length; i++) {
                best.neuralNetworks[i] = all.neuralNetworks[i];
            }
            return best;
        }

        public static Generation chooseBestFittnessProportion(Generation parents, Generation children) {
            Generation all = new Generation(parents.neuralNetworks.length + children.neuralNetworks.length);

            int no = all.neuralNetworks.length;

            double proportion[] = new double[no];
            double comulative[] = new double[no];

            int i;
            for (i = 0; i < parents.neuralNetworks.length; i++) {
                all.neuralNetworks[i] = parents.neuralNetworks[i];
            }
            for (int j = 0; j < children.neuralNetworks.length; j++, i++) {
                all.neuralNetworks[i] = children.neuralNetworks[j];
            }

            float sum = 0;
            for (int j = 0; j < no; j++) {
                sum = (float) (sum + all.neuralNetworks[j].fitness);
            }
            float pro = 0;
            for (int j = 0; j < no; j++) {
                proportion[j] = all.neuralNetworks[j].fitness / sum;
                pro = (float) (pro + proportion[j]);
                //System.out.println("a = "+j+" fittness = "+all.neuralNetworks[j].fitness+" proportion = "+proportion[j]);
            }

            comulative[0] = proportion[0];
            for (int j = 1; j < no; j++) {
                comulative[j] = proportion[j] + comulative[j - 1];
                //System.out.println(comulative[j]);
            }

            Generation best = new Generation(noOfInd);
            for (int j = 0; j < noOfInd; j++) {
                float rand = random.nextFloat();

                boolean notfound = true;
                for (int k = 1; k < comulative.length; k++) {
                    //System.out.println("k = "+k);
                    if (rand <= comulative[k] && rand > comulative[k - 1]) {
                        //System.out.println("selected "+k+" "+all.neuralNetworks[k].fitness);
                        best.neuralNetworks[j] = all.neuralNetworks[k];
                        notfound = false;
                        break;
                    }
                }
                if (notfound) {
                    //System.out.println("selected 0 "+all.neuralNetworks[0].fitness);
                    best.neuralNetworks[j] = all.neuralNetworks[0];
                }
            }

            //System.out.println("---------------------------------");
            //for (int x=0; x<noOfInd; x++)
            //{
            //System.out.println(best.neuralNetworks[x].fitness);
            //}
            return best;
        }

        public Generation(int noOfInd) {
            neuralNetworks = new NeuralNetwork[noOfInd];
        }

        public Generation createChildren(int noOfChld) {
            Generation children = new Generation(noOfChld);
            Random random = new Random();
            for (int i = 0, j = 0; i < noOfChld / 2; i++) {

                int p1 = random.nextInt(this.neuralNetworks.length);
                int p2 = random.nextInt(this.neuralNetworks.length);

                while (p1 == p2) {
                    p1 = random.nextInt(this.neuralNetworks.length);
                    p2 = random.nextInt(this.neuralNetworks.length);
                }
                //System.out.println("p1 = "+ p1+" p2 = "+p2);
                NeuralNetwork[] newChilds = Parent.createTwoChilds(this.neuralNetworks[p1], this.neuralNetworks[p2]);
                children.neuralNetworks[j] = newChilds[0];
                j++;
                children.neuralNetworks[j] = newChilds[1];
                j++;
            }
            return children;
        }
    }

    private static class Parent {

        public static NeuralNetwork[] createTwoChilds(NeuralNetwork parent1, NeuralNetwork parent2) {
            NeuralNetwork child1 = new NeuralNetwork(parent1.D.bias, parent1.E.bias, parent1.E.bias,
                    parent2.G.bias, parent2.H.bias,
                    parent1.I.bias,
                    parent1.con_A_D.currentWeight + random.nextDouble(), parent1.con_A_E.currentWeight, parent1.con_A_F.currentWeight,
                    parent2.con_B_D.currentWeight + random.nextDouble(), parent2.con_B_E.currentWeight, parent2.con_B_F.currentWeight,
                    parent1.con_C_D.currentWeight + random.nextDouble(), parent1.con_C_E.currentWeight, parent1.con_C_F.currentWeight,
                    parent2.con_D_G.currentWeight + random.nextDouble(), parent2.con_D_H.currentWeight,
                    parent1.con_E_G.currentWeight + random.nextDouble(), parent1.con_E_H.currentWeight,
                    parent2.con_F_G.currentWeight + random.nextDouble(), parent2.con_F_H.currentWeight,
                    parent1.con_G_I.currentWeight + random.nextDouble(),
                    parent2.con_H_I.currentWeight + random.nextDouble()
            );
            NeuralNetwork child2 = new NeuralNetwork(parent2.D.bias, parent2.E.bias, parent2.E.bias,
                    parent1.G.bias, parent1.H.bias,
                    parent2.I.bias,
                    parent2.con_A_D.currentWeight + random.nextDouble(), parent2.con_A_E.currentWeight, parent2.con_A_F.currentWeight,
                    parent1.con_B_D.currentWeight + random.nextDouble(), parent1.con_B_E.currentWeight, parent1.con_B_F.currentWeight,
                    parent2.con_C_D.currentWeight + random.nextDouble(), parent2.con_C_E.currentWeight, parent2.con_C_F.currentWeight,
                    parent1.con_D_G.currentWeight + random.nextDouble(), parent1.con_D_H.currentWeight,
                    parent2.con_E_G.currentWeight + random.nextDouble(), parent2.con_E_H.currentWeight,
                    parent1.con_F_G.currentWeight + random.nextDouble(), parent1.con_F_H.currentWeight,
                    parent2.con_G_I.currentWeight + random.nextDouble(),
                    parent1.con_H_I.currentWeight + random.nextDouble()
            );
            return new NeuralNetwork[]{child1, child2};
        }
    }
}
