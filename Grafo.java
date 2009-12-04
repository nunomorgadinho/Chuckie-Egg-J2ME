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
public class Grafo{
	
	/** accoes do grafo */
	public final static int UP    = 0;
        public final static int DOWN  = 1;
	public final static int LEFT  = 2;
	public final static int RIGHT = 3;
	public final static int MILHO = 4;

	protected final static int V = 0;
	protected final static int C = 1;
	protected final static int E = 2;
	protected final static int F = 3;
	protected final static int M = 4;

	private final static int maxEstados=5;
	
	private NoGrafo[] estados = new NoGrafo[maxEstados];

	int[] t;

	private int estadoActual=0;
	private int pushIndice=0;
	
	public Grafo(int ea){

		estadoActual = ea;

		estados[LEFT]  = new NoGrafo(LEFT,7);
		estados[RIGHT] = new NoGrafo(RIGHT,7);
		estados[DOWN] = new NoGrafo(DOWN,7);
		estados[UP] = new NoGrafo(UP,6);

		estados[RIGHT].setAresta(RIGHT,-1,-1,C,-1,-1);
		estados[RIGHT].setAresta(RIGHT,-1,-1,F,-1,-1);
		estados[RIGHT].setAresta(LEFT,-1,C,V,-1,-1);
		estados[RIGHT].setAresta(LEFT,-1,F,V,-1,-1);
		estados[RIGHT].setAresta(DOWN,F,-1,-1,-1,-1);
		estados[RIGHT].setAresta(UP,F,-1,-1,E,E);
		estados[RIGHT].setAresta(UP,C,-1,-1,E,E);

		estados[LEFT].setAresta(LEFT,-1,C,-1,-1,-1);
		estados[LEFT].setAresta(LEFT,-1,F,-1,-1,-1);
		estados[LEFT].setAresta(RIGHT,-1,V,C,-1,-1);
		estados[LEFT].setAresta(RIGHT,-1,V,F,-1,-1);
		estados[LEFT].setAresta(DOWN,F,-1,-1,-1,-1);
		estados[LEFT].setAresta(UP,F,-1,-1,E,E);
		estados[LEFT].setAresta(UP,C,-1,-1,E,E);

		estados[DOWN].setAresta(RIGHT,F,-1,C,-1,-1);
		estados[DOWN].setAresta(RIGHT,C,-1,C,-1,-1);		
		estados[DOWN].setAresta(LEFT,F,C,-1,-1,-1);
		estados[DOWN].setAresta(LEFT,C,C,-1,-1,-1);
		estados[DOWN].setAresta(DOWN,E,V,V,-1,-1);
		estados[DOWN].setAresta(DOWN,F,-1,-1,-1,-1);
		estados[DOWN].setAresta(UP,F,-1,-1,E,E);
		
		estados[UP].setAresta(UP,-1,-1,-1,E,E);
		estados[UP].setAresta(DOWN,F,-1,-1,-1,-1);
		estados[UP].setAresta(LEFT,F,C,-1,-1,-1);
		estados[UP].setAresta(LEFT,F,F,-1,-1,-1);
		estados[UP].setAresta(RIGHT,F,-1,C,-1,-1);
		estados[UP].setAresta(RIGHT,F,-1,F,-1,-1);

		

	}
	
	public int nextAction(int[] cond){
		int novoEstado;

		if((novoEstado = estados[estadoActual].getNextEstado(cond))!=-1)
			estadoActual=novoEstado;

		return estadoActual;
	}			
	
}
