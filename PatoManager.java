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
import javax.microedition.lcdui.game.LayerManager;

public class PatoManager{

	public static void movePatos(){
		int x;

		for(x=0;x<Nivel.num_patos;x++){
			Nivel.patos[x].nextMove();
		}
	}

	public static void appendPatosLayerManager(LayerManager lm){
		int x;

		for(x=0;x<Nivel.num_patos;x++){
			lm.append(Nivel.patos[x]);
		}
	}

	public static void inserePatosLayerManager(LayerManager lm){
		int x;

		for(x=0;x<Nivel.num_patos;x++){
			lm.insert(Nivel.patos[x],1);
		}
	}

	public static void removePatosLayerManager(LayerManager lm){
		int x;

		for(x=0;x<Nivel.num_patos;x++){
			lm.remove(Nivel.patos[x]);
		}
	}

	public static void colisaoComMilho(int j, LayerManager lm){
		int x;

		for(x=0;x<Nivel.num_patos;x++){
			if(Nivel.milhos[j]!=null && Nivel.patos[x].collidesWith(Nivel.milhos[j],true))
				Nivel.patos[x].comeMilho(j,lm);
		}
	}

	public static boolean colisaoComChuckie(){
		int x;

		for(x=0;x<Nivel.num_patos;x++){
			if(ChuckieCanvas.chuck.collidesWith(Nivel.patos[x],true))
				return true;
		}

		return false;
	}

}
