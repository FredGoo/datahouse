package geex.datahouse;

import geex.datahouse.config.FileConfig;
import geex.datahouse.service.AnalysisFileService;

public class Application {
    public static void main(String args[]) {
//        GetFileService getFile = new GetFileService(FileConfig.TEMP_FILE_NAME);
//        getFile.getFileThoughSSH();
//        UnzipFileService unzipFile = new UnzipFileService();
//        unzipFile.unzip(FileConfig.TEMP_FILE_NAME);
        AnalysisFileService analysisFile = new AnalysisFileService();
        analysisFile.deal(FileConfig.TEMP_UNZIP_FILE_NAME);
    }
}