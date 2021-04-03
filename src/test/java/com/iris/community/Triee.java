//package com.iris.community;
//
//
//
//import java.util.*;
//
//public class Triee {
//
//
//
//        private Map<Character,Triee> map = new HashMap<>();
//        private Triee root;
//        public void addSuNode(Character c, Triee e){
//            map.put(c,e);
//        }
//        public Triee getSubNode(Character c){
//            return map.get(c);
//        }
//
//
//        public void addWord(String s){
//            for(int i = 0; i < s.length(); i++){
//                char c = s.charAt(i);
//                Triee node = root.getSubNode(c);
//                if(node == null){
//                    node = new Triee();
//                    root.addSuNode(c,node);
//                }
//                root = node;
//            }
//        }
//        public int match(String s,){
//
//        }
//        public int step(String s, Triee t){
//            if(t.getSubNode(s.charAt(0)) == null)
//                return s.length();
//
//            if(t.getSubNode(s.charAt(0)) == null)
//                return s.length();
//        }
//
//
//        public static void main(String[] args) {
//            Scanner scanner = new Scanner(System.in);
//            int n = scanner.nextInt();
//            int m = scanner.nextInt();
//            String[] ss1 = new String[n];
//            String[] ss2 = new String[m];
//
//            Triee t = new Triee();
//            List<Integer> res = new ArrayList<>();
//            for(int i = 0; i < n; i++){
//                ss1[i] = scanner.next();
//                t.addWord(ss1[i]);
//            }
//
//            for(int i = 0; i < m; i++){
//                ss2[i] = scanner.next();
//                res.add(t.step(ss2[i],t));
//            }
//
//        }
//
//
//}
