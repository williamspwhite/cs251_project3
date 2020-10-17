public class SortMerge 
{

    public static void sort(int[] arr) 
    {
        Queue queue = new Queue();


        /* enqueue first index and then find subsequent indexes
           that mark the end and beginning of new sorted subsets,
           finally adds last index as the end of the list
         */
        queue.enqueue(0);
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                queue.enqueue(i - 1);
                queue.enqueue(i);
            }
        }
        queue.enqueue(arr.length - 1);


        int[] new_arr = new int[arr.length];
        if (queue.size() == 2) {
            return;
        }
        int a1_index = (int) queue.dequeue();
        int a1_back = (int) queue.dequeue();
        int a2_index = (int) queue.dequeue();
        int a2_back = (int) queue.dequeue();


        int current_index = 0;
        int smallest_index = Math.min(a1_index, a2_index);

        while (queue.isEmpty() == false) {
            current_index = smallest_index;

            while ((a1_index <= a1_back) || (a2_index <= a2_back)) {
                if (a1_index > a1_back) { // if first array is out of index, slot in second array
                    new_arr[current_index] = arr[a2_index];
                    a2_index++;
                } else if (a2_index > a2_back) { //if second array is out of index, slot in first array
                    new_arr[current_index] = arr[a1_index];
                    a1_index++;
                } else if (arr[a1_index] < arr[a2_index]) { //otherwise compare
                    new_arr[current_index] = arr[a1_index];
                    a1_index++;
                } else {
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

            a1_index = (int) queue.dequeue();
            a1_back = (int) queue.dequeue();
            a2_index = (int) queue.dequeue();
            a2_back = (int) queue.dequeue();

            if (a1_back + 1 != a2_index) {
                queue.enqueue(a1_index);
                queue.enqueue(a1_back);
                a1_index = (int) queue.dequeue();
                a2_back = (int) queue.dequeue();
            }
            smallest_index = Math.min(a1_index, a2_index);
        }
        return;


//        for (int i = 0; i < arr.length; i++) { //copies over array
//            arr[i] = new_arr[i];
//        }
//        return;
	    //TO BE IMPLEMENTED
    }

}
	