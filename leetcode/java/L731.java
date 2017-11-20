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

import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Implement a MyCalendarTwo class to store your events. A new event can be added if adding the
 * event will not cause a triple booking.
 * <p>
 * Your class will have one method, book(int start, int end). Formally, this represents a booking on
 * the half open interval [start, end), the range of real numbers x such that start <= x < end.
 * <p>
 * A triple booking happens when three events have some non-empty intersection (ie., there is some
 * time that is common to all 3 events.)
 * <p>
 * For each call to the method MyCalendar.book, return true if the event can be added to the
 * calendar successfully without causing a triple booking. Otherwise, return false and do not add
 * the event to the calendar.
 * <p>
 * Your class will be called like this: MyCalendar cal = new MyCalendar(); MyCalendar.book(start,
 * end)
 * <p>
 * Example 1:
 * <pre>
 * MyCalendar();
 * MyCalendar.book(10, 20); // returns true
 * MyCalendar.book(50, 60); // returns true
 * MyCalendar.book(10, 40); // returns true
 * MyCalendar.book(5, 15); // returns false
 * MyCalendar.book(5, 10); // returns true
 * MyCalendar.book(25, 55); // returns true
 * Explanation:
 * The first two events can be booked.  The third event can be double booked.
 * The fourth event (5, 15) can't be booked, because it would result in a triple booking.
 * The fifth event (5, 10) can be booked, as it does not use time 10 which is already double
 * booked.
 * The sixth event (25, 55) can be booked, as the time in [25, 40) will be double booked with the
 * third event;
 * the time [40, 50) will be single booked, and the time [50, 55) will be double booked with the
 * second event.
 * </pre>
 * <p>
 * Note:
 * <p>
 * The number of calls to MyCalendar.book per test case will be at most 1000. In calls to
 * MyCalendar.book(start, end), start and end are integers in the range [0, 10^9].
 */
public class L731 {
    @Test
    public void book() throws Exception {
        MyCalendarTwo myCalendar = new MyCalendarTwo();

        assertTrue(myCalendar.book(10, 20));
        assertTrue(myCalendar.book(50, 60));
        assertTrue(myCalendar.book(10, 40));
        assertFalse(myCalendar.book(5, 15));
        assertTrue(myCalendar.book(5, 10));
        assertTrue(myCalendar.book(25, 55));
    }

    class MyCalendarTwo {

        List<Tuple> c;
        Set<Integer> d;
        L729.MyCalendar my;

        public MyCalendarTwo() {
            this.c = new LinkedList<>();
            this.d = new TreeSet<>();
            this.my = new L729.MyCalendar();
        }

        public boolean book(int start, int end) {
            L729.MyCalendar tmp = new L729.MyCalendar(my);

            for (Tuple e : this.c) {
                if (end <= e.end && start >= e.start) {
                    if (!tmp.book(start, end))
                        return false;
                    continue;
                }

                if (start < e.end && start >= e.start) {
                    if (!tmp.book(start, e.end))
                        return false;
                    continue;
                }

                if (end <= e.end && end > e.start) {
                    if (!tmp.book(e.start, end))
                        return false;
                    continue;
                }

                if (start < e.start && end > e.end) {
                    if (!tmp.book(e.start, e.end))
                        return false;
                }
            }

            this.my = tmp;
            this.c.add(new Tuple(start, end));

            return true;
        }

        private class Tuple {
            int start, end;

            public Tuple(int start, int end) {
                this.start = start;
                this.end = end;
            }
        }
    }
}
