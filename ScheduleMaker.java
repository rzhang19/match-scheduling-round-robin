/*
 * Robin Zhang, 2018
 * As part of the Soccer Management Game project
 *
 * Match Scheduling Algorithm, Round Robin Style
 * Many credits to https://en.wikipedia.org/wiki/Round-robin_tournament#Scheduling_algorithm
 *
 */

import java.util.Random;
import java.util.Scanner;

public class ScheduleMaker {
   public static void main (String args[]) {
      // library calls
      Scanner scan = new Scanner (System.in);
      Random rand = new Random ();
      
      // constant values
      final int MAX_NUM_PLAYED = 4;
      final int MIN_TEAMS = 2;
      
      // settings for additional features
      int numPlayed = 1;
      boolean random = true;
      int weightWeek = -1;
      
      int input = 0;
      
      // initialize all variables
      int numTeams = 0;
      
      while (numTeams < MIN_TEAMS) {
         System.out.print("Enter the number of teams: ");
         int numTeams = scan.nextInt();
         
         if (numTeams < MIN_TEAMS) {
            System.out.println("Number of teams must be positive");
         }
      }
      
      while (input != 4) {
         System.out.println("Settings");
         System.out.println("--------");
         System.out.println("1 - edit number of times each Team plays another Team");
         System.out.println("2 - edit whether generation is random");
         System.out.println("3 - edit whether Teams are weighted and add weeks for rivalry matches");
         System.out.println("4 - continue with generating schedule");
         input = scan.nextInt();
         
         if (input < 1 || input > 4) {
            System.out.println("Invalid input, value must be between 1 and 4");
         }
         
         else if (input == 1) {
            System.out.println("Editing the number of times each Team plays another Team");
            int value = numPlayed;
            
            while (value != -1) {
               System.out.println("Current value - " + value);
               System.out.println("Enter the new value of the number of times each Team plays each other");
               System.out.println("Enter -1 to go back to the settings menu");
               System.out.println("Maximum value of " + MAX_NUM_PLAYED);
               value = scan.nextInt();
               
               if (value < -1 || value == 0) {
                  System.out.println("Invalid input, value must be positive");
               }
               
               else if (value > MAX_NUM_PLAYED) {
                  System.out.println("Invalid input, value must be between 1 and " + MAX_NUM_PLAYED + ", inclusive");
               }
               
               else if (value != 1) {
                  numPlayed = value;
               }
            }
         }
         
         else if (input == 2) {
            System.out.println("Editing whether generation is random");
            int value = random ? 1 : 0;
            
            while (value != -1) {
               System.out.println("Current setting is " + (value == 1 ? "random" : "non-random"));
               System.out.println("Enter the new value of randomness");
               System.out.println("Enter 0 to set randomness to false (non-random)");
               System.out.println("Enter 1 to set randomness to true (random)");
               System.out.println("Enter -1 to go back to the settings menu");
               value = scan.nextInt();
               
               if (value < -1 || value > 1) {
                  System.out.println("Invalid input, value must be 1, 0, or -1");
               }
               
               else if (value == 1) {
                  random = true;
               }
               
               else if (value == 0) {
                  random = false;
               }
            }
         }
         
         else if (input == 3) {
            System.out.println("Editing weighted weeks");
            int value = weightWeek;
            
            int finalWeek = numTeams;
            
            if (numTeams % 2 == 0) {
               finalWeek--;
            }
            
            while (value != -1) {
               System.out.println("Current weight week is " + (weightWeek != -1 ? weightWeek : "not set"));
               System.out.println("Enter the new weight week");
               System.out.println("Maximum value is " + finalWeek);
               System.out.println("Enter -1 to go back to the settings menu");
               value = scan.nextInt();
               
               if (value < -1 || value == 0) {
                  System.out.println("Invalid input, value must be positive");
               }
               
               else if (value > finalWeek) {
                  System.out.println("Invalid input, value must be less than or equal to " + finalWeek);
               }
               
               else if (value != -1) {
                  weightWeek = value;
               }
            }
         }
      }
      
      System.out.println();
      
      // account for even number of teams
      int evenNumTeams = numTeams;
      boolean evenTeams = true;
      
      if (evenNumTeams % 2 != 0) {
         evenNumTeams++;
         evenTeams = false;
      }
      
      int[] myTeams = new int[evenNumTeams];
      
      // these are the "IDs" of the Teams
      for (int x = 0; x < evenNumTeams; x++) {
         myTeams[x] = x;
      }
      
      // keeps track of when we reach the first matchup
      boolean finished = false;
      
      // normally, this would be the IDs of the first two teams,
      // so we can keep track of when we finish
      int first = 0;
      int second = 1;
      
      // we're going to store them in a string, but in the real SMG,
      // we use an array of Match objects to store them
      String[] matchups = new String[numTeams * (numTeams - 1) / 2 * numPlayed];
      int matchCount = 0;
      int week = 1;
      
      while (!finished) {
         System.out.println("Week " + week);
         // iterate through Teams array to create matchups
         for (int x = 0; x < evenNumTeams; x += 2) {
            // need a bye for odd number of Teams
            if (!evenTeams && myTeams[x] == evenNumTeams - 1) {
               System.out.println("Team " + myTeams[x + 1] + " receives a bye");
            }
            
            else if (!evenTeams && myTeams[x + 1] == evenNumTeams - 1) {
               System.out.println("Team " + myTeams[x] + " receives a bye");
            }
            
            else {
               matchups[matchCount] = "Team " + myTeams[x] + " plays Team " + myTeams[x + 1];
               System.out.println(matchups[matchCount]);
               matchCount++;
            }
         }
         
         if (evenNumTeams > 2) {
            int temp = myTeams[evenNumTeams - 1];
            
            for (int x = evenNumTeams - 1; x > 2; x -= 2) {
               myTeams[x] = myTeams[x - 2];
            }
            
            myTeams[1] = myTeams[2];
            
            for (int x = 2; x < evenNumTeams - 2; x += 2) {
               myTeams[x] = myTeams[x + 2];
            }
            
            myTeams[evenNumTeams - 2] = temp;
         }
         
         if ((myTeams[0] == first && myTeams[1] == second) || (myTeams[0] == second && myTeams[1] == first)) {
            finished = true;
         }
         
         System.out.println();
         week++;
      }
   }
}
