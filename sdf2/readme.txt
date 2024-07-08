                                                                       ASSIGNMENT-3
                                                                 Cachememory Simulation
                                                                  ESLAVATH NAVEEN NAIK
                                                                      CS22BTECH1021
                                                                       
The CacheSimulation is a Java program that simulates a cache memory system based on the provided cache parameters and a trace file containing memory addresses. It calculates cache hits, misses, and set-wise statistics.
How to Use:

To run the CacheSimulation program, follow these steps:

    Compile the Java source code:

(javac CacheSimulation.java)

Run the program with the appropriate command-line arguments:

    java CacheSimulation <CacheSize> <Associativity> <BlockSize> <TraceFilePath>

        <CacheSize>: The size of the cache in kilobytes (KB).
        <Associativity>: The associativity of the cache (e.g., 1 for direct-mapped, 2 for 2-way set associative, etc.).
        <BlockSize>: The size of each cache block in bytes.
        <TraceFilePath>: The path to the trace file containing memory addresses in hexadecimal format.

    The program will process the trace file, simulate the cache, and print the cache statistics.

TRACE FILE FORMAT:

The trace file should contain memory addresses in hexadecimal format, with each address on a separate line. Here is an example of a trace file:

A1F89B1E
57C2A413
F320AC56
12B7E904

CACHE STATITICS:

The CacheSimulation program calculates the following statistics:

    Total Hits: The total number of cache hits during the simulation.
    Total Misses: The total number of cache misses during the simulation.
    Set-wise Hits: The number of hits for each cache set.

