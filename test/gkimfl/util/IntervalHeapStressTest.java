package gkimfl.util;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static gkimfl.util.IntervalHeapTest.assertIsHeap;
import static org.junit.Assert.assertEquals;

public class IntervalHeapStressTest {
    Random rand = new Random();
    IntervalHeap<Integer> heap;

    @Before
    public void setUp() throws Exception {
        heap = new IntervalHeap<Integer>();
    }

    @Test
    public final void testIntervalHeapStress() {
        int count = 1000000; // number of times to call offer, pollFirst, and pollLast
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
            assertIsHeap(heap);
        }
    }

    @Test
    public final void testIntervalHeapifyStress() {
        int count = 10000; // number of times to heapify
        int size = 0; // starting collection size
        boolean show = false; // print collections
        for (int i = 0; i < count; ++i, ++size) {
            ArrayList<Integer> values = new ArrayList<Integer>(size);
            for(int j = 0; j < size; ++j) {
                values.add(rand.nextInt(10*size));
            }
            if(show)
                System.out.println(Arrays.toString(values.toArray()));
            heap.clear();
            heap.addAll(values);
            assertIsHeap(heap);
        }
    }

    private boolean increment(int[] p, int valueBound, int[] left) {
        for (int i = p.length - 1; i >= 0; --i) {
            if (p[i] < valueBound - 1) {
                p[i]++;
                return true;
            }
            p[i] = (left == null) ? 0 : left[i];
        }
        return false;
    }

    private boolean isHeap(int[] p, boolean min) {
        for (int i = 1; i < p.length; ++i) {
            int parent = p[(i-1)/2];
            if (min && (parent > p[i])) {
                return false;
            }
            if (!min && (parent < p[i])) {
                return false;
            }
        }
        return true;
    }

    private List<int[]> generateHeaps(int valueBound, int size, int[] left) {
        // Generates heaps of 'size' elements; i-th element in [left[i], valueBound).
        // If left is null, generates a min-heap. Otherwise, a max-heap.
        // left.size is guaranteed == either size or size+1.
        // If left.size == size+1, then implicitly result[size] is considered
        // equal to left[size].
        List<int[]> heaps = new ArrayList<int[]>();
        int[] p;
        if (left == null) {
            p = new int[size];
        } else {
            p = Arrays.copyOf(left, size);
        }
        do {
            if (isHeap(p, (left == null))) {
                if (left != null && left.length == size + 1) {
                    int parent = p[(size-1)/2];
                    if (parent < left[size]) continue;
                }
                heaps.add(Arrays.copyOf(p, size));
            }
        } while(increment(p, valueBound, left));
        return heaps;
    }

    private String toString(int[] h) {
        List<Integer> a = new ArrayList<Integer>();
        for (int x : h) {
            a.add(x);
        }
        return a.toString();
    }

    @Test
    public void testPermutations() {
        // Tests that, for any combination up to X that is already an interval-heap,
        // applying the heap operations to it doesn't violate the invariant.
        // A combination is an interval-heap if it's a min-heap and a max-heap.
        // The min-heap and max-heap parts can be generated partially independently.

        // Exhaustive test takes about 10 seconds with n=10 and about 2 minutes with n=11.
        // Anyway, it's necessary to test some even numbers and some odd numbers.
        for (int i = 2; i <= 10; ++i) {
            System.out.println("--- Generating interval heaps of size: " + i + " ---");
            testPermutations(i, -1);
        }
        System.out.println("--- Sampling interval heaps of size 11 (each 100th) ---");
        testPermutations(11, 10);
        System.out.println("--- Sampling interval heaps of size 12 (each ~1000th) ---");
        testPermutations(12, 30);
    }

    private void testPermutations(int n, int sampleEachNth) {
        List<int[]> minHeaps = generateHeaps(n, n - n/2, null);
        int numHeaps = 0;
        Random r = new Random(7638481941L);
        for (int im = 0; im < minHeaps.size(); im++) {
            if (sampleEachNth != -1 && r.nextInt() % sampleEachNth != 0) {
                continue;
            }

            if (im % 1000 == 0) {
                System.out.println(im + "/" + minHeaps.size() + " minHeaps");
            }
            int[] minHeap = minHeaps.get(im);
            List<int[]> maxHeaps = generateHeaps(n, n / 2, minHeap);
            for (int[] maxHeap : maxHeaps) {
                int actualMin = Integer.MAX_VALUE;
                int actualMax = Integer.MIN_VALUE;
                for (int x : minHeap) actualMin = Math.min(actualMin, x);
                for (int x : maxHeap) actualMin = Math.min(actualMin, x);
                for (int x : minHeap) actualMax = Math.max(actualMax, x);
                for (int x : maxHeap) actualMax = Math.max(actualMax, x);

                if (sampleEachNth != -1 && r.nextInt() % sampleEachNth != 0) {
                    continue;
                }

                ++numHeaps;
                IntervalHeap<Integer> base = asRawHeap(n, minHeap, maxHeap);
                assertIsHeap(base);
                IntervalHeap<Integer> h;
                for (int i = 0; i < n; ++i) {
                    h = asRawHeap(n, minHeap, maxHeap);
                    h.offer(i);
                    assertIsHeap(h);
                }
                h = asRawHeap(n, minHeap, maxHeap);
                assertEquals(actualMin, h.pollFirst().intValue());
                assertIsHeap(h);

                h = asRawHeap(n, minHeap, maxHeap);
                assertEquals(actualMax, h.pollLast().intValue());
                assertIsHeap(h);
            }
        }
        System.out.println(numHeaps + " heaps generated.");
    }

    private IntervalHeap<Integer> asRawHeap(int n, int[] minHeap, int[] maxHeap) {
        List<Integer> heap = new ArrayList<Integer>();
        for (int j = 0; j < n; ++j) {
            heap.add((j % 2 == 0) ? minHeap[j/2] : maxHeap[j/2]);
        }
        return IntervalHeap.fromRaw(heap);
    }
}
