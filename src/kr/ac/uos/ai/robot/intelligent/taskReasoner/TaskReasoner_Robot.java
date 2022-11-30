package kr.ac.uos.ai.robot.intelligent.taskReasoner;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import kr.ac.uos.ai.arbi.BrokerType;
import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.arbi.agent.logger.LoggerManager;
import kr.ac.uos.ai.arbi.ltm.DataSource;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.arbi.model.parser.ParseException;
import kr.ac.uos.ai.robot.intelligent.taskReasoner.action.TaskReasonerAction;
import kr.ac.uos.ai.robot.intelligent.taskReasoner.message.GLMessageManager;
import kr.ac.uos.ai.robot.intelligent.taskReasoner.message.JsonMessageManager;
import kr.ac.uos.ai.robot.intelligent.taskReasoner.message.RecievedMessage;
import kr.ac.uos.ai.robot.intelligent.taskReasoner.policy.PolicyHandler;
import kr.ac.uos.ai.robot.intelligent.taskReasoner.service.ServiceModelGenerator;
import kr.ac.uos.ai.robot.intelligent.taskReasoner.utility.UtilityCalculator;
import uos.ai.jam.Interpreter;
import uos.ai.jam.JAM;

public class TaskReasoner_Robot extends ArbiAgent {

	public static String ENV_JMS_BROKER;
	public static String ENV_ROBOT_NAME;
	public static final String ARBI_PREFIX = "www.arbi.com/";
	
	private static String brokerURI = "tcp://172.16.165.204:61114";	
	private static String TASKREASONER_ADDRESS = "www.arbi.com/TaskReasoner";
	private static String TASKMANAGER_ADDRESS = "agent://www.arbi.com/TaskManager";
	private String RobotPlanPath;
	private static final String	agentURIPrefix			= "agent://";
	private static final String	dsURIPrefix				= "ds://";
	
	private Interpreter									interpreter;
	private GLMessageManager							glMessageManager;
	private BlockingQueue<RecievedMessage>				messageQueue;
	private DataSource									ds;
	private PlanLoader 									planLoader;
	private PolicyHandler								policyHandler;
	private ServiceModelGenerator						serviceModelGenerator;
	private TaskReasonerAction							taskReasonerAction;
	private LoggerManager 								loggerManager;
	private JsonMessageManager							jsonMessageManager;
	private UtilityCalculator							utilityCalculator;
	

//	public TaskReasoner_Robot() {
//
//
//		initAddress();
//		//config();
//		interpreter = JAM.parse(new String[] {"./TaskReasonerTowPlan/boot.jam"} );
//		
//		ds = new TaskReasonerDataSource(this);
//		
//		messageQueue= new LinkedBlockingQueue<RecievedMessage>();
//		glMessageManager = new GLMessageManager(interpreter, ds);
//		planLoader = new PlanLoader(interpreter);
//		serviceModelGenerator = new ServiceModelGenerator(this);
//		policyHandler = new PolicyHandler(this,interpreter);
//		jsonMessageManager = new JsonMessageManager(policyHandler);
//		utilityCalculator = new UtilityCalculator(interpreter);
//		
//		ArbiAgentExecutor.execute(ENV_JMS_BROKER,  agentURIPrefix + TASKREASONER_ADDRESS, this, brokerType);
//
//		loggerManager = LoggerManager.getInstance();
//		
//		taskReasonerAction = new TaskReasonerAction(this, interpreter, loggerManager);
//		
//		init();
//	}
//	
	public TaskReasoner_Robot(String robotID, String brokerAddress, int port, BrokerType brokerType) {


		initAddress(robotID,brokerAddress);
		interpreter = JAM.parse(new String[] {"./TaskReasonerRobotPlan/boot.jam"} );
		
		ds = new TaskReasonerDataSource(this);
		
		messageQueue= new LinkedBlockingQueue<RecievedMessage>();
		glMessageManager = new GLMessageManager(interpreter, ds);
		planLoader = new PlanLoader(interpreter);
		serviceModelGenerator = new ServiceModelGenerator(this);
		policyHandler = new PolicyHandler(this,interpreter);
		jsonMessageManager = new JsonMessageManager(policyHandler);
		utilityCalculator = new UtilityCalculator(interpreter);
		
		ArbiAgentExecutor.execute(ENV_JMS_BROKER, port, agentURIPrefix + TASKREASONER_ADDRESS, this, brokerType);

		ds.connect(ENV_JMS_BROKER, port, dsURIPrefix+TASKREASONER_ADDRESS, brokerType);
		loggerManager = LoggerManager.getInstance();
		
		taskReasonerAction = new TaskReasonerAction(this, interpreter, loggerManager);
		
		init();
	}
	
	public void initAddress(String robotID, String brokerAddress) {
		String brokerURL = "";
		if(brokerAddress.equals("env")) {
			brokerURL = System.getenv("JMS_BROKER");
		} else {
			brokerURL = brokerAddress;
		}
		ENV_ROBOT_NAME = robotID;
		
		if (ENV_ROBOT_NAME.equals("AMR_LIFT1")) {
			RobotPlanPath = "./TaskReasonerRobotPlan/LiftPlanList.jam";
		} else if (ENV_ROBOT_NAME.equals("AMR_LIFT2")) {
			RobotPlanPath = "./TaskReasonerRobotPlan/LiftPlanList.jam";
		}else if (ENV_ROBOT_NAME.equals("AMR_LIFT3")) {
			RobotPlanPath = "./TaskReasonerRobotPlan/LiftPlanList.jam";
		}else if (ENV_ROBOT_NAME.equals("AMR_LIFT4")) {
			RobotPlanPath = "./TaskReasonerRobotPlan/LiftPlanList.jam";
		}
		ENV_JMS_BROKER = brokerURL;

	}

	

	public void initAddress() {
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			ENV_JMS_BROKER = "tcp://"+System.getenv("JMS_BROKER");
			ENV_ROBOT_NAME = System.getenv("ROBOT");
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
		
	private void init() {
		
		glMessageManager.assertFact("GLMessageManager", glMessageManager);
		glMessageManager.assertFact("PolicyHandler", policyHandler);
		glMessageManager.assertFact("ServiceModelGenerator", serviceModelGenerator);
		glMessageManager.assertFact("LoggerManager", taskReasonerAction);
		glMessageManager.assertFact("JsonMessageManager", jsonMessageManager);
		glMessageManager.assertFact("UtilityCalculator", utilityCalculator);
		glMessageManager.assertFact("TaskReasoner", this);
		glMessageManager.assertFact("RobotPlanPath", RobotPlanPath);
		Thread t = new Thread() {
			public void run() {
				interpreter.run();
			}
		};
		
		t.run();
	}
		
	@Override
	public void onStart() {
		System.out.println("====onStart====");
		//goal and context is wrapped
		//String subscriveGoal = "(rule (fact (goal $goal $precondition $postcondition)) --> (notify (goal $goal $precondition $postcondition)))";
		//ds.subscribe(subscriveGoal);
		// (goal (goalName ))
				
		//String subscriveContext = "(rule (fact (context $context)) --> (notify (context $context)))";
		//System.out.println(ds.subscribe(subscriveContext));
		
		
		
		System.out.println("reasoner boot complete!");

	}
	
	@Override
	public void onNotify(String sender, String notification) {
		System.out.println("Notyfied from " + sender + ". Message is " + notification);
		RecievedMessage msg = new RecievedMessage(sender, notification);
		try {
			messageQueue.put(msg);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String onRequest(String sender, String request) {
		
		return null;
	}
	
	
	@Override
	public void onData(String sender, String data) {
		try {
			System.out.println("data = " + data);
			RecievedMessage message = new RecievedMessage(sender, data);
				
			messageQueue.put(message);
		} catch (InterruptedException e) {
				e.printStackTrace();
		}
	}
	public void sleep(int count) {
		try {
			Thread.sleep(count);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean dequeueMessage() {
		
		if (messageQueue.isEmpty())
			return false;
		else {
			try {
				RecievedMessage message = messageQueue.take();
				GeneralizedList gl = null;
				String data = message.getMessage();
				String sender = message.getSender();

				gl = GLFactory.newGLFromGLString(data);

				//System.out.println("message dequeued : " + gl.toString());

				if (gl.getName().equals("context")) {

					glMessageManager.assertContext(gl.getExpression(0).asGeneralizedList());
				} else if (gl.getName().equals("goalComplete")) {
					GeneralizedList goalGL = gl.getExpression(0).asGeneralizedList();
					System.out.println("completed goal name : " + goalGL.getName());
					glMessageManager.assertFact("GoalCompleted",goalGL.getName(),goalGL.toString());
				} else if(gl.getExpression(0).isGeneralizedList()) {
					System.out.println(gl.toString());
				} else {
					glMessageManager.assertFact("RecievedMessage", sender, data);
				}

			} catch (InterruptedException | ParseException e) {
				e.printStackTrace();
			}

			return true;
		}
	}
		
	public boolean sendToTM(String type, String gl) {
		
		System.out.println("send to tm : " + type + ", " + gl);
		this.send(TASKMANAGER_ADDRESS, "(" + type + " " + gl + ")");

		return true;
	}
	
	public void parsePlan(String string) {
		planLoader.parsePlan(string);
	}
	
	public void loadPlan(String string ) {
		planLoader.loadPlan(string);
	}
	public void loadPlanPackage(String string) {
		planLoader.loadPlanPackage(string);
	}
	
	public void assertFact(String name, Object... args) {
		glMessageManager.assertFact(name, args);
	}

	public GLMessageManager getGlMessageManager() {
		return glMessageManager;
	}
	
	public PolicyHandler getPolicyHandler() {
		return policyHandler;
	}
	
	public ServiceModelGenerator getServiceModelGenerator() {
		return serviceModelGenerator;
	}
	
	public void receivedPolicyMessage(String str) {
		jsonMessageManager.updateLMPolicyValue(str);
	}

	public void putUtilityFunction(String serviceName, String stringFunction) {
		utilityCalculator.putUtilityFunction(serviceName, stringFunction);
	}

	
//	public static void main(String[] args) {
//		ArbiAgent agent = new TaskReasoner_Robot();
//	}
}
