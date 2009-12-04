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
/* Unica classe que tem metodos para trabalhar com os niveis */

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;
import javax.microedition.lcdui.game.Layer;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Graphics;
import java.util.Vector;
/**
   Classe principal, Nivel() 
*/

public class Nivel{
	
	/* para desenhar o nivel  */
	/* img_nivel_samples[chao,chao+escada,escada] */
	
	private static TiledLayer img_nivel_samples = null;	
	private static TiledLayer img_fundo_samples = null;
	
	/* Variaveis para guardar as sprites dos ovos e do milho */
	
	public static Sprite ovo;
	public static Sprite milho;
	public static Image ovo_image;
	public static Image milho_image;
	public static Image pato_image;
	public static Sprite ovos[] = new Sprite[15];
	public static Sprite milhos[] = new Sprite[15];
	public static Pato patos[] = new Pato[15];
	public static Pato pato;
	public static int num_patos;
	public static int num_ovos;
	public static int num_milho;
	
	public static char[][] matriz = new char[Simbolo.nivelWidth][Simbolo.nivelHeight];
	
	
	/* Construtor, não recebe nada */
	public Nivel(){
		try{

			ovo_image=milho_image=Image.createImage("/niveis/filmstrip.png");
			pato_image = Image.createImage("/pato/filmstrip.png");

			img_nivel_samples = new TiledLayer(Simbolo.nivelWidth,Simbolo.nivelHeight,Image.createImage("/niveis/filmstrip.png"),Simbolo.frameWidth,Simbolo.frameHeight);
			img_nivel_samples.setPosition(Simbolo.paintStartX,Simbolo.paintStartY);

			img_fundo_samples = new TiledLayer(Simbolo.nivelWidth,Simbolo.nivelHeight,Image.createImage("/niveis/fundo.png"),Simbolo.frameWidth,Simbolo.frameHeight);
			img_fundo_samples.setPosition(Simbolo.paintStartX,Simbolo.paintStartY);
			
		}catch(Exception e){
			System.out.println("Erro(Nivel+createImage): "+e.toString());
		}
	}

	public static void resetPato(){
		int x,y;
		int yLimit=Simbolo.nivelHeight-1;

		try{	
			num_patos = 0;
			
			for(y=yLimit ; y >=0 ; y--){
				for(x=0 ; x<Simbolo.nivelWidth ; x++){
					
					if( matriz[x][y] == Simbolo.PATO){
						patos[num_patos]=new Pato(pato_image,Simbolo.LEFT,x,yLimit-y,ChuckieCanvas.patoDelay);
						patos[num_patos].setPosMatriz(x,yLimit-y);
						patos[num_patos++].setFrame(0);
					}
				}
			}

		}catch(Exception e){
			System.out.println("Erro(Nivel+createImage): "+e.toString());
		}
	}
	
	public TiledLayer getLayerNivel(){
		return img_nivel_samples;
	}
	
	public TiledLayer getLayerFundo(){
		return img_fundo_samples;
	}
	
	/** inicializa a variavel nivel com o nivel n escolhido */
	public static void initNivel(int n){
		String[] info;
		int x,y;
		int xLimit=Simbolo.nivelWidth-1, yLimit=Simbolo.nivelHeight-1;
		int valor;
		num_milho = 0;
		num_patos = 0;
		num_ovos  = 0;
		
		switch(n){
		case 1:
		    info=nivel1();
		    break;
		case 2:
		    info=nivel2();
		    break;
		case 3:
		    info=nivel3();
		    break;
		default:
		    info=nivel4();
		}
		
		for(y=yLimit ; y >=0 ; y--){
			for(x=0 ; x<Simbolo.nivelWidth ; x++){
				matriz[x][y] = info[y].charAt(x);
				switch(matriz[x][y]){
				case Simbolo.CHAO:
					valor=1;
					img_nivel_samples.setCell(x,y,valor);
					break;
				case Simbolo.CHAO_ESCADA:
					valor=2;
					img_nivel_samples.setCell(x,y,valor);
					break;
				case Simbolo.ESCADA:
					valor=3;
					img_nivel_samples.setCell(x,y,valor);
					break;
				case Simbolo.OVO:
					/* Criacao de uma sprite para cada ovo encontrado
					   na matriz do nivel sua colocacao num array */
					ovo = new Sprite(ovo_image,11,6);
					ovo.setFrame(3);
					ovos[num_ovos] = ovo;
					num_ovos++;
					
					/* definição da posição real  de cada sprite de ovo 
					   no ecrã */ 
					ovo.setPosition(x * Simbolo.frameWidth, ((Simbolo.nivelHeight-(30-y)) * Simbolo.frameHeight) + Simbolo.paintStartY);
					break;
				case Simbolo.MILHO:
					/* Criacao de uma sprite para cada milho encontrado
					   na matriz do nivel e sua colocacao num array */
					milho = new Sprite(milho_image,11,6);
					milho.setFrame(4);
					milhos[num_milho] = milho;
					num_milho++;
					
					/* definição da posição real  de cada sprite de
					   milho no ecrã */ 
					milho.setPosition(x * Simbolo.frameWidth, ((Simbolo.nivelHeight-(30-y)) * Simbolo.frameHeight) + Simbolo.paintStartY);
					break;
				case Simbolo.PATO:
					patos[num_patos]=new Pato(pato_image,Simbolo.LEFT,x,yLimit-y,ChuckieCanvas.patoDelay);
					patos[num_patos].setPosMatriz(x,yLimit-y);
					patos[num_patos++].setFrame(0);
					break;
					
				}
				
				img_fundo_samples.setCell(x,y,1);
			}
		}

	}
	/** devolve o caracter da posição (x,y) que esta no nivel. referencial(0,0)
	    e o canto inferior esquerdo */
	public static char getXYnew(int x, int y){
		if(x>Simbolo.nivelWidth-1 || x<0 || y<0 || y>Simbolo.nivelHeight-1){
			return Simbolo.VAZIO;
		}

		return matriz[x][Simbolo.nivelHeight-y-1];
	}
	
	/** getXY com o (0,0) no canto superior esquerdo */
	public static char getXY(int x, int y){
		return matriz[x][y];
	}
	
	/** altera a posição (x,y) do nivel */
	public static void setXY(int x, int y, char valor){
		matriz[x][y]=valor;
	}

	/** 1º nivel do chuckie */

	public static String[] nivel1(){
		String[] nivel1={
			"                ",
			"                ",
			"                ",
			"                ",
			"    O|PM  OM   O",
			"    -+-- ---- --",
			"     |          ",
			"     |          ",
			"     |          ",
			"     |  OM      ",
			"   M |  --      ",
			"   --+      M   ",
			"     |      -   ",
			"     |     -    ",
			"     |    -     ",
			"     |  O-      ",
			"  |OM| P-   M O ",
			"  +--+--    --- ",
			"  |  |          ",
			"  |  |          ",
			"  |  |          ",
			"  |  |          ",
			" O|M |  | OM |O ",
			" ----+--+----+- ",
			"     |  |    |  ",
			"     |  |    |  ",
			"     |  |    |  ",
			"     |  |    |  ",
			" MO  |C | M  |  ",
			"----------------"
		};
		
		return nivel1;
	}
	

/** 2º nivel do chuckie */
	public static String[] nivel2(){
		String[] nivel2={
			"                ",
			"                ",
			"                ",
			"                ",
			"    |P  | M O |O",
			"    +---+ ----+-",
			"    |   |     | ",
			"    |   |     | ",
			"    |   |     | ",
			"    |   |     | ",
			" M| ||O |M |  | ",
			" -+--+--+- +----",
			"  |  |  |  |    ",
			"  |  |  |  |    ",
			"  |  |  |  |    ",
			"  |  |  |  |    ",
			" O|  |O |  | |O ",
			" -+ -+------ +--",
			"  |  |       |  ",
			"  |  |       |  ",
			"  |  |       |  ",
			"  |  |       |  ",
			" O|O |  |  O |M ",
			" -+--- -+- - +--",
			"  |     |    |  ",
			"  |     |    |  ",
			"  |     |    |  ",
			"  |     |    |  ",
			" M| O   | OM |  ",
			"--- ------------"
		};
		
		return nivel2;
	}


/** 2º nivel do chuckie */
	public static String[] nivel3(){
		String[] nivel3={
			"                ",
			"               O",
			"            |- -",
			"            +   ",
			"  |O   |M|  |   ",
			" -+-  -+-+- |   ", 
			"  |    | | M|   ",
			"  |    | | -- O ",
			"  |    | |   -- ",
			"  |    | |      ",
			" O|    | |      ",
			"--+-   | | - O  ",
			"  |    | |   - |",
			"  |    | |    -+",
			"  |    | |     |",
			"  |    | |     |",
			"M||O   | |   OM|",
			"-+--   | |   ---",
			" |     | |  -   ",
			" |     | | -    ",
			" |     | |      ",
			" |     | |      ",
			" |    M|O|    O|",
			" | O  ----   --+",
			" | -           |",
			" |             | ",
			" |             |",
			" |             |",
			" |M       M O  |",
			"----  -- -------"
		};
		
		return nivel3;
	}

/** 2º nivel do chuckie */
	public static String[] nivel4(){
		String[] nivel4={
			"                ",
			"                ",
			"             O  ",
			"                ",
			"O     M|    |  |",
			"-- -- -+-  -+-O+", 
			"       |    |  |",
			"       |    |  |",
			"      -+    |  |",
			"MMM    |    |  |",
			"--- -  |   M| O|",
			"       |   -+ -|",
			"       |    |  |",
			"     M-+-   |  |",
			" O  -- |    |   ",
			" ---   |    |   ",
			"       |   O|   ",
			"O     -+   ---  ",
			"-      |       O",
			"       |       -",
			"       |        ",
			"       |        ",
			" M |  O|     |O ",
			"---+- -+-  --+- ",
			"   |   |     |  ",
			"   |   |     |  ",
			"   |   |     |  ",
			"   |   |     |  ",
			"O  |   |M  O |  ",
			"----- ---  -----"
		};
		
		return nivel4;
	}

}
