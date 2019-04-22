import java.util.ArrayList;
import java.util.Scanner;

public class BulgarianSolitaireSimulator 
{
    public static void main(String[] args) 
	{
	     
	      boolean singleStep = false;
	      boolean userConfig = false;

	      for (int i = 0; i < args.length; i++) 
	      {
	         if (args[i].equals("-u")) 
	         {
	            userConfig = true;
	         }
	         else if (args[i].equals("-s")) 
	         {
	            singleStep = true;
	         }
	      }
	      runSolitaireGame(userConfig, singleStep);
	      
	}
	   
	    // <add private static methods here>
	
	

	   public static void runSolitaireGame(boolean userModelOn, boolean singleStepOn) 
	    {
	      Scanner enterToGoOn = new Scanner(System.in);
	      
	      if(userModelOn)
	      {
	       // run the game in user model with single step
	         SolitaireBoard getCards = new SolitaireBoard();
	         Scanner readCards = new Scanner(getCards.configString());
	         int numCards = 0;
	         
	         for(int i=0;readCards.hasNextInt();i++)
	         {
	            numCards = numCards + readCards.nextInt();
	         }
	         
	         SolitaireBoard runUserModel = new SolitaireBoard(userModel(numCards));
             System.out.println("Initial configuration: "+ runUserModel.configString());
             
	         for(int i=1; !runUserModel.isDone(); i++)
             {
	            runUserModel.playRound();
                System.out.println("["+i+"] Current Configuration: "+ runUserModel.configString());
                
	            if(singleStepOn)
	            {
	               System.out.print("<Type return to continue>");
	               enterToGoOn.nextLine();
	            }
             }
	         System.out.println("Done!");
	      }
	      
	      else
	      {
	         SolitaireBoard runRandomModel = new SolitaireBoard();
	         System.out.println("Initial configuration: "+ runRandomModel.configString());
	         
	         for(int i=1; !runRandomModel.isDone(); i++)
             {
                runRandomModel.playRound();
                System.out.println("["+i+"] Current Configuration: "+ runRandomModel.configString());
                
                if(singleStepOn)
                {
                   System.out.print("<Type return to continue>");
                   enterToGoOn.nextLine();
                }
             }
	         System.out.println("Done!");
	      }
	    }
	    
	   
	
	public static ArrayList<Integer> userModel(int numPiles)
	{
	   final int CARD_TOTAL = numPiles;
	   //number of total card expected
	   
	   ArrayList<Integer> cardPiles = new ArrayList<Integer>();
       boolean inputCheck = false;
       String userInputStr = null;
       
	   System.out.println("Number of total card is " + numPiles);
	   System.out.println("You will be entering the initial configuration of the cards "
	         + "(i.e., how many in each pile).");
	   System.out.println("Please enter a space-separated list of positive integers followed "
	         + "by newline:");
       
       // check if the user input is valid
	   for(int i=0; !inputCheck; i++)
	   {
	      String userInput = null;
	      int sum = 0;
	      
	   // user input string
	      Scanner keyBoardInput = new Scanner(System.in);
	      userInput = keyBoardInput.nextLine();
	      
	   //check if the user input has invalid char
	      Scanner theInputString = new Scanner(userInput);
	      int nonZeroPilesCheck = 1;
	      
	      for(int j=0; theInputString.hasNext(); j++)
	      {
	         String usersNum = "";
	         
	         usersNum = theInputString.next();
	         if(usersNum.matches("[0-9]+")&&nonZeroPilesCheck>0)
	         {
	            int switchToInt = 0;
	             
	            switchToInt = Integer.parseInt(usersNum);
	            
	            if(switchToInt<=0)
	            {
	               nonZeroPilesCheck = 0;
	            }
	            
	            sum = sum + switchToInt;
	         }
	         else
	         {
	            sum = 0;
	         }
	         
	      }
	      System.out.println(sum);
	    // check if the user input has the right card total number
	         if(sum!=CARD_TOTAL)
	         {
	            System.out.println("ERROR: Each pile must"
	                + " have at least one card and the total number of cards must be 45");
	            System.out.println("Please enter a space-separated list of positive integers followed "
	                + "by newline:");
	            
	            inputCheck = false;
	         }
	         
	    // pass the check, get the right string
	         else
	         {
	            userInputStr = userInput;
	            inputCheck = true;
	         }
	   }
	   
	   //fill the ArrayList
	   Scanner in = new Scanner(userInputStr);
	   for(int i=0; in.hasNext(); i++)
	   {
	      cardPiles.add(i,Integer.parseInt(in.next()));
	   }
	   
	   return cardPiles;
   }

}
