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
import java.util.Random;


public class NoGrafo{

	private static Random rand=null;
	private final static int maxCondicoes = 5;
	private int maxArestas;

	private int action;

	// {chao,chao_esq,chao_dir,up,pos}
	private Aresta arestas[];
	private int indiceAresta = 0;

	public NoGrafo(int act,int numArestas){
		action=act;		
		arestas = new Aresta[numArestas];
		maxArestas = numArestas;

		if(rand==null)
			rand = new Random();
	}
	
	private boolean satisfazCondicao(int[] c1, int[] c2){
		int x;
		boolean value=true;

		for(x=0;x<maxCondicoes;x++){
			if(c2[x]!=-1  && c1[x]!=c2[x]){
				return false;
			}
		}
		
		return value;
	}
	
	public int getNextEstado(int[] condicao){

		int[] possiveisEstados = new int[10];
		int totalEstados = 0;
		int x;

		for(x=0 ; x<maxArestas ; x++){
			if(satisfazCondicao(condicao,arestas[x].cond)){
				possiveisEstados[totalEstados++]=arestas[x].action;
			}
		}

		if(totalEstados==0)
			return -1;
		else{
			x = (int) System.currentTimeMillis() % totalEstados;//Math.abs(rand.nextInt() % totalEstados);

			return possiveisEstados[x];
				

		}
		
	}

	public void setAresta(int ligacao, int c, int c_esq, int c_dir, int up, int pos){
		Aresta tmp = new Aresta(ligacao,c,c_esq,c_dir,up,pos);

		arestas[indiceAresta++] = tmp;
	}
}
