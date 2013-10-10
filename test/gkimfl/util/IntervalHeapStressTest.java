package gkimfl.util;

import static gkimfl.util.IntervalHeapTest.assertIsHeap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class IntervalHeapStressTest {
    Random rand = new Random();
    IntervalHeap<Integer> heap;

    @Before
    public void setUp() throws Exception {
        heap = new IntervalHeap<Integer>();
    }

    @Test
    public final void testIntervalHeapStress() {
        int count = 1000000; // number of times to call offer, pollFirst, and pollLast
        boolean show = false; // print operations on the heap as they are performed
        for (int i = 0; i < count; ++i) {
            if (!heap.isEmpty() && rand.nextBoolean()) {
                if (rand.nextBoolean()) {
                    int value = heap.pollFirst();
                    if (show)
                        System.out.println("poll first " + value);
                }
                else {
                    int value = heap.pollLast();
                    if (show)
                        System.out.println("poll last " + value);
                }
            }
            else {
                int value = rand.nextInt();
                heap.offer(value);
                if (show)
                    System.out.println("offer " + value);
            }
            if (show)
                System.out.flush();
            assertIsHeap(heap);
        }
    }

    @Test
    public final void testIntervalHeapifyStress() {
        int count = 10000; // number of times to heapify
        int size = 0; // starting collection size
        boolean show = false; // print collections
        for (int i = 0; i < count; ++i, ++size) {
            ArrayList<Integer> values = new ArrayList<Integer>(size);
            for(int j = 0; j < size; ++j) {
                values.add(rand.nextInt(10*size));
            }
            if(show)
                System.out.println(Arrays.toString(values.toArray()));
            heap.clear();
            heap.addAll(values);
            assertIsHeap(heap);
        }
    }
}
