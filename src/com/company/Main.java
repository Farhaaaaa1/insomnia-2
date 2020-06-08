package com.company;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println(            "output_["+java.time.LocalDate.now()+"]"
);
            FileOutputStream fout = new FileOutputStream("D:\\testout.txt");
            FileInputStream fin = new FileInputStream("1.png");
            FileInputStream k = new FileInputStream("D:\\testout.txt");
            FileReader fileReader = new FileReader("D:\\testout.txt");


            FileWriter fileWriter = new FileWriter("D:\\testout.txt",true);
            FileWriter fileWriter1 = new FileWriter("D:\\testout.txt",true);
            fileWriter.write("jchsdijchjksdbc");
            fileWriter1.write("111111");
            fileWriter.close();
            fileWriter1.close();
            int i ;
            while ((i = k.read())!=-1) {
                System.out.print((char) i);
                //System.out.println("hello");
            }
            System.out.println();
            System.out.println("success...");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
