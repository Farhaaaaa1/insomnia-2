package com.company;

import com.sun.org.apache.regexp.internal.RE;

import javax.print.DocFlavor;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class hello {
    static RE mailValid;
    static String[] legalArgs = {"-M", "--method", "-H", "--headers", "-i", "-h", "--help", "-O", "--output", "-S", "--save", "-d", "--data", "list", "create", "fire"};
    static List<String> legalList = Arrays.asList(legalArgs);
    static String[] methods = {"GET", "HEAD", "POST", "PUT", "DELETE", "CONNECT", "OPTIONS", "TRACE", "PATCH"};
    static List<String> methodsList = Arrays.asList(methods);

    public static void main(String[] args) {

        String str = "-i -o http://google.com";
        duplicated("hello farhan my name is farhan --method  POST  --method ");
        method("hello farhan my name is farhan --method  POST  --method ");
        str = str.replaceAll("\\s{2,}", " ").trim();
        String[] mystr = str.split(" ");
        List<String> url1 = new ArrayList<String>();
        for (String a :
                mystr) {
            System.out.println(a);
        }
       // url1 = extractUrls("hello farhan my name is farhan --method  POST  --method http://www.google.com/gdrg ");
        System.out.println();
        System.out.println();
        System.out.println(url1.get(0));
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

    public String getWord(String string) {
        string = string.replaceAll("\\s{2,}", " ").trim();
        return string;
    }

    public Boolean isCorrects(String mail) {
        return true;
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
