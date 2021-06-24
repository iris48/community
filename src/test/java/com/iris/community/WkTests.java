package com.iris.community;

import java.io.IOException;

public class WkTests {
    public static void main(String[] args) {
        //不适配所有的操作系统 而且需要自动创建文件夹
        String cmd = "D:/Programming/wkhtmltopdf/bin/wkhtmltoimage --quality 75 https://www.nowcoder.com/ D:/Programming/work/data/wk-images/3.png";
        try {
            Runtime.getRuntime().exec(cmd);
            System.out.println("ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
