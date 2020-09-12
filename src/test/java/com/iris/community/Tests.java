package com.iris.community;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tests {

    @Test
    //平均值 距离函数
    public int[] svgNearest3(){
        int[] nums = {33,44,61,2,36,42,56,81,11,17};
        int avg = 0;
        for(int num : nums){
            avg += num;
        }
        avg /= nums.length;
        System.out.println(avg);
        int[] d = new int[nums.length];
        Map<Integer,Integer> res = new HashMap() ;

        for(int i = 0; i < nums.length; i++){
            d[i] = Math.abs(avg - nums[i]);
            System.out.print(d[i]+"  ");
        }
        int count = 3, index = 0;

        int min = Integer.MAX_VALUE;
        while(count > 0){
            for(int i = 0; i < nums.length; i ++){
                if(d[i] < min && !res.containsKey(i)){
                    min = d[i];
                    index = i;
                }
            }
            res.put(index,nums[index]);
            min = Integer.MAX_VALUE;
            count --;
        }
        int[] ans = new int[3];
        int i = 0;
        for(Integer key : res.keySet()){
            ans[i++] = res.get(key);
        }

        return ans;
    }
    @Test
    //字符串加减
    public int stringSubAdd(){
        String s = "   100+200-1+2-3  ";
        s = s.trim();
        String sb = "";
        char[] str = s.toCharArray();

        List<Character> symbol = new ArrayList<>();
        List<Integer> number = new ArrayList<>();
        for(int i = 0; i < str.length; i++){
            if(str[i] == '+' || str[i] == '-'){
                symbol.add(str[i]);
                number.add(Integer.parseInt(sb));
                sb = "";

            }else if(Character.isDigit(str[i])){
                sb += str[i];
            }else{
                continue;
            }
            if(i == str.length - 1){
                number.add(Integer.parseInt(sb));
            }
        }
        int res = number.get(0);
        for(int i = 0; i < symbol.size(); i++){
            if(symbol.get(i) == '+')
                res += number.get(i + 1);
            else
                res -= number.get(i + 1);
        }
        System.out.println(res);
        return res;
    }
    public static void main(String[] args) {


    }
}
