import java.net.*;
import java.io.*;

public class ChatServer {
    private Socket socket;
    BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
    final String Exit = "Exit";
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private String ip;
    private int port;

    public static void message(String s) {
        System.out.println(s);
    }

    public void executeConnexion() {
        var thread = new Thread(new Runnable() {

            public void run() {
                try {
                    socket = new Socket(ip, port);
                    message("Please Wait an Agent ");
                    routeFiles();
                    Information();
                } catch (ConnectException e) {
                    System.out.println("Can't attend thanks for your waiting");
                    System.exit(0);
                } catch (IOException x) {

                }
            }
        });
         thread.start();
    }

    public ChatServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
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

    public void sendFiles(String l) {
        try {
            out.writeUTF(l);
            out.flush();
        } catch (IOException e) {
            message("Error  ");

        }
    }

    public void writeFiles() {
        String z = "";
        while (true) {
            try {
                System.out.print("Citizen: ");
                z = scanner.readLine();
                if (z.length() > 0)
                    sendFiles(z);
            } catch (Exception e) {
            }
        }
    }

    public void Information() {
        String data = "";
        try {
            do {
                data = (String) in.readUTF();
                message("\nAgent: " + data);
                System.out.print("Citizen: ");
            } while (!data.equals(Exit));

        } catch (IOException e) {
        }
    }


}