package com.company;

import com.sun.org.apache.regexp.internal.RE;

import javax.print.DocFlavor;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class hello {
    static RE mailValid;
    static String[] legalArgs = {"-M", "--method", "-H", "--headers", "-i", "-h", "--help", "-O", "--output", "-S", "--save", "-d", "--data", "list", "create", "fire"};
    static List<String> legalList = Arrays.asList(legalArgs);
    static String[] methods = {"GET", "HEAD", "POST", "PUT", "DELETE", "CONNECT", "OPTIONS", "TRACE", "PATCH"};
    static List<String> methodsList = Arrays.asList(methods);

    public static void main(String[] args) {
        while (true) {
            Scanner input = new Scanner(System.in);
            String string = input.nextLine();
            amadeSazi(string);
        }
    }

    public static String extractUrls(String text) {
        if (extractNumberOfUrls(text) == 1) {
            List<String> containedUrls = new ArrayList<String>();
            String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
            Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
            Matcher urlMatcher = pattern.matcher(text);
            while (urlMatcher.find()) {
                containedUrls.add(text.substring(urlMatcher.start(0),
                        urlMatcher.end(0)));
            }
            return containedUrls.get(0);
        }
        return null;
    }

    public static int extractNumberOfUrls(String text) {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);
        while (urlMatcher.find()) {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }
        return containedUrls.size();
    }

    public void checkNext(String string, String word1) {
        String[] mystr = string.split(" ");
        List<String> myStrList = Arrays.asList(mystr);
        if (myStrList.contains(word1)) ;


    }

  static   public String replacer(String string) {
        string = string.replaceAll(" --headers ", " -H ");
        string = string.replaceAll(" --method ", " -M ");
        string = string.replaceAll(" --output ", " -O ");
        string = string.replaceAll(" --save ", " -S ");
        string = string.replaceAll(" --data ", " -d ");
        string = string.replaceAll(" --help ", " -h ");
        return string;
    }

    public String getWord(String string) {
        string = string.replaceAll("\\s{2,}", " ").trim();
        return string;
    }

   static public void amadeSazi(String string) {
        string = string.replaceAll("\\s{2,}", " ").trim();
        string = " "+string+" ";
       System.out.println(string);
        string = replacer(string);
        // removing sapces from first and last
        string = string.substring(1,string.length()-1);
       System.out.println(string);

    }

    public static void method(String string) {
        int n = 999;
        String[] mystr = string.split(" ");
        for (String a :
                mystr) {
            if (legalList.contains(a))
                n = Arrays.asList(mystr).indexOf(a);
        }
        System.out.println(n);
        System.out.println(n);
    }

    public static void duplicated(String string) {
        String[] mystr = string.split(" ");
        int i, k;
        for (i = 0; i < mystr.length; ++i)
            for (k = 0; k < i; ++k)
                if (mystr[i].equals(mystr[k]) && legalList.contains(mystr[k]))
                    System.out.println(mystr[k]);
    }

}
