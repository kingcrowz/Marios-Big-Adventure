import java.applet.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JApplet;
import java.text.DecimalFormat;
import java.util.Random;
@SuppressWarnings("serial")
public class Game extends JApplet implements Runnable, MouseMotionListener,MouseListener,KeyListener{
	
	/** Note
	 * 		level 1 is an incomplete level, without enemies and poor physics, do not enable level 1 unless you want to view the game in Alpha testing phases.
	 * 			Enabling ALPHA PHASE : Change the variable "int level = 2;" to "int level = 1;" and change "int MarioY=428;" to "int MarioY=180;"
	 * 			Enabling INVULNERABILITY : Change the variable "boolean StarPower=false;" to "boolean StarPower=true;"
	 * 			Disability SOUND : Change the variable "int count = 0;" to "int count = 1;"
	 * 		level 2 has a few minor glitches in terms on boundaries and the ground	
	 * 			But do not fret, it just adds to the challenge of the game
	 * 
	 * HINT **there are three secrets hidden within the game, can you find them all? **You will know its a secret immediately when you see it**
	 */ 
	
	String Coin             = "File:/Network/Servers/student10.eccrsd.us/Homes/zacharyzaleski/Desktop/Mario's Big Adventure/Sounds/Coin.wav";	
	String CourseCleared    = "File:/Network/Servers/student10.eccrsd.us/Homes/zacharyzaleski/Desktop/Mario's Big Adventure/Sounds/CourseCleared.wav";
	String GameOver         = "File:/Network/Servers/student10.eccrsd.us/Homes/zacharyzaleski/Desktop/Mario's Big Adventure/Sounds/GameOver.wav";
	String JumpSound        = "File:/Network/Servers/student10.eccrsd.us/Homes/zacharyzaleski/Desktop/Mario's Big Adventure/Sounds/jumpsound.wav";
	String title            = "File:/Network/Servers/student10.eccrsd.us/Homes/zacharyzaleski/Desktop/Mario's Big Adventure/Sounds/Super Mario Bros.wav";
	
	Image brickRemover;
	Image CoinRemover;
	Image DDD;
	Image EnemyRemover;
	Image Mushroom;
	Image Star;
	Image Title;
	Image W1L1;
	Image W1L2;
	Image WalkingMario;	
	
	int coinsObtained=0;
	int count = 0;
	int endlvl = 1;
	int Health=1;
	int invincibilityframe=0;
	int jumpcounter;
	int leftcount=0;
	int level = 2;
	int lives = 2;
	int MapX=0;
	int MarioY=428;
	int rightcount=0;
	int starCount=0;
	int StarsRemaining = 1;
	
	boolean climb=false;
	boolean DontDoDrugs=false;
	boolean falling=false;	
	boolean GameRunning=true;
	boolean grounded=true;
	boolean hasStarted=false;
	boolean inBounds=true;
	boolean jump=false;	
	boolean left=false;
	boolean l2Coins[];
	boolean onScreen=true;
	boolean pipes=false;
	boolean pit=false;
	boolean PlayAgain=true;	
	boolean right=false;
	boolean rising=false;
	boolean StarOnScreen=false;
	boolean StarPower=false;
	
	Random r;
	Thread t;
	
//Koopa
	boolean KoopasAlive[];
	boolean KoopaChangeDirection[];
	boolean KoopasOnScreen[];
	int KoopaDefaultX[];
	int KoopaDefaultY[];
	int KoopaWalkingCounter[];
	int KoopasX[];
	int KoopasY[];
	Image Koopa[];
	
//Goomba
	boolean GoombasAlive[];
	boolean GoombaChangeDirection[];
	boolean GoombasOnScreen[];
	int GoombaDefaultX[];
	int GoombaDefaultY[];
	int GoombaWalkingCounter[];
	int GoombasX[];
	int GoombasY[];
	Image Goomba[];
	
//Tube Chomp
	boolean TubeChompsAlive[];
	boolean TubeChompsOnScreen[];
	boolean TubeChompsSurfaced[];	
	int TubeChompDefaultX[];
	int TubeChompDefaultY[];
	int TubeChompWalkingCounter[];
	int TubeChompsX[];
	int TubeChompsY[];
	Image TubeChomp[];
		
	public Game(){
		r=new Random();
		t=new Thread(this);
		t.start();
	}
	public void init(){
		l2Coins = new boolean[60];
		for(int count=0; count<60; count++){
			l2Coins[count] = true;
		}
//KOOPA
		KoopasAlive = new boolean[7];
		KoopasOnScreen = new boolean[7];
		Koopa = new Image[7];
		KoopaWalkingCounter = new int[7];
		KoopaChangeDirection = new boolean[7];
		KoopaDefaultY = new int[7];
		KoopaDefaultX = new int[7];
		KoopasY = new int[7];
		KoopasX = new int[7];
		for(int count=0; count<=6; count++){
			KoopasAlive[count] = true;
			KoopasOnScreen[count] = false;
			Koopa[count] = getImage(getCodeBase(), "KoopaLeft1.png");
			KoopaWalkingCounter[count] = 0;
			KoopaChangeDirection[count] = false;
			KoopaDefaultY[count]=188;
		}
				KoopaDefaultX[0] = 590; 
					KoopaDefaultY[0] = 428;
				KoopaDefaultX[1] = 2400;
				KoopaDefaultX[2] = 2600;
				KoopaDefaultX[3] = 2800;
				KoopaDefaultX[4] = 3000;
				KoopaDefaultX[5] = 2200;
				KoopaDefaultX[6] = 2100;
///GOOMBA
		GoombasAlive = new boolean[14];
		GoombasOnScreen = new boolean[14];
		Goomba = new Image[14];
		GoombaWalkingCounter = new int[14];
		GoombaChangeDirection = new boolean[14];
		GoombaDefaultX = new int[14];
		GoombaDefaultY = new int[14];
		GoombasX = new int[14];
		GoombasY = new int[14];
		for(int count=0; count<=13; count++)
		{
			GoombasAlive[count] = true;
			GoombasOnScreen[count] = false;
			Goomba[count] = getImage(getCodeBase(),"GoombaLeft1.png");
			GoombaWalkingCounter[count] = 0;
			GoombaChangeDirection[count] = false;
			GoombaDefaultY[count] = 428;
		}
					GoombaDefaultX[0] = 450;
					GoombaDefaultX[1] = 878;
					GoombaDefaultX[2] = 1355;
						GoombaDefaultY[2] = 368;
					GoombaDefaultX[3] = 1307;
						GoombaDefaultY[3] = 368;
					GoombaDefaultX[4] = 1565;
					GoombaDefaultX[5] = 1630;
					GoombaDefaultX[6] = 1800;
					GoombaDefaultX[7] = 1900;
					GoombaDefaultX[8] = 1965;
					GoombaDefaultX[9] = 2450;
					GoombaDefaultX[10] = 2550;
					GoombaDefaultX[11] = 2650;
					GoombaDefaultX[12] = 2750;
///PIRAHNA PLANT
		TubeChompsAlive = new boolean[5];
		TubeChompsOnScreen = new boolean[5];
		TubeChomp = new Image[5];
		TubeChompDefaultX = new int[4];
		TubeChompDefaultY = new int[4];
		TubeChompsX = new int[4];
		TubeChompsY = new int[4];
		TubeChompWalkingCounter = new int[4];
		TubeChompsSurfaced = new boolean[4];
		for(int count=0; count<4; count++)
		{
			TubeChompsAlive[count] = true;
			TubeChompsOnScreen[count] = false;
			TubeChomp[count] = getImage(getCodeBase(), "PirahnaPlant.png");
			TubeChompsSurfaced[count] = true;
		}
		TubeChompWalkingCounter[0] = 12;
		TubeChompWalkingCounter[1] = 24;
		TubeChompWalkingCounter[2] = 48;
		TubeChompWalkingCounter[3] = 36;
			TubeChompDefaultX[0] = 1076;
				TubeChompDefaultY[0] = 398;
			TubeChompDefaultX[1] = 524;
				TubeChompDefaultY[1] = 383;
			TubeChompDefaultX[2] = 617;
				TubeChompDefaultY[2] = 365;
			TubeChompDefaultX[3] = 917;
				TubeChompDefaultY[3] = 383;
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		setVisible(true);
		setSize(1000, 210);		
		brickRemover = getImage(getCodeBase(), "BrickRemover.png");
		CoinRemover = getImage(getCodeBase(),"CoinRemover.png");
		WalkingMario = getImage(getCodeBase(),"MarioWalkingSprite4.png");
		Title = getImage(getCodeBase(),"MBATitleScreen.png");
		W1L1 = getImage(getCodeBase(),"MBA1-1.png");
		W1L2 = getImage(getCodeBase(),"MBA1-2.png");
		EnemyRemover = getImage(getCodeBase(),"EnemyRemover.png");
		Star = getImage(getCodeBase(), "Star.png");
		Mushroom = getImage(getCodeBase(), "Mushroom.png");
		DDD = getImage(getCodeBase(), "DontDoDrugs.png");
	}
	public void paint(Graphics g)
	{
		if (hasStarted)
		{
			if (inBounds && Health>0)
			{
				if(count==0)
				{
					this.loopSound(title);
					count=1;
				}
				super.paint(g);
				this.level2EnemyBounds();
				g.drawImage(W1L1,  MapX, 0, this);
				if(level==2)
				{
					W1L2 = getImage(getCodeBase(), "MBA1-3.png");
					W1L1 = null;
					g.drawImage(W1L2,  MapX, 0, this);
					this.level2RemoveEnemies(g);
					this.Level2EnemyMovements(g);
					this.level2RemoveBricks(g);
					this.Star(g);
					this.level2Coins(g);
					g.drawImage(Mushroom, MapX+300, 428, this);
					if(MapX<=-90 && MapX>=-120 && MarioY>418)
						DontDoDrugs=true;
				}
				if(MapX>-3098 || MarioY<200)
					g.drawImage(WalkingMario, 200, MarioY, this);
				if(level==3){
					W1L2 = getImage(getCodeBase(), "MariosBigAdventureEndScreen.png");
					if(coinsObtained==40)
						W1L2 = getImage(getCodeBase(), "RubertTul.jpg");
					g.drawImage(W1L2,  MapX, 0, this);
				}
				System.out.println(MapX+"                    "+MarioY+"                    "+coinsObtained);
				if(MapX>=210)
					Health=0;
				if(left)
				{
					if((MarioY>200 && MapX<=-2991))
						MapX-=3;
					MapX=MapX+3;
					leftcount++;
					if(leftcount>=12)
						leftcount=0;
					if(leftcount>=0 && leftcount<3)
						WalkingMario = getImage(getCodeBase(), "MarioWalkingSprite1.png");
					if(leftcount>=3 && leftcount<6)
						WalkingMario = getImage(getCodeBase(), "MarioWalkingSprite2.png");
					if(leftcount>=6 && leftcount<9)
						WalkingMario = getImage(getCodeBase(), "MarioWalkingSprite3.png");
					if(leftcount>=9)
						WalkingMario = getImage(getCodeBase(), "MarioWalkingSprite2.png");
				}
				if(right)
				{
					if((MarioY>200 && MapX<=-2991))
						MapX+=3;
					MapX=MapX-3;
					rightcount++;
					if(rightcount>=12)
						rightcount=0;
					if(rightcount>=0 && rightcount<3)
						WalkingMario = getImage(getCodeBase(), "MarioWalkingSprite4.png");
					if(rightcount>=3 && rightcount<6)
						WalkingMario = getImage(getCodeBase(), "MarioWalkingSprite5.png");
					if(rightcount>=6 && rightcount<9)
						WalkingMario = getImage(getCodeBase(), "MarioWalkingSprite6.png");
					if(rightcount>=9)
						WalkingMario = getImage(getCodeBase(), "MarioWalkingSprite5.png");
				}
				if(level==1)
					this.level1();
				if(level==2)
				{
					setSize(1000, 480);
					this.level2Jumping();
					this.level2Objects();
				}
				if(DontDoDrugs)
					g.drawImage(DDD, 0, 0, this);
				if(GameRunning==false){
				}
			}
			else{				
				GameRunning=false;
			}
		}
		else{
			// Game has not started yet.
			g.drawImage(Title, 0, 0, this);
		}
	}	
	public void level1(){
		if((!jump && MapX<-1170 && MapX>-1215 && MarioY>=180) || (MarioY>180 && MarioY<405 && !jump) || (!jump && MapX<-900 && MapX>-930 && MarioY>=180 && MarioY<390))
		{
			pit=true;
			MarioY+=3;
			setSize(1000, 425);
			if(MarioY>335 && (MapX<-935 || MapX>-685))
				this.Reset();
			if(MarioY>=400 && MarioY<=405)
			{
				MarioY-=3;
				if(right && MapX<-885)
				{
					MapX=-2420;
					MarioY=151;
					pit=false;
				}
				else if(MapX>-678 && (MarioY>=400 && MarioY<=405))
					MarioY+=3;
				else
				{
					MarioY-=3;
				}
			}
		}
		if(!jump && MapX<-756 && MapX>-867 && MarioY>357 && MarioY<362)
		{ 
			MarioY-=3;
		}
		if(jump)
		{
			MarioY-=3;
			if(
/* Level 1 */	(MarioY<=110 && MapX>-2151) || (MarioY>250 && MarioY<320) || (MarioY<=90 && MapX<=-2151 && MapX>-2700) || (MarioY<=45 && MapX<=-2700)
/* Level 2 */	)
				jump=false;
		}
		if(!jump && (MarioY<180 || MarioY>390) && !pipes)
			MarioY+=3;
		if(MarioY>180 && MarioY<=183 && MapX<-1900)
			MarioY=180;
		if(pit && MarioY>240)
		{
			if(jump)
				MarioY=MarioY-=3;
			if(MarioY<=240)
				jump=false;
		}			
		if(MapX>-275 && MapX<-228)
		{	
			pipes=false;
			if(MarioY<=150 && MarioY>=148)
			{
				MarioY-=3;
				jump=false;
				pipes=true;
			}
			if(MarioY>155)
			{
				pipes=false;
				
				if(right)
				{
					MapX+=3;
					right=false;
				}
				if(left)
				{
					MapX-=3;
					left=false;
				}
				
			}
		}
		else if(MapX<-390 && MapX>-438)
		{
			if(MarioY>=133 && MarioY<=137)
			{
				MarioY-=3;
				jump=false;
				pipes=true;
			}
			if(MarioY>137)
			{
				pipes=false;
				
				if(right)
				{
					MapX+=3;
					right=false;
				}
				if(left)
				{
					MapX-=3;
					left=false;
				}
				
			}
		}
		else if(MapX<-518 && MapX>-565)
		{
			if(MarioY>=122 && MarioY<=125)
			{
				MarioY-=3;
				jump=false;
				pipes=true;
			}
			if(MarioY>125)
			{
				pipes=false;
				
				if(right)
				{
					MapX+=3;
					right=false;
				}
				if(left)
				{
					MapX-=3;
					left=false;
				}
				
			}
		}
		else if(MapX<-693 && MapX>-740 && MarioY<350)
		{	
			pipes=false;
			pit = true;
			if(MarioY>=122 && MarioY<=125)
			{
				MarioY-=3;
				jump=false;
				pipes=true;
			}
			if(MarioY>125)
			{
				pipes=false;
				
				if(right)
				{
					MapX+=3;
					right=false;
				}
				if(left)
				{
					MapX-=3;
					left=false;
				}
				
			}
		}
		if(MapX<=-2400 && MapX>=-2440)
		{
			if(MarioY>=151 && MarioY<=154)
			{
				MarioY-=3;
				MarioY=151;
				pipes=true;
			}
			else if(MarioY>154)
			{
				pipes=false;
				if(right)
				{
					MapX+=3;
					right=false;
				}
				if(left)
				{
					MapX-=3;
					left=false;
				}
			}
			if(MarioY<151 && !jump)
				MarioY+=3;
		}
		else
		{
			pipes=false;
			pit = false;
		}
		if(MapX>-60 && MapX<-40)
		{
			if(MarioY>130 && MarioY<150)
				jump=false;
			if(MarioY<130)
			{
				jump=false;
				MarioY=120;
			}
		}
		else if(MapX>-200 && MapX<-100)
		{
			if(MarioY>130 && MarioY<150)
				jump=false;
			if(MarioY<130)
			{
				jump=false;
				MarioY=120;
			}
		}
		else if(MapX>=-1073 && MapX<=-1020)
		{
			if(MarioY>=130 && MarioY<=150)
			{
				jump=false;
			}
			if(MarioY<130)
			{
				jump=false;
				MarioY=120;
			}
		}
		else if(MapX>=-1317 && MapX<=-1287)
		{
			if(MarioY>=130 && MarioY<=150)
			{
				jump=false;
			}
			if(MarioY==126)
			{
				jump=false;
//				MarioY=120;
				MarioY-=3;
			}
		}
		else if(MapX>=-1428 && MapX<=-1383)
		{
			if(MarioY>=130 && MarioY<=150)
			{
				jump=false;
			}
			if(MarioY==123)
			{
				jump=false;
				MarioY-=3;
			}
		}
		else if(MapX>=-1506 && MapX<=-1476)
		{
			if(MarioY>=130 && MarioY<=150)
			{
				jump=false;
			}
			if(MarioY==123)
			{
				jump=false;
				MarioY-=3;
			}
		}
		else if(MapX>=-1554 && MapX<-1527)
		{
			if(MarioY>=130 && MarioY<=150)
			{
				jump=false;
			}
			if(MarioY==123)
			{
				jump=false;
				MarioY-=3;
			}
		}
		else if(MapX>=-1602 && MapX<=-1575)
		{
			if(MarioY>=130 && MarioY<=150)
			{
				jump=false;
			}
			if(MarioY==123)
			{
				jump=false;
				MarioY-=3;
			}
		}
		else if(MapX>=-1698 && MapX<=-1668)
		{
			if(MarioY>=130 && MarioY<=150)
			{
				jump=false;
			}
			if(MarioY==123)
			{
				jump=false;
				MarioY-=3;
			}
		}
		else if(MapX>=-1893 && MapX<=-1848)
		{
			if(MarioY>=130 && MarioY<=150)
			{
				jump=false;
			}
			if(MarioY==123)
			{
				jump=false;
				MarioY-=3;
			}
		}
		else if(MapX<=-1926 && MapX>=-2000)
		{
			if(MarioY>=170 && right)
			{
				right=false;
				MapX+=3;
			}
			if(MapX<=-1926 && MapX>=-1941)
			{
				if(MarioY>166 && right)
				{
					right=false;
					MapX+=3;
				}
				if(MarioY>=164 && MarioY<=167)
				{
					if(MarioY>165)
						MarioY=162;
					else
						MarioY-=3;
				}
				if(MarioY>165)
					MarioY=162;
			}
			if(MapX<=-1942 && MapX>=-1959)
			{
				if(MarioY>150 && right)
				{
					right=false;
					MapX+=3;
				}
				if(MarioY>=149 && MarioY<=152)
				{
					if(MarioY>150)
						MarioY=147;
					else
						MarioY-=3;
				}
				if(MarioY>150)
					MarioY=147;
			}
			if(MapX<=-1960 && MapX>=-1975)
			{
				if(MarioY>137 && right)
				{
					right=false;
					MapX+=3;
				}
				if(MarioY>=136 && MarioY<=139)
				{
					if(MarioY>137)
						MarioY=134;
					else
						MarioY-=3;
				}
				if(MarioY>137)
					MarioY=134;
			}
			if(MapX<=-1976 && MapX>=-2000)
			{
				if(MarioY>125 && right)
				{
					right=false;
					MapX+=3;
				}
				if(MarioY>=124 && MarioY<=127)
				{
					if(MarioY>125)
						MarioY=122;
					else
						MarioY-=3;
				}
				if(MarioY>125)
					MarioY=122;
			}
		}
		if(MapX<-2000 && MapX>=-2025)
		{
			if(MapX>-2004 && left && MarioY>127)
			{
				left=false;
				MapX-=3;
			}
			if(MapX<-2022 && right && MarioY>127)
			{
				right=false;
				MapX+=3;
			}
		}
		if(MapX<=-2026 && MapX>=-2116)
		{
			if(MapX<=-2026 && MapX>=-2050)
			{
				if(MarioY>125 && left)
				{
					left=false;
					MapX-=3;
				}
				if(MarioY>=124 && MarioY<=127)
				{
					if(MarioY>125)
						MarioY=122;
					else
						MarioY-=3;
				}
				if(MarioY>125)
					MarioY=122;
			}
			if(MapX<=-2051 && MapX>=-2069)
			{
				if(MarioY>137 && left)
				{
					left=false;
					MapX-=3;
				}
				if(MarioY>=136 && MarioY<=139)
				{
					if(MarioY>137)
						MarioY=134;
					else
						MarioY-=3;
				}
				if(MarioY>137)
					MarioY=134;
			}
			if(MapX<=-2070 && MapX>=-2082)
			{
				if(MarioY>150 && left)
				{
					left=false;
					MapX-=3;
				}
				if(MarioY>=149 && MarioY<=152)
				{
					if(MarioY>150)
						MarioY=147;
					else
						MarioY-=3;
				}
				if(MarioY>150)
					MarioY=147;
			}
			if(MapX<=-2083 && MapX>=-2095)
			{
				if(MarioY>170 && left)
				{
					left=false;
					MapX-=3;
				}
				if(MarioY>=169 && MarioY<=172)
				{
					if(MarioY>170)
						MarioY=167;
					else
						MarioY-=3;
				}
				if(MarioY>170)
					MarioY-=3;
			}
			if(MapX<=-2096 && MapX>=-2100 && left && MarioY>170)
			{
				left=false;
				MapX-=3;
			}
		}
		
		
		
		
		else if(MapX<=-2151 && MapX>=-2241)
		{
			if(MarioY>=170 && right)
			{
				right=false;
				MapX+=3;
			}
			if(MapX<=-2151 && MapX>=-2169)
			{
				if(MarioY>166 && right)
				{
					right=false;
					MapX+=3;
				}
				if(MarioY>=164 && MarioY<=167)
				{
					if(MarioY>165)
						MarioY=162;
					else
						MarioY-=3;
				}
				if(MarioY>165)
					MarioY=162;
			}
			if(MapX<=-2170 && MapX>=-2185)
			{
				if(MarioY>150 && right)
				{
					right=false;
					MapX+=3;
				}
				if(MarioY>=149 && MarioY<=152)
				{
					if(MarioY>150)
						MarioY=147;
					else
						MarioY-=3;
				}
				if(MarioY>150)
					MarioY=147;
			}
			if(MapX<=-2186 && MapX>=-2200)
			{
				if(MarioY>137 && right)
				{
					right=false;
					MapX+=3;
				}
				if(MarioY>=136 && MarioY<=139)
				{
					if(MarioY>137)
						MarioY=134;
					else
						MarioY-=3;
				}
				if(MarioY>137)
					MarioY=134;
			}
			if(MapX<=-2201 && MapX>=-2241)
			{
				if(MarioY>125 && right)
				{
					right=false;
					MapX+=3;
				}
				if(MarioY>=124 && MarioY<=127)
				{
					if(MarioY>125)
						MarioY=122;
					else
						MarioY-=3;
				}
				if(MarioY>125)
					MarioY=122;
			}
		}
		if(MapX<-2241 && MapX>=-2266)
		{
			if(MarioY>170)
				this.Reset();
			if(MapX>-2245 && left && MarioY>127)
			{
				left=false;
				MapX-=3;
			}
			if(MapX<-2263 && right && MarioY>127)
			{
				right=false;
				MapX+=3;
			}
		}
		if(MapX<=-2267 && MapX>=-2341)
		{
			if(MapX<=-2267 && MapX>=-2293)
			{
				if(MarioY>125 && left)
				{
					left=false;
					MapX-=3;
				}
				if(MarioY>=124 && MarioY<=127)
				{
					if(MarioY>125)
						MarioY=122;
					else
						MarioY-=3;
				}
				if(MarioY>125)
					MarioY=122;
			}
			if(MapX<=-2294 && MapX>=-2307)
			{
				if(MarioY>137 && left)
				{
					left=false;
					MapX-=3;
				}
				if(MarioY>=136 && MarioY<=139)
				{
					if(MarioY>137)
						MarioY=134;
					else
						MarioY-=3;
				}
				if(MarioY>137)
					MarioY=134;
			}
			if(MapX<=-2308 && MapX>=-2322)
			{
				if(MarioY>150 && left)
				{
					left=false;
					MapX-=3;
				}
				if(MarioY>=149 && MarioY<=152)
				{
					if(MarioY>150)
						MarioY=147;
					else
						MarioY-=3;
				}
				if(MarioY>150)
					MarioY=147;
			}
			if(MapX<=-2323 && MapX>=-2337)
			{
				if(MarioY>170 && left)
				{
					left=false;
					MapX-=3;
				}
				if(MarioY>=169 && MarioY<=172)
				{
					if(MarioY>170)
						MarioY=167;
					else
						MarioY-=3;
				}
				if(MarioY>170)
					MarioY-=3;
			}
			if(MapX<=-2338 && MapX>=-2341 && left && MarioY>170)
			{
				left=false;
				MapX-=3;
			}
			
		}
		
		if(MapX<=-2645 && MapX>=-2685)
		{;
			if(MarioY>=151 && MarioY<=154)
			{
				MarioY-=3;
				MarioY=151;
				pipes=true;
			}
			else if(MarioY>154)
			{
				pipes=false;
				if(right)
				{
					MapX+=3;
					right=false;
				}
				if(left)
				{
					MapX-=3;
					left=false;
				}
			}
			if(MarioY<151 && !jump)
				MarioY+=3;
		}
		if(MapX<=-2686 && MapX>=-2740)
		{
			if(MarioY>=170 && right)
			{
				right=false;
				MapX+=3;
			}
			if(MapX<=-2686 && MapX>=-2696)
			{
				if(MarioY>166 && right)
				{
					right=false;
					MapX+=3;
				}
				if(MarioY>=164 && MarioY<=167)
				{
					if(MarioY>165)
						MarioY=162;
					else
						MarioY-=3;
				}
				if(MarioY>165)
					MarioY=162;
			}
			if(MapX<=-2697 && MapX>=-2708)
			{
				if(MarioY>150 && right)
				{
					right=false;
					MapX+=3;
				}
				if(MarioY>=149 && MarioY<=152)
				{
					if(MarioY>150)
						MarioY=147;
					else
						MarioY-=3;
				}
				if(MarioY>150)
					MarioY=147;
			}
			if(MapX<=-2709 && MapX>=-2735)
			{
				if(MarioY>137 && right)
				{
					right=false;
					MapX+=3;
				}
				if(MarioY>=136 && MarioY<=139)
				{
					if(MarioY>137)
						MarioY=134;
					else
						MarioY-=3;
				}
				if(MarioY>137)
					MarioY=134;
			}
			if(MapX<=-2736 && MapX>=2740 && left && MarioY>170)
			{
				left=false;
				MapX-=3;
			}
		}
		
		
		if(MapX<=-2757)
		{
			if(endlvl==1)
			{
				this.stopSound(title);
				this.playSound(CourseCleared);
			}
			endlvl++;
			if(endlvl==406)
			{
				MarioY=428;
				level++;
				endlvl=1;
				MapX=0;
				leftcount=0;
				rightcount=0;
				Health=1;
				left=false;
				right=false;
				jump=false;
				grounded=true;
			}
		}
	
	}
	public void level2Jumping()
	{
		if(!jump && jumpcounter==0)
		{
			rising=false;
			grounded=true;
			falling=false;
		}
		else if(!jump && grounded)
			jumpcounter=0;
		else if(jump && jumpcounter==0)
			rising=true;
		if(rising && jump)
		{
			jumpcounter++;
			MarioY-=5;
			grounded=false;
			if(MarioY>=368  && MarioY<=383 && MapX<=-1860  && MapX>=-1880 && StarsRemaining>0)
			{
				StarOnScreen=true;
				StarsRemaining--;
			}
		}
		if(jumpcounter==20 || (jumpcounter>=1 && !jump))
		{
			rising=false;
			falling=true;
		}
		if(falling && !grounded)
		{
			jumpcounter--;
			MarioY+=5;
		}
		
		
	}
	public void level2Objects()
	{
		if(MarioY<=188)
		{
			if(MapX<=-1809 && MapX>=-3165)
			{
				if(MarioY==188)
					grounded=true;
				if(MarioY!=188 && MarioY!=98 && MarioY!=88 && jumpcounter==0 && !climb)
					MarioY+=5;
				if(MapX<=-2632 && MapX>=-2653)
				{
					if(MapX<=-2632 && MapX>-2635 && MarioY>100 && MarioY<130 && right)
						MapX+=3;
					if(MapX<-2650 && MapX>=-2653 && MarioY>100 && MarioY<130 && left)
						MapX-=3;
					if(rising && MapX<=-2635 && MapX>=-2650 && MarioY>129 && MarioY<139)
					{
						rising=false;
						falling=true;
					}
					if(MapX<=-2635 && MapX>=-2650 && MarioY==98)
						grounded=true;
					
				}
				if(MapX<=-2797 && MapX>=-2823)
				{
					if(MapX<=-2797 && MapX>-2800 && MarioY>100 && MarioY<130 && right)
						MapX+=3;
					if(MapX<-2820 && MapX>=-2823 && MarioY>100 && MarioY<130 && left)
						MapX-=3;
					if(rising && MapX<=-2800 && MapX>=-2820 && MarioY>129 && MarioY<139)
					{
						rising=false;
						falling=true;
					}
					if(MapX<=-2800 && MapX>=-2820 && MarioY==98)
						grounded=true;
					
				}
				if((MapX>-2635 || MapX<-2650 && MapX>-2800 || MapX<-2820) && MarioY==98 && !rising)
						MarioY+=5;
				if(MapX<=-2895 && MapX>=-2948)
				{
					if(MapX<=-2895 && MapX>-2898 && MarioY>90 && MarioY<110 && right)
						MapX+=3;
					if(MapX<-2945 && MapX>=-2948 && MarioY>90 && MarioY<110 && left)
						MapX-=3;
					if(MapX<=-2898 && MapX>=-2945 && MarioY==88)
						grounded=true;
				}
				if((MapX<=-2898 && MapX>=-2948) && MarioY==88 && !rising)
				{
					MarioY-=5;
					System.out.println("Yep");
				}
				if(((MapX<=-2880 && MapX>=-2898) || (MapX<=-2948 && MapX>=-2965)) && MarioY<100)
					MarioY+=5;
			}
			else if(MapX>-1809)
				MarioY+=10;
			else if(MapX<-3165 && MarioY==188)
			{
				MapX=-2400;
				MarioY+=10;
			}
		}
		else if(MapX<-44 && MapX>-66)
		{
			if(MarioY==368 && MapX<-47 && MapX>-63)
				grounded=true;
			else if(MarioY<368 && jumpcounter==0)
			{
				MarioY+=5;
			}
			else if(MarioY>375 && MarioY<=395 && rising)
			{
				rising=false;
				falling=true;
			}
			if(MarioY>368 && MarioY<=395 && MapX<=-45 && MapX>=-47 && right)
				MapX+=3;
			if(MarioY>368 && MarioY<=395 && MapX<=-63 && MapX>=-65 && left)
				MapX-=3;
		}
		else if(MapX<=-90 && MapX>=-110)
		{
			if(MarioY==348)
				grounded=true;
			else if(MarioY>370 && MarioY<=380 && rising)
			{
				rising=false;
				falling=true;
			}
			if(MarioY>348 && MarioY<=368 && MapX<=-45 && MapX>=-47 && right)
				MapX+=3;
			if(MarioY>348 && MarioY<=368 && MapX<=-63 && MapX>=-65 && left)
				MapX-=3;
		}
		else if(MapX<=-132 && MapX>=-160)
		{
			if(MarioY<=350 && MarioY>=346)
				grounded=true;
			else if(MarioY>355 && MarioY<=375 && rising)
			{
				rising=false;
				falling=true;
			}
		}
		else if(MapX<=-197 && MapX>=-199 && right && MarioY>=370 && MarioY<=390)
			MapX+=3;
		else if(MapX<=-200 && MapX>=-255)
		{
			if(MarioY==368)
			{
				grounded=true;
			}
			if((MarioY<366 && jumpcounter==0) || (MarioY<428 && MarioY>375 && jumpcounter==0))
			{
				MarioY+=5;
			}
			if(MarioY>375 && MarioY<=395 && rising)
			{
				rising=false;
				falling=true;
			}
		}
		else if(MapX<=-256 && MapX>=-258 && left && MarioY>=370 && MarioY<=390)
			MapX-=3;
		else if(MapX<=-291 && MapX>=-295)
		{
			if(MarioY>383 && right)
			{
				MapX+=3;
			}
			if(MarioY<428 && jumpcounter==0)
				MarioY+=5;		
		}
		else if(MapX<=-294 && MapX>=-334)
		{
			if(MarioY==383)
				grounded=true;
			else if(MarioY<383 && jumpcounter==0)
				MarioY+=5;
		}
		else if(MapX<=-335 && MapX>=-337)
		{
			if(MarioY>383 && left)
			{
				MapX-=3;
			}
			if(MarioY<428 && jumpcounter==0)
				MarioY+=5;
		}
		else if(MapX<=-387 && MapX>=-389)
		{
			if(MarioY>363 && right)
			{
				MapX+=3;
			}
			if(MarioY<428 && jumpcounter==0)
				MarioY+=5;
//			System.out.println(jumpcounter);
		}
		else if(MapX<=-390 && MapX>=-430)
		{
			if(MarioY==363)
				grounded=true;
			else if(MarioY<363 && jumpcounter==0)
				MarioY+=5;
		}
		else if(MapX<=-431 && MapX>=-433)
		{
			if(MarioY>363 && left)
			{
				MapX-=3;
			}
			if(MarioY<428 && jumpcounter==0)
				MarioY+=5;
		}
		else if(MapX<-505 && MapX>-555 && grounded && MarioY>410)
			this.Reset();
		else if(MapX<=-689 && MapX>=-694)
		{
			if(MarioY>385 && right)
			{
				MapX+=3;
			}			
		}
		else if(MapX<=-693 && MapX>=-733)
		{
			if(MarioY==383)
				grounded=true;
			else if(MarioY<380 && jumpcounter==0)
				MarioY+=5;
		}
		else if(MapX<=-732 && MapX>=-738)
		{
			if(MarioY>385 && left)
			{
				MapX-=3;
			}
		}
		else if(MapX<=-760 && MapX>=-780)
		{
			if(MarioY<=370 && MarioY>=366)
			{
				grounded=true;
			}
			if(MarioY<366 && jumpcounter==0)
			{
				MarioY+=5;
			}
			if(MarioY>375 && MarioY<=395 && rising)
			{
				rising=false;
				falling=true;
			}
			if(jumpcounter==0 && MarioY<428 && MarioY>395)
				MarioY+=5;
		}
		else if(MapX<=-849 && MapX>=-851)
		{
			if(MarioY>=398 && right)
			{
				MapX+=3;
			}			
		}
		else if(MapX<=-852 && MapX>=-897)
		{
			if(MarioY==398)
				grounded=true;
			else if(MarioY<398 && jumpcounter==0)
				MarioY+=5;
		}
		else if(MapX<=-898 && MapX>=-900)
		{
			if(MarioY>=398 && left)
			{
				MapX-=3;
			}
		}
		else if(MapX<=-945 && MapX>=-1165)
		{
			if(MapX<=-945 && MapX>=-948 && MarioY>413 && right)
			{
				MapX+=3;
			}
			else if(MapX<=-949 && MapX>=-966)
			{
				if(MarioY==413)
				{
					grounded=true;
					falling=false;
				}
				else if(MarioY<413 && grounded)
					MarioY+=5;
				if(MapX<=-963 && MapX>=-966 && right && MarioY>398)
				{
					MapX+=3;
				}
			}
			else if(MapX<=-967 && MapX>=-984)
			{
				if(MarioY==398)
				{
					grounded=true;
					falling=false;
				}
				else if(MarioY<398 && grounded)
					MarioY+=5;
				if(MapX<=-981 && MapX>=-984 && right && MarioY>383)
				{
					MapX+=3;
				}
			}
			else if(MapX<=-985 && MapX>=-1000)
			{
				if(MarioY==383)
				{
					grounded=true;
					falling=false;
				}
				else if(MarioY<383 && grounded)
					MarioY+=5;
				if(MapX<=-997 && MapX>=-1000 && right && MarioY>368)
				{
					MapX+=3;
				}
			}
			else if(MapX<=-1001 && MapX>=-1165)
			{
				if(MarioY==368)
				{
					grounded=true;
					falling=false;
				}
				if(MarioY<368 && jumpcounter==0)
					MarioY+=5;
			}
		}
		else if(MapX<=-1165 && MapX>-1200)
		{
			grounded=false;
			if(!jump && !falling && !rising)
			{
				grounded=false;
				MarioY+=5;
			}
			if(MarioY>=400)
				this.Reset();
			else if(MarioY>368 && right && MapX<=-1196 && MapX>-1200)
			{
				MapX-=3;
			}
			else if(MarioY>368 && left && MapX<-1165 && MapX>=-1169)
			{
				MapX+=3;
			}
		}
		else if(MapX<=-1200 && MapX>=-1237 && MarioY>350)
		{
			if(MapX<=-1200 && MapX>=-1214)
			{
				if(MarioY==368)
				{
					grounded=true;
					falling=false;
				}
			}
			if(MapX==-1215 && left && MarioY>368)
			{
				MapX-=3;;
			}
			if(MapX<=-1219 && MapX>=-1235)
			{
				if(MarioY==398)
				{
					grounded=true;
					falling=false;
				}
				if(MarioY<398 && jumpcounter==0)
					MarioY+=5;
			}
			if(MapX==-1236 && left && MarioY>398)
			{
				MapX-=3;
			}
		}
		else if(MapX<=-1219 && MapX>=-1284)
		{
			if(MarioY==303)
			{
				grounded=true;
				falling=false;
			}
			else if(!jump && !falling && !rising && MarioY!=428)
			{
				MarioY+=5;
			}
		}
		else if(MapX<=-1427 && MapX>=-1479)
		{
			if(MapX<=-1427 && MapX>=-1429 && right && MarioY>368)
			{
				MapX+=3;
			}
			if(MapX<=-1430 && MapX>=-1476)
			{
				if(MarioY>368)
					MarioY=368;
				if(MarioY==368)
					grounded=true;
				
			}
			if(MapX<=-1477 && MapX>=-1479 && left && MarioY>368)
			{
				MapX-=3;
			}
			if(grounded && MarioY!=428 && MarioY!=368)
			{
				MarioY+=5;
			}
		}
		else if(MapX<=-1557 && MapX>=-1749)
		{
			if(rising && MarioY<=398 && MarioY>=380)
			{
				rising=false;
				falling=true;
			}
			if(MarioY==368 || MarioY==428)
			{
				grounded=true;
			}
			else
				grounded=false;	
			if(MarioY!=368 && MarioY!=428 && MarioY!=303 && !jump && jumpcounter==0)
			{
				MarioY+=5;
				if(rising)
					MarioY-=5;
			}
			if(MapX<=-1557 && MapX>=-1731)
			{
				if(rising && MarioY<330 && MarioY>320)
				{
					rising=false;
					falling=true;
				}
				if(MarioY==303)
					grounded=true;
			}
				
		}
		else if(MapX<=-1793 && MapX>=-1831)
		{
			if(MapX<=-1793 && MapX>=-1796 && right && MarioY>398)
				MapX+=3;
			if(MapX<=-1797 && MapX>=-1827)
			{
				if(MarioY==398) 
					grounded=true;	
				else if(MarioY<398 && jumpcounter==0)
				{
					MarioY+=5;
				}
				else
				{
					grounded=false;
				}
			}
			if(MapX<=-1828 && MapX>=-1831 && left && MarioY>398)
				MapX-=3;
		}
		else if(MapX<=-1840 && MapX>=-1898)
		{
			if(grounded && MarioY>=428)
			{
				this.Reset();
//				MarioY=353;
			}
			if(MapX<=-1845 && (MarioY==353 || MarioY==303 || MarioY==428))
				grounded=true;
			else if(!jump && !falling && !rising && MarioY!=353 && MarioY!=303 && !climb && MarioY>303)
			{
				grounded=false;
				MarioY+=5;
			}
			if(rising && ((MarioY<330 && MarioY>320)||(MarioY>=368 && MarioY<=373)))
			{
				rising=false;
				falling=true;
			}
			if((MapX<=-1840 && MapX>=-1844 && right) && ((MarioY>355 && MarioY<375) || (MarioY>310 && MarioY<330)))
				MapX+=3;
			if(MarioY<=303 && MarioY>=135 && MapX<=-1890 && MapX>=-1897 && climb)
				MarioY-=5;
		}
		else if(MapX<=-1956 && MapX>=-2069)
		{
			if(right && MarioY>=413 && MapX<=-1956 && MapX>-1959)
				MapX+=3;
			if(MapX<=-1959 && MapX>=-1975)
			{
				if(MarioY==413)
					grounded=true;
				if(MarioY<413 && jumpcounter==0)
					MarioY+=5;
				if(MapX<=-1973 && MapX>=-1975 && right && MarioY>=398)
					MapX+=3;
			}
			if(MapX<=-1976 && MapX>=-1992)
			{
				if(MarioY==398)
					grounded=true;
				if(MarioY<398 && jumpcounter==0)
					MarioY+=5;
				if(MapX<=-1990 && MapX>=-1992 && right && MarioY>=383)
					MapX+=3;
			}
			if(MapX<=-1993 && MapX>=-2009)
			{
				if(MarioY==383)
					grounded=true;
				if(MarioY<383 && jumpcounter==0)
					MarioY+=5;
				if(MapX<=-2007 && MapX>=-2009 && right && MarioY>=368)
					MapX+=3;	
			}
			if(MapX<=-2010 && MapX>=-2026)
			{
				if(MarioY==368)
					grounded=true;
				if(MarioY<368 && jumpcounter==0)
					MarioY+=5;
				if(MapX<=-2024 && MapX>=-2026 && right && MarioY>=348)
					MapX+=3;
			}
			if(MapX<=-2027 && MapX>=-2045)
			{
				if(MarioY==348)
					grounded=true;
				if(MarioY<348 && jumpcounter==0)
					MarioY+=5;
				if(MapX<=-2043 && MapX>=-2045 && right && MarioY>=333)
					MapX+=3;
			}
			if(MapX<=-2046 && MapX>=-2064)
			{
				if(MarioY==333)
					grounded=true;
				if(MarioY<333 && jumpcounter==0)
					MarioY+=5;
			}
			if(MapX<=-2067 && MapX>=-2069 && MarioY>330 && left)
			{
				MapX-=3;
				MarioY+=5;
			}
		}
		else if(MapX<=-2070 && MapX>=-2090 && MarioY>=420)
		{
			this.Reset();
		}
		else if(MapX<=-2178 && MapX>=-2244)
		{
			if(MapX<=-2181 && MarioY==363 || MarioY==303)
				grounded=true;
			if(!jump && !falling && !rising && MarioY!=363 && MarioY!=303 && MarioY!=428)
			{
				grounded=false;
				MarioY+=5;
			}
			if(rising && ((MarioY<330 && MarioY>320) || (MarioY<=395 && MarioY>=385)))
			{
				rising=false;
				falling=true;
			}
			if((MapX<=-2178 && MapX>=-2180 && right) && ((MarioY>365 && MarioY<385) || (MarioY>310 && MarioY<330)))
				MapX+=3;
			if((MapX<=-2245 && MapX>=-2247 && left) && ((MarioY>365 && MarioY<385) || (MarioY>310 && MarioY<330)))
				MapX-=3;
		}
		else if(MapX<=-2256 && MapX>=-2325)
		{
			if(MapX<=-2259 && MarioY==363 || MarioY==303)
				grounded=true;
			if(!jump && !falling && !rising && MarioY!=363 && MarioY!=303 && MarioY!=428)
			{
				grounded=false;
				MarioY+=5;
			}
			if(rising && ((MarioY<330 && MarioY>320) || (MarioY<=395 && MarioY>=385)))
			{
				rising=false;
				falling=true;
			}
			if((MapX<=-2256 && MapX>=-2258 && right) && ((MarioY>365 && MarioY<385) || (MarioY>310 && MarioY<330)))
				MapX+=3;
			if((MapX<=-2323 && MapX>=-2325 && left) && ((MarioY>365 && MarioY<385) || (MarioY>310 && MarioY<330)))
				MapX-=3;
		}
		else if(MapX<=-2439 && MapX>=-2535)
		{
			if(MapX<=-2442 && MapX>=-2532 && MarioY==363)
				grounded=true;
			if(!jump && !falling && !rising && MarioY!=363 && MarioY!=428)
			{
				grounded=false;
				MarioY+=5;
			}
			if(rising && MarioY<=395 && MarioY>=385)
			{
				rising=false;
				falling=true;
			}
			if(MapX<=-2439 && MapX>=-2441 && right && MarioY>365 && MarioY<385)
				MapX+=3;
			if(MapX<=-2533 && MapX>=-2535 && left && MarioY>365 && MarioY<385)
				MapX-=3;
		}
		else if(MapX<=-2566 && MapX>=-2616)
		{
			if(MapX<=-2566 && MapX>=-2568 && MarioY>383 && right)
				MapX+=3;
			if(MapX<=-2569 && MapX>=-2583)
			{
				if(MarioY==383)
					grounded=true;
				if(MarioY<383 && jumpcounter==0)
					MarioY+=5;
				if(MapX<=-2581 && MapX>=-2583 && right && MarioY>338)
					MapX+=3;
			}
			if(MapX<-2583 && MapX>=-2613)
			{
				if(MarioY==338)
					grounded=true;
				if(MarioY<338 && jumpcounter==0)
					MarioY+=5;;
			}
			if(MapX<=-2614 && MapX>=-2616 && MarioY>338 && left)
				MapX-=3;			
		}
		else if(MapX<=-2709 && MapX>=-2865)
		{
			if(MapX==-2709 && right && MarioY>413)
				MapX+=3;
			if(MapX<-2709 && MapX>=-2727)
			{
				if(MarioY==413)
					grounded=true;
				if(jumpcounter==0 && MarioY<413)
					MarioY+=5;
				if(MapX==-2727 && right && MarioY>=398)
					MapX+=3;
			}
			if(MapX<-2727 && MapX>=-2745)
			{
				if(MarioY==398)
					grounded=true;
				if(jumpcounter==0 && MarioY<398)
					MarioY+=5;
				if(MapX==-2745 && right && MarioY>=383)
					MapX+=3;
			}
			if(MapX<-2745 && MapX>=-2763)
			{
				if(MarioY==383)
					grounded=true;
				if(jumpcounter==0 && MarioY<383)
					MarioY+=5;
				if(MapX==-2763 && right && MarioY>=368)
					MapX+=3;
			}
			if(MapX<-2763 && MapX>=-2778)
			{
				if(MarioY==368)
					grounded=true;
				if(jumpcounter==0 && MarioY<368)
					MarioY+=5;
				if(MapX==-2778 && right && MarioY>=353)
					MapX+=3;
			}
			if(MapX<-2778 && MapX>=-2796)
			{
				if(MarioY==353)
					grounded=true;
				if(jumpcounter==0 && MarioY<353)
					MarioY+=5;
				if(MapX==-2796 && right && MarioY>=338)
					MapX+=3;
			}
			if(MapX<-2796 && MapX>=-2811)
			{
				if(MarioY==338)
					grounded=true;
				if(jumpcounter==0 && MarioY<338)
					MarioY+=5;
				if(MapX==-2811 && right && MarioY>=318)
					MapX+=3;
			}
			if(MapX<-2811 && MapX>=-2829)
			{
				if(MarioY==318)
					grounded=true;
				if(jumpcounter==0 && MarioY<318)
					MarioY+=5;
				if(MapX==-2829 && right && MarioY>=303)
					MapX+=3;
			}
			if(MapX<-2829 && MapX>=-2865)
			{
				if(MarioY==303)
					grounded=true;
				if(jumpcounter==0 && MarioY<303)
					MarioY+=5;
			}
		}
		else if(MapX<-2865 && MapX>=-2868 && MarioY>303 && left)
			MapX-=3;
		else if(MapX<=-2991)
		{
			if(endlvl==1)
			{
				this.stopSound(title);
				this.playSound(CourseCleared);
				MarioY-=20;
			}
			endlvl++;
			if(MarioY<428)
				MarioY+=1;
			if(MarioY==428)
				MapX-=3;
			if(MapX<=-3100)
				MapX+=3;
			if(endlvl%3==0 && MarioY==428)	
				WalkingMario = getImage(getCodeBase(), "MarioWalkingSprite4.png");
			if(endlvl%3!=0 && MarioY==428)	
				WalkingMario = getImage(getCodeBase(), "MarioWalkingSprite5.png");
			if(endlvl==406)
			{
				MarioY=428;
				level++;
				endlvl=1;
				MapX=0;
				leftcount=0;
				rightcount=0;
				MarioY=180;
				Health=1;
				left=false;
				right=false;
				jump=false;
			}
		}
		else
		{
			grounded=false;
			if(MarioY!=428 && jumpcounter==0)
				MarioY+=5;
		}
	}
	public void level2Coins(Graphics g)
	{
//Coin[0]
		if(l2Coins[0]){
			if(MarioY<=105 && MapX<=-2061 && MapX>=-2070)
			{
				l2Coins[0]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
		{
			g.drawImage(CoinRemover, (MapX + 2263), 97, this);	
		}
//Coin[1]
		if(l2Coins[1]){
			if(MarioY<=105 && MapX<=-2076 && MapX>=-2089)
			{
				l2Coins[1]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
		{
			g.drawImage(CoinRemover, (MapX + 2283), 97, this);
		}
//Coin[2]
		if(l2Coins[2]){
			if(MarioY<=105 && MapX<=-2090 && MapX>=-2105)
			{
				l2Coins[2]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2299), 97, this);
//Coin[3]
		if(l2Coins[3]){
			if(MarioY<=105 && MapX<=-2105 && MapX>=-2120)
			{
				l2Coins[3]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2315), 97, this);
//Coin[4]
		if(l2Coins[4]){
			if(MarioY<=105 && MapX<=-2130 && MapX>=-2140)
			{
				l2Coins[4]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2335), 97, this);
//Coin[5]
		if(l2Coins[5]){
			if(MarioY<=105 && MapX<=-2140 && MapX>=-2155)
			{
				l2Coins[5]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2350), 97, this);
//Coin[6]
		if(l2Coins[6]){
			if(MarioY<=105 && MapX<=-2155 && MapX>=-2170)
			{
				l2Coins[6]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2365), 97, this);
//Coin[7]
		if(l2Coins[7]){
			if(MarioY<=105 && MapX<=-2170 && MapX>=-2185)
			{
				l2Coins[7]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2380), 97, this);
//Coin[8]
		if(l2Coins[8]){
			if(MarioY<=105 && MapX<=-2185 && MapX>=-2200)
			{
				l2Coins[8]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2395), 97, this);
//Coin[9]
		if(l2Coins[9]){
			if(MarioY<=105 && MapX<=-2200 && MapX>=-2215)
			{
				l2Coins[9]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2412), 97, this);
//Coin[10]
		if(l2Coins[10]){
			if(MarioY<=105 && MapX<=-2215 && MapX>=-2230)
			{
				l2Coins[10]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2427), 97, this);	
//Coin[11]
		if(l2Coins[11]){
			if(MarioY<=105 && MapX<=-2230 && MapX>=-2245)
			{
				l2Coins[11]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2443), 97, this);
//Coin[12]
		if(l2Coins[12]){
			if(MarioY<=105 && MapX<=-2245 && MapX>=-2260)
			{
				l2Coins[12]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2455), 97, this);
//Coin[13]
		if(l2Coins[13]){
			if(MarioY<=105 && MapX<=-2265 && MapX>=-2280)
			{
				l2Coins[13]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2475), 97, this);
//Coin[14]
		if(l2Coins[14]){
			if(MarioY<=105 && MapX<=-2275 && MapX>=-2290)
			{
				l2Coins[14]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2491), 97, this);
//Coin[15]
		if(l2Coins[15]){
			if(MarioY<=105 && MapX<=-2295 && MapX>=-2310)
			{
				l2Coins[15]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2507), 97, this);
////////Row2
//Coin[16]
		if(l2Coins[16]){
			if(MarioY<=98 && MapX<=-2365 && MapX>=-2380)
			{
				l2Coins[16]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2575), 93, this);
//Coin[17]
		if(l2Coins[17]){
			if(MarioY<=98 && MapX<=-2380 && MapX>=-2395)
			{
				l2Coins[17]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2590), 93, this);
//Coin[18]
		if(l2Coins[18]){
			if(MarioY<=98 && MapX<=-2395 && MapX>=-2410)
			{
				l2Coins[18]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2605), 93, this);
//Coin[19]
		if(l2Coins[19]){
			if(MarioY<=98 && MapX<=-2410 && MapX>=-2425)
			{
				l2Coins[19]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2620), 93, this);
//Coin[20]
		if(l2Coins[20]){
			if(MarioY<=98 && MapX<=-2425 && MapX>=-2440)
			{
				l2Coins[20]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2635), 93, this);
//Coin[21]
		if(l2Coins[21]){
			if(MarioY<=98 && MapX<=-2440 && MapX>=-2455)
			{
				l2Coins[21]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2649), 93, this);
//Coin[22]
		if(l2Coins[22]){
			if(MarioY<=98 && MapX<=-2455 && MapX>=-2470)
			{
				l2Coins[22]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2667), 93, this);
//Coin[23]
		if(l2Coins[23]){
			if(MarioY<=98 && MapX<=-2470 && MapX>=-2485)
			{
				l2Coins[23]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2683), 93, this);
//Coin[24]
		if(l2Coins[24]){
			if(MarioY<=98 && MapX<=-2485 && MapX>=-2500)
			{
				l2Coins[24]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2691), 93, this);
//Coin[25]
		if(l2Coins[25]){
			if(MarioY<=98 && MapX<=-2500 && MapX>=-2515)
			{
				l2Coins[25]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2709), 93, this);
//Coin[26]
		if(l2Coins[26]){
			if(MarioY<=98 && MapX<=-2515 && MapX>=-2530)
			{
				l2Coins[26]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2719), 93, this);
//Coin[27]
		if(l2Coins[27]){
			if(MarioY<=98 && MapX<=-2530 && MapX>=-2545)
			{
				l2Coins[27]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2738), 93, this);
//Coin[28]
		if(l2Coins[28]){
			if(MarioY<=98 && MapX<=-2545 && MapX>=-2560)
			{
				l2Coins[28]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2748), 93, this);
//Coin[29]
		if(l2Coins[29]){
			if(MarioY<=98 && MapX<=-2560 && MapX>=-2575)
			{
				l2Coins[29]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2766), 93, this);
//Coin[30]
		if(l2Coins[30]){
			if(MarioY<=98 && MapX<=-2575 && MapX>=-2590)
			{
				l2Coins[30]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2781), 93, this);
//Coin[31]
		if(l2Coins[31]){
			if(MarioY<=98 && MapX<=-2590 && MapX>=-2605)
			{
				l2Coins[31]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2796), 93, this);
//Coin[32]
		if(l2Coins[32]){
			if(MarioY<=98 && MapX<=-2605 && MapX>=-2620)
			{
				l2Coins[32]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2811), 93, this);
////////Row3
//Coin[33]
		if(l2Coins[33]){
			if(MarioY<=83 && MarioY>=70 && MapX<=-2670 && MapX>=-2685)
			{
				l2Coins[33]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2876), 82, this);
//Coin[34]
		if(l2Coins[34]){
			if(MarioY<=83 && MarioY>=70 && MapX<=-2685 && MapX>=-2700)
			{
				l2Coins[34]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2891), 82, this);
//Coin[35]
		if(l2Coins[35]){
			if(MarioY<=83 && MarioY>=70 && MapX<=-2700 && MapX>=-2715)
			{
				l2Coins[35]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2906), 82, this);
//Coin[36]
		if(l2Coins[36]){
			if(MarioY<=83 && MarioY>=70 && MapX<=-2715 && MapX>=-2730)
			{
				l2Coins[36]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2921), 82, this);
//Coin[37]
		if(l2Coins[37]){
			if(MarioY<=83 && MarioY>=70 && MapX<=-2730 && MapX>=-2745)
			{
				l2Coins[37]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2936), 82, this);
//Coin[38]
		if(l2Coins[38]){
			if(MarioY<=83 && MarioY>=70 && MapX<=-2745 && MapX>=-2760)
			{
				l2Coins[38]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2956), 82, this);
//Coin[39]
		if(l2Coins[39]){
			if(MarioY<=83 && MarioY>=70 && MapX<=-2760 && MapX>=-2775)
			{
				l2Coins[39]=false;
				this.playSound(Coin);
				coinsObtained++;
			}
		}
		else
			g.drawImage(CoinRemover, (MapX + 2971), 82, this);
	}
	public void level2RemoveBricks(Graphics g)
	{
		g.drawImage(brickRemover, (MapX+256 ), 384, this);
		g.drawImage(brickRemover, (MapX+304 ), 368, this);
		g.drawImage(brickRemover, (MapX+352 ), 369, this);
		g.drawImage(brickRemover, (MapX+1439), 320, this);
		g.drawImage(brickRemover, (MapX+1809), 320, this);
		g.drawImage(brickRemover, (MapX+1872), 321, this);
		g.drawImage(brickRemover, (MapX+2673), 384, this);
		g.drawImage(brickRemover, (MapX+2416), 320, this);
		g.drawImage(brickRemover, (MapX+2416), 384, this);
		g.drawImage(brickRemover, (MapX+2496), 320, this);
		g.drawImage(brickRemover, (MapX+2496), 384, this);
	}
	public void level2RemoveEnemies(Graphics g)
	{
		//Jumpkin Koopa									(-195)
		g.drawImage(EnemyRemover, MapX + 400, 425, this);
		//Jumpin Koopa									(-246)
		g.drawImage(EnemyRemover, MapX + 446, 409, this);
		//Pirahna Plant									(-314)
		g.drawImage(EnemyRemover, MapX + 514, 377, this);
		//Goomba										(-385)
		g.drawImage(EnemyRemover, MapX + 585, 425, this);
		//Pirahna Plant									(-411)
		g.drawImage(EnemyRemover, MapX + 611, 361, this);
		//Goomba X3										(-645) && (-670)  && (-690)
		g.drawImage(EnemyRemover, MapX + 845, 425, this);
		g.drawImage(EnemyRemover, MapX + 870, 425, this);
		g.drawImage(EnemyRemover, MapX + 890, 425, this);
		//Pirahna Plant									(-720)
		g.drawImage(EnemyRemover, MapX + 920, 377, this);
		//Koopa											(-837)
		g.drawImage(EnemyRemover, MapX + 1037, 425, this);
		//Pirahna Plant									(-880)
		g.drawImage(EnemyRemover, MapX + 1080, 393, this);
		//Bridge /w 3 Goombas							(-1026 - ?)
		g.drawImage(EnemyRemover, MapX + 1230, 363, this);
		g.drawImage(EnemyRemover, MapX + 1253, 363, this);
		g.drawImage(EnemyRemover, MapX + 1276, 363, this);
		g.drawImage(EnemyRemover, MapX + 1299, 363, this);
		g.drawImage(EnemyRemover, MapX + 1322, 363, this);
		g.drawImage(EnemyRemover, MapX + 1345, 363, this);
		g.drawImage(EnemyRemover, MapX + 1368, 363, this);
		g.drawImage(EnemyRemover, MapX + 1307, 318, this);
		//GoombaX2										(-1304) & (-1323)
		g.drawImage(EnemyRemover, MapX + 1504, 425, this);
		g.drawImage(EnemyRemover, MapX + 1523, 425, this);
		//Koopa											(-1414)
		g.drawImage(EnemyRemover, MapX + 1614, 425, this);
		//Pirahna Plant 								(-1455)
		g.drawImage(EnemyRemover, MapX + 1655, 361, this);
		//Hammer tosser platform						(-1608)
		g.drawImage(EnemyRemover, MapX + 1808, 361, this);
		//Hammer tosser ground 							(-1656)
		g.drawImage(EnemyRemover, MapX + 1850, 425, this);
		//Goomba										(-2017)
		g.drawImage(EnemyRemover, MapX + 2217, 361, this);
		//Goomba										(-2035)
		g.drawImage(EnemyRemover, MapX + 2233, 345, this);
		//Koopa 										(-2177)
		g.drawImage(EnemyRemover, MapX + 2377, 425, this);
		//Koopa											(-2230)
		g.drawImage(EnemyRemover, MapX + 2430, 297, this);
		//Goomba (X3)									(-2260) && alot .-.
		g.drawImage(EnemyRemover, MapX + 2460, 425, this);
		g.drawImage(EnemyRemover, MapX + 2480, 425, this);
		g.drawImage(EnemyRemover, MapX + 2480, 405, this);
		g.drawImage(EnemyRemover, MapX + 2500, 425, this);
		g.drawImage(EnemyRemover, MapX + 2500, 405, this);
		g.drawImage(EnemyRemover, MapX + 2520, 425, this);
		g.drawImage(EnemyRemover, MapX + 2520, 405, this);
		g.drawImage(EnemyRemover, MapX + 2514, 400, this);
		//JKoopa										(-2439)
		g.drawImage(EnemyRemover, MapX + 2639, 425, this);
		g.drawImage(EnemyRemover, MapX + 2639, 400, this);
		g.drawImage(EnemyRemover, MapX + 2639, 405, this);
		//JKoopa										(-2474)
		g.drawImage(EnemyRemover, MapX + 2674, 425, this);
		g.drawImage(EnemyRemover, MapX + 2673, 405, this);
		g.drawImage(EnemyRemover, MapX + 2694, 425, this);
		g.drawImage(EnemyRemover, MapX + 2694, 405, this);
		//Koopa											(-2515)
		g.drawImage(EnemyRemover, MapX + 2715, 361, this);
		//JKoopa										(-2536)
		g.drawImage(EnemyRemover, MapX + 2736, 425, this);
		//Koopa											(-2801)
		g.drawImage(EnemyRemover, MapX + 3001, 330, this);
		g.drawImage(EnemyRemover, MapX + 3055, 297, this);
	}
	public void Level2EnemyMovements(Graphics g)
	{
		for(int count=0; count<13; count++)
		{
			if(count<7)
			{
				if(KoopasOnScreen[count] && KoopasAlive[count])
				{
						g.drawImage(Koopa[count],KoopasX[count]+MapX,KoopasY[count],this);
						if(r.nextInt(4)>=1)
						{
							KoopaWalkingCounter[count]++;
							if(KoopaChangeDirection[count]==false)
							{
								KoopasX[count]-=1;
							}
							else if(KoopaChangeDirection[count]==true)
							{
								KoopasX[count]+=1;
							}
						}
						if(KoopaWalkingCounter[count]==50)
						{
							KoopaWalkingCounter[count]=0;
							if(!KoopaChangeDirection[count])
								KoopaChangeDirection[count]=true;
							else
								KoopaChangeDirection[count]=false;
						}
						else if(KoopaWalkingCounter[count] % 2 == 0 && !KoopaChangeDirection[count])
						{
							Koopa[count] = getImage(getCodeBase(), "KoopaLeft1.png");
						}
						else if(KoopaWalkingCounter[count] % 2 > 0 && !KoopaChangeDirection[count])
						{
							Koopa[count] = getImage(getCodeBase(), "KoopaLeft2.png");
						}
						else if(KoopaWalkingCounter[count] % 2 == 0 && KoopaChangeDirection[count])
						{
							Koopa[count] = getImage(getCodeBase(), "KoopaRight1.png");
						}
						else if(KoopaWalkingCounter[count] % 2 > 0 && KoopaChangeDirection[count])
						{
							Koopa[count] = getImage(getCodeBase(), "KoopaRight2.png");
						}
				}
			}
			if(count<13)
			{
				if(GoombasOnScreen[count] && GoombasAlive[count])
				{
					g.drawImage(Goomba[count],GoombasX[count]+MapX,GoombasY[count],this);
					if(r.nextInt(4)>=1)
					{
						GoombaWalkingCounter[count]++;
						if(GoombaChangeDirection[count]==false)
						{
							GoombasX[count]-=1;
						}
						else if(GoombaChangeDirection[count]==true)
						{
							GoombasX[count]+=1;
						}
					}
					if(GoombaWalkingCounter[count]==75)
					{
						GoombaWalkingCounter[count]=0;
						if(!GoombaChangeDirection[count])
							GoombaChangeDirection[count]=true;
						else
							GoombaChangeDirection[count]=false;
					}
					else if(GoombaWalkingCounter[count] % 2 == 0 && !GoombaChangeDirection[count])
					{
						Goomba[count] = getImage(getCodeBase(), "GoombaLeft1.png");
					}
					else if(GoombaWalkingCounter[count] % 2 > 0 && !GoombaChangeDirection[count])
					{
						Goomba[count] = getImage(getCodeBase(), "GoombaLeft2.png");
					}
					else if(GoombaWalkingCounter[count] % 2 == 0 && GoombaChangeDirection[count])
					{
						Goomba[count] = getImage(getCodeBase(), "GoombaRight1.png");
					}
					else if(GoombaWalkingCounter[count] % 2 > 0 && GoombaChangeDirection[count])
					{
						Goomba[count] = getImage(getCodeBase(), "GoombaRight2.png");
					}
				}
			}
			if(count<4)
			{
				if(TubeChompsOnScreen[count])
				{
					if(TubeChompsSurfaced[count])
						g.drawImage(TubeChomp[count],TubeChompsX[count]+MapX,TubeChompsY[count],this);
					TubeChompWalkingCounter[count]++;
					if(TubeChompWalkingCounter[count]>=50 && TubeChompsSurfaced[count])
					{
						TubeChompWalkingCounter[count]=0;
						TubeChompsSurfaced[count]=false;
					}
					if(TubeChompWalkingCounter[count]>=50 && !TubeChompsSurfaced[count])
					{
						TubeChompWalkingCounter[count]=0;
						TubeChompsSurfaced[count]=true;
					}
				}
			}
		}			
			
		
	}
	public void level2EnemyBounds()
	{
		for(int count=0; count<13; count++)
		{
			if(count<13 && GoombasAlive[count])
			{
				if(((GoombaDefaultX[count]) + (MapX) > 0) && ((GoombaDefaultX[count])+ (MapX) < 1000) && GoombasOnScreen[count]==false) 
				{
					GoombasOnScreen[count] = true;
					GoombasX[count]=GoombaDefaultX[count];
					GoombasY[count]=GoombaDefaultY[count];
				}
				if(MarioY-10 <GoombasY[count] && MarioY+10>GoombasY[count])
				{
					if(225>GoombasX[count]+MapX && 180<GoombasX[count]+MapX)
					{
						if(((MarioY<428 && MarioY>390) || (MapX<=-1001 && MapX>=-1165 && MarioY<368 && MarioY>=338)) && !grounded)
						{
							GoombasAlive[count]=false;
							MarioY-=30;
						}
						else if(!falling && ((MarioY==428 || MarioY==368)))
							this.Reset();
					}
				}
			}
			if(count<7 && KoopasAlive[count])
			{
				if(((KoopaDefaultX[count]) + (MapX) > 0) && ((KoopaDefaultX[count])+ (MapX) < 1000) && !KoopasOnScreen[count]) 
				{
					KoopasOnScreen[count] = true;
					KoopasX[count]=KoopaDefaultX[count];
					KoopasY[count]=KoopaDefaultY[count];
				}
				if(MarioY-10 <KoopasY[count] && MarioY+10>KoopasY[count])
				{
					if(225>KoopasX[count]+MapX && 180<KoopasX[count]+MapX)
					{
						if(((MarioY<428 && MarioY>390) || (MarioY<188 && MarioY>150)) && !grounded)
						{
							KoopasAlive[count]=false;
							MarioY-=30;
						}
						else if(!falling && ((MarioY==428) || (MarioY==188)))
							this.Reset();
					}
				}
			}
			
			if(count<4 && TubeChompsAlive[count])
			{
				if(((TubeChompDefaultX[count]) + (MapX) > 0) && ((TubeChompDefaultX[count]) + (MapX)< 1000) && !TubeChompsOnScreen[count])
				{
					TubeChompsOnScreen[count]=true;
					TubeChompsX[count]=TubeChompDefaultX[count];
					TubeChompsY[count]=TubeChompDefaultY[count];
				}
				if(MarioY-10 < TubeChompsY[count] && MarioY+10 > TubeChompsY[count])
				{
					if(225 > TubeChompsX[count] + MapX && 180 < TubeChompsX[count] + MapX)
					{
						if(MarioY < TubeChompsY[count] && MarioY > TubeChompsY[count]-25 && TubeChompsSurfaced[count])
							this.Reset();
					}
				}
			}
		}
	}
	public void Star(Graphics g)
	{
		if(StarOnScreen)
		{
			if(starCount>=1000)
				StarOnScreen=false;
			starCount++;
			g.drawImage(Star, (MapX+2070), 353, this);
			if(MarioY==353 && (MapX>=-1880 && MapX<=-1860))
			{
				StarOnScreen=false;
				StarPower=true;
			}
		}
		if(StarPower)
		{
			g.drawImage(Star, 200, (MarioY-25), this);
		}
	}
	public void Reset()
	{
		if(!StarPower)
		{
			lives--;
			this.stopSound(title);
			this.playSound(GameOver);
			endlvl=1;
			GameRunning=true;
			PlayAgain=true;
			count=900000000;		
			left=false;
			right=false;
			jump=false;
			inBounds=true;
			onScreen=true;
			pipes=false;	
			MapX=0;
			leftcount=0;
			rightcount=0;		
			MarioY=180;
			if(level==2)
				MarioY=423;
			Health=1;
			invincibilityframe=0;
			grounded=true;
			rising=false;
			falling=false;
			jumpcounter=0;
			if(lives<=0)
				PlayAgain=false;
			for(int count=0; count<40; count++)
			{
				l2Coins[count]=true;
				if(count<13)
					GoombasAlive[count]=true;
				if(count<7)
					KoopasAlive[count]=true;
			}
		}
	}
	public void update(Graphics g)
	{
		paint(g);
	}
	public void run(){
		while(PlayAgain==true){
			while(GameRunning==true){
				repaint();
				try{
					Thread.sleep(2);
				}
				catch (InterruptedException e){};
			}
			if(GameRunning==false){
			}
		}	
	}
	public void mouseDragged(MouseEvent ev) {
	}
	public void mouseMoved(MouseEvent ev) {
	}
	public void mouseClicked(MouseEvent ev)
	{
		if (!hasStarted)
		{
			hasStarted=true;
		}
		if(GameRunning==false)
		{
			if(ev.getX()>0&&ev.getX()<1000&&ev.getY()>0&&ev.getY()<1000)
			{
				GameRunning=true;
			}
		}		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
		onScreen=true;
		inBounds=true;
	}
	public void mouseExited(MouseEvent ev)
	{
		onScreen=false;
		inBounds=false;
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	@Override
	public void keyPressed(KeyEvent e)
	{
		int key=e.getKeyCode();
		if(key==e.VK_A)
			left=true;
		if(key==e.VK_D)
			right=true;
		if(key==e.VK_W)
			climb=true;
		if(key==e.VK_SPACE && 
/* level 1 */		(((MarioY==180 || MarioY==399 || MarioY==402 || MarioY==405 && level==1) || (pipes && MarioY>100) || (pit) || (MapX<-1926 && MapX>-2337 && level==1) || (level==1 && MapX<=-2686 && MapX>=-2735))
/* level 2 */		||(level==2 && jumpcounter==0)))
		{
			jump=true;
			this.playSound(JumpSound);
		}			
	}
	@Override
	public void keyReleased(KeyEvent e)
	{
		int key=e.getKeyCode();
		if(key==e.VK_A)
			left=false;
		if(key==e.VK_D)
			right=false;
		if(key==e.VK_W)
			climb=false;
		if(key==e.VK_SPACE)
			jump=false;
	}
	@Override
	public void keyTyped(KeyEvent e) 
	{
		int key=e.getKeyCode();	
	}
	public void playSound(String location)
	{
		try {
			AudioClip clip = Applet.newAudioClip(
			new URL(location));
			clip.play();
			} catch (MalformedURLException murle) {
			System.out.println(murle);
			}
	}
	public void stopSound(String location)
	{
		try {
			AudioClip clip = Applet.newAudioClip(
			new URL(location));
			clip.stop();
			} catch (MalformedURLException murle) {
			System.out.println(murle);
			}
	}
	public void loopSound(String location)
	{
		try {
			AudioClip clip = Applet.newAudioClip(
			new URL(location));
			clip.loop();
			} catch (MalformedURLException murle) {
			System.out.println(murle);
			}
	}
}