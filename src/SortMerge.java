import java.security.spec.RSAOtherPrimeInfo;

public class SortMerge
{

    public static void sort(int[] arr) 
    {
        /* printer() */
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print("" + arr[i] + ", ");
        }
        System.out.println("]");
        /* printer done */

        System.out.println("Array length:" + arr.length);
        Queue queue = new Queue();



        /* enqueue first index and then find subsequent indexes
           that mark the end and beginning of new sorted subsets,
           finally adds last index as the end of the list
         */
        queue.enqueue(0);
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                System.out.print(i - 1);
                System.out.print(", ");
                System.out.print(i);
                System.out.print(", ");
                queue.enqueue(i - 1);
                queue.enqueue(i);
            }
        }
        queue.enqueue(arr.length - 1);
        System.out.println("\n" + queue.size());





        int[] new_arr = new int[arr.length];
        if (queue.size() == 2) {
            return;
        }


        while (queue.size() != 2) {
            int a1_index = (int) queue.dequeue();
            int a1_back = (int) queue.dequeue();
            int a2_index = (int) queue.dequeue();
            int a2_back = (int) queue.dequeue();
            if ((a1_back + 1 != a2_index) && (a2_back + 1 != a1_index)) {
                System.out.println("uneven");
                queue.enqueue(a1_index);
                queue.enqueue(a1_back);
                a1_index = (int) queue.dequeue();
                a1_back = (int) queue.dequeue();
            }
            System.out.println("a1_index: " + a1_index);
            System.out.println("a1_back: " + a1_back);
            System.out.println("a2_index: " + a2_index);
            System.out.println("a2_back: " + a2_back);

            int smallest_index = Math.min(a1_index, a2_index);
            int current_index = smallest_index;

            while ((a1_index <= a1_back) || (a2_index <= a2_back)) { //while either array is not empty
                if (a1_index > a1_back) { // if first array is out of index, slot in second array
                    //System.out.println("out of first array indices");
                    new_arr[current_index] = arr[a2_index];
                    a2_index++;
                } else if (a2_index > a2_back) { //if second array is out of index, slot in first array
                    //System.out.println("out of second array indices");
                    new_arr[current_index] = arr[a1_index];
                    a1_index++;
                } else if (arr[a1_index] < arr[a2_index]) { //otherwise compare
                    //System.out.println("first array placed first");
                    new_arr[current_index] = arr[a1_index];
                    a1_index++;
                } else {
                    //System.out.println("second array placed first");
                    new_arr[current_index] = arr[a2_index];
                    a2_index++;
                }

                current_index++;
            }
            queue.enqueue(smallest_index);
            queue.enqueue(Math.max(a1_back, a2_back));
            for (int i = smallest_index; i <= Math.max(a1_back, a2_back); i++) {
                arr[i] = new_arr[i];
            }
        }

        return;


//        for (int i = 0; i < arr.length; i++) { //copies over array
//            arr[i] = new_arr[i];
//        }
//        return
	    //TO BE IMPLEMENTED
    }
    
}
	