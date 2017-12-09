package geex.datahouse.service;

import geex.datahouse.config.FileConfig;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipFileService {
    public void unzip(String srcZipFile) {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(FileConfig.OUTPUT + srcZipFile));
            ZipInputStream zis = new ZipInputStream(bis);

            BufferedOutputStream bos;

            //byte[] b = new byte[1024];
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String entryName = entry.getName();
                bos = new BufferedOutputStream(new FileOutputStream(FileConfig.OUTPUT + entryName));
                int b = 0;
                while ((b = zis.read()) != -1) {
                    bos.write(b);
                }
                bos.flush();
                bos.close();
            }
            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
