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
    static String[] legalArgs = {"-M", "--method", "-H", "--headers", "-i", "-h", "--help", "-O", "--output", "-S", "--save", "-d", "--data",
            "list", "create", "fire", "--json", "-j", "--upload"};
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
            String urlRegex;
            urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
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

    static public String replacer(String string) {
        string = string.replaceAll(" --headers ", " -H ");
        string = string.replaceAll(" --method ", " -M ");
        string = string.replaceAll(" --output ", " -O ");
        string = string.replaceAll(" --save ", " -S ");
        string = string.replaceAll(" --data ", " -d ");
        string = string.replaceAll(" --help ", " -h ");
        string = string.replaceAll(" --json ", " -j ");
        return string;
    }

    static public String getNextWord(String string, String word) {
        String[] mystr = string.split(" ");
        int index;
        List<String> myStrList = Arrays.asList(mystr);
        if (myStrList.contains(word)) {
            index = myStrList.indexOf(word);
            if (index != myStrList.size() - 1)
                if (!legalList.contains(myStrList.get(++index)))
                    return myStrList.get(index);
        }
        return null;
    }

    static public void amadeSazi(String string) {
        String url;
        string = string.replaceAll("\\s{2,}", " ").trim();
        string = " " + string + " ";
        //System.out.println(string);
        string = replacer(string);
        // removing sapces from first and last
        string = string.substring(1, string.length() - 1);
        //System.out.println(string);
        duplicated(string);
        url = extractUrls(string);
        System.out.println("url : " + url);
        if(url!=null)
        getWords(string);
        else
            System.out.println("type url first plz  ");
        //System.out.println("new word : "+getNextWord(string,"-i"));
    }

    public static void getWords(String string) {
        String[] mystr = string.split(" ");
        List<String> myStrList = new ArrayList<>();
        String wordsAfterArgs[] = {"", "", "", "", ""};
        String argsName[] = {"json : ", "output : ", "upload : ", "headers : ", "method : "};

        wordsAfterArgs[0] = getNextWord(string, "-j");
        wordsAfterArgs[1] = getNextWord(string, "-O");
        wordsAfterArgs[2] = getNextWord(string, "--upload");
        wordsAfterArgs[3] = getNextWord(string, "-H");
        wordsAfterArgs[4] = getNextWord(string, "-M");
        for (int i = 0; i < 5; i++) {
            System.out.print(argsName[i]);
            System.out.println(wordsAfterArgs[i]);
        }
    }

    public static void duplicated(String string) {
        String[] myStr = string.split(" ");
        int i, k;
        System.out.println(myStr.length);
        String str = "";
        for (i = 0; i < myStr.length; ++i)
            for (k = 0; k < i; ++k)
                if (myStr[i].equals(myStr[k]) && legalList.contains(myStr[k])) {
                    if (have(str, myStr[k]))
                        str = str + myStr[k] + "/";
                }
        if (str.length() > 0)
            str = str.substring(0, str.length() - 1);

        System.out.println("you use " + str + " more than one time");
    }

    public static Boolean have(String string, String myStr) {
        String[] myStringsArr = string.split("/");
        List<String> myStringList = new ArrayList<>();
        myStringList = Arrays.asList(myStringsArr);
        return !myStringList.contains(myStr);
    }
    public static String nullHandling(String string , int i)
    {

    }
}
