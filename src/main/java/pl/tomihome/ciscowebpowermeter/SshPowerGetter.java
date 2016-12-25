package pl.tomihome.ciscowebpowermeter;

import java.io.ByteArrayOutputStream;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshPowerGetter {

	public static String getValueBySsh() throws JSchException, InterruptedException {
		JSch jsch = new JSch();
		Session session = null;
		ChannelExec channel = null;

		jsch.addIdentity(SshConfig.pathToKey, SshConfig.keyPass);
		session = jsch.getSession(SshConfig.sshUser, SshConfig.host, SshConfig.port);
		session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);

		session.connect();
		channel = (ChannelExec) session.openChannel("exec");
		
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		channel.setOutputStream(outStream, true);
		channel.setCommand(SshConfig.command);
		channel.connect();

		do {
			Thread.sleep(1000);
		} while (!channel.isEOF());

		channel.disconnect();
		session.disconnect();

		return new String(outStream.toByteArray());
	}
}
