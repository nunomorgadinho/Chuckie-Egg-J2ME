/* 
    Copyright 2003 
    
    Nuno Morgadinho (l13591 at alunos dot uevora dot pt)
    Claudio Fernandes (l14103 at alunos dot uevora dot pt)
    Tiago Fernandes (l13614 at alunos dot uevora dot pt)
    Tiago Bilou (l15243 at alunos dot uevora dot pt)

    This file is part of Chuckie Egg J2ME.

    Chuckie Egg J2ME is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Chuckie Egg J2ME is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Chuckie Egg J2ME; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Image;
import java.lang.Thread;

public final class ChuckieCanvas extends GameCanvas implements Runnable{
	
	private static ChuckieMidLet midlet=null;
	private static Nivel nivel=null;
	public  static boolean playing=true;
	public  static Chuckie chuck;
	private static boolean init=true;
	public static LayerManager lm;
	public static Thread thread;
	public static Image teste;
	

	// defenicao dos delays para os patos, tempo, loop principal
	public final static int systemDelay = 20;
	public final static int timeDelay = 900;
	public final static int patoDelay = 100;
	public  static int n_ovos ;
	public Panel painel = new Panel(timeDelay);

	public ChuckieCanvas(boolean flag){
		super(flag);
		thread = new Thread(this);
	}
	
	public static void start(){
		thread.start();
	}
	
	public void sleep(long t){
		try{
			thread.sleep(t);
		}catch(Exception e){};
	}

	public void setMidLet(ChuckieMidLet midlet){
		this.midlet=midlet;
		this.setFullScreenMode(true);
		nivel = new Nivel();
		lm = new LayerManager();
		
		try{

			/*
			  Criacao da Sprite Chuckie
			  Inicialmente é colocada no quadrado da matrix (5,2)
			*/
			
			chuck = new Chuckie(Image.createImage("/chuckie/chuck.png"),5,2);
			teste = Image.createImage("/chuckie/chicken.png");
					
		}catch(Exception e){
			System.out.println("setMidlet: "+e.toString());
		}
	}
	

	/* --- TECLAS --- */

 	public void keyPressed(int keyCode){
		
		//saimos do jogo?
		switch(keyCode){
		case KEY_NUM0: //tecla 0 termina o jogo
			playing = false;
			break;
		}
	}
	
		
	public void resetLayer(){
		int a = 0;
		int b = 0;

		lm.remove(chuck);
		PatoManager.removePatosLayerManager(lm);
			
	
		/* Inserção das sprites dos ovos no layer manager */
			for(a = 0; a < Nivel.num_ovos; a++){
			if(nivel.ovos[a] != null)
				lm.remove(nivel.ovos[a]);
				}

		/* Inserção dos sprites dos milhos no layer manager */
			for(b = 0; b < Nivel.num_milho; b++){
			if(nivel.milhos[b] != null)
				lm.remove(nivel.milhos[b]);
				}
		
		lm.remove(nivel.getLayerNivel());
		lm.remove(nivel.getLayerFundo());

		
	}
	
	public void morte(){

		
		lm.remove(chuck);
		PatoManager.removePatosLayerManager(lm);
		
		
		/* Reset ao tempo */
		painel.reset_time();
		
		try{
			/*
			  Criacao da Sprite Chuckie
			  Inicialmente é colocada no quadrado da matrix (5,2)
			*/
			chuck = new Chuckie(Image.createImage("/chuckie/chuck.png"),5,2);
			
		}catch(Exception e){}
	
		Nivel.resetPato();
		PatoManager.inserePatosLayerManager(lm);
		lm.insert(chuck,0);
		painel.chuckieKilled();
	}

	public void  nextNivel(int n){
		
		int a = 0;
		int b = 0;
		
		
		try{
			/*
			  Criacao da Sprite Chuckie
			  Inicialmente é colocada no quadrado da matrix (5,2)
			*/
			chuck = new Chuckie(Image.createImage("/chuckie/chuck.png"),5,2);
			
		}catch(Exception e){}
		
		nivel = new Nivel();
		nivel.initNivel(n);
		painel.reset_time();
		
		// insere as layers no layer manager
				
		lm.append(chuck);
		PatoManager.appendPatosLayerManager(lm);
	
		/* Inserção das sprites dos ovos no layer manager */
		for(a = 0; a < Nivel.num_ovos; a++){
			lm.append(nivel.ovos[a]);
		}
		/* Inserção dos sprites dos milhos no layer manager */
		for(b = 0; b < Nivel.num_milho; b++){
			lm.append(nivel.milhos[b]);
		}
		
		lm.append(nivel.getLayerNivel());
		lm.append(nivel.getLayerFundo());

		n_ovos = Nivel.num_ovos;		
	   
	}

	public void run(){
		
		Graphics g=getGraphics();
		long ct,lt=0;
		
		Font font = g.getFont();
		/* incializacao do nivel 1*/
		nextNivel(1);
		g.setFont(font.getFont(font.getFace(),font.getStyle(),font.SIZE_SMALL));

		/* corre o jogo */
		while(playing){
			// do stuff
			
			//int key = getKeyStates();
			int i = 0;
			int j = 0;
			
			ct = System.currentTimeMillis();
			
			if(ct-lt>systemDelay){
				
				painel.paint(g);
				
				lm.paint(g,0,0);
				painel.nextTime();
				
				/* Verifica se está a saltar, e qual a 
				   direcção do salto. Se estiver a 
				   saltar, não permite receber teclas de
				   
				   input do telemóvel */
								
				if(chuck.salto()){
					switch(chuck.jump){
					case Simbolo.UP:
						chuck.jump();
						break;
					case Simbolo.RIGHT:
						chuck.jump_dir();
						break;
					case Simbolo.LEFT:
						chuck.jump_left();
						break;
					}
				}else
					if (!(chuck.cair())){
						input();
					}


				/* Verifica colisoes com os ovos e  milho e patos */
				for(i = 0; i < Nivel.num_ovos; i++){
					if(Nivel.ovos[i] != null){
						if(chuck.collidesWith(Nivel.ovos[i],true)){
							lm.remove(Nivel.ovos[i]);
							n_ovos--;
							painel.aumentaPontos(100);
							Nivel.ovos[i] = null;
						}
					}
				}
				
				for(j = 0; j < Nivel.num_milho; j++){
					if(Nivel.milhos[j] != null){
						if(chuck.collidesWith(Nivel.milhos[j],true)){
							lm.remove(Nivel.milhos[j]);
							painel.aumentaPontos(100);
							Nivel.milhos[j] = null;
						}else
							PatoManager.colisaoComMilho(j,lm);
					}
				}               			
				
				if(PatoManager.colisaoComChuckie()){
					morte();
					Alert a = new Alert("","Morreu...",teste,null);
					Menu.chuck.display.setCurrent(a);
					sleep(3000);
				}
				
				if(n_ovos == 0){
					
					Alert a = new Alert("","Nivel Completo!",teste,null);

					Menu.chuck.display.setCurrent(a);
					sleep(3000);
					
					painel.aumentaPontos(painel.getTempo());
					painel.aumentaNivel();
					n_ovos = 0;
					resetLayer();
					nextNivel(painel.getNivel());
					
				}
				
				if(painel.getTempo() == 0){
					morte();					
					Alert a = new Alert("","Acabou o Tempo!",teste,null);
					Menu.chuck.display.setCurrent(a);
					sleep(3000);
				}
				
				if(chuck.morreu_cair == true){
				    chuck.morreu_cair = false;
				    morte();
				    Alert a = new Alert("","Morreu...",teste,null);
				    Menu.chuck.display.setCurrent(a);
				    sleep(3000);
				}
				
				PatoManager.movePatos();
				
				if(Panel.getVidas() == 0){
					Panel.reset_panel_all();
					resetLayer();
					playing = false;
				}

				/* Realização do flush
				   buffer para o ecran */
				flushGraphics();
				
				lt = ct;
			}
		}
		playing = true;
		midlet.exitToMenu();
	}

	private void input(){
		int keyStates = getKeyStates();
			
		/* Saltos. Se está a saltar para a esquerda ou para 
		   a direita, não pode saltar para cima ao mesmo tempo*/
		
		if(((keyStates & FIRE_PRESSED) != 0) & 
		   (keyStates & RIGHT_PRESSED) != 0){
			chuck.jump = Simbolo.RIGHT;
			chuck.jump_flag = true;
			chuck.jump_dir();
		}
		
		else{
			if(((keyStates & FIRE_PRESSED) != 0) & 
			   (keyStates & LEFT_PRESSED) != 0){
				chuck.jump = Simbolo.LEFT;
				chuck.jump_flag = true;
				chuck.jump_left();
				
			}
			else{
				
				if((keyStates & FIRE_PRESSED)  != 0){
					chuck.jump = Simbolo.UP;
					chuck.jump_flag = true;
					chuck.jump();
				}
			}
		}
		
		/* Movimentos básicos */
		if((keyStates & LEFT_PRESSED) != 0){
			chuck.left();
			chuck.dir_actual = Simbolo.LEFT;
		}
		
		
		if((keyStates & RIGHT_PRESSED) != 0){
			chuck.right();
			chuck.dir_actual = Simbolo.RIGHT;
		}
		
		
		if((keyStates & UP_PRESSED) != 0)
			chuck.up();
		
		
		if((keyStates & DOWN_PRESSED) != 0)
			chuck.down();
		
	}
	
	public void keyReleased(int keyCode){
		
		int kc = getGameAction(keyCode);
		
		switch(kc){
		case UP:/*{
			  chuck.direction = -2;
			  chuck.move();
			  }*/
			break;
			
		case DOWN:
			/*{
			  chuck.direction = -2;
			  chuck.move();
			  }*/
			break;

		case LEFT:
		{
			chuck.direction = -1;
			chuck.move();
		}
		break;
		case RIGHT:
		{
			chuck.direction = -1;
			chuck.move();
		}
		break;
		
		
		}
	}
}

