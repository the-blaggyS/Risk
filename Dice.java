import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates random number between 1 and 6 included.
 */
public class Dice
{

    public Dice()
    {

    }

    /**
     * Generates a random number
     * 
     * Arguments:
     * int amountOfDices -- number of random numbers to generate
     * 
     * Return:
     * int[] rolledNumbers -- random numbers
     */
    public int[] roll(int amountOfDices)
    {
        int[] rolledNumbers = new int[amountOfDices];
        for (int i = 0; i < amountOfDices; i++)
        {
            rolledNumbers[i] = ThreadLocalRandom.current().nextInt(1, 7);
        }
        
        for (int i = 0; i < rolledNumbers.length; i++)
        {
            for (int j = i; j > 0; j--)
            {
                if (rolledNumbers[j] > rolledNumbers[j-1])
                {
                    int tmp = rolledNumbers[j];
                    rolledNumbers[j] = rolledNumbers[j-1];
                    rolledNumbers[j-1] = tmp;
                }
            }
        }
        
        return rolledNumbers;
    }
    
}
