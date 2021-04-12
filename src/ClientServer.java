import java.util.Scanner;

public class ClientServer {
    public static void main(String[] args) {
        ClientServer c = new ClientServer();
    }

    private Scanner sc;//Submit info
    private String pets;

    private String[] dataPet = new String[]{"1 specie",
            "2 size", "3 city ", "4 Adress",
            "5 Name complete person reporting",
            "6 Phone number of the person who reports ",
            "7 Email of the person who reports",
            "8 General comments"};

    ClientServer() {
        pets = "";
        sc = new Scanner(System.in);

        System.out.println("Welcome Citizen  ");
        System.out.println("1 if you want to create a case ");
        System.out.println("2 if you want to speak to an agent");
        int number = sc.nextInt();

        if (number == 1) {

            int x = 1;

            int numberCase;

            System.out.println("Select which case you want to report ");
            System.out.println("1 Lose");
            System.out.println("2 Stole");
            System.out.println("3 abandonment");
            System.out.println("4 Potent Dangerous");
            System.out.println("5 Improper handling on public roads");


            numberCase = sc.nextInt();

            if (numberCase < 1 || numberCase > 5) {
                System.out.println("Error number");
                System.exit(0);
            }
            for (int i = 0; i < dataPet.length; i++) {
                System.out.println("complete the following questions :");
                System.out.println(dataPet[i]);
                String infox = sc.nextLine();
                if (infox.equals("")) {
                    System.out.println("Error");
                    i = i - 1;
                } else {
                    pets += infox.toUpperCase() + ",";
                }
            }

        }
        if (number == 2) {
            var isRunning = false;
            var port = 4460;
            do {
                isRunning = false;
                try {
                    ChatServer ChatServer = new ChatServer("127.0.0.1", port);
                    ChatServer.executeConnexion();
                    ChatServer.writeFiles();
                } catch (Exception e) {
                    port++;
                    System.out.println(port);
                    isRunning = true;
                    e.printStackTrace();
                }
            } while (isRunning);
        } else {
            System.out.println("Error Number");
            new ClientServer();
        }
    }


}
