package enn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

/**
 * @author aftab
 */
public class NeuralNetwork {

    static Random random;
    static double Iteration = 0;

    public Neuron A, B, C, D, E, F, G, H, I;

    Connection con_A_D, con_A_E, con_A_F, con_B_D, con_B_E, con_B_F, con_C_D, con_C_E, con_C_F, con_D_G, con_D_H, con_E_G, con_E_H, con_F_G, con_F_H, con_G_I, con_H_I;
    double fitness;
    private double error;

    public NeuralNetwork() {
        random = new Random();

        //CREATING NEURON
        A = new Neuron("A", 0, 3);
        B = new Neuron("B", 0, 3);
        C = new Neuron("C", 0, 3);
        D = new Neuron("D", random.nextDouble(), 2);
        E = new Neuron("E", random.nextDouble(), 2);
        F = new Neuron("F", random.nextDouble(), 2);
        G = new Neuron("G", random.nextDouble(), 1);
        H = new Neuron("H", random.nextDouble(), 1);
        I = new Neuron("I", random.nextDouble(), 0);

        //NOW FIRST WE MUST MAKE THE CONNECTION BETWEEN EACH NEURONS
        //FIRST WE MUST CONNECT NEURON "A" WITH "D","E","F"
        con_A_D = new Connection( //A-D
                A, //A
                D, //D
                random.nextDouble() //random weight
        );
        con_A_E = new Connection( //A-E
                A, //A
                E, //E
                random.nextDouble()
        );
        con_A_F = new Connection( //A-F
                A, //A
                F, //F
                random.nextDouble()
        );
        //NOW EACH CONNECTION KNOW THEY ARE CONNECTED TO "A" and "D","E","F" RESPECTIVELY
        //BUT "A" DOESNT KNOW TO WHOM CONNECTINS IT IS CONNECTED
        //AND A NEURON MUST KNOW ALL THE OUTGOING CONNECTIONS FROM IT SO WE MUST TELL "A" ABOUT THE OUTGOING CONNECTIONS
        A.connections[0] = con_A_D;
        A.connections[1] = con_A_E;
        A.connections[2] = con_A_F;

        //NOW WE MUST CONNECT NEURON "B" WITH "D","E","F"
        con_B_D = new Connection( //B-D
                B, //B
                D, //D
                random.nextDouble() //random weight
        );
        con_B_E = new Connection( //B-E
                B, //B
                E, //E
                random.nextDouble()
        );
        con_B_F = new Connection( //B-F
                B, //B
                F, //F
                random.nextDouble()
        );
        //NOW EACH CONNECTION KNOW THEY ARE CONNECTED TO "B" and "D","E","F" RESPECTIVELY
        //BUT "B" DOESNT KNOW TO WHOM CONNECTINS IT IS CONNECTED
        //AND A NEURON MUST KNOW ALL THE OUTGOING CONNECTIONS FROM IT SO WE MUST TELL "B" ABOUT THE OUTGOING CONNECTIONS
        B.connections[0] = con_B_D;
        B.connections[1] = con_B_E;
        B.connections[2] = con_B_F;

        //FINALLY WE MUST CONNECT NEURON "C" WITH "D","E","F"
        con_C_D = new Connection( //C-D
                C, //C
                D, //D
                random.nextDouble() //random weight
        );
        con_C_E = new Connection( //C-E
                C, //C
                E, //E
                random.nextDouble()
        );
        con_C_F = new Connection( //C-F
                C, //C
                F, //F
                random.nextDouble()
        );
        //NOW EACH CONNECTION KNOW THEY ARE CONNECTED TO "C" and "D","E","F" RESPECTIVELY
        //BUT "C" DOESNT KNOW TO WHOM CONNECTINS IT IS CONNECTED
        //AND A NEURON MUST KNOW ALL THE OUTGOING CONNECTIONS FROM IT SO WE MUST TELL "C" ABOUT THE OUTGOING CONNECTIONS
        C.connections[0] = con_C_D;
        C.connections[1] = con_C_E;
        C.connections[2] = con_C_F;

        //THE FIRST LAYER IS CONNECTED WITH HIDDEN LAYER ONE KNOW HIDDEN LAYER ONE WILL CONNECT WITH HIDDEN LAYER TWO
        //WE MUST CONNECT NEURON "D" WITH "G","H"
        con_D_G = new Connection( //D-G
                D, //D
                G, //G
                random.nextDouble() //random weight
        );
        con_D_H = new Connection( //D-H
                D, //D
                H, //H
                random.nextDouble()
        );

        //NOW EACH CONNECTION KNOW THEY ARE CONNECTED TO "D" and "G","H" RESPECTIVELY
        //BUT "D" DOESNT KNOW TO WHOM CONNECTINS IT IS CONNECTED
        //AND A NEURON MUST KNOW ALL THE OUTGOING CONNECTIONS FROM IT SO WE MUST TELL "D" ABOUT THE OUTGOING CONNECTIONS
        D.connections[0] = con_D_G;
        D.connections[1] = con_D_H;

        //WE MUST CONNECT NEURON "E" WITH "G","H"
        con_E_G = new Connection( //E-G
                E, //E
                G, //G
                random.nextDouble() //random weight
        );
        con_E_H = new Connection( //E-H
                E, //E
                H, //H
                random.nextDouble()
        );

        //NOW EACH CONNECTION KNOW THEY ARE CONNECTED TO "F" and "G","H" RESPECTIVELY
        //BUT "E" DOESNT KNOW TO WHOM CONNECTINS IT IS CONNECTED
        //AND A NEURON MUST KNOW ALL THE OUTGOING CONNECTIONS FROM IT SO WE MUST TELL "D" ABOUT THE OUTGOING CONNECTIONS
        E.connections[0] = con_E_G;
        E.connections[1] = con_E_H;

        //WE MUST CONNECT NEURON "F" WITH "G","H"
        con_F_G = new Connection( //F-G
                F, //F
                G, //G
                random.nextDouble() //random weight
        );
        con_F_H = new Connection( //F-H
                F, //E
                H, //H
                random.nextDouble()
        );

        //NOW EACH CONNECTION KNOW THEY ARE CONNECTED TO "F" and "G","H" RESPECTIVELY
        //BUT "F" DOESNT KNOW TO WHOM CONNECTINS IT IS CONNECTED
        //AND A NEURON MUST KNOW ALL THE OUTGOING CONNECTIONS FROM IT SO WE MUST TELL "D" ABOUT THE OUTGOING CONNECTIONS
        F.connections[0] = con_F_G;
        F.connections[1] = con_F_H;

        //NOW CONNECTING G AND H TO I
        //CONNECTING G TO I
        con_G_I = new Connection( //G-I
                G, //G
                I, //I
                random.nextDouble() //random weight
        );
        //AND TRLLING G ABOUT THE CONNECTION
        G.connections[0] = con_G_I;

        //CONNECTING H TO I
        con_H_I = new Connection( //H-I
                H, //H
                I, //I
                random.nextDouble() //random weight
        );
        //AND TRLLING H ABOUT THE CONNECTION
        H.connections[0] = con_H_I;

    }

    public NeuralNetwork(
            double bias_D, double bias_E, double bias_F,
            double bias_G, double bias_H,
            double bias_I,
            double weight_A_D, double weight_A_E, double weight_A_F,
            double weight_B_D, double weight_B_E, double weight_B_F,
            double weight_C_D, double weight_C_E, double weight_C_F,
            double weight_D_G, double weight_D_H,
            double weight_E_G, double weight_E_H,
            double weight_F_G, double weight_F_H,
            double weight_G_I,
            double weight_H_I
    ) {
        random = new Random();

        //CREATING NEURON
        A = new Neuron("A", 0, 3);
        B = new Neuron("B", 0, 3);
        C = new Neuron("C", 0, 3);
        D = new Neuron("D", bias_D, 2);
        E = new Neuron("E", bias_E, 2);
        F = new Neuron("F", bias_F, 2);
        G = new Neuron("G", bias_G, 1);
        H = new Neuron("H", bias_H, 1);
        I = new Neuron("I", bias_I, 0);

        //NOW FIRST WE MUST MAKE THE CONNECTION BETWEEN EACH NEURONS
        //FIRST WE MUST CONNECT NEURON "A" WITH "D","E","F"
        con_A_D = new Connection( //A-D
                A, //A
                D, //D
                weight_A_D
        );
        con_A_E = new Connection( //A-E
                A, //A
                E, //E
                weight_A_E
        );
        con_A_F = new Connection( //A-F
                A, //A
                F, //F
                weight_A_F
        );
        //NOW EACH CONNECTION KNOW THEY ARE CONNECTED TO "A" and "D","E","F" RESPECTIVELY
        //BUT "A" DOESNT KNOW TO WHOM CONNECTINS IT IS CONNECTED
        //AND A NEURON MUST KNOW ALL THE OUTGOING CONNECTIONS FROM IT SO WE MUST TELL "A" ABOUT THE OUTGOING CONNECTIONS
        A.connections[0] = con_A_D;
        A.connections[1] = con_A_E;
        A.connections[2] = con_A_F;

        //NOW WE MUST CONNECT NEURON "B" WITH "D","E","F"
        con_B_D = new Connection( //B-D
                B, //B
                D, //D
                weight_B_D //random weight
        );
        con_B_E = new Connection( //B-E
                B, //B
                E, //E
                weight_B_E
        );
        con_B_F = new Connection( //B-F
                B, //B
                F, //F
                weight_B_F
        );
        //NOW EACH CONNECTION KNOW THEY ARE CONNECTED TO "B" and "D","E","F" RESPECTIVELY
        //BUT "B" DOESNT KNOW TO WHOM CONNECTINS IT IS CONNECTED
        //AND A NEURON MUST KNOW ALL THE OUTGOING CONNECTIONS FROM IT SO WE MUST TELL "B" ABOUT THE OUTGOING CONNECTIONS
        B.connections[0] = con_B_D;
        B.connections[1] = con_B_E;
        B.connections[2] = con_B_F;

        //FINALLY WE MUST CONNECT NEURON "C" WITH "D","E","F"
        con_C_D = new Connection( //C-D
                C, //C
                D, //D
                weight_C_D
        );
        con_C_E = new Connection( //C-E
                C, //C
                E, //E
                weight_C_E
        );
        con_C_F = new Connection( //C-F
                C, //C
                F, //F
                weight_C_F
        );
        //NOW EACH CONNECTION KNOW THEY ARE CONNECTED TO "C" and "D","E","F" RESPECTIVELY
        //BUT "C" DOESNT KNOW TO WHOM CONNECTINS IT IS CONNECTED
        //AND A NEURON MUST KNOW ALL THE OUTGOING CONNECTIONS FROM IT SO WE MUST TELL "C" ABOUT THE OUTGOING CONNECTIONS
        C.connections[0] = con_C_D;
        C.connections[1] = con_C_E;
        C.connections[2] = con_C_F;

        //THE FIRST LAYER IS CONNECTED WITH HIDDEN LAYER ONE KNOW HIDDEN LAYER ONE WILL CONNECT WITH HIDDEN LAYER TWO
        //WE MUST CONNECT NEURON "D" WITH "G","H"
        con_D_G = new Connection( //D-G
                D, //D
                G, //G
                weight_D_G //random weight
        );
        con_D_H = new Connection( //D-H
                D, //D
                H, //H
                weight_D_H
        );

        //NOW EACH CONNECTION KNOW THEY ARE CONNECTED TO "D" and "G","H" RESPECTIVELY
        //BUT "D" DOESNT KNOW TO WHOM CONNECTINS IT IS CONNECTED
        //AND A NEURON MUST KNOW ALL THE OUTGOING CONNECTIONS FROM IT SO WE MUST TELL "D" ABOUT THE OUTGOING CONNECTIONS
        D.connections[0] = con_D_G;
        D.connections[1] = con_D_H;

        //WE MUST CONNECT NEURON "E" WITH "G","H"
        con_E_G = new Connection( //E-G
                E, //E
                G, //G
                weight_E_G
        );
        con_E_H = new Connection( //E-H
                E, //E
                H, //H
                weight_E_H
        );

        //NOW EACH CONNECTION KNOW THEY ARE CONNECTED TO "F" and "G","H" RESPECTIVELY
        //BUT "E" DOESNT KNOW TO WHOM CONNECTINS IT IS CONNECTED
        //AND A NEURON MUST KNOW ALL THE OUTGOING CONNECTIONS FROM IT SO WE MUST TELL "D" ABOUT THE OUTGOING CONNECTIONS
        E.connections[0] = con_E_G;
        E.connections[1] = con_E_H;

        //WE MUST CONNECT NEURON "F" WITH "G","H"
        con_F_G = new Connection( //F-G
                F, //F
                G, //G
                weight_F_G //random weight
        );
        con_F_H = new Connection( //F-H
                F, //E
                H, //H
                weight_F_H
        );

        //NOW EACH CONNECTION KNOW THEY ARE CONNECTED TO "F" and "G","H" RESPECTIVELY
        //BUT "F" DOESNT KNOW TO WHOM CONNECTINS IT IS CONNECTED
        //AND A NEURON MUST KNOW ALL THE OUTGOING CONNECTIONS FROM IT SO WE MUST TELL "D" ABOUT THE OUTGOING CONNECTIONS
        F.connections[0] = con_F_G;
        F.connections[1] = con_F_H;

        //NOW CONNECTING G AND H TO I
        //CONNECTING G TO I
        con_G_I = new Connection( //G-I
                G, //G
                I, //I
                weight_G_I //random weight
        );
        //AND TRLLING G ABOUT THE CONNECTION
        G.connections[0] = con_G_I;

        //CONNECTING H TO I
        con_H_I = new Connection( //H-I
                H, //H
                I, //I
                weight_H_I //random weight
        );
        //AND TRLLING H ABOUT THE CONNECTION
        H.connections[0] = con_H_I;

    }

    public void test(double dataSet[][]) {
        double truePositive = 0;
        for (int i = 0; i < dataSet.length; i++) {

            A.output        = dataSet[i][0];     //INPUT OF "A" or OUTPUT OF "A"
            B.output        = dataSet[i][1];     //INPUT OF "B" or OUTPUT OF "B"
            C.output        = dataSet[i][2];     //INPUT OF "C" or OUTPUT OF "C"
            double target   = dataSet[1][3];

            //NOW WE MUST SEND INPUT TO THE firstHiddenLayer
            //INPUT TO "D"
            D.input = con_A_D.inputNeuron.output * con_A_D.currentWeight //   "A" * A-D
                    + con_B_D.inputNeuron.output * con_B_D.currentWeight // + "B" * B-D
                    + con_C_D.inputNeuron.output * con_C_D.currentWeight;  // + "C" * C-D
            D.calcOutput();                                                // D got its input must calulate its output
            //System.out.println("output of D = "+D.output);

            //INPUT TO "E"
            E.input = con_A_E.inputNeuron.output * con_A_E.currentWeight //   "A" * A-E
                    + con_B_E.inputNeuron.output * con_B_E.currentWeight // + "B" * B-E
                    + con_C_E.inputNeuron.output * con_C_E.currentWeight;  // + "C" * C-E
            E.calcOutput();                                                // E got its input must calulate its output

            //INPUT TO "F"
            F.input = con_A_F.inputNeuron.output * con_A_F.currentWeight //   "A" * A-F
                    + con_B_F.inputNeuron.output * con_B_F.currentWeight // + "B" * B-F
                    + con_C_F.inputNeuron.output * con_C_F.currentWeight;  // + "C" * C-F
            F.calcOutput();                                                // F got its input must calulate its output

            //NOW WE MUST SEND INPUT TO THE secondHiddenLayer
            //INPUT TO "G"
            G.input = con_D_G.inputNeuron.output * con_D_G.currentWeight //   "D" * D-G
                    + con_E_G.inputNeuron.output * con_E_G.currentWeight // + "E" * E-G
                    + con_F_G.inputNeuron.output * con_F_G.currentWeight;  // + "F" * F-G
            G.calcOutput();                                                // G got its input must calulate its output

            //INPUT TO "H"
            H.input = con_D_H.inputNeuron.output * con_D_H.currentWeight //   "D" * D-H
                    + con_E_H.inputNeuron.output * con_E_H.currentWeight // + "E" * E-H
                    + con_F_H.inputNeuron.output * con_F_H.currentWeight;  // + "F" * F-H
            H.calcOutput();                                                // H got its input must calulate its output

            //FINALLY WE SEND INPUT TO THE LAST LAYER
            I.input = con_G_I.inputNeuron.output * con_G_I.currentWeight //   "G" * G-I
                    + con_H_I.inputNeuron.output * con_H_I.currentWeight;  // + "H" * H-I
            I.calcOutput();                                           //I GOT INPUT MUST calculate its out put i.e the output of the entire Nueral Network
            
            this.error = Math.abs((target - I.output) * I.output * (1.0d - I.output));
            //this.error = Math.abs((1.0d/2.0d) * Math.pow(target-I.output,2.0d));
            //System.out.println(error);
            if(this.error<0.00001)
            {
                truePositive++;
            }
            //System.out.println(error);
           
        }
        this.fitness = truePositive;
    }

    //THE NEURON CLASS i.e THE CIRCULAR NODES that we have on THE DIAGRAM
    public static class Neuron {

        private String name;
        //input will be hardly use but we will save it
        private double input;
        //the output that it will generate, will be used to generate error
        private double output;
        //its bias
        public double bias;
        //its error
        private double error;

        //THIS ARE THE CONNECTION or lines in diagram THAT WILL CONNECT DIFFERENT NODES(NEURONS)
        //WE WILL NOT NOTE THE INCOMMING CONNECTION ON A NEURON
        //WE ONLY NOTE ONLY THE OUTGOING CONNECTION OF EACH NEURON
        private Connection connections[];

        //THE CONSTRUCTOR
        //EACH NUERON AT THE TIME OF CONSTRCTION ONLY KNOWS ITS NAME AND BIAS VALUe
        //NOTE THAT WHEN WE WERE CREATING THE INPUT LAYER THE BIAS WAS 0
        //FOR HIDDEN AND OUTPUT LAYERS THE BIAS WAS RANDOM NUMBER
        public Neuron(String name, double bias, int no_of_connections) {
            this.name = name;
            this.bias = bias;
            this.connections = new Connection[no_of_connections];
        }

        private void calcOutput() {
            //SIGMOD FUNCTION
            this.input = this.input + this.bias;
            //System.out.println("in = "+this.input);
            this.output = (1.0d / (1.0d + Math.exp(-this.input)));
            //System.out.println("out = "+this.output);
        }
    }

    //THE CLASS OF CONNECTION
    public static class Connection {

        private String name;

        //THE CONNECTION MUST KNOW THE NEURON IT CAME AND THE NEURON IT GO
        private Neuron inputNeuron;
        private Neuron outputNeuron;

        //THE CURRENT WEIGHT OF THE NEURON
        public double currentWeight;

        public Connection(Neuron left, Neuron right, double weight) {
            this.name = "w" + left.name + right.name;
            this.inputNeuron = left;
            this.outputNeuron = right;
            this.currentWeight = weight;
        }
    }
    
    @Override
    public String toString() {
        return " fittness : "+this.fitness;
        //return D.bias+" "+E.bias+" "+F.bias+" "+this.fitness;
    }
}
