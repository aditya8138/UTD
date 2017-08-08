from threading import RLock

class Clock(object):
    """ A Singlton clock.
    Based on [The Singleton](https://goo.gl/T3kxkR)
    """
    __instance = None
    def __new__(cls):
        if Clock.__instance is None:
            Clock.__instance = object.__new__(cls)
            Clock.__instance.val = int()
            Clock.__instance.lock = RLock()
        return Clock.__instance

    def tick(self):
        self.lock.acquire()
        try:
            self.val += 1
        finally:
            self.lock.release()
        return self

    def reset(self):
        self.lock.acquire()
        try:
            self.val = 0
        finally:
            self.lock.release()
        return self

    @property
    def time(self):
        self.lock.acquire()
        try:
            return self.val
        finally:
            self.lock.release()

def main():
    c = Clock()
    print(c.time)
    c.tick()
    print(c.time)
    cp = Clock()
    print(cp.time)
    cp.tick()
    print(c.time, cp.time)
    print(Clock().tick().time)

if __name__ == '__main__':
    main()
