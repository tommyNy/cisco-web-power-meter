package pl.tomihome.ciscowebpowermeter.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.util.SystemPropertyUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class JschTest {

	@Test
	public void doJschTest() {
		JSch jsch = new JSch();
		Session session = null;
		String privateKeyPath = "/home/tomi/.ssh/id_rsa";

		boolean assertion = true;

		try {
			jsch.addIdentity(privateKeyPath);
			session = jsch.getSession("tomi", "192.168.0.3", 22);
			session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
		} catch (JSchException e) {
			assertion = false;
			throw new RuntimeException("Failed to create Jsch Session object.", e);
		}
		// String command = "echo \"Sit down, relax, mix yourself a drink and
		// enjoy the show...\" >> /home/tomi/test.out";
		String command = "date";
		try {
			System.out.println("TEST1");

			try {
				session.connect();
			} catch (Exception e) {
				System.out.println("Oops exception: " + e.getMessage());
			}

			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);
			((ChannelExec) channel).setPty(false);
			channel.connect();
			channel.disconnect();
			session.disconnect();
		} catch (JSchException e) {
			assertion = false;
			throw new RuntimeException("Error durring SSH command execution. Command: " + command);
		}
		assertEquals(assertion, true);
	}
}
