package com.company;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
/**
 * in this code we do something about http
 * i use http client in this project  and also copy lot of method from internet cuz
 * me and also 99 percent of student don't know any thing about http
 * @author Farhan Farsi
 * @version 0.0
 */
public class Main {
    public static void main(String[] args) {
        while (true) {
            Scanner input = new Scanner(System.in);
            String string = input.nextLine();
            if (string.contains("list"))
                FileWork.showSavedReq();
            else if (string.contains("fire")) {
                try {
                    for (RequestModel model : getIndexOfFire(string))
                        new Request(model).sendRequest();
                } catch (FileNotFoundException e) {
                    System.out.println("bad format!");
                }
            } else if (string.contains("help"))
                System.out.println("helpppppp");
            else {
                RequestModel requsetModel = new RequestModel(string);
                if (requsetModel.getSave())
                    FileWork.SaveToDirectory(requsetModel);

                if(requsetModel.getSendable())
                { Request request = new Request(requsetModel);
                request.sendRequest();}
            }
        }
    }

    /**
     * here we get index of directory that we want to fire
     * @param string
     * @return
     * @throws NumberFormatException
     * @throws FileNotFoundException
     */
    public static RequestModel[] getIndexOfFire(String string) throws NumberFormatException, FileNotFoundException {
        String[] myStr = string.split(" ");
        List<RequestModel> requestModels = new ArrayList<RequestModel>();
        List myCommandAsList = Arrays.asList(myStr);
        int indexOfFire = myCommandAsList.indexOf("fire"); //legalArgs[15] = fire
        if (indexOfFire < myStr.length)
            for (int i = indexOfFire + 1; i < myStr.length; i++) {
                requestModels.add(FileWork.loadReq(Integer.parseInt(myStr[i])));
            }
        RequestModel[] requestArray = new RequestModel[requestModels.size()];
        requestModels.toArray(requestArray);
        return requestArray;
    }
}
