package com.company;

public  final class Error {
    public static final void errorList1(int i) {
        switch (i) {
            case 0:
                break;
            case 1:
                System.out.println("you'r format for output it's not true");
                break;
            case 2:
                System.out.println("you'r format for --upload it's not true");
                break;
            case 3:
                System.out.println("you'r format for --header/-H it's not true");
                break;
            case 4:
                System.out.println("you'r format for --method/-M it's not true");
                break;
            case 5:
                System.out.println("you'r format for --data/-d it's not true");
                break;
            case 6:
                System.out.println("you'r format is  not true");
                break;
            case 7:
                System.out.println("you have to use url just one time");
                break;

            case 8:
                System.out.println("your first word is not URL ");
                break;
        }
    }
}
