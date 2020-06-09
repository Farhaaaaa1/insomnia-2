package com.company;


import javax.print.DocFlavor;
import java.io.File;
import java.lang.reflect.Array;
import java.net.FileNameMap;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class hello {
    private ArrayList<String> allThing = new ArrayList<>();
    private final String[] legalArgs = {"-M", "--method", "-H", "--headers", "-i", "-h", "--help", "-O", "--output", "-S", "--save", "-d", "--data",
            "list", "create", "fire", "--json", "-j", "--upload"};
    private final List<String> legalList = Arrays.asList(legalArgs);

    private final String[] methods = {"GET", "HEAD", "POST", "PUT", "DELETE", "CONNECT", "OPTIONS", "TRACE", "PATCH"};

    private final List<String> methodsList = Arrays.asList(methods);

    public hello() {
        String url;
       // while (true) {
            Scanner input = new Scanner(System.in);
            String string = input.nextLine();
            string = amadeSazi(string);
            url = extractUrls(string);
            allThing.add(url);
            System.out.println("url : " + url);
           // if (duplicated(string))
              //  break;
            getWords(string);
        }
   // }

    public static void main(String[] args) {

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

    public String getNextWord(String string, String word) {
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


    static public String amadeSazi(String string) {
        // replace 2 or more space to 1 space
        string = string.replaceAll("\\s{2,}", " ").trim();
        string = " " + string + " ";
        //System.out.println(string);
        string = replacer(string);
        // removing spaces from first and last
        string = string.substring(1, string.length() - 1);
        System.out.println("type url first plz  ");
        return string;
    }

    public void getWords(String string) {
        String[] mystr = string.split(" ");
        Boolean key = true;
        List<String> myStrList = Arrays.asList(mystr);
        String wordsAfterArgs[] = {"", "", "", "", "", ""};
        String argsName[] = {"-j", "-O", "--upload", "-H", "-M", "--data"};
        wordsAfterArgs[0] = getNextWord(string, "-j");
        wordsAfterArgs[1] = getNextWord(string, "-O");
        wordsAfterArgs[2] = getNextWord(string, "--upload");
        wordsAfterArgs[3] = getNextWord(string, "-H");
        wordsAfterArgs[4] = getNextWord(string, "-M");
        wordsAfterArgs[5] = getNextWord(string, "-d");
        if (wordsAfterArgs[1] == null)
            wordsAfterArgs[1] = "output_[" + java.time.LocalDate.now() + "]";
        for (int i = 0; i < wordsAfterArgs.length; i++) {
            if (i == 0)
                for (int j = 0; j < wordsAfterArgs.length; j++)
                    if (!checkFormat(wordsAfterArgs[j], j)) {
                        new Error().errorList1(j);
                        key = false;
                    }
            if (key) {
                if (!myStrList.contains(argsName[1]))
                    wordsAfterArgs[1] = null;
                allThing.add(wordsAfterArgs[i]);
                System.out.print(argsName[i] + " : ");
                System.out.println(wordsAfterArgs[i]);
            }
        }
    }

    public Boolean duplicated(String string) {
        int numberOfDuplicate = 0;
        String[] myStr = string.split(" ");
        int i, k;
        System.out.println(myStr.length);
        String str = "";
        for (i = 0; i < myStr.length; ++i)
            for (k = 0; k < i; ++k)
                if (myStr[i].equals(myStr[k]) && legalList.contains(myStr[k])) {
                    if (have(str, myStr[k]))
                        str = str + myStr[k] + "/";
                    numberOfDuplicate++;
                }
        if (str.length() > 0)
            str = str.substring(0, str.length() - 1);
        if (numberOfDuplicate == 0)
            return false;
        else {
            System.out.println("you use " + str + " more than one time");
            return true;
        }
    }

    public Boolean have(String string, String myStr) {
        String[] myStringsArr = string.split("/");
        List<String> myStringList = new ArrayList<>();
        myStringList = Arrays.asList(myStringsArr);
        return !myStringList.contains(myStr);
    }

    public Boolean checkFormat(String string, int i) {
        switch (i) {
            case 0:
                //  if (Pattern.matches(, string))
                return true;
            case 1:
                if (string.equals("output_[" + java.time.LocalDate.now() + "]") || Pattern.matches("(\\w+[.]\\w+)", string))
                    return true;
            case 2:
                if (string != null) {
                    Path file = new File(string).toPath();
                    if (Files.exists(file))
                        return true;
                }
            case 3:
                if (string == null || Pattern.matches("(\\w+[:]\\w+;?)+", string))
                    return true;
            case 4:
                if (string == null || methodsList.contains(string))
                    return true;
            case 5:
                if (string == null || Pattern.matches("(\\w+[=]\\w+&?)+", string))
                    return true;
        }
        return false;
    }

    public Boolean wordAfterArgs(String string) {
        String[] myStringsArr = string.split(" ");
        String[] addAbleArgs = {"-j", "-O", "--upload", "-H", "-M", "-d"};
        List<String> myStringsList = Arrays.asList(myStringsArr);
        List<String> addAbleArgsList = Arrays.asList(addAbleArgs);
        for (int i = 1; i < myStringsArr.length; i++) {
            if (!legalList.contains(myStringsArr[i])) {
                int index = myStringsList.indexOf(myStringsArr[i]) - 1;
                if (!addAbleArgsList.contains(myStringsList.get(index)))
                    return false;
            }
        }
        return true;
    }

    public ArrayList<String> getAllThing() {
        return allThing;
    }
}
