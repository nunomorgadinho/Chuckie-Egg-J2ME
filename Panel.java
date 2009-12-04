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
import javax.microedition.lcdui.game.Layer;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Image;

public class Panel{

	private final static int fontColor = 0xefbe15;
	private static int delay;
	private static long lt=0,ct;
	
	private static int pontos;
	private static int tempo;
	private static int vidas;
	private static int n_nivel;
	private static Sprite img_chuck;
	
	private static final int tempoInicial = 500;

	public Panel(int d){
		delay=d;
		n_nivel = 1;
		reset_panel_all();

		try{
			
			img_chuck = new Sprite(Image.createImage("/chuckie/chuck.png"),11,19);
			img_chuck.setFrame(0);

		}catch(Exception e){}
	}

	public static void reset_panel_all(){
		
		pontos=0;
		tempo=tempoInicial;
		vidas=3;
		n_nivel = 1;
		
	}

	public void reset_time(){
		tempo = tempoInicial;
	}

	/* devolve o nivel actual */
	public int getNivel(){
		return n_nivel;
	}
	/** devolve o numero de vidas */
	public static int getVidas(){
		return vidas;
	}

	/** devolve o tempo */
	public int getTempo(){
		return tempo;
	}

    

	/** devolve os pontos */
	public int getPontos(){
		return pontos;
	}
	

	/** decrementa uma vida */
	public void chuckieKilled(){
		vidas-=1;
	}

	/** aumenta os pontos em n unidades */
	public void aumentaPontos(int n){
		pontos+=n;
	}
	
	/* aumenta de nivel */
	public void aumentaNivel(){
		n_nivel++;
	}

	/** decrementa uma unidade ao tempo */
	public void nextTime(){

		ct=System.currentTimeMillis();
		if(ct-lt > delay){
			tempo-=1;
			lt = ct;
		}
	}
	
	public void paint(Graphics g){
		
		int oldColor = g.getColor();
		int i,x=140,y=2;

		String pt = new String("Pontos: "+pontos);
		String tp = new String("Tempo: "+tempo);

		// coloca o fundo a preto
		g.fillRect(0,0,176,28);

		// muda a cor da fonte
		g.setColor(fontColor);
	
		// desenha os pontos e tempo
		g.drawString(pt,2,2,0);
		g.drawString(tp,2,15,0);

		// repoe a cor antiga
		g.setColor(oldColor);

		// desenha as vidas
		for(i=0;i<vidas;i++){
			img_chuck.setPosition(x+(i*11),y);
			img_chuck.paint(g);
		}
		
		
	}
	
}
