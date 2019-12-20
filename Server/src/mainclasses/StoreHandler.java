package mainclasses;

import constants.BinaryStatus;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

public class StoreHandler {
    private static int fileid = 0;
    private  static String filepathprefix = "/home/kunal/Inventory/file";

    public static FileInfo store(byte []ar){

        String filepathsuffix;
        String filepath = null;
        int fid = updatefileid();

        try {

            filepathsuffix = String.valueOf(fid);
            filepath = filepathprefix + filepathsuffix;     //new file path to store new file
            File file = new File(filepath);
            file.createNewFile();                       //create file

            FileOutputStream fileOutputStream = new FileOutputStream(filepath);
            fileOutputStream.write(ar);                 //store image or video in file
            fileOutputStream.close();


        }catch (IOException e){
            e.printStackTrace();
            return new FileInfo(BinaryStatus.FAILURE);
        }

        return new FileInfo(fid, filepath);
    }

    public static synchronized int  updatefileid(){
        fileid++;
        return fileid;
    }
}
