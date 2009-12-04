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
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.LayerManager;

public class Pato extends GameSprite{

	private int newaction;
	private int delay;
	private long ct,lt=0;
	private int direccao, direccaoOld;
	private boolean stop = false;
	private Grafo grafo;

	private int acumx=0,acumy=0;
	private boolean oneWalk = true;

	// para controlar a animacao do pato a comer o milho
	private int[] framesComeMilho={4,5};
	private int frameComeMilho = 0;
	private boolean animComeMilho = false;
	private int frameActual = -1;
	private int delayBack;

	public Pato(Image img, int d, int xM, int yM,int del){
		
		super(img,11,19,xM,yM);
		
		delay=del;
		
		direccao = d;
		direccaoOld = d;
		setFrame(0);
		
		defineReferencePixel(5,0);
		
		if(d==Simbolo.RIGHT)
			vira();
		
		switch(d){
		case Simbolo.LEFT:
			grafo = new Grafo(Grafo.LEFT);
			break;
		case Simbolo.RIGHT:
			grafo = new Grafo(Grafo.RIGHT);
			break;
		}
	}
	
	/** selecciona a frame para a direccao e
	    actualiza a posicao da sprite */
	public void move(){
		switch(direccao){
		case Simbolo.RIGHT:
			if(stop){
				setFrame(0);
				vira();
			}else{
				setFrame(1);
				vira();
			}break;
			
		case Simbolo.LEFT:
			noTransform();
			if(stop)
				setFrame(0);
			else
				setFrame(1);
			break;
			
		case Simbolo.UP:
			
		case Simbolo.DOWN:
			setFrame(2);
			if(stop){
				vira();
			}else{
				noTransform();
			}
			break;
		}
		
		stop = !stop;

		detectLock(dx,dy);

		setPosition(getX()+dx,getY()+dy);
		
		// reset deslocamento
		dx=dy=0;
	}
	
	/** decrementa o dx */
	public void esquerda(){
		direccaoOld = direccao;
		direccao = Simbolo.LEFT;

		if(oneWalk || checkOneWalk()){
			dx = -1;
			oneWalk=false;
		}else
			dx = -2;

		move();
	}
	
	/** aumenta o dx */
	public void direita(){
		direccaoOld = direccao;
		direccao = Simbolo.RIGHT;

		if(oneWalk || checkOneWalk()){
			dx = 1;
			oneWalk=false;
		}else
			dx = 2;

		move();
	}

	/** decrementa o dy */
	public void cima(){
		direccaoOld = direccao;
		direccao = Simbolo.UP;

		dy = -2;
		
		move();
	}

	/** incrementa o dy */
	public void baixo(){
		direccaoOld = direccao;
		direccao = Simbolo.DOWN;

		dy = 2;

		move();
	}
	
	/** coloca a sprite no (x,y) referente ao ecran,
	    dados o x e y em relacao a matriz do nivel*/
	public void  setPosMatriz(int x, int y){
		setPosition(x * Simbolo.frameWidth, ((Simbolo.nivelHeight-y) * Simbolo.frameHeight) + Simbolo.paintStartY - 19);
	}

	// move o pato de acordo com o grafo
	public void nextMove(){
		int tmp = yMatriz-1;

		int[] x = {xMatriz,xMatriz-1,xMatriz+1,xMatriz,xMatriz};
		int[] y = {tmp,tmp,tmp,yMatriz,yMatriz+1,yMatriz};
 
		int[] cond = {-1,-1,-1,-1,-1};


		ct=System.currentTimeMillis();
		if(ct-lt>delay){
			
			if(animComeMilho){
				comeMilho(-1,null);
			}else{

				if(acumx == 0 && acumy == 0){
					
					for(tmp=0;tmp<5;tmp++){
						
						switch(nivel.getXYnew(x[tmp],y[tmp])){
						case Simbolo.VAZIO:
							cond[tmp]=0;
							break;
						case Simbolo.CHAO:
							cond[tmp]=1;
							break;
						case Simbolo.ESCADA:
							cond[tmp]=2;
							break;
						case Simbolo.CHAO_ESCADA:
							cond[tmp]=3;
							break;
						}
					}
					
					newaction = grafo.nextAction(cond);
				}
				
				
				switch(newaction){
				case Grafo.LEFT:
					esquerda();
					break;
				case Grafo.RIGHT:
					direita();
					break;
				case Grafo.DOWN:
					baixo();
					break;
				case Grafo.UP:
					cima();
					break;
				}
				
				lt=ct;
			}
		}
	}
					  
					  


	public boolean checkOneWalk(){
		if(direccao!=direccaoOld || acumx==0)
			return true;
		else
			return false;
	}

	public void detectLock(int x, int y){

		if(x!=0){
			acumx+=x;
			
			if(acumx==11 || acumx==-11){
				if(acumx>0)
					xMatriz+=1;
				else
					xMatriz-=1;
				acumx=0;
				oneWalk = true;
				
			}
		}
		
		if(y!=0){
			acumy+=y;
			if(acumy==6 || acumy==-6){
				if(acumy<0)
					yMatriz+=1;
				else
					yMatriz-=1;
				acumy=0;

			}
		}
	}
	
	public void comeMilho(int j, LayerManager lm){

		if(frameComeMilho>2){
			delay = delayBack;
			frameComeMilho = 0;
			animComeMilho=false;			
		}else
			if(frameActual==-1){
				animComeMilho=true;
				
				frameActual = getFrame();
				
				delayBack = delay;
				
				delay *= 2 ;
			}else
				if(frameComeMilho>1){
					
					setFrame(frameActual);
					
					
					frameActual = -1;
					frameComeMilho++;
					
					lm.remove(Nivel.milhos[j]);
					Nivel.milhos[j] = null;
					
				}else{
					setFrame(framesComeMilho[frameComeMilho++]);
					if(direccao==Simbolo.RIGHT)
						vira();
				}
	}
	
}
