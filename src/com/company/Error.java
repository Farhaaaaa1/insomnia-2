package com.company;

public final class Error {
    public  void errorList1(int i) {
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
        }
    }
}
