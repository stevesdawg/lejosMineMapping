package mostRecentPackage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;


public class BluetoothR {

	private final static char LEFT = 'l';
	private static final char RIGHT = 'r';
	private final static char GO = 'g';
	private final static char WAVE = 'w';
	public static void main(String[] args) throws IOException, InterruptedException {
		String connected = "Connected";
		String waiting = "Waiting...";
		String closing = "Closing...";

		LCD.drawString(waiting, 0, 0);
		NXTConnection connection = Bluetooth.waitForConnection();
		LCD.clear();
		LCD.drawString(connected, 0, 0);

		DataInputStream dis = connection.openDataInputStream();
		DataOutputStream dos = connection.openDataOutputStream();
		char command;
		while (true) {
			command = dis.readChar();
//			if (command==LEFT){
//				LCD.drawString("Left Pressed", 0, 5);
//				dos.writeChar(LEFT);
//				Motor.B.rotate(180, true);
//				Motor.C.rotate(-180,false);
//			}
//			else if (command==RIGHT){
//				LCD.drawString("Right Pressed", 0, 5);
//				dos.writeChar(RIGHT);
//				Motor.B.rotate(-180, true);
//				Motor.C.rotate(180,false);
//			}
//			else if (command==GO){
//				LCD.drawString("Middle Pressed", 0, 5);
//				dos.writeChar(GO);
//				Motor.B.rotate(720, true);
//				Motor.C.rotate(720,false);
//			}
			if (command == WAVE)
			{
				LCD.drawString("Wave Command Received", 0, 4);
				Thread.sleep(30000);
				dos.writeChar('w');
				Motor.A.rotate(100);
				Motor.A.rotate(-200);
				Motor.A.rotate(200);
				Motor.A.rotate(-100);
			}
			LCD.drawString(""+command, 0, 6);
			dos.flush();
				
		}

	}

}
