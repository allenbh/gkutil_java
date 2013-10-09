package gkimfl.util;

import static java.lang.Math.log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Double ended priority queue implemented as an interval heap.
 * 
 * This collection provides efficient access to the minimum and maximum elements
 * that it contains. The minimum and maximum elements can be queried in constant
 * O(1) time, and removed in O(log(N)) time. Elements can be added in O(log(N))
 * time.
 * 
 * A textbook implementation will describe an array of intervals, where each
 * interval has a large and small value. This implementation takes care to avoid
 * allocating many small objects to record the intervals. Instead, this
 * implementation maintains a min heap in the even array indices, and a max heap
 * in the odd array indices. Intervals can be reconstructed from the even odd
 * pairs.
 * 
 * @author Allen Hubbe
 * 
 * @param <E>
 *            - the type of elements held in this collection
 */
public class IntervalHeap<E> extends AbstractDequeue<E> {

    Comparator<E> cmp;

    final ArrayList<E> queue;

    public IntervalHeap() {
        cmp = new NaturalComparator<E>();
        queue = new ArrayList<E>();
    }

    public IntervalHeap(Comparator<E> comparator) {
        cmp = comparator;
        queue = new ArrayList<E>();
    }

    public IntervalHeap(Collection<? extends E> c) {
        cmp = new NaturalComparator<E>();
        queue = new ArrayList<E>(c);
        heapify();
    }

    public IntervalHeap(Collection<? extends E> c, Comparator<E> comparator) {
        cmp = comparator;
        queue = new ArrayList<E>(c);
        heapify();
    }

    public IntervalHeap(int initialCapacity) {
        cmp = new NaturalComparator<E>();
        queue = new ArrayList<E>(initialCapacity);
    }

    public IntervalHeap(int initialCapacity, Comparator<E> comparator) {
        cmp = comparator;
        queue = new ArrayList<E>(initialCapacity);
    }

    /**
     * Remove all elements from the heap.
     */
    @Override
    public void clear() {
        queue.clear();
    }

    /**
     * Return true if the heap is empty.
     */
    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Return an iterator for the elements. This iterator does not yield
     * elements in sorted order.
     */
    @Override
    public Iterator<E> iterator() {
        return queue.iterator();
    }

    /**
     * Insert several elements into the heap. If the number of elements to be
     * added is large, this may call heapify for efficiency instead of adding
     * the elements one at a time.
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        int cSize = c.size();
        int nSize = cSize + queue.size();
        if (nSize <= cSize * log(nSize) / log(2)) {
            queue.addAll(c);
            heapify();
            return true;
        }
        else {
            return super.addAll(c);
        }
    }

    /**
     * Insert an element into the heap.
     */
    @Override
    public boolean offer(E e) {
        queue.add(e);
        int iBound = queue.size();
        int i = iBound - 1;
        if ((i & 1) == 0) {
            pullUpMax(i);
            pullUpMin(i);
        }
        else {
            if (lessSwap(i, i - 1)) {
                pullUpMin(i - 1);
            }
            pullUpMax(i);
            if (lessSwap(i, i - 1)) {
                pullUpMin(i - 1);
                pullUpMax(i);
            }
        }
        return true;
    }

    /**
     * Return the minimum element.
     */
    @Override
    public E peekFirst() {
        return queue.get(0);
    }

    /**
     * Return the maximum element.
     */
    @Override
    public E peekLast() {
        if (queue.size() < 2) {
            return queue.get(0);
        }
        return queue.get(1);
    }

    /**
     * Return and remove the minimum element.
     */
    @Override
    public E pollFirst() {
        int iBound = queue.size() - 1;
        if (iBound < 1) {
            return queue.remove(0);
        }
        else {
            E e = queue.get(0);
            if (iBound > 0) {
                queue.set(0, queue.remove(iBound));
                int i = pushDownMin(0);
                if (i + 1 < iBound && lessSwap(i + 1, i)) {
                    // i is a leaf of the min heap
                    assert ((i << 1) + 2 > iBound);
                    pullUpMax(i + 1);
                }
            }
            return e;
        }
    }

    /**
     * Return and remove the maximum element.
     */
    @Override
    public E pollLast() {
        int iBound = queue.size() - 1;
        if (iBound < 1) {
            return queue.remove(0);
        }
        else {
            E e = queue.get(1);
            if (iBound < 2) {
                queue.remove(1);
            }
            else {
                queue.set(1, queue.remove(iBound));
                int i = pushDownMax(1);
                if (lessSwap(i, i - 1)) {
                    // i is a leaf of the max heap
                    assert ((i << 1) + 1 > iBound);
                    pullUpMin(i - 1);
                }
            }
            return e;
        }
    }

    /**
     * Removing arbitrary elements is not supported.
     */
    @Override
    public boolean removeElem(E e) {
        throw new UnsupportedOperationException();
    }

    /**
     * Return the number of elements in the heap.
     */
    @Override
    public int size() {
        return queue.size();
    }

    private void heapify() {
        int iBound = queue.size();
        int i = (iBound - 1) & ~1;
        for (; 0 <= i; i -= 2) {
            if (i + 1 < iBound) {
                lessSwap(i + 1, i);
                pushDownMax(i + 1);
            }
            else if (0 < i - 1) {
                lessSwap(i - 1, i);
            }
            pushDownMin(i);
        }
    }

    private boolean less(int iMax, int iMin) {
        return cmp.compare(queue.get(iMax), queue.get(iMin)) < 0;
    }

    private boolean lessSwap(int iMax, int iMin) {
        if (less(iMax, iMin)) {
            Collections.swap(queue, iMax, iMin);
            return true;
        }
        return false;
    }

    private int pullUpMax(int i) {
        while (1 < i) {
            int iUp = ((i >> 1) - 1) | 1;
            if (!lessSwap(iUp, i)) {
                break;
            }
            i = iUp;
        }
        return i;
    }

    private int pullUpMin(int i) {
        while (0 < i) {
            int iUp = ((i >> 1) - 1) & ~1;
            if (!lessSwap(i, iUp)) {
                break;
            }
            i = iUp;
        }
        return i;
    }

    private int pushDownMax(int i) {
        int iBound = queue.size();
        while (true) {
            int iDown = (i << 1) + 1;
            if (iBound <= iDown) {
                // Element being pushed is former max of interval at iBound.
                // As max of interval, guaranteed to be no less than iBound-1.
                assert (iDown != iBound || !less(i, iBound - 1));
                break;
            }
            int iRight = iDown + 2;
            if (iRight < iBound && less(iDown, iRight)) {
                iDown = iRight;
            }
            if (!lessSwap(i, iDown)) {
                break;
            }
            i = iDown;
        }
        return i;
    }

    private int pushDownMin(int i) {
        int iBound = queue.size();
        while (true) {
            int iDown = (i << 1) + 2;
            if (iBound <= iDown) {
                break;
            }
            int iRight = iDown + 2;
            if (iRight < iBound && less(iRight, iDown)) {
                iDown = iRight;
            }
            if (!lessSwap(iDown, i)) {
                break;
            }
            i = iDown;
        }
        return i;
    }
}
