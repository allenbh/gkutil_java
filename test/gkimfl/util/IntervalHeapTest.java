package gkimfl.util;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class IntervalHeapTest {

    IntervalHeap<Integer> heap;

    @Before
    public void setUp() throws Exception {
        heap = new IntervalHeap<Integer>(Arrays.asList(new Integer[] {
                1, 7, 5, 3, 8, 2, 4, 6, 0
        }));
    }

    public static void assertIsMaxHeap(IntervalHeap<Integer> h) {
        for (int i = 3; i < h.size(); i += 2) {
            int iUp = ((i >> 1) - 1) | 1;
            int v = h.queue.get(i);
            int vUp = h.queue.get(iUp);
            assertFalse(v > vUp);
        }
        
        // empty interval min leaf is also considered in the max heap
        if ((h.size() & 1) == 1 && h.size() > 2) {
            int i = h.size() - 1;
            int iUp = ((i >> 1) - 1) | 1;
            int v = h.queue.get(i);
            int vUp = h.queue.get(iUp);
            assertFalse(v > vUp);
        }
    }

    public static void assertIsMinHeap(IntervalHeap<Integer> h) {
        for (int i = 2; i < h.size(); i += 2) {
            int iUp = ((i >> 1) - 1) & ~1;
            int v = h.queue.get(i);
            int vUp = h.queue.get(iUp);
            assertFalse(v < vUp);
        }
    }

    public static void assertIsInterval(IntervalHeap<Integer> h) {
        for (int i = 0; i + 1 < h.size(); i += 2) {
            int iSib = i + 1;
            int v = h.queue.get(i);
            int vSib = h.queue.get(iSib);
            assertFalse(vSib < v);
        }
    }

    public static void assertIsHeap(IntervalHeap<Integer> h) {
        assertIsMaxHeap(h);
        assertIsMinHeap(h);
        assertIsInterval(h);
    }

    @Test
    public final void testOffer() {
        heap = new IntervalHeap<Integer>();
        heap.offer(1);
        heap.offer(7);
        heap.offer(5);
        heap.offer(3);
        heap.offer(8);
        heap.offer(2);
        heap.offer(4);
        heap.offer(6);
        heap.offer(0);
        assertIsHeap(heap);
    }

    @Test
    public final void testPollMin() {
        assertTrue(heap.pollFirst() == 0);
        assertTrue(heap.pollFirst() == 1);
        assertTrue(heap.pollFirst() == 2);
        assertTrue(heap.pollFirst() == 3);
        assertTrue(heap.pollFirst() == 4);
        assertTrue(heap.pollFirst() == 5);
        assertTrue(heap.pollFirst() == 6);
        assertTrue(heap.pollFirst() == 7);
        assertTrue(heap.pollFirst() == 8);
    }

    @Test
    public final void testPollMax() {
        assertTrue(heap.pollLast() == 8);
        assertTrue(heap.pollLast() == 7);
        assertTrue(heap.pollLast() == 6);
        assertTrue(heap.pollLast() == 5);
        assertTrue(heap.pollLast() == 4);
        assertTrue(heap.pollLast() == 3);
        assertTrue(heap.pollLast() == 2);
        assertTrue(heap.pollLast() == 1);
        assertTrue(heap.pollLast() == 0);
    }
}
