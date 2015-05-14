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

	static int rotateIncrementL = 270;
	static int rotateIncrementR = 270;

	static NXTRegulatedMotor rightMotor = Motor.B;
	static NXTRegulatedMotor leftMotor = Motor.C;

	static UltrasonicSensor frontSensor = new UltrasonicSensor(SensorPort.S1);
	static UltrasonicSensor leftSensor = new UltrasonicSensor(SensorPort.S2);

	static String name = "Group 1";
	static boolean stop = false;

	public static void main(String[] args) throws IOException {
		FileOutputStream fos = new FileOutputStream(new File("data.dat"));
		DataOutputStream bbw = new DataOutputStream(fos);
		boolean turnLeft = false;
		boolean turnRight = false;

		int counter = 0;
		String dataReading = "";
Thread t1=new Thread(new StopThread());
t1.start();
		while (!stop) {
			// Store distances on left and in front.
			int frontData = frontSensor.getDistance();
			int leftData = leftSensor.getDistance();

			// Turn both motors forward 90 degrees.
			rightMotor.rotate(rotateIncrementR, true);
			leftMotor.rotate(rotateIncrementL, false);

			// After the robot moves, check left distance again.
			if (leftSensor.getDistance() - leftData > 15) {
				// If new distance is 10 units greater than previous reading,
				// turn left.
				turnLeft = true;
				turnRight = false;
				leftMotor.rotate(150, true);
				rightMotor.rotate(150, false);
				leftMotor.rotate(-180, true);
				rightMotor.rotate(180, false);
				frontData = frontSensor.getDistance();
				leftData = leftSensor.getDistance();
			}
			if (frontSensor.getDistance() < 17) {
				if (leftSensor.getDistance() - leftData > 10) {
					// If new distance is 10 units greater than previous
					// reading, turn left.
					turnLeft = true;
					turnRight = false;
					leftMotor.rotate(150, true);
					rightMotor.rotate(150, false);
					leftMotor.rotate(-180, true);
					rightMotor.rotate(180, false);
					frontData = frontSensor.getDistance();
					leftData = leftSensor.getDistance();
				}

				else {
					leftMotor.rotate(180, true);
					rightMotor.rotate(-180, false);
					turnLeft = false;
					turnRight = true;
					frontData = frontSensor.getDistance();
					leftData = leftSensor.getDistance();
				}
			}

			dataReading = "" + counter + "," + leftData + "," + frontData + ","
					+ turnLeft + "," + turnRight + "\n";
			bbw.writeChars(dataReading);
			bbw.flush();

			turnLeft = false;
			turnRight = true;

		}
		bbw.close();
		System.exit(0);
	}

	private static class StopThread implements Runnable {

		@Override
		public void run() {
			if (Button.waitForAnyPress() == Button.ID_ENTER) {
				stop = true;
			}

		}

	}
}
