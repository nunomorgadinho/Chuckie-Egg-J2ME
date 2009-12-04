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
import java.lang.Thread;
import java.io.IOException; 
import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Form;



public class ChuckieMidLet extends MIDlet{
	
	public static Display display;
	public static List menu2;
	public static Menu menu;
	public static ChuckieCanvas canvas;
	public static ChuckieMidLet midlet;
	public static Form intro;
	public static Image splashScreen;
	

	public ChuckieMidLet(){
		display = Display.getDisplay(this);
		menu = new Menu(this);
		midlet = this;

		intro = new Form("Chuckie Egg");
		

		try{
			splashScreen = Image.createImage("/chuckie/logo.png");
			
		}catch(Exception e){
			System.out.println("Erro(Nivel+createImage): "+e.toString());
		}
		
		
	}
	
	public static void newCanvas(){
		canvas = new ChuckieCanvas(false);
		canvas.setMidLet(midlet);
	}

	public void startApp(){
	 	newCanvas();
		intro.append(splashScreen);
		display.setCurrent(intro);
		canvas.sleep(10000);
		menu = new Menu(this);
		menu2 = menu.CreateMenu();
		display.setCurrent(menu2);

	}
	
	public void pauseApp(){
	}
	
	public void destroyApp(boolean value){
		Display.getDisplay(this).setCurrent(null);
	}
	
	public void exitToMenu(){
		display.setCurrent(menu2);
		
	}
	public void exitGame(){
		
		destroyApp(false);
		notifyDestroyed();
	}
}
