package mostRecentPackage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.bluetooth.RemoteDevice;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;


public class BluetoothI {

	private final static char LEFT = 'l';
	private static final char RIGHT = 'r';
	private final static char GO = 'g';
	private final static char WAVE='w';
	public static void main(String[] args) throws IOException {
		DataInputStream is;
		DataOutputStream os;
		String name = "Group 9";
		LCD.drawString("Connecting...", 0, 0);
		RemoteDevice btrd = Bluetooth.getKnownDevice(name);
		if (btrd == null) {
			LCD.clear();
			LCD.drawString("No such device", 0, 0);
			Button.waitForAnyPress();
			System.exit(1);
		}

		BTConnection btc = Bluetooth.connect(btrd);

		if (btc == null) {
			LCD.clear();
			LCD.drawString("Connect fail", 0, 0);
			Button.waitForAnyPress();
			System.exit(1);
		}

		LCD.clear();
		LCD.drawString("Connected", 0, 0);
		is = btc.openDataInputStream();
		os = btc.openDataOutputStream();
		LightSensor light = new LightSensor(SensorPort.S1);
		int lightValue;
		while (true) {
			lightValue=light.getNormalizedLightValue();
			if (lightValue>900){
				os.writeChar(WAVE);
				os.flush();
			}
			if(is.readChar() == WAVE)
			{
				LCD.drawString("Wave Command Received", 0, 4);
			}
		}
	}
}
