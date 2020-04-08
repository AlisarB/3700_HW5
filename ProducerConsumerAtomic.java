import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.concurrent.atomic.*;

class FruitProducerConsumer extends Thread
{
    //Made 11 because consumers can't reach index 0 spot, so still 10 usable spots
    int max;
    AtomicInteger i = new AtomicInteger(0);
    int amountToEat;
    static AtomicInteger eaten =  new AtomicInteger(0);
    int type; //1= producer, 2 = consumer
    static AtomicInteger bufferSpot = new AtomicInteger(0);
    long start = System.nanoTime();
    long finish;

    public FruitProducerConsumer(int max, int amountToEat, int type)
    {
        this.max = max;
        this.type = type;
        this.amountToEat = amountToEat;
        
    }

    public void run()
    {
    //name = 1 is a producer type
      if(type == 1)
      {
         while(i.get() < 100)
         {
            if(bufferSpot.get() < 11) 
            {
                  System.out.println("A Fruit has been produced");
                  bufferSpot.incrementAndGet();
                  i.incrementAndGet();
            }
            else if(bufferSpot.get() == 10) //Give a chance for the consumers to aquire lock and eat items
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
            while (eaten.get() < amountToEat)
            {
               if(bufferSpot.get() > 0)
               {
                  bufferSpot.decrementAndGet();
                  eaten.incrementAndGet();
                  System.out.println("A Fruit has been eaten! Fruit number " + eaten);
                  try
                  {
                     Thread.sleep(1000);
                  }
                  catch(InterruptedException e)
                  {
                     e.printStackTrace();
                  }
                  if(eaten.get() == amountToEat)
                  {
                     finish = System.nanoTime();
                     System.out.println("The amount of time it took for this program to run is " + (finish-start));
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

public class ProducerConsumerAtomic
{
    public static void main(String args[])
    {
        int numProducers = 2;
        int numProductsPerProducer = 100;
        //Made 11 because consumers can't reach index 0 spot, so still 10 usable spots
        final int BUFFER_MAX_SIZE = 11;
        
        //Producer(s)
        new FruitProducerConsumer( BUFFER_MAX_SIZE, numProducers*numProductsPerProducer, 1).start();
        new FruitProducerConsumer( BUFFER_MAX_SIZE, numProducers*numProductsPerProducer, 1).start();
        
        //Consumer(s)
        new FruitProducerConsumer(BUFFER_MAX_SIZE, numProducers*numProductsPerProducer, 2).start();
        new FruitProducerConsumer(BUFFER_MAX_SIZE, numProducers*numProductsPerProducer, 2).start();
        new FruitProducerConsumer(BUFFER_MAX_SIZE, numProducers*numProductsPerProducer, 2).start();
        new FruitProducerConsumer(BUFFER_MAX_SIZE, numProducers*numProductsPerProducer, 2).start();
        new FruitProducerConsumer(BUFFER_MAX_SIZE, numProducers*numProductsPerProducer, 2).start();
    }
}