import java.util.*;

public class L721 {
//    class Solution {
//        public List<List<String>> accountsMerge(List<List<String>> accounts) {
//            int index = 0;
//            ArrayList<String> emailList = new ArrayList<>();
//            HashMap<String, Integer> indexMap = new HashMap<>();
//
//            for (List<String> account : accounts) {
//                List<String> emails = account.subList(1, account.size());
//                for (String email : emails) {
//                    emailList.add(email);
//                    indexMap.put(email, index);
//                    index++;
//                }
//            }
//
//            int[] uf = new int[emailList.size()];
//            for (int i = 0; i < uf.length; i++)
//                uf[i] = i;
//
//            for (List<String> account : accounts) {
//                Iterator<String> ita = account.iterator();
//                String name = ita.next();
//
//                int findex = indexMap.get(ita.next());
//
//                while (ita.hasNext()) {
//                    union(uf, findex, indexMap.get(ita.next()));
//                }
//            }
//
//
//        }
//
//        void union(int[] uf, int a, int b) {
//            uf[find(uf, b)] = find(uf, a);
//        }
//
//        int find(int[] uf, int i) {
//            while (uf[i] != i)
//                i = uf[i];
//            return i;
//        }
//    }
}
