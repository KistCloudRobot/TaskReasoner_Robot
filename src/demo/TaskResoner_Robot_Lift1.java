package demo;

import kr.ac.uos.ai.arbi.BrokerType;
import kr.ac.uos.ai.robot.intelligent.taskReasoner.TaskReasoner_Robot;

public class TaskResoner_Robot_Lift1 {

	public static void main(String[] args) {

		String brokerAddress;
		String robotID;
		int port;
		if(args.length == 0) {
<<<<<<< HEAD
			brokerAddress = "172.16.165.141";
//			brokerAddress = "192.168.100.10";
//			brokerAddress = "127.0.0.1";
=======
//			brokerAddress = "tcp://172.16.165.141:61116";
			brokerAddress = "tcp://192.168.100.10:61116";
>>>>>>> branch 'KyonggiDemo' of https://github.com/KistCloudRobot/TaskReasoner_Robot.git
			robotID = "AMR_LIFT1";	
			port = 61116;
		} else {
			robotID = args[0];
			brokerAddress = args[1];
			port = Integer.parseInt(args[2]);
		}
		
		TaskReasoner_Robot reasoner = new TaskReasoner_Robot(robotID, brokerAddress, port, BrokerType.ACTIVEMQ);
	}
}
