package gkimfl.util;

import static gkimfl.util.HeapTest.assertIsHeap;
import static gkimfl.util.HeapTest.TestItem;
import static gkimfl.util.HeapTest.TestItemMutableInt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class HeapStressTest {
    Random rand = new Random();
    Heap<TestItem> heap;

    @Before
    public void setUp() throws Exception {
        heap = new Heap<TestItem>(new TestItemMutableInt());
    }

    @Test
    public final void testHeapStress() {
        int count = 1000000; // number of times to call offer, pollFirst, and pollLast
        boolean show = false; // print operations on the heap as they are performed
        for (int i = 0; i < count; ++i) {
            if (!heap.isEmpty() && rand.nextBoolean()) {
                TestItem item = heap.poll();
                if (show)
                    System.out.println("poll " + item.val);
            }
            else {
                TestItem item = new TestItem(rand.nextInt());
                heap.offer(item);
                if (show)
                    System.out.println("offer " + item.val);
            }
            if (show)
                System.out.flush();
            assertIsHeap(heap);
        }
    }

    @Test
    public final void testHeapifyStress() {
        int count = 10000; // number of times to heapify
        int size = 0; // starting collection size
        boolean show = false; // print collections
        for (int i = 0; i < count; ++i, ++size) {
            ArrayList<TestItem> items = new ArrayList<TestItem>(size);
            for (int j = 0; j < size; ++j) {
                items.add(new TestItem(rand.nextInt(10 * size)));
            }
            if (show)
                System.out.println(Arrays.toString(items.toArray()));
            heap.clear();
            heap.addAll(items);
            assertIsHeap(heap);
        }
    }
}
