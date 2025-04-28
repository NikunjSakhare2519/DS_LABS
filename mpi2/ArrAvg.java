import mpi.MPI;

import java.util.Scanner;

import mpi.*;

public class ArrAvg{
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
                System.out.println("Element " + i + "\t = " + i);
                send_buffer[i] = i;
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
        for(int i = 1; i < unitsize; i++) {
            recieve_buffer[0] += recieve_buffer[i];
        }
        recieve_buffer[0]=recieve_buffer[0]/unitsize;
        System.out.println(
            "Intermediate sum at process " + rank + " is " + recieve_buffer[0]
        );
        

        //  Gather data from processes
        MPI.COMM_WORLD.Gather(
            recieve_buffer,
            0,
            1,
            MPI.DOUBLE,
            new_recieve_buffer,
            0,
            1,
            MPI.DOUBLE,
            root
        );

        //  Aggregate output from all non root processes
        if(rank == root) {
            double total_sum = 0;
            for(int i = 0; i < size; i++) {
                total_sum += new_recieve_buffer[i];
            }
            total_sum=total_sum/size;
            System.out.println("Final sum : " + total_sum);
        }

        MPI.Finalize();
    }
}




// export MPJ_HOME=/home/nikunj/Desktop/final/mpi2/mpj
// export PATH=$MPJ_HOME/bin:$PATH
// javac -cp $MPJ_HOME/lib/mpj.jar ArrAvg.java
// $MPJ_HOME/bin/mpjrun.sh -np 4 ArrAvg

