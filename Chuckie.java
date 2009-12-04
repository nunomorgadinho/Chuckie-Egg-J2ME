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
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class Chuckie extends GameSprite{
	
	private Image chuckie;
	private Image c ;
	
	/* Variavel que determina a animacao das frames */
	private boolean stop = false;
	
	/*** Variaveis de Posicao do Chuckie ***/
	/* Deslocalmento */
	private int dx=0, dy=0;
	
	/* Coordenadas da Posicao Inicial */
	public  int initx = 66;
	public  int inity = 183;
	
	/* Coordenadas do boneco a qualquer instante */
	private int currentPixelX=initx;
	private int currentPixelY=(inity+19)-29;
	
	/* Variaveis para o calculo das coordenadas da matriz */
	private int acumx = 0, acumy = 0;
	
	/* Coordenadas da matriz */
	private int matrizX = currentPixelX / Simbolo.frameWidth;
	private int matrizY = currentPixelY / Simbolo.frameHeight;
	
	/* How the sprite is placed
	   
	        |              |
	28px	|	       |
                |--------------|
	        |	       |
 	        |	       |
	        |	       |
	        |	       |
	180px	|	       |
	        |	  o    |
	        |_________T____|
	6px	|______________|
	
	O ponto de referencia da sprite encontra-se na coordenada
	(0,0) assim, as contas que estamos a fazer referem-se
	ao canto inferior esquerdo da sprite. Uma vez que as coordenadas
	da matriz estao no canto superior esquerdo, para nos referirmos  ao chao
	necessario somar o tamanho do boneco.
	*/
	

	/*** Variaveis de controlo do movimento ****/
	/* Detectam quando eh k o boneco pode fazer um determinado movimento */
	public boolean flagdown = false;
	public boolean flagup = false;
	public boolean flagleft = true;
	public boolean flagright = true;
	
	/* Variavel de controlo para um pequeno hack.
	   Uma vez que cada elemento da matriz tem 11 pixeis de comprimento
	   e o boneco se desloca de 2 em 2 pixeis no eixo dos X optou-se por
	   realizar o movimento de 1 pixel se for o primeiro para alinhar o boneco
	   com a matriz. A variavel last_dir contem a direccao do ultimo movimento
	   realizado pelo boneco */
	public boolean first = true;
        private int last_dir = Simbolo.LEFT;
	
	/* Variavel que determina quando eh k o boneco estah alinhado com a matriz */
	private boolean lock = true;
	
	

	
	
	
	/*** Variaveis de controlo do salto ***/
	/* Utilizada no move para controlo de transformacao das sprites */
	public int dir_actual;
	
	/* Detecta o inicio e fim de um salto */
	public boolean jump_flag;
	
	/* Sempre que o chuckie salta tem de verificar se tem chao debaixo dos pes
	   Esta variavel controla um pequeno hack para isso */
	public boolean jump_cair;
	
	
	public int jump;
	private int jump_dir_size=16;
	private int jump_left_size=16;
	private int jump_size = 5;
	private int jump_time = 5;
	private int jump_index = 0;
	private int jump_dir_index=0;
	private int jump_left_index=0;
	
	/* Indica se o chuckie caiu num buraco e morreu R.I.P */
	public boolean morreu_cair = false;
	
	/* Controla se o boneco esta a cair ou nao */
	private boolean cair = false;
	
	
	/* vector que define os saltos direccionais */
	private int jump_dir[] ={1,2,1,2,1,1,1,1,
				 -1,-1,-1,-1,-2,-1,-2,-1};
	private int jump_left[] ={1,2,1,2,1,1,1,1,
				  -1,-1,-1,-1,-2,-1,-2,-1};
	
	
	/* Flag que define qual o movimento actual do Chuckie. 
	   Usada no move() para a transformacao das sprites */
	public int direction;
	
	/* Sprite do chuckie */
	private GameSprite chuck_sprite;
	
	
	/* Constructor */ 
	public Chuckie(Image img, int x, int y){
		super(img,11,19,x,y);
		
		/* Posicao inicial do chuckie */
		setRefPixelPosition(initx, inity);
		
		/* definicao do eixo de rotacao */
		defineReferencePixel(5,0);
		setFrame(0);
		direction = -1;
		jump_flag = false;
	}
	
	public void reset_move(){
		dx=0;
		dy=0;
	}
	
	public void resetupdown(){
		flagup = false;
		flagdown = false;
	}
	
	public void resetleftright(){
		flagleft = false;
		flagright = false;
	}
	
	public void setleftright(){
		flagleft = true;
		flagright = true;
	}
	
	public void setupdown(){
		flagup = true;
		flagdown = true;
	}
	
	public boolean salto(){
		return jump_flag;
	}
	

	/* Funcao que detecta quando eh k o boneco chega ao chao */
	public boolean cair(){
		
		if(cair){
			/* Hack para evitar que o chuckie quando salta em cima de uma plataforma
			   verifique primeiro o chao debaixo dos pes e nao abaixo deles */
			if(jump_cair)
				dy = 0;
			else
				dy = +2;
			
			dx = 0;
			/* Actualizamos o acumulador e a posicao da matriz */
			
			detectLock(dx,dy);
			
			currentPixelX = currentPixelX+dx;
			currentPixelY = currentPixelY+dy;
			
			if (acumy == 0){
				if ((nivel.getXY(matrizX,matrizY+1) == Simbolo.CHAO) | 
				    (nivel.getXY(matrizX,matrizY+1) == Simbolo.CHAO_ESCADA)){
					cair = false;
				}
				else{
					cair = true;
				}
			}
			/* Detecta se caiu num buraco */
			if(currentPixelY > 173){
				cair = false;
				morreu_cair = true;
			}
			
			setPosition(getX()+dx,getY()+dy);
			reset_move();
		}
		/* Fim do Hack */
		jump_cair = false;
		return cair;
	}



	
	public void move(){
		switch(direction){
		case Simbolo.RIGHT:			
			if(stop){
				setFrame(0);
				vira();
			}
			else{
				setFrame(1);
				vira();
			}
			break;
			
		case Simbolo.LEFT:
			if(stop){
				setFrame(0);
				noTransform();
			}
			else
			{
				setFrame(1);
				noTransform();
			}
			break;
			
		case Simbolo.UP:
			if(stop){
				setFrame(2);
				vira();
			}
			else
			{
				setFrame(2);
				noTransform();
			}
			break;
			
		case Simbolo.DOWN:
			if(stop){
				setFrame(2);
				vira();
			}
			else
			{
				setFrame(2);
				noTransform();
				
			}
			break;
			
			/* Detecta quando o boneco fica parado. Junta as pernas */
		case Simbolo.FREEZE:
			setFrame(0);
			break;
			
		case Simbolo.JUMP:
			if(dir_actual == Simbolo.RIGHT){
				if(stop){
					setFrame(0);
					vira();
				}
				else
				{
					setFrame(1);
					vira();
				}
			}
			else{
				if(stop){
					setFrame(0);
					noTransform();
				}else{
					setFrame(1);
					noTransform();
				}
			}
			
			break;
			
		case Simbolo.JR:
			if(stop){
				setFrame(0);
				vira();
				
			}else{
				setFrame(1);
				vira();
			}
			break;
			
		case Simbolo.JL:
			if(stop){
				setFrame(0);
				noTransform();
			}
			else{
				setFrame(1);
				noTransform();
			}
			break;
			
		}
		
		stop = !stop;
		
		/* Coloca o boneco no ecra com a transformacao certa */
		setPosition(getX()+dx,getY()+dy);
	}
	
	
	public void jump(){
		
		if(jump_index <= jump_size){
			dy =-2;
			dx = 0;
			jump_index+=1;
		}
		
		if(jump_index > jump_size){
			dy = 2;
			dx = 0;
			jump_time--;
		}
		
		if(jump_time == 0){
			jump_index = 0;
			jump_time = 5;
			jump_flag = false;
			
		}
		
		direction = Simbolo.JUMP;
		move();
		reset_move();
	}
	
	
	
	public void jump_dir(){
		jump_cair = true;
		dx = 2;

		if(!checkLimit(dx,0))
			dx = 0;
		
		dy=-jump_dir[jump_dir_index++];
		detectLock(dx,dy);
		currentPixelX += dx;
		currentPixelY += dy;
		
		if(jump_dir_index>=jump_dir_size){
			jump_dir_index=0;
			jump_flag = false;
			cair = true;
			if (first)
			    first = false;
			else
			    first = true;
		}
		direction = Simbolo.JR;
		move();
		reset_move();
		/* BUG FIX */
		setleftright();
	}
	
	
	public void jump_left(){
		jump_cair = true;
		dx = -2;

		if(!checkLimit(dx,0))
			dx = 0;
		
		dy=-jump_left[jump_left_index++];
		detectLock(dx,dy);
		currentPixelX += dx;
		currentPixelY += dy;
		
		if(jump_left_index>=jump_left_size){
			jump_left_index=0;
			jump_flag = false;
			cair = true;
			if (first)
			    first = false;
			else
			    first = true;
		}
		
		direction = Simbolo.JL;
		move();
		reset_move();
		/* BUG FIX */
		setleftright();
		
	}
	
	
	/* Actualiza o acumulador e detecta quando muda de quadrante da matriz */
	public void detectLock(int ax, int ay){
		/* Actualiza o acumulador */
		acumx+=ax;
		acumy+=ay;

		/* COORDENADAS HORIZONTAIS */
		if( acumx >= Simbolo.frameWidth){
			/* Passamos para outra posicao da matriz */
			matrizX++;
			acumx = acumx - Simbolo.frameWidth;
		}
		else if( acumx < 0){
			matrizX--;
			acumx = Simbolo.frameWidth + acumx;
		}
		
		/* COORDENADAS VERTICAIS */
		if (acumy >= Simbolo.frameHeight) {
			matrizY++;
			acumy -= Simbolo.frameHeight;
		}
		else if( acumy < 0){
			matrizY--;
			acumy = Simbolo.frameHeight + acumy;
		}
		
	}
	
	/* Verifica quando eh k o boneco esta em sintonia, isto eh quando esta enquadrado numa
	   posicao da matriz. Se estiver alinhado chama a funcao chama o what2do para ver o k pode fazer */
	
	public void checkLock(){
		/* Vamos observar as condicoes */
		if ( (acumy == 0) & (acumx != 0) ){
			resetupdown();
			setleftright();
		}
		else if ( (acumx == 0) & (acumy != 0)){
			resetleftright();
			setupdown();
		}
		else if( (acumx == 0) & (acumy == 0) ){
			what2do();
			first = true;
			
		}
	}
	

	/* Funcao mais feia do trabalho!
	   Consoante o k encontra activa ou desactiva as flags */
	public void what2do(){
		/* Estamos perante escadas */
		if ( (nivel.getXY(matrizX,matrizY) == Simbolo.ESCADA) & 
		     (nivel.getXY(matrizX,(matrizY-1)) != Simbolo.VAZIO)){
			flagup=true;
			resetleftright();
		}
		else if( (nivel.getXY(matrizX,matrizY) == Simbolo.ESCADA) & 
			 (nivel.getXY(matrizX,(matrizY-1)) == Simbolo.VAZIO)){
			flagup=false;
			setleftright();
		}
		if ( (nivel.getXY(matrizX,matrizY) == Simbolo.ESCADA) & 
		     (nivel.getXY(matrizX,(matrizY+1)) != Simbolo.CHAO)){
			flagdown=true;
		}
		else if( (nivel.getXY(matrizX,matrizY) == Simbolo.ESCADA) & 
			 (nivel.getXY(matrizX,(matrizY+1)) == Simbolo.CHAO)){
			flagdown=false;
			setleftright();
		}
		
		if ((nivel.getXY(matrizX,matrizY) == Simbolo.ESCADA) & 
		    (nivel.getXY(matrizX,matrizY+1) == Simbolo.CHAO_ESCADA)){
			setleftright();
		}
		
		if( (nivel.getXY(matrizX,matrizY+1) == Simbolo.VAZIO) | 
		    (nivel.getXY(matrizX,matrizY+1) == Simbolo.OVO) |
		    (nivel.getXY(matrizX,matrizY+1) == Simbolo.MILHO)){
			cair = true;
		}
	}
	
	
	public boolean checkLimit(int x, int y){
		if ( ((currentPixelX+x) < 0) | ((currentPixelX+x) > 165) ){
			reset_move();
			return false;
		}
		/* TODO 
		   Detectar colisoes horizontais */
		
		else return true;
	}
	
	

	public void left(){
		if (flagleft){
		    if (last_dir == Simbolo.RIGHT)
			first = true;
		    
		    if (first)
			dx = -1;
		    else
			dx = -2;
		    
		    first = false;
		    dy = 0;
		    
		    if(checkLimit(dx,dy)){
			    
			    detectLock(dx,dy);
			    checkLock();
			    
			    currentPixelY = currentPixelY+dy;
			    currentPixelX = currentPixelX+dx;
			    
			    direction = Simbolo.LEFT;
			    move();
			    reset_move();
		    }
		}
		last_dir = Simbolo.LEFT;		
	}
	

	public void right(){
		if (flagright){
		    if (last_dir == Simbolo.LEFT)
			first = true;
		    
		    if (first)
			dx = 1;
		    else
			dx = 2;
		    
		    first = false;
		    dy = 0;
			
			if(checkLimit(dx,dy)){

			    detectLock(dx,dy);
			    checkLock();
				
			    currentPixelY = currentPixelY+dy;
			    currentPixelX = currentPixelX+dx;

			    direction = Simbolo.RIGHT;
			    move();
			    reset_move();
			}
			last_dir = Simbolo.RIGHT;
		}
		
	}
	
	public void up(){
		if (flagup){
			dy = -2;
			dx = 0;
			
			
			detectLock(dx,dy);
			checkLock();
			
			currentPixelY = currentPixelY+dy;
			currentPixelX = currentPixelX+dx;
			
			direction = Simbolo.UP;
			move();
			reset_move();
			
		}
		
		
	}
	
	public void down(){
		if (flagdown){
			dy = 2;
			dx = 0;
			

			detectLock(dx,dy);
			checkLock();

			currentPixelY = currentPixelY+dy;
			currentPixelX = currentPixelX+dx;
			
			direction = Simbolo.DOWN;
			move();
			reset_move();
		}
	}
}
