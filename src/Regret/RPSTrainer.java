import java.util.Arrays;
import java.util.Random;

// @author: chat gpt
public class RPSTrainer {

    public static final int ROCK = 0, PAPER = 1, SCISSORS = 2, NUM_ACTIONS = 3;
    public static final Random random = new Random();
    double[] regretSumPlayer1 = new double[NUM_ACTIONS],
            regretSumPlayer2 = new double[NUM_ACTIONS],
            strategyPlayer1 = new double[NUM_ACTIONS],
            strategyPlayer2 = new double[NUM_ACTIONS],
            strategySumPlayer1 = new double[NUM_ACTIONS],
            strategySumPlayer2 = new double[NUM_ACTIONS],
            oppStrategy = { 0.4, 0.3, 0.3 };

    private double[] getStrategy(double[] regretSum) {
        double[] strategy = new double[NUM_ACTIONS];
        double normalizingSum = 0;
        for (int a = 0; a < NUM_ACTIONS; a++) {
            strategy[a] = regretSum[a] > 0 ? regretSum[a] : 0;
            normalizingSum += strategy[a];
        }
        for (int a = 0; a < NUM_ACTIONS; a++) {
            if (normalizingSum > 0)
                strategy[a] /= normalizingSum;
            else
                strategy[a] = 1.0 / NUM_ACTIONS;
        }
        return strategy;
    }

    public int getAction(double[] strategy) {
        double r = random.nextDouble();
        int a = 0;
        double cumulativeProbability =  0;
        while (a < NUM_ACTIONS - 1) {
            cumulativeProbability += strategy[a];
            if (r < cumulativeProbability)
                break;
            a++;
        }
        return a;
    }

    public void train(int iterations) {
        double[] actionUtilityPlayer1 = new double[NUM_ACTIONS];
        double[] actionUtilityPlayer2 = new double[NUM_ACTIONS];
        for (int i = 0; i < iterations; i++) {
            double[] strategyPlayer1 = getStrategy(regretSumPlayer1);
            double[] strategyPlayer2 = getStrategy(regretSumPlayer2);

            int myActionPlayer1 = getAction(strategyPlayer1);
            int myActionPlayer2 = getAction(strategyPlayer2);

            actionUtilityPlayer1[myActionPlayer2] = 0;
            actionUtilityPlayer1[myActionPlayer2 == NUM_ACTIONS - 1 ? 0 : myActionPlayer2 + 1] = 1;
            actionUtilityPlayer1[myActionPlayer2 == 0 ? NUM_ACTIONS - 1 : myActionPlayer2 - 1] = -1;

            actionUtilityPlayer2[myActionPlayer1] = 0;
            actionUtilityPlayer2[myActionPlayer1 == NUM_ACTIONS - 1 ? 0 : myActionPlayer1 + 1] = -1;
            actionUtilityPlayer2[myActionPlayer1 == 0 ? NUM_ACTIONS - 1 : myActionPlayer1 - 1] = 1;

            for (int a = 0; a < NUM_ACTIONS; a++) {
                regretSumPlayer1[a] += actionUtilityPlayer1[a] - actionUtilityPlayer1[myActionPlayer1];
                regretSumPlayer2[a] += actionUtilityPlayer2[a] - actionUtilityPlayer2[myActionPlayer2];
            }
        }
    }

    public double[] getAverageStrategy(double[] strategySum) {
        double[] avgStrategy = new double[NUM_ACTIONS];
        double normalizingSum = 0;
        for (int a = 0; a < NUM_ACTIONS; a++)
            normalizingSum += strategySum[a];
        for (int a = 0; a < NUM_ACTIONS; a++)
            if (normalizingSum > 0)
                avgStrategy[a] = strategySum[a] / normalizingSum;
            else
                avgStrategy[a] = 1.0 / NUM_ACTIONS;
        return avgStrategy;
    }

    public static void main(String[] args) {
        RPSTrainer trainer = new RPSTrainer();
        trainer.train(1000000);
        System.out.println("Player 1 average strategy: " + Arrays.toString(trainer.getAverageStrategy(trainer.strategySumPlayer1)));
        System.out.println("Player 2 average strategy: " + Arrays.toString(trainer.getAverageStrategy(trainer.strategySumPlayer2)));
    }
}