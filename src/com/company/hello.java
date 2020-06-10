package com.company;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * in this class we check the given command
 * and if they are illegal command we send error
 * and after this class we give command to another class to
 * create request .
 */
public class hello {
    private ArrayList<String> allThing = new ArrayList<>();
    private ArrayList<String> ourArgs = new ArrayList<>();
    private final String[] legalArgs = {"-M", "--method", "-H", "--headers", "-i", "-h", "--help", "-O", "--output", "-S", "--save", "-d", "--data",
            "list", "create", "fire", "--json", "-j", "--upload"};
    private final List<String> legalList = Arrays.asList(legalArgs);

    private final String[] methods = {"GET", "HEAD", "POST", "PUT", "DELETE", "CONNECT", "OPTIONS", "TRACE", "PATCH"};

    private final List<String> methodsList = Arrays.asList(methods);

    public hello() {
        while (true) {
            String url;
            Scanner input = new Scanner(System.in);
            String string = input.nextLine();
            string = convertToStandard(string);
            url = extractUrls(string);
            allThing.add(url);
            System.out.println("url : " + url);
            if (!duplicated(string)) {
                if (getWords(string))
                break;
            }
        }
    }

    /**
     * in this method we extract the url
     *
     * @param text our command
     * @return we return the url code
     */
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

    /**
     * here we just get number of url and if the number is more than 1
     * or less than 1 (means defiantly 1 LOL (: )
     * we send an error
     *
     * @param text our command
     * @return number of url
     */
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

    /**
     * here we replace complete args to abbreviation model of it
     * cuz after this change we can handle it easier
     *
     * @param string out command
     * @return again our command but in new form
     */
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

    /**
     * in this method we get the word wich is
     * after the our given word
     * @param string our command
     * @param word   our given word that we want to get word after this word
     * @return word after our given word
     */
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

    /**
     * here we convert our command to standard form
     * @param string our command
     * @return our command but in new format
     */
    static public String convertToStandard(String string) {
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

    /**
     * here we get what we put to the
     * @param string
     */
    public Boolean getWords(String string) {
        String[] mystr = string.split(" ");
        Boolean key = true;
        List<String> myStrList = Arrays.asList(mystr);
        String wordsAfterArgs[] = {"", "", "", "", "", ""};
        String argsName[] = {"-j", "-O", "--upload", "-H", "-M", "--data"};
        wordsAfterArgs[0] = getNextWord(string, "-j");
        wordsAfterArgs[1] = getNextWord(string, "-O"); // output 1
        wordsAfterArgs[2] = getNextWord(string, "--upload");
        wordsAfterArgs[3] = getNextWord(string, "-H"); // header 2
        wordsAfterArgs[4] = getNextWord(string, "-M"); // method 3
        wordsAfterArgs[5] = getNextWord(string, "-d");
        if (wordsAfterArgs[1] == null)
            wordsAfterArgs[1] = "output_[" + java.time.LocalDate.now() + "]";
        System.out.println("inja 1");
        for (int j = 0; j < wordsAfterArgs.length; j++)
            if (!checkFormat(wordsAfterArgs[j], argsName[j])) {
                new Error().errorList1(j);
                key = false; }
        if (key) {
            if (!myStrList.contains(argsName[1]))
                wordsAfterArgs[1] = null;
            allThing.add(wordsAfterArgs[1]);
            allThing.add(wordsAfterArgs[3]);
            System.out.println("here");
            allThing.add(wordsAfterArgs[4]);
            allThing.add(addToMessageBody(wordsAfterArgs[0], wordsAfterArgs[2], wordsAfterArgs[5])); }
        System.out.println(key);
        return key; }

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

    public Boolean checkFormat(String string, String name) {
        switch (name) {
            case "-j":
                return true;
            case "-O":
                if (string.equals("output_[" + java.time.LocalDate.now() + "]") || Pattern.matches("(\\w+[.]\\w+)", string))
                    return true;
            case "--upload":
                // -d
                if (string != null) {
                    Path file = new File(string).toPath();
                    if (Files.exists(file))
                        return true;
                }
            case "-H":
                if (string == null || Pattern.matches("(\\w+[:]\\w+;?)+", string))
                    return true;
            case "-M":
                if (string == null || methodsList.contains(string))
                    return true;
            case "--data":
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

    public String addToMessageBody(String json, String upload, String data) {
        String messegeBody = "JSON=" + json;
        if (upload != null) {
            messegeBody = "FILE=" + upload;
        }
        if (data != null) {
            messegeBody = data;
        }
        return messegeBody;
    }

    public ArrayList<String> getAllThing() {
        return allThing;
    }
    public ArrayList<String> getOurArgs() {
        return ourArgs;
    }

    public void addArgs(String string)
    {
        String[] ourArgsArr = string.split(" ");
        for (String A:
             ourArgsArr) {
            if(legalList.contains(A))
                ourArgs.add(A);
        }
    }
}
