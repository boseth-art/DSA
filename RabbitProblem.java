public class RabbitProblem {

    public static long calculateRabbitPairs(int months) {
        if (months <= 0) return 0;
        if (months == 1 || months == 2) return 1;

        long prevPrev = 1; // Month 1
        long prev = 1;     // Month 2
        long current = 0;

        for (int i = 3; i <= months; i++) {
            current = prev + prevPrev;
            prevPrev = prev;
            prev = current;
        }

        return current;
    }

    public static void main(String[] args) {
        int months = 12; // Calculate for 1 year
        long totalPairs = calculateRabbitPairs(months);
        
        System.out.println("Total rabbit pairs after " + months + " months: " + totalPairs);
        
        // Printing the sequence for clarity
        System.out.print("Growth sequence: ");
        for (int i = 1; i <= months; i++) {
            System.out.print(calculateRabbitPairs(i) + (i == months ? "" : ", "));
        }
    }
}