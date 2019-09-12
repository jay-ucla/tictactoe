//package Game;

import java.io.*;

public class MiniMax
{
	char winner_symbol,loser_symbol;
	char[] original_state;
	int[][] win_ss=new int[8][3];
	
	MiniMax(char c)
	{
		winner_symbol=c;
		if(c=='X')
		loser_symbol='O';
		else
		loser_symbol='X';
		System.out.println("Computer plays :"+winner_symbol+" User plays :"+loser_symbol);
		win_ss[0]=new int[]{1,4,7};
		win_ss[1]=new int[]{3,4,5};
		win_ss[2]=new int[]{0,3,6};
		win_ss[3]=new int[]{0,1,2};
		win_ss[4]=new int[]{0,4,8};
		win_ss[5]=new int[]{2,5,8};
		win_ss[6]=new int[]{2,4,6};
		win_ss[7]=new int[]{6,7,8};
	}
	int minimax(char[] state)
	{
		int[] minimax_scores;
		int[] minimax_indexes;
		minimax_scores=new int[9];
		minimax_indexes=new int[9];
		original_state=java.util.Arrays.copyOf(state,state.length);
		int move, j=0,max;
		for(int i=0;i<9;i++)
		{
			if(state[i]=='#')
			{
				minimax_indexes[j]=i;
				char[] gen_s=java.util.Arrays.copyOf(state,state.length);
				gen_s[i]=winner_symbol;
				//System.out.println("New Avenue : Enter "+winner_symbol+" into "+i);
				//showstate(gen_s);
				int res=WinLoseDraw(gen_s);
				//System.out.println("\nResult is "+res);
				if(res==10)
				{
					minimax_scores[j]=10;
					return minimax_indexes[j];
				}
				
				else if(res==-10)
				{
					minimax_scores[j]=-10;
					return minimax_indexes[j];
				}
				else if(res==0 && finals(gen_s))
				{
					minimax_scores[j]=0;
					return minimax_indexes[j];
				}
				else
					minimax_scores[j]=minimize(gen_s,1);

				//System.out.println("Avenue "+i+" "+minimax_scores[j]+"");
				j++;
			}
		}
		move=minimax_indexes[0];
		max=minimax_scores[0];
		System.out.println("Final result of node "+minimax_indexes[0]+" is "+ minimax_scores[0]);
		for(int i=1;i<j;i++)
		{
			System.out.println("Final result of node "+minimax_indexes[i]+" is "+ minimax_scores[i]);
			if(minimax_scores[i]>=max)
			{
				move=minimax_indexes[i];
				max=minimax_scores[i];
			}
		}
		return move;
	}
	int minimize(char[] s,int depth)
	{
		int min=100,res;
		char[] gen_s;
		for(int i=0;i<9;i++)
		{
			if(s[i]=='#')
			{
				gen_s=java.util.Arrays.copyOf(s,s.length);
				gen_s[i]=loser_symbol;
				//System.out.println("Insert "+loser_symbol+" into "+i);
				//showstate(gen_s);
				res=WinLoseDraw(gen_s);
				//System.out.println("\nResult is "+res);
				if(res==-10)
					return -10;
				else if(res==10)
					return 10;
				else if(res==0 && finals(gen_s))
					return 0;
				else
				{
					//System.out.println("\nEntering depth "+(depth+1));
					res=maximize(gen_s,depth+1);
					//System.out.println("Got back "+res+" from "+(depth+1)+"\nBack to minimize@ "+depth);
					if(res<min)
					min=res;
				}				
			}
		}
		return min;
	}
	int maximize(char[] s,int depth)
	{
		int max=-100,res;
		char[] gen_s;
		for(int i=0;i<9;i++)
		{
			if(s[i]=='#')
			{
				gen_s=java.util.Arrays.copyOf(s,s.length);
				gen_s[i]=winner_symbol;
				//System.out.println("Insert "+winner_symbol+" into "+i);
				//showstate(gen_s);
				res=WinLoseDraw(gen_s);
				//System.out.println("\nResult is "+res);
				if(res==10)
				return 10;
				else if(res==-10)
				return -10;
				else if(res==0 && finals(gen_s))
				return 0;
				else
				{
					//System.out.println("\nEntering depth "+(depth+1));
					res=minimize(gen_s,depth+1);
					//System.out.println("Got back "+res+" from "+(depth+1)+"\nBack to maximize@ "+depth);
					if(res>max)
					max=res;
				}				
			}
		}
		return max;
	}
	int WinLoseDraw(char[] s)
	{
		for(int i=0;i<8;i++)
		{
			if(s[win_ss[i][0]]!='#')
			{	
				if(s[win_ss[i][0]]==s[win_ss[i][1]]&&s[win_ss[i][1]]==s[win_ss[i][2]])	
				{
					if(s[win_ss[i][0]]==winner_symbol)
					return 10;
					else
					return -10;
				}
			}
		}
		return 0;
	}
	boolean finals(char[] s)
	{
		for(int i=0;i<9;i++)
		{
			if(s[i]=='#')
			return false;
		}
		return true;
	}
	void showstate(char[] s)
	{
		System.out.print(""+s[0]+s[1]+s[2]+"\n"+s[3]+s[4]+s[5]+"\n"+s[6]+s[7]+s[8]);
	}	
} 
