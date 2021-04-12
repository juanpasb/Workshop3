import java.net.*;
import java.io.*;
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
        var port = 5000;
        int accountant = 0;
        String received;
        var pool = Executors.newFixedThreadPool(5);
        Boolean loading ;


        do {
            loading = false;
            try (var listener = new ServerSocket(port)) {
                loading = false;
                System.out.println("Welcome Agent  ");
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
        String info = "";
        try {
            //do {
            if (info.equals(EXIT)) {
                info = (String) in.readUTF();
                message("\nCITIZEN: " + info);
                System.out.print("YOU: ");
            }while (!info.equals(EXIT));

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
            @Override
            public void run() {
                while (true) {
                    safePrintln("Did a citizen arrive?");
                    safePrintln("1 Accept");
                    safePrintln("2 Deny");
                    BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
                    String answer = "";
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
  /*  try{
        Scanner reader = new Scanner(System.in);
        InetAddress ip = InetAddress.getByName("localhost");
        Socket socket = new Socket(ip,6666);
        DataInputStream dataIn = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
        while(true){
            System.out.println(dataIn.readUTF());
            String toSend = reader.nextLine();
            dataOut.writeUTF(toSend);
            if(toSend.equals("Exit")){
                System.out.println("Closing this connection: " + socket);
                socket.close();
                System.out.println("Connection closed!");
                break;
            }
            String received = dataIn.readUTF();
            System.out.println(received);
        }
        reader.close();
        dataIn.close();
        dataOut.close();
    }catch (Exception e){
        e.printStackTrace();
    }
}
}*/
}
