
import mpi.MPI;

import java.util.Scanner;

import mpi.*;

public class ArrRec{
    public static void main(String[] args) throws Exception{
        MPI.Init(args);
        
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        
        int unitsize = 4;
        int root = 0;
        double send_buffer[] = null;
        //  1 process is expected to handle 4 elements
        send_buffer = new double [unitsize * size];
        double recieve_buffer[] = new double [unitsize];
        double new_recieve_buffer[] = new double [size];

        //  Set data for distribution
        if(rank == root) {
            int total_elements = unitsize * size;
            System.out.println("Enter " + total_elements + " elements");
            for(int i = 0; i < total_elements; i++) {
                System.out.println("Element " + i + "\t = " + (1.0/(i+1)));
                send_buffer[i] = (1.0/(i+1));
            }
        }

        //  Scatter data to processes
        MPI.COMM_WORLD.Scatter(
            send_buffer,
            0,
            unitsize,
            MPI.DOUBLE,
            recieve_buffer,
            0,
            unitsize,
            MPI.DOUBLE,
            root
        );

        //  Calculate sum at non root processes
        //  Store result in first index of array
        double local_sum = 0;
        for(int i = 0; i < unitsize; i++) {
            local_sum += recieve_buffer[i];
        }
        recieve_buffer[0] = local_sum;

        System.out.println(
            "Intermediate sum at process " + rank + " is " + recieve_buffer[0]
        );
        

        //  Gather data from processes
        
        
}


// export MPJ_HOME=/home/vboxuser/Downloads/mpj-v0_44
// export PATH=$MPJ_HOME/bin:$PATH
// javac -cp $MPJ_HOME/lib/mpj.jar ArrRec.java
// $MPJ_HOME/bin/mpjrun.sh -np 4 ArrRec


