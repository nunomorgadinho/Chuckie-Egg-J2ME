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
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Image;

public class GameSprite extends Sprite{
	
	protected int xMatriz=0,yMatriz=0;
	protected int oldx=0,oldy=0;
	protected int dx=0,dy=0;
	protected static Nivel nivel= null;
	
	public GameSprite(Image img, int width, int height, int xM, int yM){
		super(img,width,height);
		
		xMatriz = xM;
		yMatriz = yM;
		
	}
	
	/** faz o mirror da sprite */
	protected void vira(){
		setTransform(Sprite.TRANS_MIRROR);
	}
	
	/** retira uma transformacao aplicada
	    anteriormente */
	protected void noTransform(){
		setTransform(Sprite.TRANS_NONE);
	}
		
}
