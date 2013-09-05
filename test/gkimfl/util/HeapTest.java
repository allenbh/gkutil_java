package gkimfl.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

public class HeapTest {

    static class TestItem implements Comparable<TestItem> {
        public TestItem() {}

        public TestItem(int value) {
            val = value;
        }

        public int compareTo(TestItem o) {
            return val - o.val;
        }

        int val;
        int pos;
    }

    static class TestItemMutableInt extends MutableInt<TestItem> {
        @Override
        public int get(TestItem o) {
            return o.pos;
        }

        @Override
        public void set(TestItem o, int value) {
            o.pos = value;
        }
    }

    Heap<TestItem> heap;

    @Before
    public void setUP() {
        heap = new Heap<TestItem>(
                Arrays.asList(new TestItem[] {
                        new TestItem(4), new TestItem(1), new TestItem(5),
                        new TestItem(7), new TestItem(3), new TestItem(8),
                        new TestItem(6), new TestItem(2), new TestItem(0), }),
                new TestItemMutableInt());
    }

    @Test
    public void testIsMinHeap() {
        for (int i = 1; i < heap.queue.size(); ++i) {
            int iUp = (i - 1) >> 1;
            assertFalse(heap.queue.get(i).compareTo(heap.queue.get(iUp)) < 0);
        }
    }

    @Test
    public void testIsInPosition() {
        for (int i = 1; i < heap.queue.size(); ++i) {
            assertTrue(heap.queue.get(i).pos == i);
        }
    }

    @Test
    public void testPoll() {
        assertTrue(heap.poll().val == 0);
        assertTrue(heap.poll().val == 1);
        assertTrue(heap.poll().val == 2);
        assertTrue(heap.poll().val == 3);
        assertTrue(heap.poll().val == 4);
        assertTrue(heap.poll().val == 5);
        assertTrue(heap.poll().val == 6);
        assertTrue(heap.poll().val == 7);
        assertTrue(heap.poll().val == 8);
    }

    @Test
    public void testOffer() {
        heap = new Heap<TestItem>(new TestItemMutableInt());
        heap.offer(new TestItem(4));
        heap.offer(new TestItem(1));
        heap.offer(new TestItem(5));
        heap.offer(new TestItem(7));
        heap.offer(new TestItem(3));
        heap.offer(new TestItem(8));
        heap.offer(new TestItem(6));
        heap.offer(new TestItem(2));
        heap.offer(new TestItem(0));
        testIsMinHeap();
        testIsInPosition();
    }

    @Test
    public void testRemove() {
        ArrayList<TestItem> elements = new ArrayList<TestItem>(heap);
        Collections.shuffle(elements);
        for(TestItem elem : elements) {
            heap.removeElem(elem);
            assertFalse(heap.contains(elem));
            testIsMinHeap();
            testIsInPosition();
        }
    }

}
