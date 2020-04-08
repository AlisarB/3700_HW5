import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.ArrayList;

class FruitProducerConsumer extends Thread
{
    long start = System.nanoTime();
    long finish;
    static ArrayList items = new ArrayList();
    int max;
    static Lock theLock = new ReentrantLock();
    int i = 0;
    int amountToEat;
    static int eaten = 0;
    int type; //1= producer, 2 = consumer

    public FruitProducerConsumer(ArrayList items, int max, int amountToEat, int type)
    {
        this.items = items;
        this.max = max;
        this.theLock = theLock;
        this.type = type;
        this.amountToEat = amountToEat;
        
    }

    public void run()
    {
      //name = 1 is a producer type
      if(type == 1)
      {
         while(i < 100)
         {
            if(items.size() < 10)
            {
               synchronized(this)
               {
                  System.out.println("A Fruit has been produced!");
                  items.add(1);
                  i++;
               } 
            }
            else if(items.size() == 10) //Give a chance for the consumers to aquire lock and eat items
            {
              try
              {
                 Thread.sleep(2000);
              }
              catch(InterruptedException e)
              {
                 e.printStackTrace();
              }
            }
            else //needs to aquire lock to fill the list
            {
               try
               {
                  Thread.sleep(200);
               }
               catch(InterruptedException e)
               {
                  e.printStackTrace();
               }
            }
          }
       }
       else //it is a consumer type, type == 2
       {
            while (eaten < amountToEat)
            {
               if(items.size() > 0)
               {
                  synchronized(this)
                  {
                     items.remove(items.size() - 1);
                     eaten++;
                     System.out.println("A Fruit has been eaten! Fruit number " + eaten);
                     //Sleep for 1 second to consume item
                     try
                     {
                        Thread.sleep(1000);
                     }
                     catch(InterruptedException e)
                     {
                        e.printStackTrace();
                     }
                     if(eaten == amountToEat)
                     {
                        finish = System.nanoTime();
                        System.out.println("This program took " + (finish-start) + " amount of time to run.");
                     }
                  }
               }
               else
               {
                  try
                  {
                     Thread.sleep(750);
                  }
                  catch(InterruptedException e)
                  {
                     e.printStackTrace();
                  }
               }
            }
       }
   }
}
public class ProducerConsumerIsolatedSections
{
    public static void main(String args[])
    {
        int numProducers = 2;
        int numProductsPerProducer = 100;
        ArrayList items = new ArrayList();
        final int BUFFER_MAX_SIZE = 10;
        
        //Producers
        new FruitProducerConsumer(items, BUFFER_MAX_SIZE, numProducers*numProductsPerProducer, 1).start();
        new FruitProducerConsumer(items, BUFFER_MAX_SIZE, numProducers*numProductsPerProducer, 1).start();
        
        //Consumers
        new FruitProducerConsumer(items, BUFFER_MAX_SIZE, numProducers*numProductsPerProducer, 2).start();
        new FruitProducerConsumer(items, BUFFER_MAX_SIZE, numProducers*numProductsPerProducer, 2).start();
        new FruitProducerConsumer(items, BUFFER_MAX_SIZE, numProducers*numProductsPerProducer, 2).start();
        new FruitProducerConsumer(items, BUFFER_MAX_SIZE, numProducers*numProductsPerProducer, 2).start();
        new FruitProducerConsumer(items, BUFFER_MAX_SIZE, numProducers*numProductsPerProducer, 2).start();
    }
}