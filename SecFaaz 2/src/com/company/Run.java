package com.company;

public class Run {
    public static void main(String[] args) {
        String pdfName = "rjehgehg:dfkvjonb;grb";
        if(pdfName.contains(":"))
            System.out.println("yes");
        String[]tokens = pdfName.split(":|;");
        for (String A:
             tokens) {
            System.out.println(A);
        }

    }
}
