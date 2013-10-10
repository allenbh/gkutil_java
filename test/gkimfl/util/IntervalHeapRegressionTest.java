package gkimfl.util;

import static gkimfl.util.IntervalHeapTest.assertIsHeap;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class IntervalHeapRegressionTest {
    Random rand = new Random();
    IntervalHeap<Integer> heap;

    @Before
    public void setUp() throws Exception {
        heap = new IntervalHeap<Integer>();
    }

    @Test
    public final void testIntervalFail1() {
        // sequence of operations from a failed stress test
        heap.offer(0);
        assertIsHeap(heap);
        heap.offer(-1);
        assertIsHeap(heap);
        heap.offer(1);
        assertIsHeap(heap);
        heap.offer(2);
        assertIsHeap(heap);
        heap.pollLast();
        assertIsHeap(heap);
        heap.offer(-2);
        assertIsHeap(heap);
    }

    @Test
    public final void testIntervalFail2() {
        // sequence of operations from a failed stress test
        heap.offer(-5);
        assertIsHeap(heap);
        heap.offer(-4);
        assertIsHeap(heap);
        heap.offer(-2);
        assertIsHeap(heap);
        heap.offer(-1);
        assertIsHeap(heap);
        heap.offer(3);
        assertIsHeap(heap);
        heap.offer(-3);
        assertIsHeap(heap);
        heap.pollFirst();
        assertIsHeap(heap);
        heap.pollLast();
        assertIsHeap(heap);
        heap.offer(5);
        assertIsHeap(heap);
        heap.offer(1);
        assertIsHeap(heap);
        heap.offer(2);
        assertIsHeap(heap);
        heap.offer(4);
        assertIsHeap(heap);
    }

    @Test
    public final void testIntervalFail3() {
        // sequence of operations from a failed stress test
        heap.offer(-1165102467);
        assertIsHeap(heap);
        heap.offer(102193069);
        assertIsHeap(heap);
        heap.offer(205876161);
        assertIsHeap(heap);
        heap.offer(127207961);
        assertIsHeap(heap);
        heap.offer(-594993806);
        assertIsHeap(heap);
        heap.offer(1765546476);
        assertIsHeap(heap);
        heap.offer(-1764968581);
        assertIsHeap(heap);
        heap.pollFirst();
        assertIsHeap(heap);
        heap.pollLast();
        assertIsHeap(heap);
        heap.pollFirst();
        assertIsHeap(heap);
        heap.offer(-1439426752);
        assertIsHeap(heap);
        heap.offer(372984614);
        assertIsHeap(heap);
        heap.offer(565856971);
        assertIsHeap(heap);
        heap.offer(640358302);
        assertIsHeap(heap);
    }

    @Test
    public final void testFail4() {
        // crafted to have 50 and 40 out of order in max heap
        heap.offer(0);
        heap.offer(100);

        heap.offer(1);
        heap.offer(40);
        heap.offer(2);
        heap.offer(50);

        heap.offer(60);
        assertIsHeap(heap);
    }
}
