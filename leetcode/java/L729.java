/*
 * Copyright (c) 2017.  Hanlin He
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Map;
import java.util.TreeMap;

/**
 * <h1>729. My Calendar I</h1>
 * <p>
 * Implement a {@code MyCalendar} class to store your events. A new event can be added if adding the
 * event will not cause a double booking.
 * <p>
 * Your class will have the method, {@code book(int start, int end)}. Formally, this represents a
 * booking on the half open interval {@code [start, end)}, the range of real numbers {@code x} such
 * that {@code start <= x < end}.
 * <p>
 * A double booking happens when two events have some non-empty intersection (ie., there is some
 * time that is common to both events.)
 * <p>
 * For each call to the method {@code MyCalendar.book}, return {@code true} if the event can be
 * added to the calendar successfully without causing a double booking. Otherwise, return {@code
 * false} and do not add the event to the calendar.
 * <p>
 * Your class will be called like this: {@code MyCalendar cal = new MyCalendar();
 * MyCalendar.book(start, end)}.
 * <p>
 * Example 1:
 * <pre>
 * MyCalendar();
 * MyCalendar.book(10, 20); // returns true
 * MyCalendar.book(15, 25); // returns false
 * MyCalendar.book(20, 30); // returns true
 * Explanation:
 * The first event can be booked.  The second can't because time 15 is already booked by another
 * event.
 * The third event can be booked, as the first event takes every time less than 20, but not
 * including 20.
 * </pre>
 * <p>
 * Note:
 * <p>
 * The number of calls to {@code MyCalendar.book} per test case will be at most 1000. In calls to
 * {@code MyCalendar.book(start, end)}, start and end are integers in the range [0, 10^9].
 */
public class L729 {
    @Test
    public void book() throws Exception {
        MyCalendar myCalendar = new MyCalendar();

        assertTrue(myCalendar.book(97, 100));
        assertTrue(myCalendar.book(33, 51));
        assertFalse(myCalendar.book(89, 100));
        assertFalse(myCalendar.book(83, 100));
        assertTrue(myCalendar.book(75, 92));
    }

    public static class MyCalendar {

        Map<Integer, Integer> c;

        public MyCalendar() {
            this.c = new TreeMap<>();
        }

        public MyCalendar(MyCalendar o) {
            this.c = new TreeMap<>(o.c);
        }

        public boolean book(int start, int end) {
            for (Map.Entry<Integer, Integer> e : this.c.entrySet())
                if ((start < e.getValue() && start >= e.getKey()) ||
                        (end <= e.getValue() && end > e.getKey()) ||
                        (start <= e.getKey() && end >= e.getValue()))
                    return false;

            this.c.put(start, end);
            return true;
        }
    }
}
