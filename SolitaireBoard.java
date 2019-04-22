import java.util.ArrayList;
import java.util.Random;

   /*
	 class SolitaireBoard
	 The board for Bulgarian Solitaire.  You can change what the total number of cards is for the game
	 by changing NUM_FINAL_PILES, below.  Don't change CARD_TOTAL directly, because there are only some values
	 for CARD_TOTAL that result in a game that terminates.
	 (See comments below next to named constant declarations for more details on this.)
   */

   public class SolitaireBoard 
   {
      private static final int NUM_FINAL_PILES = 9;
	  // number of piles in a final configuration
	  // (note: if NUM_FINAL_PILES is 9, then CARD_TOTAL below will be 45)
	   
	  private static final int CARD_TOTAL = NUM_FINAL_PILES * (NUM_FINAL_PILES + 1) / 2;
	  // bulgarian solitaire only terminates if CARD_TOTAL is a triangular number.
	  // see: http://en.wikipedia.org/wiki/Bulgarian_solitaire for more details
	  // the above formula is the closed form for 1 + 2 + 3 + . . . + NUM_FINAL_PILES
	  
      private static final int OVERFLOW_PREVENTER = 1;
	  // this number is prevent out-of-bound error in some boundary cases by longing the partial array by 1.
	  // this one more unit for the partial array can make sure it works normally even in some boundary cases.
	   
	  // Note to students: you may not use an ArrayList.
	   
	   
	  /**
	      Representation invariant:
	      	      
	      1.The total number of cards stays the same as the game begin;
	      2.Card numbers of each pile should be larger than zero;
	      3.The number of the piles should not be larger than 45.
	  */
	   
	  
      private int[] cardPiles;          // contains card piles
	  private int locOfTheLastElement;  // the location of the last card pile.
	  
	 
	  /**
	     Creates a solitaire board with the configuration specified in piles.
	     piles has the number of cards in the first pile, then the number of cards in the second pile, etc.
	     PRE: piles contains a sequence of positive numbers that sum to SolitaireBoard.CARD_TOTAL
	  */
      public SolitaireBoard(ArrayList<Integer> piles) 
	  {
		   
	     cardPiles = new int[CARD_TOTAL+OVERFLOW_PREVENTER];    
		 
		 // initialize the card piles array by adding elements.
		 for(int i=0; i<piles.size(); i++) 
		 {
			cardPiles[i] = piles.get(i);
		 }
		 
		 // mark the last card pile's position.
		 locOfTheLastElement = piles.size()-1;
		 
		 // check the status of the Solitaire Board.
	     assert isValidSolitaireBoard();   
      }
	 
	   
	  /**
	     Creates a solitaire board with a random initial configuration.
	  */
	  public SolitaireBoard() 
	  {
		 Random generateOneRandomPile = new Random();
		 cardPiles = new int[CARD_TOTAL+OVERFLOW_PREVENTER];
		 int cardsRemained = CARD_TOTAL;
		   
		 // fill the card piles with random card pile.
		 for(int i=0; cardsRemained>0; i++) 
		 {
		    cardPiles[i] = generateOneRandomPile.nextInt(cardsRemained)+1;
			cardsRemained = cardsRemained-cardPiles[i];
			locOfTheLastElement = i;
		 }
	     // check the status of the Solitaire Board.
	     assert isValidSolitaireBoard();
	  }
	   
	  
	  /**
	     Plays one round of Bulgarian solitaire.  Updates the configuration according to the rules
	     of Bulgarian solitaire: Takes one card from each pile, and puts them all together in a new pile.
	     The old piles that are left will be in the same relative order as before, 
	     and the new pile will be at the end.
	  */
      public void playRound() 
	  {
		 int numOfNewPile = 0; // used to count the number of card piles
		 int k = 0;     // works like a counter to help in the program.
		  
		 // make sure all positive piles are in the left side of the array 
		 // without changing its order.
		 // subtract each card piles by one.
		 for(int i=0; i<=locOfTheLastElement; i++) 
		 {
			if(cardPiles[i]!=0) 
			{
			   cardPiles[numOfNewPile] = cardPiles[i] - 1;
			   numOfNewPile++;
			}
		 }
		   
		 // create the new piles as the last pile.
		 cardPiles[numOfNewPile] = numOfNewPile;
		  
		 // mark the location of the last element in the array.
		 locOfTheLastElement = numOfNewPile;
		   
		 // make sure all positive elements is on the left end all zeros on
		 // the right side without changing its order.
		 for(int i=0; i<=locOfTheLastElement;i++)
		 {
			if(cardPiles[i]!=0) 
			{
			   cardPiles[k]=cardPiles[i];
			   k++;
			}
		 }
		   
		 // determine the location of the last element.
		 locOfTheLastElement = k-1;
		   
         for(int i=k; i<=CARD_TOTAL; i++) 
		   {
		      cardPiles[i] = 0;
		   }
		   
         // check the status of the Solitaire Board.
		 assert isValidSolitaireBoard();
      }
	   
	   /**
	      Returns true iff the current board is at the end of the game.  That is, there are NUM_FINAL_PILES
	      piles that are of sizes 1, 2, 3, . . . , NUM_FINAL_PILES, in any order.
	   */
	  
      public boolean isDone() 
	  {
         // check if any of the card piles are more than the number of card piles.
	     for(int i=0; i<=locOfTheLastElement; i++) 
		 {
		    if(cardPiles[i]>NUM_FINAL_PILES) 
			{
			   return false;
			}
		 }
		
	     // check if all card piles have different number of cards of each other.
		 for(int i=0; i<locOfTheLastElement; i++) 
		 {
		    for(int j=i+1; j<=locOfTheLastElement; j++) 
			{
			   if(cardPiles[i]==cardPiles[j]) 
			   {
				  return false;
			   }
			}
		 }
		 
		 // check the status of the Solitaire Board.
		 assert isValidSolitaireBoard();
		 return true;   
      }

	   
	  /**
	     Returns current board configuration as a string with the format of
	     a space-separated list of numbers with no leading or trailing spaces.
	     The numbers represent the number of cards in each non-empty pile.
	   */
      public String configString() 
	  {   
		 String sequencesOfPiles = "";
		   
		 // create string to return in the instructed format.
		 for(int i=0; i<locOfTheLastElement; i++) 
		 {
	        sequencesOfPiles = sequencesOfPiles + cardPiles[i]+" ";
		 }
		   
		 sequencesOfPiles = sequencesOfPiles + cardPiles[locOfTheLastElement];
		 
		 // check the status of the Solitaire Board.
		 assert isValidSolitaireBoard();
	     return sequencesOfPiles; 
	       
      }
	   
	   
	  /**
	     Returns true iff the solitaire board data is in a valid state
	     (See representation invariant comment for more details.)
	   */
      private boolean isValidSolitaireBoard() 
	  {
	     int sumOfCards = 0; 
		   
	     // make sure all card piles is not larger than total card number. 
		 if(locOfTheLastElement>=CARD_TOTAL) 
		 {
		    return false;
		 }
		   
		 // make sure all cad piles are positive.
		 for(int i=0; i<=locOfTheLastElement; i++) 
		 {
		    if(cardPiles[i]<=0) 
			   {
			      return false;
			   }
		 }
		   
		 // make sure the total number of cards stays the same.
		 for(int i=0; i<=locOfTheLastElement; i++) 
		 {
		    sumOfCards = sumOfCards + cardPiles[i];
		 }
	      
		 if(sumOfCards!=CARD_TOTAL) 
		    {
			   return false;
		    }
		   
	     return true;
	   }
	   
   }



