// Copyright © 2016-2018 Andy Goryachev <andy@goryachev.com>
package goryachev.fx;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;


/**
 * Context Menu.
 */
public class FxContextMenu
	extends ContextMenu
{
	public FxContextMenu()
	{
		setHideOnEscape(true);
		setAutoHide(true);
		setAutoFix(true);
	}
	
	
	public void add(MenuItem m)
	{
		getItems().add(m);
	}
	
	
	public void add(String text, FxAction a)
	{
		FxMenuItem m = new FxMenuItem(text, a);
		add(m);
	}
	
	
	public void add(String text)
	{
		FxMenuItem m = new FxMenuItem(text);
		m.setDisable(true);
		add(m);
	}
	
	
	public void separator()
	{
		add(new SeparatorMenuItem());
	}
}
