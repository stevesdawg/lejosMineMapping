package mostRecentPackage;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.remote.NXTComm;


public class PCReceiver {
	
	public static void main(String[] args)
	{
		System.out.println("Waiting for Mapper to connect...");
		NXTConnection mapperConnection = Bluetooth.waitForConnection();
		
		NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
		nxtComm.search("MYNXT");
		System.out.println("Connected to Mapper.");
		
		DataInputStream dis = mapperConnection.openDataInputStream();
		DataOutputStream dos = mapperConnection.openDataOutputStream();
	}

}
