LeetCode
========

At first, I tried to solve the problems in C. The debugging and testing process
was painful for me, especially in those problem which I have to define and
allocate many different structures or `char *`, simply to perform a simple unit
test.

In the summer of 2017, I wrote my first project in Python. It's really
enjoyable to program in such a _really_ high level language. No low level
detail to worry about. So I started solve problems in Python. However, I soon
realized, apart from the famous design philosophy __[Zen of Python
(PEP20)](https://www.python.org/dev/peps/pep-0020/)__, the _Pythonic_ way also
comes with some trade-off. For example, to preserve stack traces, the author of
Python ruled out the Tail Call Optimization (TCO)
[[1]](https://neopythonic.blogspot.com.au/2009/04/tail-recursion-elimination.html).
Also, Python only allows 1000 recursions by default
[[2]](https://neopythonic.blogspot.com.au/2009/04/final-words-on-tail-calls.html).
Although the reason for that is well stated by the author, I still believe that
Python might not be a best way to solve Leetcode problems.

Now I turned to Java. The one language that everyone use for every purpose.
Though the progress is slow, but finally there is some progress ;)

Fun coding!
