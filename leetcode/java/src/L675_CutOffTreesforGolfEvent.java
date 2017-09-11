import java.util.ArrayDeque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class L675_CutOffTreesforGolfEvent {

    public static class WeightedTuple implements Comparable<WeightedTuple> {
        public int x;
        public int y;
        public int weight;

        public WeightedTuple(int x, int y, int weight) {
            this.x = x;
            this.y = y;
            this.weight = weight;
        }

        @Override
        public int compareTo(WeightedTuple o) {
            if (this.weight > o.weight)
                return 1;
            if (this.weight == o.weight)
                return 0;
            return -1;
        }
    }

    public int cutOffTree(List<List<Integer>> forest) {
        boolean[][] been = new boolean[forest.size()][forest.get(0).size()];
        Integer count = 0;
        for (List<Integer> l : forest)
            for (Integer i : l)
                if (i > 0)
                    count++;

        int maxx = forest.size();
        int maxy = forest.get(0).size();
        int steps = 0;

        PriorityQueue<WeightedTuple> tree = new PriorityQueue<>();
        Queue<WeightedTuple> traverse = new ArrayDeque<>();

        int height = 0;
        if (forest.get(0).get(0) > height) {
            tree.offer(new WeightedTuple(0, 0, forest.get(0).get(0)));
            traverse.offer(new WeightedTuple(0, 0, forest.get(0).get(0)));
        }

        WeightedTuple cur, next;

        int prex = 0;
        int prey = 0;

        while (traverse.peek() != null) {
            cur = traverse.poll();
            count--;
            been[cur.x][cur.y] = true;
//            height = cur.weight;
//            steps += Math.abs(cur.x - prex + cur.y - prey);
            prex = cur.x;
            prey = cur.y;

            if (cur.x + 1 < maxx && !been[cur.x + 1][cur.y]) {
                next = new WeightedTuple(cur.x + 1, cur.y, forest.get(cur.x + 1).get(cur.y));
                if (forest.get(cur.x + 1).get(cur.y) > 0)
                    tree.offer(next);
                traverse.offer(next);
            }
            if (cur.x - 1 < maxx && !been[cur.x - 1][cur.y]) {
                next = new WeightedTuple(cur.x - 1, cur.y, forest.get(cur.x - 1).get(cur.y));
                if (forest.get(cur.x - 1).get(cur.y) > 0)
                    tree.offer(next);
                traverse.offer(next);
            }
            if (cur.y + 1 < maxy && !been[cur.x][cur.y + 1]) {
                next = new WeightedTuple(cur.x, cur.y + 1, forest.get(cur.x).get(cur.y + 1));
                if (forest.get(cur.x).get(cur.y + 1) > 0)
                    tree.offer(next);
                traverse.offer(next);
            }
            if (cur.y - 1 < maxy && !been[cur.x][cur.y - 1]) {
                next = new WeightedTuple(cur.x, cur.y - 1, forest.get(cur.x).get(cur.y - 1));
                if (forest.get(cur.x).get(cur.y - 1) > 0)
                    tree.offer(next);
                traverse.offer(next);
            }
//            if (cur.x + 1 < maxx && !been[cur.x+1][cur.y] && forest.get(cur.x+1).get(cur.y) > 0) {
//                next.offer(new WeightedTuple(cur.x + 1, cur.y, forest.get(cur.x + 1).get(cur.y)));
//                traverse.offer(new WeightedTuple(cur.x + 1, cur.y, forest.get(cur.x + 1).get(cur.y)));
//            }
//            if (cur.x - 1 >= 0 && !been[cur.x-1][cur.y] && forest.get(cur.x-1).get(cur.y) > 0) {
//                next.add(new WeightedTuple(cur.x - 1, cur.y, forest.get(cur.x - 1).get(cur.y)));
//            }
//            if (cur.y + 1 < maxy && !been[cur.x][cur.y+1] && forest.get(cur.x).get(cur.y+1) > 0) {
//                next.add(new WeightedTuple(cur.x, cur.y + 1, forest.get(cur.x).get(cur.y + 1)));
//            }
//            if (cur.y - 1 >= 0 && !been[cur.x][cur.y-1] && forest.get(cur.x).get(cur.y-1) > 0) {
//                next.add(new WeightedTuple(cur.x, cur.y - 1, forest.get(cur.x).get(cur.y - 1)));
//            }
        }

        if (count == 0)
            return steps;

        return -1;
    }
}
