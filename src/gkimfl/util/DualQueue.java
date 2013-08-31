package gkimfl.util;

import java.util.Iterator;

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
    public int size() {
        return fore.size();
    }

    @Override
    public boolean removeElem(E e) {
        fore.removeElem(e);
        back.removeElem(e);
        return true;
    }
}
