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
      Scanner scan = new Scanner (System.in);
      Random rand = new Random ();
      
      // initialize all variables
      System.out.print("Enter the number of teams: ");
      int numTeams = scan.nextInt();
      
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
      String[] matchups = new String[numTeams * (numTeams - 1) / 2];
      int matchCount = 0;
      int week = 1;
      
      while (!finished) {
         System.out.println("Weel " + week);
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
