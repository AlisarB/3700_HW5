import java.util.Scanner;
import java.util.ArrayList;

public class HW5PrimesSingle
{
   public static int[] findPrimes(int num)
   {
      int[] primes = new int[num+1];
      int i = 2;
      int j = 0;
      
      for(int k = 0; k < (num+1); k++)
      {
         primes[k] = 1;
      }
      primes[0] = 0;
      primes[1] = 0;
      
      while((i*i) <= num)
      {
         if(primes[i] == 1) //means this index is a prime number
         {
            //set multiples = to hold 0, meaning they are composite numbers
            j = i;
            while((j*i) <= num)
            {
               primes[i*j] = 0;
               j++;
            }
            i++;
         }
         else
         {
            i++;
         }
      }
      
      return primes;
   }
   
   public static void main(String[] args)
   {
      long start;
      long finish;
   
      int lastNum;
      int[] foundNums;
      Scanner reader = new Scanner(System.in);
      
      //**Enter number from user, used to know what number we should find all the primes until
      System.out.println("Please enter, until what number shall we find all the prime numbers up to?");
      lastNum = reader.nextInt();
      //System.out.println(lastNum);
      
      start = System.nanoTime();
      foundNums = findPrimes(lastNum);
      finish = System.nanoTime();
      System.out.println("The total time it took to calculate all the primes was: " + (finish - start));
            
      //**Print the elements in the arrayList (the prime numbers)
      for(int i = 0; i < foundNums.length; i++)
      {
         if(foundNums[i] == 1)
         {
            System.out.print(i + " ");
         }
      }
      
      //Output 1's and 0's of array
      /*
      for(int i = 0; i < foundNums.length; i++)
      {
         System.out.print(foundNums[i] + " ");
      }
      */

   }
}