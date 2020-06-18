package com.company;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * in this class we check the given command
 * and if they are illegal command we send error
 * and after this class we give command to another class to
 * create request .
 */
public class RequestModel implements Serializable {
    private Boolean save = false;
    private Boolean saveBody = false;
    private Boolean sendable = false;
    private ArrayList<String> allThing = new ArrayList<>();
    private ArrayList<String> ourArgs = new ArrayList<>();
    private final String[] legalArgs = {"-M", "--method", "-H", "--headers", "-i", "-h", "--help", "-O", "--output", "-S", "--save", "-d", "--data", // untill index 12
            "list", "create", "fire", "--json", "-j", "--upload","-f"};
    private final List<String> legalList = Arrays.asList(legalArgs);
    private String url;
    private final String[] methods = {"GET", "HEAD", "POST", "PUT", "DELETE", "CONNECT", "OPTIONS", "TRACE", "PATCH"};

    private final List<String> methodsList = Arrays.asList(methods);

    public RequestModel(String string) {
        string = convertToStandard(string);
        String[] firstString = string.split(" ");
        addArgs(string);
        url = extractUrls(string);
        System.out.println(url);
        String string1 = " " + string + " ";
        allThing.add(url);
        savingPar(string1);
        if (!duplicated(string) && extractNumberOfUrls(string)) {
            if (firstString[0] != null && firstString[0].equals(extractUrls(string))) {
                if (wordAfterArgs(string) && getWords(string)) {
                    System.out.println("mission complete boss");
                    sendable = true;
                }
            } else
                Error.errorList1(8);
        }
    }

    /**
     * in this method we extract the url
     *
     * @param text our command
     * @return we return the url code
     */
    public static String extractUrls(String text) {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex;
        urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);
        while (urlMatcher.find()) {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }
        if (containedUrls.size() != 0)
            return containedUrls.get(0);
        else return null;
    }

    /**
     * here we just get number of url and if the number is more than 1
     * or less than 1 (means defiantly 1 LOL (: )
     * we send an error
     *
     * @param text our command
     * @return number of url
     */
    public boolean extractNumberOfUrls(String text) {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);
        while (urlMatcher.find()) {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }
        if (containedUrls.size() == 1)
            return true;
        else {
            Error.errorList1(7);
            return false;
        }
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
     *
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
     *
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
        return string;
    }

    /**
     * here we get what we get word that we type after addable args
     *
     * @param string our command
     */
    public Boolean getWords(String string) {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy.mm.dd hh.mm.ss");
        String strDate = dateFormat.format(date);
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
        if (wordsAfterArgs[4] == null)
            wordsAfterArgs[4] = methods[0];
        for (int j = 0; j < wordsAfterArgs.length; j++)
            if (!checkFormat(wordsAfterArgs[j], argsName[j])) {
                Error.errorList1(j);
                key = false;
            }
        if (wordsAfterArgs[1] == null)
            wordsAfterArgs[1] = "output_[" + strDate + "]";
        if (key) {
            if (!myStrList.contains(argsName[1]))
                wordsAfterArgs[1] = null;
            allThing.add(wordsAfterArgs[1]); // 1 -> output
            allThing.add(wordsAfterArgs[3]); // 2 -> header
            allThing.add(wordsAfterArgs[4]); // 3 -> method
            allThing.add(addToMessageBody(wordsAfterArgs[0], wordsAfterArgs[2], wordsAfterArgs[5]));
        }
        return key;
    }

    /**
     * if we use one or more than one arg more than one time here we show
     * an error
     *
     * @param string our command
     * @return boolean to show its duplicated or not
     */
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

    /**
     * this method is absloutly unneccery
     *
     * @param string our command
     * @return if we count it or not
     */
    public Boolean have(String string, String myStr) {
        String[] myStringsArr = string.split("/");
        List<String> myStringList = new ArrayList<>();
        myStringList = Arrays.asList(myStringsArr);
        return !myStringList.contains(myStr);
    }

    /**
     * in this method we check what we type (format checking)
     *
     * @param string what we write (our command)
     * @param name   name of our argomans
     * @return return boolean to get our formats are true or not
     */
    public Boolean checkFormat(String string, String name) {
        switch (name) {
            case "-j":
                return true;
            case "-O":
                if (string == null||Pattern.matches("(\\w+[.]\\w+)", string) )
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

    /**
     * here we check our word(not args) to before them we have to have addable args
     *
     * @param string our command
     * @return bolean we have this or not
     */
    public Boolean wordAfterArgs(String string) {
        String[] myStringsArr = string.split(" ");
        String[] addAbleArgs = {"-j", "-O", "--upload", "-H", "-M", "-d"};
        List<String> myStringsList = Arrays.asList(myStringsArr);
        List<String> addAbleArgsList = Arrays.asList(addAbleArgs);
        for (int i = 1; i < myStringsArr.length; i++) {
            if (!legalList.contains(myStringsArr[i])) {
                int index = myStringsList.indexOf(myStringsArr[i]) - 1;
                if (!addAbleArgsList.contains(myStringsList.get(index))) {
                    Error.errorList1(6);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * here we create our messege body
     *
     * @param json   json mode
     * @param upload --upload mode
     * @param data   form data mode
     * @return our messege body
     */
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

    /**
     * in this method we first find our argoman and next we put it
     * into the ourArgs list to use it in other class
     * @param string
     */
    public void addArgs(String string) {
        String[] ourArgsArr = string.split(" ");
        for (String A :
                ourArgsArr) {
            if (legalList.contains(A))
                ourArgs.add(A);
        }
    }

    /**
     * here we got that we want to save or not
     *
     * @param string our command that we type it on the terminal
     */
    public void savingPar(String string) {
        if (string.contains(" " + legalArgs[9] + " "))
            save = true;
        if (string.contains(" " + legalArgs[7] + " "))
            saveBody = true;
    }

    // getter and settttters
    public String getUrl() {
        return url;
    }

    public ArrayList<String> getLegalArgs() {
        return allThing;
    }

    public Boolean getSave() {
        return save;
    }

    public Boolean getSaveBody() {
        return saveBody;
    }

    public Boolean getSendable() {
        return sendable;
    }
}

