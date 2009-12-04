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
public class Simbolo{
	
	/* simbolos da matriz do nivel */
	final static char VAZIO=' ';
	final static char OVO='O';
	final static char MILHO='M';
	final static char CHUCKIE='C';
	final static char PATO='P';
	final static char CHAO='-';
	final static char CHAO_ESCADA='+';
	final static char ESCADA='|';
	
	// direcções
	final static int LEFT  = 4;
	final static int RIGHT = 6;
	final static int UP    = 8;
	final static int DOWN  = 2;
	final static int JUMP  = 10;
	final static int JL= 3;
	final static int JR = 5;
	
        // chuckie
	final static int FREEZE = -1;
	final static int STAIRS = -2;
	
	/* Nivel */
	final static int frameWidth = 11;
	final static int frameHeight = 6;
	
	/* largura x altura  da matriz */
	final static int nivelWidth=16;
	final static int nivelHeight=30;
	
	/* coordenadas onde o nivel começa
	   a ser desenhado */
	final static int paintStartX = 0;
	final static int paintStartY = 28;
	
}
