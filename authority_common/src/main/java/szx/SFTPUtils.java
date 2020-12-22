package szx;

import com.jcraft.jsch.*;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

public class SFTPUtils {
    private static final String host = "192.168.18.131";
    private static final int port = 22;
    private static final String username = "root";
    private static final String password = "root";
    private static final String path = "/usr/local/nginx/html";



    public ChannelSftp setupJsch() throws JSchException {
        JSch jsch = new JSch();
        jsch.setKnownHosts("/root/.ssh/known_hosts");
        Session jschSession = jsch.getSession(username, host);
        jschSession.setPassword(password);
        jschSession.connect();
        return (ChannelSftp) jschSession.openChannel("sftp");
    }

    public void whenUploadFileUsingJsch_thenSuccess(MultipartFile file) throws JSchException, SftpException, IOException {
        ChannelSftp channelSftp = setupJsch();
        channelSftp.connect();
        channelSftp.put(file.getInputStream(), path );
        channelSftp.exit();
    }

}
