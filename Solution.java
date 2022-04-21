/*
    Siguiendo la resolución llegada en la entrevista

    Supuestos: 
        - Input acotado: no preocuparme por la eficiencia
        - Los datos llegan en un estado correcto (ambos arrays son de enteros y tienen el mismo tamaño, etc)
        - Puede haber dos empresas con el mismo currentValue y futureValue
*/
public class Solution{


    public static void main(String[] args) {
        int currentValues[] = new int[] {175,133,109,210,97};
        int futureValues[] = new int[] {200,125,128,228,133};
       

        int savings = 250;
        solve(currentValues,futureValues,savings);
    }


    /*
        Brute force solution. Iterates through all possible combinations and prints the optimal choices.
        The companies are named based on their position in the array.
    */
    public static void solve(int[] currentValues, int[] futureValues,int savings){
        int n = currentValues.length;
        boolean combination[];
        int maxBenefit = 0, currentBenefit=0;
        boolean optimalCombination[] = new boolean[n];
        
        for(int i=0; i < (int)Math.pow(2, n); i++){ // Traversing all possible combinations.
            combination = generateCombination(i,n);
            currentBenefit = 0;
            if((currentBenefit = getBenefit(combination, currentValues, futureValues)) > maxBenefit){
               if(savings > getCost(combination, currentValues)){
                maxBenefit = currentBenefit ;
                optimalCombination = combination.clone();
               }
            }
        }

        System.out.println("Max benefit: " + maxBenefit);
        printOptimalChoice(optimalCombination);
    }

    
    public static void printOptimalChoice(boolean optimalCombination[]){
        System.out.println("The companies whose stocks have to be bought are: ");
        for(int i=0; i<optimalCombination.length; i++){
           if(optimalCombination[i])
                System.out.print(i+1 + " ");
        }
        System.out.println(" ");
    }

    public static int getCost(boolean combination[], int currentValues[]){
        int cost = 0;

        for(int i=0; i<combination.length; i++){
            if(combination[i])
                cost += currentValues[i];
        }

        return cost;
    }


    public static int getBenefit(boolean combination[], int currentValues[], int futureValues[]){
        int benefit = 0;

        for(int i=0; i<combination.length; i++){
            if(combination[i])
                benefit = benefit + futureValues[i] - currentValues[i];
        }

        return benefit;
    }

    /*
        There is a one to one mapping between each combination and each iteration (represented in binary) 
        showing wether a company´s share should be bought. This function finds said mapping and returns an
        array of booleans indicating the position in the array currentValue of the companies whose shares 
        should be bought.
    */
    public static boolean[] generateCombination(int iteration, int numbCompanies){
        boolean combination[] = new boolean[numbCompanies];

        // Filling the array from the last position to the first
        for(int i=0; i<numbCompanies; i++){
            combination[i] = Integer.lowestOneBit(iteration>>i) == 1; //checks if the last bit is 1 (I.e. if that company is considered in this combination)
        }
        
        return combination;
    }
}