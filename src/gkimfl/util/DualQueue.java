package gkimfl.util;

import java.util.Iterator;

/**
 * Double ended queue based on a pair of single-ended queues.
 * 
 * If the component queues are priority queues with opposite ordering, The
 * DualQueue provides a double ended priority queue implementation. The
 * component queues may even be ordered independently for other applications.
 * 
 * @author Allen Hubbe
 * 
 * @param <E>
 *            - the type of elements held in this collection
 */
public class DualQueue<E> extends AbstractDequeue<E> {

    final AbstractQueue<E> fore;
    final AbstractQueue<E> back;

    public DualQueue(AbstractQueue<E> foreward, AbstractQueue<E> backward) {
        fore = foreward;
        back = backward;
    }

    @Override
    public boolean offer(E e) {
        fore.offer(e);
        back.offer(e);
        return true;
    }

    @Override
    public E peekFirst() {
        return fore.peek();
    }

    @Override
    public E peekLast() {
        return back.peek();
    }

    @Override
    public E pollFirst() {
        E e = fore.poll();
        back.removeElem(e);
        return e;
    }

    @Override
    public E pollLast() {
        E e = back.poll();
        fore.removeElem(e);
        return e;
    }

    @Override
    public Iterator<E> iterator() {
        return fore.iterator();
    }

    @Override
    public boolean isEmpty() {
        return fore.isEmpty();
    }

    @Override
    public int size() {
        return fore.size();
    }

    @Override
    public boolean removeElem(E e) {
        fore.removeElem(e);
        back.removeElem(e);
        return true;
    }
    
    @Override
    public void clear() {
        fore.clear();
        back.clear();
    }
}
