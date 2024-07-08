import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CacheSimulation {
    private int cacheSize;
    private int associativity;
    private int blockSize;
    private String traceFilePath;
    
    private int numSets;
    private int setIndexBits;
    
    private ArrayList<ArrayList<Integer>> cachesets;
    private int[] tags;
    private boolean[] validBits;
    
    private List<String> traceaddresses;
    
    private int totalhits;
    private int totalmisses;
    private int[] sethits;
    private int[] setmisses;

    public CacheSimulation(int cacheSize, int associativity, int blockSize, String traceFilePath) {
        this.cacheSize = cacheSize;
        this.associativity = associativity;
        this.blockSize = blockSize;
        this.traceFilePath = traceFilePath;

        this.numSets = (cacheSize * 1024) / (associativity * blockSize);
        this.setIndexBits = (int) (Math.log(numSets) / Math.log(2) );

        this.cachesets = new ArrayList<ArrayList<Integer>>(numSets);
        this.tags = new int[numSets * associativity ];
        this.validBits = new boolean[numSets * associativity];

	Arrays.fill(tags,-1);

        for (int i = 0; i < numSets; i++) {
            cachesets.add(new ArrayList<Integer>(associativity));
        }

        this.traceaddresses = new ArrayList<>();
    }

    public void simulateCache() {
        readTraceFile();
        processTraceAddresses();
        printCacheStatistics();
    }

    private void readTraceFile() {
        try (Scanner scanner = new Scanner(new File(traceFilePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                
               // String extractedString = line.substring(0, line.length() - 6);
                
               	
                String address = Long.toBinaryString(Long.parseLong(line , 16)); 
                address=(String.format("%32s",address)).replace(" ","0");    
                
        	//int addre = Integer.parseInt( address,2 );
        	//System.out.print(addre +" ");
        	
                traceaddresses.add(address);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Trace file not found: " + traceFilePath);
            return;
        }
    }

    private void processTraceAddresses() {
        this.totalhits = 0;
        this.totalmisses = 0;
        this.sethits = new int[numSets];
        this.setmisses = new int[numSets];


        for (String address : traceaddresses) {
            //int = (int)Math.pow(2,setIndexBits)-1;
            //int tshift= (int)Math.pow(2,26-setIndexBits)-1;
            String sshift  = address.substring(26-setIndexBits,26);
            String tshift = address.substring(0,26-setIndexBits);
            int setIndex=Integer.parseInt(sshift,2);
            int tag = Integer.parseInt(tshift,2);

            ArrayList<Integer> cacheSet = new ArrayList<Integer>();
            cacheSet=cachesets.get(setIndex);
            boolean hit = false;

            for (int i = 0; i < cacheSet.size(); i++) {
                //if (validBits[setIndex * associativity + i] && tags[setIndex * associativity + i] == tag) {
                    // Cache hit
                    if(cacheSet.get(i)==tag){
                    hit = true;
                    totalhits++;
                    sethits[setIndex]++;

                    // Update LRU position by moving the hit block to the front
                    cacheSet.remove(i);
                    cacheSet.add(tag);

                    break;
                }
            }

            if (!hit) {
            
                // Cache miss
                totalmisses++;
                setmisses[setIndex]++;

                if (cacheSet.size() < associativity) {
                    // Add the block to the cache set since there is an empty slot
                    //int emptySlot = cacheSet.size();
                    cacheSet.add(tag);
                } else {
                    // Replace the LRU block in the cache set
                   // int lruBlock = cacheSet.get(associativity - 1);
                    cacheSet.remove(0);
                    cacheSet.add(tag);

                    // Update the metadata for the replaced block
                    //validBits[setIndex * associativity + lruBlock] = true;
                   // tags[setIndex * associativity + lruBlock] = tag;
                }
            }
        }
    }

    private void printCacheStatistics() {
        System.out.println("Total Hits: " + totalhits);
        System.out.println("Total Misses: " + totalmisses);

        System.out.println("\nSet-wise Hits:");
        for (int i = 0; i < numSets; i++) {
            System.out.print(sethits[i]);
            System.out.print( " ");
        }

        System.out.println("\nSet-wise Misses:");
        for (int i = 0; i < numSets; i++) {
            System.out.print(sethits[i]);
            System.out.print( " ");
       }
    }

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Usage: java CacheSimulation <CacheSize> <Associativity> <BlockSize> <TraceFilePath>");
            return;
        }

        int cacheSize = Integer.parseInt(args[0]);
        int associativity = Integer.parseInt(args[1]);
        int blockSize = Integer.parseInt(args[2]);
        String traceFilePath = args[3];

        CacheSimulation cacheSimulator = new CacheSimulation(cacheSize, associativity, blockSize, traceFilePath);
        cacheSimulator.simulateCache();
    }
}


