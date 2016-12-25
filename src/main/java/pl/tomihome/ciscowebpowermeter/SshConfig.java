package pl.tomihome.ciscowebpowermeter;

public class SshConfig {

	public static String host, pathToKey, keyPass, sshUser, command;
	public static int port;
	
	public void setCommand(String command) {
		SshConfig.command = command;
	}
	public void setHost(String host) {
		SshConfig.host = host;
	}
	public void setPathToKey(String pathToKey) {
		SshConfig.pathToKey = pathToKey;
	}
	public void setKeyPass(String keyPass) {
		SshConfig.keyPass = keyPass;
	}
	public void setSshUser(String sshUser) {
		SshConfig.sshUser = sshUser;
	}
	public void setPort(int port) {
		SshConfig.port = port;
	}
}
