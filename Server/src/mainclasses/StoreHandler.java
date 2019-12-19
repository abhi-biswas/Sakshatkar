package mainclasses;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

public class StoreHandler {
    private static int fileid = 0;
    private  static String filepathprefix = "../Inventory/file";

    public static FileInfo store(byte []ar){

        fileid++;
        String filepathsuffix;
        String filepath = null;


        try {
            filepathsuffix = String.valueOf(fileid);
            filepath = filepathprefix + filepathsuffix;     //new file path to store new file
            File file = new File(filepath);
            file.createNewFile();

            FileOutputStream fileOutputStream = new FileOutputStream(filepath);
            fileOutputStream.write(ar);                 //store file
            fileOutputStream.close();


        }catch (IOException e){
            e.printStackTrace();
        }

        return new FileInfo(fileid, filepath);
    }
}
