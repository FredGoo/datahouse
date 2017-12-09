package geex.log.analysis.old.service;

import geex.log.analysis.config.FileConfig;

import java.io.*;

public class AnalysisFileService {
    public void deal(String fileName) {
        InputStream is = null;
        try {
            is = new FileInputStream(FileConfig.OUTPUT + fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
            // 读取一行，存储于字符串列表中
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                line = line.trim();
                // do something here
                System.out.println(line);
            }

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
