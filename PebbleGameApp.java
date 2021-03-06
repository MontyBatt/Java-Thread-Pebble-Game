package com.company;
// Importing all the necessary plug-ins
import java.io.*;
import java.util.*;
//Declaring the game
@SuppressWarnings("ALL")
public class PebbleGameApp {
    //Creating the player class extends thread to make multiple run through
    // Listing the variables in player class
    @SuppressWarnings({"StatementWithEmptyBody", "BusyWait"})
    public static class Player extends Thread {
        private final int playerNumber;
        private final ArrayList<Integer> hand = new ArrayList<>();
        private final ArrayList<Bag> blackBag;
        private final ArrayList<Bag> whiteBag;
        private final int numPlayer;
        //Constructor of player class
        public Player(int playerNumber, ArrayList<Bag> blackBag, ArrayList<Bag> whiteBag, int numPlayer) {
            this.playerNumber = playerNumber;
            this.blackBag = blackBag;
            this.whiteBag = whiteBag;
            this.numPlayer = numPlayer;
        }
        //Run to start the threads
        @Override
        public void run() {
            File fileName = new File("out.txt");
            //random to generate the unpredictability of the game
            Random random = new Random();
            int round = 0;
            // ArrayList containing 3 black bags
            // Take one of them at random and draw 10 pebs
            for (int i = 0; i < 10; i++) {
                try {
                    hand.add(blackBag.get(random.nextInt(3)).drawFromBag());
                } catch (NullPointerException ignored) {
                }
            }
            //Shows players first hand out of the bag
            System.out.println("Player " + playerNumber + "'s initial hand: " + getHand() + " with a value of " + getSum());
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Random rand = new Random();
                //Checks the size of the black bags to fill them up with white bags
                for (int i = 0; i < 3; i++) {
                    if (blackBag.get(i).contents.size() <= numPlayer) {
                        for (int j = 0; j < whiteBag.get(i).contents.size(); j++) {
                            blackBag.get(i).contents.add(whiteBag.get(i).contents.get(j));
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Checks if the hand equals 100 and has a different winning message based on the round the game was won on.
                if (getSum() == 100) {
                    if (round == 0) {
                        System.out.println("Player " + playerNumber + " has won on round of the initial hand");
                    } else {
                        System.out.println("Player " + playerNumber + " has won on round " + round);
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                round++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Shows the user the current round the game is on.
                System.out.println("THIS IS ROUND " + round);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Random pebble drawn out of a random bag bound by the 3 black bags
                int bagIndex = rand.nextInt(3);
                int drawnPebble = blackBag.get((bagIndex)).drawFromBag();

                try {
                    //Adds the pebble to the players hand
                    hand.add(blackBag.get((bagIndex)).drawFromBag());
                } catch (NullPointerException ignored) {
                }
                //Chooses a random pebble to take out of the hand and to be discarded to a random white bag
                int chosenPebble = rand.nextInt(10);
                int pebble = hand.get(chosenPebble);
                hand.remove(chosenPebble);
                whiteBag.get(bagIndex).discardToBag(pebble);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Announces the pebble discarded for all players
                System.out.println("Player " + playerNumber + " discarded " + pebble + " to white bag " + whiteBag.get(bagIndex).bagName);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Announces the pebble drawn for all players
                System.out.println("Player " + playerNumber + " drew " + drawnPebble + " from black bag " + blackBag.get(bagIndex).bagName);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Shows each players hand and shows the weight in pebbles of them.
                System.out.println("Player " + playerNumber + "'s" + " new hand is " + getHand() + " with a value of " + getSum());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        // Method gets the array list of the threads hand
        public ArrayList<Integer> getHand() {
            return hand;
        }
        // Method gets the sum of the hand array list
        public int getSum() {
            int x = 0;
            for (Integer integer : hand) x += integer;
            return x;
        }
    }
    public static void main(String[] args) {
        // The making the different bags with corresponding names
        Bag whiteBagA = new Bag("A");
        Bag whiteBagB = new Bag("B");
        Bag whiteBagC = new Bag("C");
        Bag blackBagX = new Bag("X");
        Bag blackBagY = new Bag("Y");
        Bag blackBagZ = new Bag("Z");
        ArrayList<Bag> blackBag = new ArrayList<>();
        ArrayList<Bag> whiteBag = new ArrayList<>();
        blackBag.add(blackBagX);
        blackBag.add(blackBagY);
        blackBag.add(blackBagZ);
        whiteBag.add(whiteBagA);
        whiteBag.add(whiteBagB);
        whiteBag.add(whiteBagC);
        //gets number of players
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of players: ");
        int numPlayer = scanner.nextInt();
        //gets csv file and put it into a List
        List<List<String>> records = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter PATH to csv file for Black bag x: ");
        String path = sc.next();
        try {
            String line1;
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line1 = br.readLine()) != null) {
                String[] values = line1.split(",");
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<List<String>> records1 = new ArrayList<>();
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Enter PATH to csv file for Black bag Y: ");
        String path1 = sc.next();
        try {
            String line2;
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line2 = br.readLine()) != null) {
                String[] values = line2.split(",");
                records1.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<List<String>> records2 = new ArrayList<>();
        Scanner sc2 = new Scanner(System.in);
        System.out.println("Enter PATH to csv file for Black bag Z: ");
        String path2 = sc.next();
        try {
            String line3;
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line3 = br.readLine()) != null) {
                String[] values = line3.split(",");
                records2.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Makes sure that the number of players is valid
        if (numPlayer < 1) {
            System.out.println("You can not play with " + numPlayer + " players. Number of players can only be positive integer. ");
        }
        //Make sure the csv file has the all positive whole numbers
        try {
            for (int i = 0; i < records.get(0).size(); i++) {
                if (Integer.parseInt(records.get(0).get(i)) < 0) {
                    System.out.println(records.get(0).get(i) + " is not a valid number for this game, please check your csv file.");
                    System.exit(0);
                }
            }//Create duplicates of the numbers in the given csv file to ensure that their are more than eleven times the number of players in the game.
        } catch (NumberFormatException e) {
            System.out.println("There is a value that cannot be converted to an integear, please check your csv file.");
            System.exit(0);
        }//Make sure the csv file has the all positive whole numbers
        try {
            for (int i = 0; i < records.get(0).size(); i++) {
                if (Integer.parseInt(records.get(0).get(i)) < 0) {
                    System.out.println(records.get(0).get(i) + " is not a valid number for this game, please check your csv file.");
                    System.exit(0);
                }
            }//Create duplicates of the numbers in the given csv file to ensure that their are more than eleven times the number of players in the game.
        } catch (NumberFormatException e) {
            System.out.println("There is a value that cannot be converted to an integear, please check your csv file.");
            System.exit(0);
        }//Make sure the csv file has the all positive whole numbers
        try {
            for (int i = 0; i < records1.get(0).size(); i++) {
                if (Integer.parseInt(records1.get(0).get(i)) < 0) {
                    System.out.println(records1.get(0).get(i) + " is not a valid number for this game, please check your csv file.");
                    System.exit(0);
                }
            }//Create duplicates of the numbers in the given csv file to ensure that their are more than eleven times the number of players in the game.
        } catch (NumberFormatException e) {
            System.out.println("There is a value that cannot be converted to an integear, please check your csv file.");
            System.exit(0);
        }
        try {
            for (int i = 0; i < records2.get(0).size(); i++) {
                if (Integer.parseInt(records2.get(0).get(i)) < 0) {
                    System.out.println(records2.get(0).get(i) + " is not a valid number for this game, please check your csv file.");
                    System.exit(0);
                }


            }//Create duplicates of the numbers in the given csv file to ensure that their are more than eleven times the number of players in the game.
        } catch (NumberFormatException e) {
            System.out.println("There is a value that cannot be converted to an integear, please check your csv file.");
            System.exit(0);
        }
        try {
            for (int i = 0; i < records2.get(0).size(); i++) {
                if (Integer.parseInt(records2.get(0).get(i)) < 0) {
                    System.out.println(records2.get(0).get(i) + " is not a valid number for this game, please check your csv file.");
                    System.exit(0);
                }
            }//Create duplicates of the numbers in the given csv file to ensure that their are more than eleven times the number of players in the game.
        } catch (NumberFormatException e) {
            System.out.println("There is a value that cannot be converted to an integear, please check your csv file.");
            System.exit(0);
        }
        while (true) {
            if (blackBagY.getContents().size() < 13 * numPlayer) {
                for (int i = 0; i < records.get(0).size(); i++) {
                    blackBagY.getContents().add(Integer.parseInt(records.get(0).get(i)));
                    blackBagX.getContents().add(Integer.parseInt(records1.get(0).get(i)));
                    blackBagZ.getContents().add(Integer.parseInt(records2.get(0).get(i)));
                }
            } else {
                break;
            }
        }
        //Starts the player threads
        for(int i = 0; i<numPlayer; i++ ) {
            Player player = new Player(i + 1, blackBag, whiteBag, numPlayer);
            try {
                player.start();

            }
            catch (NullPointerException nullPointerException){
                System.out.println("This thread was unable to get an item from th bag and stopped");
            }
        }

    }

    //com.company.PebbleGameApp.Bag class
    //Creates the bag class
    public static class Bag {
        ArrayList<Integer> contents = new ArrayList<>();
        String bagName;

        public Bag(String bagName) {
            this.bagName = bagName;
        }
        //Method moves a pebble from hand to the white bag
        public void discardToBag(int pebble) {
            contents.add(pebble);
        }

        //Method moves a pebble from a black bag to the hand
        public int drawFromBag() {
            try {
                Random random = new Random();

                int i = random.nextInt(contents.size());
                int removedPebble = contents.get(i);
                contents.remove(i);
                return removedPebble;
            } catch (NullPointerException e){
                return 2;
            }
        }

        //Gets the contents of the bag
        public ArrayList<Integer> getContents() {
            return contents;
        }


    }
}
