import java.util.Scanner;

public class Main {
    

    /*
     * COMMANDS:
     * 
     *      pos x y -> select the tile based on coordinates
     *      fl  x y -> set flag if the tile is invicible ! removes flag when already the tile have flag.
     */
    public static void main(String[] args)
    {
        Board board= new Board();
        Scanner scan= new Scanner(System.in);
        int itr=0,moves=0;
        do{
            System.out.println("SCORE!:"+board.getScore());
            System.out.println(board);
            System.out.print("command:");
            String command=scan.nextLine();
            String[] commandAry=command.split(" ");
            if(commandAry.length!=3)
            {
                System.out.println("enter valid inputs!");
                continue;
            }
            int x=Integer.parseInt(commandAry[1]);
            int y=Integer.parseInt(commandAry[2]);
            switch(commandAry[0])
            {
                case "pos":
                    moves++;
                    int result;
                    if(itr==0)
                        result=board.arrangeBoard(x,y);
                    else
                        result=board.selectTile(x, y);
                    if(result==-1)
                    {
                        System.out.println(board);
                        System.out.println("GAME OVER!");
                        System.out.println("SCORE!:"+board.getScore());
                        System.out.println("Moves:"+moves);
                        return;
                    }
                    else if(result==2)
                    {
                        System.out.println("You win!");
                        System.out.println("SCORE!:"+board.getScore());
                        System.out.println("MOVES:"+moves);
                        return;
                    }
                    else if(result==0)
                        moves--;
                    break;
                case "fl":
                    board.setFlag(x, y);
                    break;
                default:
                    System.out.println("enter valid command!");
                    break;
                    
            }
            itr++;
           
            


        }while(true);
    }
}
