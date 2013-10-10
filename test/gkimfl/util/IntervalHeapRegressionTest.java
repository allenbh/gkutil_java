package gkimfl.util;

import static gkimfl.util.IntervalHeapTest.assertIsHeap;

import java.util.Arrays;
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

    @Test
    public final void testHeapify1() {
        heap.addAll(Arrays.asList(new Integer[] {
                92, 100, 71, 85, 47, 42, 85, 51, 41, 102, 53 }));
        assertIsHeap(heap);
    }

    @Test
    public final void testHeapify2() {
        heap.addAll(Arrays.asList(new Integer[] {
                42, 78, 63, 56, 187, 158, 164, 7, 135, 167, 47,
                70, 89, 117, 105, 21, 121, 128, 145 }));
        assertIsHeap(heap);
    }

    @Test
    public final void testHeapify3() {
        heap.addAll(Arrays.asList(new Integer[] {
                113, 159, 4, 117, 41, 143, 16, 79, 7, 8, 92, 105,
                64, 84, 17, 47, 117 }));
        assertIsHeap(heap);
    }

    @Test
    public final void testHeapify4() {
        heap.addAll(Arrays.asList(new Integer[] {
                146, 122, 130, 119, 64, 24, 106, 61, 85, 104, 138, 50, 12, 10, 32 }));
        assertIsHeap(heap);
    }

}
