package gkimfl.util;

import static org.junit.Assert.*;

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

    public final void assertIsMaxHeap() {
        for (int i = 3; i < heap.size(); i += 2) {
            int iUp = ((i >> 1) - 1) | 1;
            int v = heap.queue.get(i);
            int vUp = heap.queue.get(iUp);
            assertFalse(v > vUp);
        }
    }

    public final void assertIsMinHeap() {
        for (int i = 2; i < heap.size(); i += 2) {
            int iUp = ((i >> 1) - 1) & ~1;
            int v = heap.queue.get(i);
            int vUp = heap.queue.get(iUp);
            assertFalse(v < vUp);
        }
    }

    public final void assertIsInterval() {
        for (int i = 0; i + 1 < heap.size(); i += 2) {
            int iSib = i + 1;
            int v = heap.queue.get(i);
            int vSib = heap.queue.get(iSib);
            assertFalse(vSib < v);
        }
    }

    public final void assertIsHeap() {
        assertIsMaxHeap();
        assertIsMinHeap();
        assertIsInterval();
    }

    @Test
    public final void testIntervalFail1() {
        // sequence of operations from a failed stress test
        heap.offer(0);
        assertIsHeap();
        heap.offer(-1);
        assertIsHeap();
        heap.offer(1);
        assertIsHeap();
        heap.offer(2);
        assertIsHeap();
        heap.pollLast();
        assertIsHeap();
        heap.offer(-2);
        assertIsHeap();
    }

    @Test
    public final void testIntervalFail2() {
        // sequence of operations from a failed stress test
        heap.offer(-5);
        assertIsHeap();
        heap.offer(-4);
        assertIsHeap();
        heap.offer(-2);
        assertIsHeap();
        heap.offer(-1);
        assertIsHeap();
        heap.offer(3);
        assertIsHeap();
        heap.offer(-3);
        assertIsHeap();
        heap.pollFirst();
        assertIsHeap();
        heap.pollLast();
        assertIsHeap();
        heap.offer(5);
        assertIsHeap();
        heap.offer(1);
        assertIsHeap();
        heap.offer(2);
        assertIsHeap();
        heap.offer(4);
        assertIsHeap();
    }

    @Test
    public final void testIntervalFail3() {
        // sequence of operations from a failed stress test
        heap.offer(-1165102467);
        assertIsHeap();
        heap.offer(102193069);
        assertIsHeap();
        heap.offer(205876161);
        assertIsHeap();
        heap.offer(127207961);
        assertIsHeap();
        heap.offer(-594993806);
        assertIsHeap();
        heap.offer(1765546476);
        assertIsHeap();
        heap.offer(-1764968581);
        assertIsHeap();
        heap.pollFirst();
        assertIsHeap();
        heap.pollLast();
        assertIsHeap();
        heap.pollFirst();
        assertIsHeap();
        heap.offer(-1439426752);
        assertIsHeap();
        heap.offer(372984614);
        assertIsHeap();
        heap.offer(565856971);
        assertIsHeap();
        heap.offer(640358302);
        assertIsHeap();
    }

    @Test
    public final void testIntervalHeapStress() {
        int count = 10000000; // number of times to call offer, pollFirst, and pollLast
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
            assertIsHeap();
        }
    }
}
