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

import java.util.*;

public class L731 {
    class MyCalendarTwo {

        List<Tuple> c;
        Set<Integer> d;
        MyCalendar my;

        public MyCalendarTwo() {
            this.c = new LinkedList<>();
            this.d = new TreeSet<>();
            this.my = new MyCalendar();
        }

        public boolean book(int start, int end) {
            MyCalendar tmp = new MyCalendar(my);

            for (Tuple e : this.c) {
                if (end <= e.end && start >= e.start) {
                    if (!my.book(start, end))
                        return false;
                }

                if (start < e.end && start >= e.start) {
                    if (!my.book(start, e.end))
                        return false;
                }

                if (end <= e.end && end > e.start) {
                    if (!my.book(e.start, end))
                        return false;
                }

                if (start < e.start && end >= e.end) {
                    if (!my.book(e.start, e.end))
                        return false;
                }
            }

            this.my = tmp;
            this.c.add(new Tuple(start, end));

            return true;
        }

        class MyCalendar {

            Map<Integer, Integer> c;

            public MyCalendar(MyCalendar o) {
                this.c = new TreeMap<>(o.c);
            }

            public MyCalendar() {
                this.c = new TreeMap<>();
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

        private class Tuple {
            int start, end;

            public Tuple(int start, int end) {
                this.start = start;
                this.end = end;
            }
        }
    }
}
