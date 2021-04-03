package com.iris.community;

import com.iris.community.util.CommunityUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.util.DigestUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Tests {

//    @Test
//    //平均值 距离函数
//    public int[] svgNearest3(){
//        int[] nums = {33,44,61,2,36,42,56,81,11,17};
//        int avg = 0;
//        for(int num : nums){
//            avg += num;
//        }
//        avg /= nums.length;
//        System.out.println(avg);
//        int[] d = new int[nums.length];
//        Map<Integer,Integer> res = new HashMap() ;
//
//        for(int i = 0; i < nums.length; i++){
//            d[i] = Math.abs(avg - nums[i]);
//            System.out.print(d[i]+"  ");
//        }
//        int count = 3, index = 0;
//
//        int min = Integer.MAX_VALUE;
//        while(count > 0){
//            for(int i = 0; i < nums.length; i ++){
//                if(d[i] < min && !res.containsKey(i)){
//                    min = d[i];
//                    index = i;
//                }
//            }
//            res.put(index,nums[index]);
//            min = Integer.MAX_VALUE;
//            count --;
//        }
//        int[] ans = new int[3];
//        int i = 0;
//        for(Integer key : res.keySet()){
//            ans[i++] = res.get(key);
//        }
//
//        return ans;
//    }
//    @Test
//    //字符串加减
//    public int stringSubAdd(){
//        String s = "   100+200-1+2-3  ";
//        s = s.trim();
//        String sb = "";
//        char[] str = s.toCharArray();
//
//        List<Character> symbol = new ArrayList<>();
//        List<Integer> number = new ArrayList<>();
//        for(int i = 0; i < str.length; i++){
//            if(str[i] == '+' || str[i] == '-'){
//                symbol.add(str[i]);
//                number.add(Integer.parseInt(sb));
//                sb = "";
//
//            }else if(Character.isDigit(str[i])){
//                sb += str[i];
//            }else{
//                continue;
//            }
//            if(i == str.length - 1){
//                number.add(Integer.parseInt(sb));
//            }
//        }
//        int res = number.get(0);
//        for(int i = 0; i < symbol.size(); i++){
//            if(symbol.get(i) == '+')
//                res += number.get(i + 1);
//            else
//                res -= number.get(i + 1);
//        }
//        System.out.println(res);
//        return res;
//    }
//    public static String getMinString (String str1, String str2) {
//        // write code here
//        if(!str1.equals("") && str2.equals(""))return str1;
//        Map<Character,List<Integer>> map = new HashMap<>();
//        List<Integer> res = new ArrayList<>();
//
//        char[] str = str1.toCharArray();
//        for(int i = 0; i < str.length; i++){
//            if(!map.containsKey(str[i])){
//                List<Integer> list = new ArrayList<>();
//                list.add(i);
//            }else{
//                List<Integer> list = map.get(str[i]);
//                list.add(i);
//            }
//        }
//        char[] atr = str2.toCharArray();
//        for(int i = 0; i < atr.length; i++){
//            if(!map.containsKey(atr[i]))return "";
//            else{
//                List<Integer> list = map.get(atr[i]);
//                res.add(list.get(0));
//                list.remove(0);
//                if(list.size() == 0)map.remove(atr[i]);
//                else map.put(atr[i],list);
//            }
//        }
//        Collections.sort(res);
//        return str1.substring(res.get(0),res.get(res.size()-1) + 1);
//    }
//    public static int maxsubNums(int[] nums){
//        if(nums.length == 0)return 0;
//        int max = Integer.MIN_VALUE;
//        int sum = 0;
//        for(int i = 0; i < nums.length; i++){
//            if(sum <= 0){
//                sum = nums[i];
//
//            }else{
//                sum += nums[i];
//            }
//            if(sum > max)
//                max = sum;
//        }
//        return max;
//    }
//    public static void main(String[] args) {
//
//        Scanner in = new Scanner(System.in);
//        int t = in.nextInt();
//        for(int i = 0; i < t;i++){
//            int n = in.nextInt();
//            int[] nums = new int[n];
////            for(int j = 0; j < n; j++){
////                nums[i] = in.nextInt();
////            }
//            int q = 0;
//            while(in.hasNextInt()){
//                nums[q++] = in.nextInt();
//            }
//            int max1,max2;
//            max1 = maxsubNums(nums);
//            int res = 0;
//            int[] arr = new int[n];
//            for(int k = 0; k < n; k++){
//                res += nums[k];
//                arr[k] = -nums[k];
//            }
//            max2 = maxsubNums(arr);
//            max2 = max2 + res;
//            int ans = max1 > max2 ? max1:max2;
//            System.out.println(ans);
//
//        }
//    }
    @Test
    public void md5(){
        System.out.println(CommunityUtil.md5("1234l"));

    }

}
