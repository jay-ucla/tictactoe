//package Game;

import java.io.*;

public class TicTacToe
{
	public static void main(String[] args)
	{
		new mygameboard().newgame();
	}
}

class mygameboard extends javax.swing.JFrame
{
	public char[] state;
	public int[][] winstate;
	public int totalmoves;
	public char movesymbol;
	public char usersymbol;
	javax.swing.JButton[] tiles;
	MiniMax ai_player;
	void initBoard()
	{
		state=new char[9];
		for(int i=0;i<9;i++)
		{
			state[i]='#';
		}
		totalmoves=0;
		winstate=new int[8][3];
		winstate[0]=new int[]{1,4,7};
		winstate[1]=new int[]{3,4,5};
		winstate[2]=new int[]{0,3,6};
		winstate[3]=new int[]{0,1,2};
		winstate[4]=new int[]{0,4,8};
		winstate[5]=new int[]{2,5,8};
		winstate[6]=new int[]{2,4,6};
		winstate[7]=new int[]{6,7,8};
	}
	void startgame(char C)
	{
		initBoard();
		movesymbol='X';
		usersymbol=C;
		System.out.println("New Game : User Chooses "+usersymbol);
		javax.swing.JPanel p=new javax.swing.JPanel(new java.awt.GridLayout(3,3));
		tiles=new javax.swing.JButton[9];
		for(int i=0;i<9;i++)
		{
			tiles[i]=new javax.swing.JButton(i+"");
			p.add(tiles[i]);
			tiles[i].addActionListener(new newMove());			
		}
		this.add(p);
		this.setVisible(true);
		if(usersymbol=='O')
		{
			ai_player=new MiniMax('X');
			int m=new java.util.Random().nextInt(9);
			tiles[m].doClick();
			changeturn();
		}
		else if(usersymbol=='X')
		{
			ai_player=new MiniMax('O');
		}
	}
	class newMove implements java.awt.event.ActionListener
	{
		public void actionPerformed(java.awt.event.ActionEvent e)
		{
			javax.swing.JButton b=(javax.swing.JButton)e.getSource();
			b.setEnabled(false);
			int cell=Integer.parseInt(b.getText());
			state[cell]=movesymbol;		//changing state
			totalmoves++;
			b.setText(movesymbol+"");
			System.out.println(movesymbol+" clicked on "+cell);
			if(WinLoseDraw()==1)
			{
				javax.swing.JOptionPane.showMessageDialog(null, movesymbol+" wins!","Game Over",javax.swing.JOptionPane.PLAIN_MESSAGE);
				dispose();
				new mygameboard().newgame();
			}
			else if(WinLoseDraw()==0&&totalmoves==9)
			{
				javax.swing.JOptionPane.showMessageDialog(null,"It's a draw!","Game Over",javax.swing.JOptionPane.PLAIN_MESSAGE);
				dispose();
				new mygameboard().newgame();
			}
			//check for wins,loses,draws
			else if(movesymbol==usersymbol)
			{
				changeturn();
				System.out.println("Next move: AI ENGINE");
				//call minimax and run move
				if(totalmoves==1&&state[4]=='#'&&(state[0]==usersymbol||state[2]==usersymbol||state[6]==usersymbol||state[8]==usersymbol))
				tiles[4].doClick();
				else if(totalmoves==1&&state[4]==usersymbol)
				tiles[0].doClick();
				else
				{
					int ai_move=ai_player.minimax(state);
					System.out.println("AI Engine Chooses "+ai_move);
					tiles[ai_move].doClick();
				}
				changeturn();
				System.out.println("Next move: USER");
			}
		}
	}
	int WinLoseDraw()
	{
		for(int i=0;i<8;i++)
		{
			//System.out.println(i+" "+winstate[i][0]+"");
			if(state[winstate[i][0]]!='#')
			{	
				if(state[winstate[i][0]]==state[winstate[i][1]]&&state[winstate[i][1]]==state[winstate[i][2]])	
				return 1;
			}
		}
		return 0;
	}
	void changeturn()
	{
		if(movesymbol=='X')
			movesymbol='O';
		else movesymbol='X';
	}
	void newgame()
	{
		javax.swing.JPanel p=new javax.swing.JPanel(new java.awt.GridLayout(1,2));
		javax.swing.JPanel p1=new javax.swing.JPanel(new java.awt.BorderLayout());
		javax.swing.JLabel temp=new javax.swing.JLabel("<html><br><br><pre>               </pre></html>");
		javax.swing.JLabel l1=new javax.swing.JLabel("<html><br><br><br><pre>                    X starts the game</pre></html>");
		javax.swing.JButton b1=new javax.swing.JButton("Play");
		final javax.swing.JRadioButton r1=new javax.swing.JRadioButton("X");
		final javax.swing.JRadioButton r2=new javax.swing.JRadioButton("O");
		javax.swing.ButtonGroup bg=new javax.swing.ButtonGroup();
		bg.add(r1);
		bg.add(r2);
		p.add(r1);
		p.add(r2);
		p1.add(p,java.awt.BorderLayout.CENTER);
		p1.add(l1,java.awt.BorderLayout.PAGE_START);
		p1.add(temp,java.awt.BorderLayout.LINE_START);
		p1.add(b1,java.awt.BorderLayout.PAGE_END);
		r1.setSelected(true);
		b1.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				if(r1.isSelected())
				new mygameboard().startgame('X');
				else if(r2.isSelected())
				new mygameboard().startgame('O');
				dispose();	
			}
		});
		this.add(p1);
		this.setVisible(true);
	}
	mygameboard()
	{
		this.setTitle("Tic Tac Toe!");
		this.setSize(400,400);
		this.setResizable(false);
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
	}
}
