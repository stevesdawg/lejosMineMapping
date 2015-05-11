package mostRecentPackage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.bluetooth.RemoteDevice;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;


public class Mapper {
	
	static int rotateIncrementL = 90;
	static int rotateIncrementR = 90;
	
	static NXTRegulatedMotor rightMotor = Motor.B;
	static NXTRegulatedMotor leftMotor = Motor.C;
	
	static UltrasonicSensor frontSensor = new UltrasonicSensor(SensorPort.S1);
	static UltrasonicSensor leftSensor = new UltrasonicSensor(SensorPort.S2);
	
	static String name = "Group 1";

	public static void main(String[] args) throws FileNotFoundException {
		
		DataInputStream dis;
		DataOutputStream dos;
		
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
		dis = btc.openDataInputStream();
		dos = btc.openDataOutputStream();
		
		boolean turnLeft = false;
		
		int counter = 0;
		String dataReading="";
		
		while(true)
		{
			int frontData = frontSensor.getDistance();
			int leftData = leftSensor.getDistance();
			
			
			rightMotor.rotate(rotateIncrementR, true);
			leftMotor.rotate(rotateIncrementL, false);
			
			if(Math.abs(leftSensor.getDistance() - leftData) > 10)
			{
				turnLeft = true;
				leftMotor.rotate(-180, true);
				rightMotor.rotate(180, false);
			}
			if(frontData < 3)
			{
				leftMotor.rotate(180, true);
				rightMotor.rotate(-180, false);
				turnLeft = true;
			}
			
			dataReading = ""+counter+","+leftData+","+frontData+","+turnLeft+"\n";
			turnLeft = false;
			try {
				dos.write(dataReading.getBytes());
				dos.flush();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
