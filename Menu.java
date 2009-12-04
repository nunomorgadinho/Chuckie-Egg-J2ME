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
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Ticker;

public class Menu implements CommandListener{
	
	public static ChuckieMidLet chuck;
	private List menu;
	public static Ticker message;
	
	public Menu(ChuckieMidLet c){
		chuck = c;
		message = new Ticker("http://alunos.uevora.pt/~l13591/chuckie/");
	}

	public List CreateMenu(){
		
		menu = new List("Menu",List.IMPLICIT); 	
		menu.setTicker(message);
		menu.append("Iniciar",null); 
		menu.append("About",null); 
		menu.addCommand(new Command("Ok",Command.ITEM,1));
		menu.setCommandListener(this);		
		return menu;		
	}
	
	public void commandAction(Command c, Displayable d){
		
		try{
			System.out.println("Menu: "+menu.getString(menu.getSelectedIndex()));
			if(menu.getSelectedIndex() == 0){
				ChuckieMidLet.newCanvas();
				ChuckieMidLet.display.setCurrent(chuck.canvas);
				ChuckieCanvas.start();
			}
			
			if(menu.getSelectedIndex() == 1){
				Alert a = new Alert("Credits:","Claudio Fernandes,Nuno Morgadinho,Tiago Bilou e Tiago Fernandes. ",null,null);
				Menu.chuck.display.setCurrent(a);
				a.setTimeout(a.FOREVER);
				chuck.exitToMenu();
			}
		}catch(Exception e){}
	}
}

