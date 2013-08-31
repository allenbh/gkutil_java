package gkimfl.util;

import static java.lang.Math.log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

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
        } else {
            return super.addAll(c);
        }
    }

    @Override
    public boolean removeElem(E e) {
        int i = pos.get(e);
        int iBound = queue.size() - 1;
        if (iBound == 0) {
            queue.remove(0);
        } else if (i == iBound) {
            queue.remove(iBound);
        } else {
            queue.set(i, queue.remove(iBound));
            pos.set(queue.get(i), i);
            pushDown(pullUp(i));
        }
        return true;
    }

    @Override
    public boolean offer(E e) {
        int i = queue.size();
        pos.set(e, i);
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
        } else {
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
            pos.set(queue.get(i), i);
            pushDown(i);
        }
    }

    private boolean less(int iMax, int iMin) {
        return cmp.compare(queue.get(iMax), queue.get(iMin)) < 0;
    }

    private boolean lessSwap(int iMax, int iMin) {
        if (less(iMax, iMin)) {
            Collections.swap(queue, iMax, iMin);
            pos.set(queue.get(iMax), iMax);
            pos.set(queue.get(iMin), iMin);
            return true;
        }
        return false;
    }

    private int pullUp(int i) {
        while (0 < i) {
            int iUp = (i - 1) >> 1;
            if (!lessSwap(i, iUp)) {
                break;
            }
            i = iUp;
        }
        return i;
    }

    private int pushDown(int i) {
        int iBound = queue.size();
        while (true) {
            int iDown = (i << 1) + 1;
            if (iBound <= iDown) {
                break;
            }
            int iRight = iDown + 1;
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
