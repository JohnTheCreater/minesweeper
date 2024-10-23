import java.util.Random;

class Board{
    private int ROW=10;
    private int COL=10;
    private int bombTotalCount=10;
    private char[][] board;
    private int[][] visible;
    private int[][] bombsCoordinates;  //when game is over needed to show where all the bombs positioned
    private int visibleTileCount=0;
    private char bombCharacter='B';

    private String inVisibleDenoter="?";
    private String flagDenoter="##";
    

    public Board()
    {
        board= new char[ROW][COL];
        visible= new int[ROW][COL];
        bombsCoordinates=new int[bombTotalCount][2];
    }

    //runs initially for the first selection
    public void arrangeBoard(int x,int y)
    {

        setBomb(x,y); 
        setNumbers();
        selectTile(x,y); 
    }

    private void setNumbers() {
        for(int i=0;i<ROW;i++)
        { 
            for(int j=0;j<COL;j++)
            {
                if(board[i][j]=='\0')
                {

                    int bombCount=0;
                    for(int l=i-1;l<=i+1;l++)
                    {
                        for(int k=j-1;k<=j+1;k++)
                        {
                            if(l!=i || k!=j)
                            {
                                // System.out.println("hi"+l+k);

                                if(l>=0 && k>=0 && l<ROW && k<COL && board[l][k]==bombCharacter)
                                        bombCount++;
                            }
                        }
                    }
                // if(i-1>-1 && j-1>-1 && board[i-1][j-1]==bombCharacter)
                //     bombCount++;
                // if(i-1>-1 && board[i-1][j]==bombCharacter)
                //     bombCount++;
                // if(i-1>-1 && j+1<COL && board[i-1][j+1]==bombCharacter)
                //     bombCount++;
                // if(j-1>-1 && board[i][j-1]==bombCharacter)
                //     bombCount++;
                // if(j+1<COL && board[i][j+1]==bombCharacter)
                //     bombCount++;
                // if(j-1>-1&& i+1<ROW && board[i+1][j-1]==bombCharacter)
                //     bombCount++;
                // if(i+1<ROW && board[i+1][j]==bombCharacter)
                //     bombCount++;
                // if(i+1<ROW && j+1<COL &&board[i+1][j+1]==bombCharacter)
                //     bombCount++;

                board[i][j]=(char)(bombCount+48);
                }
            }
        }
    }

    //sets bomb except this coordinates(runs initially)
    private void setBomb(int x,int y) {

        Random rand= new Random();
        int xAxis,yAxix,bombsGenerated=0;

        while(bombsGenerated<bombTotalCount)
        {
            do{
                xAxis=rand.nextInt(ROW);
                yAxix=rand.nextInt(COL);

            }while(board[xAxis][yAxix]==bombCharacter|| (xAxis==x && yAxix==y) || isNearToPosition(x,y,xAxis,yAxix));

            board[xAxis][yAxix]=bombCharacter;
            bombsCoordinates[bombsGenerated][0]=xAxis;
            bombsCoordinates[bombsGenerated][1]=yAxix;
            bombsGenerated++;
        } 
    }

    private boolean isNearToPosition(int x, int y,int xAxis,int yAxix) {
        for(int i=x-1;i<=x+1;i++)
        {
            for(int j=y-1;j<=y+1;j++)
            {
                if(i!=x || j!=y)
                {
                    if(i==xAxis && j==yAxix)
                        return true;
                }
            }
        }
        return false;
    }

    //selecting the tile
    public int selectTile(int x,int y)
    {
        if(x<0 || y<0 || x>=ROW || y>=COL)
        {
            System.out.println("please enter valid position!");
            return 0;
        }


        if(visible[x][y]==1 || visible[x][y]==2)
                return 0;
        
        if(board[x][y]==bombCharacter)
            {
                for(int i=0;i<bombTotalCount;i++)
                {
                    visible[bombsCoordinates[i][0]][bombsCoordinates[i][1]]=1;
                }
                return -1;
            }
        expandBoard(x,y);
            System.out.println("visible:"+visibleTileCount);
        if(visibleTileCount+bombTotalCount==ROW*COL)
            return 2;
        return 1;
    }

    private void expandBoard(int x, int y) {
        if(x<0 || y<0 || x>=ROW ||y>=COL)
            return;
        if(visible[x][y]==1)
            return;
        visible[x][y]=1;
        visibleTileCount++;
        if(board[x][y]>='1'&& board[x][y]<='8')
            return;

        for(int i=x-1;i<=x+1;i++)
        {
            for(int j=y-1;j<=y+1;j++)
            {
                if(i!=x || j!=y)
                {
                    expandBoard(i, j);
                }
            }
        }
        // expandBoard(x-1, y-1);
        // expandBoard(x-1, y);
        // expandBoard(x-1, y+1);
        // expandBoard(x, y-1);
        // expandBoard(x, y+1);
        // expandBoard(x+1, y-1);
        // expandBoard(x+1, y);
        // expandBoard(x+1, y+1);
        
    }


    public String toString()
    {
        String str="";
        str+="\t";
        for(int i=0;i<COL;i++)
            str+=i+"\t";
        str+='\n';
        for(int i=0;i<ROW;i++)
        {
            str+=i+"\t";
            for(int j=0;j<COL;j++)
            {
                if(visible[i][j]==1)
                str+=((board[i][j]=='0'?'.':board[i][j])+"\t");
                else if(visible[i][j]==2)
                    str+=(flagDenoter+" \t");
                else
                str+=(inVisibleDenoter+" \t");
            }
            str+='\n';
            // System.out.println("\t---------------------------------------------------------------------------");
        }
        return str;
    }

   

   
//if selected tile is not visible its going to set flag 
// if already have flag! this  removes the flag and set that tile to invisible
    public void setFlag(int x,int y)
    {
        if(x<0 || y<0 || x>=ROW || y>=COL)
        {
            System.out.println("please enter valid position!");
            return;
        }

        if(visible[x][y]==0)
            visible[x][y]=2;  
        else if(visible[x][y]==2)
            visible[x][y]=0;
        
    }



}