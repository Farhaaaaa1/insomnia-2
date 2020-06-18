package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * here we create class for using file and etc
 * may be here is memmory orgenizer center
 */
public class FileWork {
    static String SAVE_PATH = "savingFile";
    static String RES_PATH = "savingFiles";

    public FileWork() {
    }

    static {
        if (new File(SAVE_PATH).mkdirs())
            System.out.println("save dirs created");
    }

    /**
     * name of method is enough but for more
     * detail i can use space for it LOL
     * we show saved request
     */
    public static void showSavedReq() {
        int count = 0;
        File[] myList = new File(SAVE_PATH).listFiles();
        for (File eachFolder :
                myList) {
            System.out.println(++count + ")" + " " + eachFolder.getName());
        }
    }

    /**
     * here we save sth in to the directory
     *
     * @param requestModel use this to access sth from it
     */
    public static void SaveToDirectory(RequestModel requestModel) {
        FileOutputStream fileOutputStream = null;
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy.mm.dd hh.mm.ss");
        String strDate = dateFormat.format(date);
        try {
            fileOutputStream = new FileOutputStream(SAVE_PATH + File.separator +
                    requestModel.getUrl().replaceAll("[/:]", "-") + " " + strDate + ".bin");
            ObjectOutputStream object =
                    new ObjectOutputStream(fileOutputStream);
            object.writeObject(requestModel);
            fileOutputStream.close();
            object.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * here we use this method for fire arg
     *
     * @param n index
     * @return request model
     * @throws FileNotFoundException
     */
    public static RequestModel loadReq(int n) throws FileNotFoundException {
        Path path = new File(SAVE_PATH).toPath();
        File select = new File(SAVE_PATH);
        ObjectInputStream in;
        FileInputStream inputStream;
        if (Files.exists(path)) {
            try {
                File selected = select.listFiles()[n - 1];
                inputStream = new FileInputStream(selected);
                in = new ObjectInputStream(inputStream);
                return (RequestModel) in.readObject();

            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ArrayIndexOutOfBoundsException("this index is not invalid");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void saveResBody(byte[] resBody, String fileName) {
        if (new File(SAVE_PATH + "new").mkdirs())
            System.out.println("save directories created");
        try (FileOutputStream out = new FileOutputStream(SAVE_PATH + "new"+File.separator+fileName);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out)) {
            bufferedOutputStream.write(resBody);
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
