package geex.datahouse.service;

import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import geex.datahouse.config.FileConfig;

import java.io.*;

public class GetFileService {
    public void getFileThoughSSH(String filename) {
        SshClient client = new SshClient();
        try {
            client.connect(FileConfig.SOURCE_SERVER_HOST, FileConfig.SOURCE_PORT);
            // 设置用户名和密码
            PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
            pwd.setUsername(FileConfig.USER_NAME);
            pwd.setPassword(FileConfig.USER_PASSWORD);
            int result = client.authenticate(pwd);
            if (result == AuthenticationProtocolState.COMPLETE) {// 如果连接完成
                OutputStream os = new FileOutputStream(FileConfig.OUTPUT + filename);
                client.openSftpClient().get(FileConfig.SOURCE_SERVER_DIR + filename, os);
                File file = new File(FileConfig.OUTPUT + filename);
                if (!file.exists()) {
                    file.createNewFile();
                }
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(file));
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e1) {
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
