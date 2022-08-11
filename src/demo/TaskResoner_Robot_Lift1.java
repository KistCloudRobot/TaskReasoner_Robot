package demo;

import kr.ac.uos.ai.robot.intelligent.taskReasoner.TaskReasoner_Robot;

public class TaskResoner_Robot_Lift1 {

	public static void main(String[] args) {

		String brokerAddress;
		String robotID;
		if(args.length == 0) {
			brokerAddress = "tcp://172.16.165.141:61116";
			robotID = "AMR_LIFT1";	
		} else {
			robotID = args[0];
			brokerAddress = args[1];
		}
		
		TaskReasoner_Robot reasoner = new TaskReasoner_Robot(robotID, brokerAddress);
	}
}
