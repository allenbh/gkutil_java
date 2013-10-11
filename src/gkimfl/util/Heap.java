package gkimfl.util;

import static java.lang.Math.log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Single ended priority queue implemented as a heap.
 * 
 * The Heap priority queue provides efficient O(1) time access to the first
 * element in the queue. Elements can be inserted and removed from the queue in
 * log(N) time. Many elements can be inserted at once with time bounded by O(N).
 * Arbitrary elements may be removed if the position in the queue is tracked for
 * each element. The first element can always be removed.
 * 
 * @author Allen Hubbe
 * 
 * @param <E>
 *            - the type of elements held in this collection
 */
public class Heap<E> extends AbstractQueue<E> {
    final Comparator<E> cmp;
    final MutableInt<E> pos;
    ArrayList<E> queue;

    public Heap() {
        cmp = new NaturalComparator<E>();
        pos = new MutableInt<E>();
        queue = new ArrayList<E>();
    }

    public Heap(Comparator<E> comparator) {
        cmp = comparator;
        pos = new MutableInt<E>();
        queue = new ArrayList<E>();
    }

    public Heap(Comparator<E> comparator, MutableInt<E> position) {
        cmp = comparator;
        pos = position;
        queue = new ArrayList<E>();
    }

    public Heap(MutableInt<E> position) {
        cmp = new NaturalComparator<E>();
        pos = position;
        queue = new ArrayList<E>();
    }

    public Heap(Collection<E> collection) {
        cmp = new NaturalComparator<E>();
        pos = new MutableInt<E>();
        queue = new ArrayList<E>(collection);
        heapify();
    }

    public Heap(Collection<E> collection, Comparator<E> comparator) {
        cmp = comparator;
        pos = new MutableInt<E>();
        queue = new ArrayList<E>(collection);
        heapify();
    }

    public Heap(Collection<E> collection, Comparator<E> comparator, MutableInt<E> position) {
        cmp = comparator;
        pos = position;
        queue = new ArrayList<E>(collection);
        heapify();
    }

    public Heap(Collection<E> collection, MutableInt<E> position) {
        cmp = new NaturalComparator<E>();
        pos = position;
        queue = new ArrayList<E>(collection);
        heapify();
    }

    public Heap(int initialCapacity) {
        cmp = new NaturalComparator<E>();
        pos = new MutableInt<E>();
        queue = new ArrayList<E>(initialCapacity);
    }

    public Heap(int initialCapacity, Comparator<E> comparator) {
        cmp = comparator;
        pos = new MutableInt<E>();
        queue = new ArrayList<E>(initialCapacity);
    }

    public Heap(int initialCapacity, Comparator<E> comparator, MutableInt<E> position) {
        cmp = comparator;
        pos = position;
        queue = new ArrayList<E>(initialCapacity);
    }

    public Heap(int initialCapacity, MutableInt<E> position) {
        cmp = new NaturalComparator<E>();
        pos = position;
        queue = new ArrayList<E>(initialCapacity);
    }

    @Override
    public void clear() {
        queue.clear();
    }

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

    @Override
    public boolean removeElem(E e) {
        int i = pos.get(e);
        int iBound = queue.size() - 1;
        if (iBound == 0) {
            queue.remove(0);
        }
        else if (i == iBound) {
            queue.remove(iBound);
        }
        else {
            queue.set(i, queue.remove(iBound));
            pos.set(queue.get(i), i);
            pushDown(pullUp(i));
        }
        return true;
    }

    @Override
    public boolean offer(E e) {
        int i = queue.size();
        queue.add(e);
        pullUp(i);
        return true;
    }

    @Override
    public E peek() {
        return queue.get(0);
    }

    @Override
    public E poll() {
        E elem = queue.get(0);
        int i = queue.size() - 1;
        if (i == 0) {
            queue.remove(0);
        }
        else {
            queue.set(0, queue.remove(i));
            pos.set(queue.get(0), 0);
            pushDown(0);
        }
        return elem;
    }

    @Override
    public Iterator<E> iterator() {
        return queue.iterator();
    }

    @Override
    public int size() {
        return queue.size();
    }

    private void heapify() {
        int i = queue.size() - 1;
        for (; 0 <= i; --i) {
            pushDown(i);
        }
    }

    private boolean less(E vA, E vB) {
        return cmp.compare(vA, vB) < 0;
    }

    private int pullUp(int i) {
        E v = queue.get(i);
        while (0 < i) {
            int iUp = (i - 1) >> 1;
            E vUp = queue.get(iUp);
            if (!less(v, vUp)) {
                break;
            }
            queue.set(i, vUp);
            pos.set(vUp, i);
            i = iUp;
        }
        queue.set(i, v);
        pos.set(v, i);
        return i;
    }

    private int pushDown(int i) {
        E v = queue.get(i);
        int iBound = queue.size();
        while (true) {
            int iDown = (i << 1) + 1;
            if (iBound <= iDown) {
                break;
            }
            E vDown = queue.get(iDown);
            int iRight = iDown + 1;
            if (iRight < iBound) {
                E vRight = queue.get(iRight);
                if (less(vRight, vDown)) {
                    vDown = vRight;
                    iDown = iRight;
                }
            }
            if (!less(vDown, v)) {
                break;
            }
            queue.set(i, vDown);
            pos.set(vDown, i);
            i = iDown;
        }
        queue.set(i, v);
        pos.set(v, i);
        return i;
    }
}
