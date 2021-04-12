import java.io.*;
import java.net.*;
import java.util.concurrent.Executors;

public class AgentServer {

    private static Socket socket;
    private static BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
    private final String EXIT = "exit";
    private static DataInputStream in = null;
    private static DataOutputStream out = null;
    private static final boolean[] isTrue = {true};

    public AgentServer(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) throws Exception {
        AgentServer agentServer;
        var port = 4460;
       int accountant = 0;
        var pool = Executors.newFixedThreadPool(5);
        Boolean loading ;
        do {
            loading = false;
            try (var listener = new ServerSocket(port)) {
                loading = false;
                System.out.println("Welcome Agent  " + accountant);
                System.out.println(" You are in the port:" + port);
                System.out.println("waiting  an citizen...........Wait");
                while (true) {
                    agentServer = new AgentServer(listener.accept());
                    pool.execute(agentServer.run());
                    agentServer.writeFiles();
                }
            } catch (BindException e) {
                accountant++;
                loading = true;
                port++;
            } catch (Exception e) {
                System.out.println("ERROR");
            }
       } while (loading);
    }

    public void Information() {
        String m = "";
        try {
            //do {
            if (m.equals(EXIT)) {
                m = (String) in.readUTF();
                message("\nCITIZEN: " + m);
                System.out.print("YOU: ");
            }while (!m.equals(EXIT));

        } catch (IOException e) {
        }
    }

    public void routeFiles() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            out.flush();
        } catch (IOException e) {
            message("Error route data");
        }
    }


    public void sendFiles(String s) {
        try {
            out.writeUTF(s);
            out.flush();
        } catch (IOException e) {
            message("Error : ");
        } catch (Exception e) {

        }
    }

    public static void message(String complement) {
        System.out.println(complement);
    }


    public Runnable run() {

        var thread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    safePrintln("Did a citizen arrive?");
                    safePrintln("1 Accept");
                    safePrintln("2 Deny");
                    String answer = "";
                    BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
                    
                    try {
                        System.out.println("Wait a moment");
                        answer = scanner.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    switch (answer) {
                        case "1":
                            try {
                                Information();
                                routeFiles();

                            } finally {
                            }

                            break;
                        case "2":
                            safePrintln("At this time we cannot attend to you. Wait");
                            try {
                                isTrue[0] = false;
                                System.out.println("Looking for an agent to be served");
                                socket.close();
                            } catch (IOException e) {
                            }
                            break;
                        default:
                            System.out.println("Invalid option");
                            run();
                            break;
                    }
                }
            }
        });
        return thread;
    }

    public void writeFiles() {
        while (true) {
            try {
                System.out.print("AGENT:  ");
                sendFiles(scanner.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void safePrintln(String s) {
        synchronized (System.out) {
            System.out.println(s);
        }
    }
}
