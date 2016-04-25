// Copyright (c) 2016 Andy Goryachev <andy@goryachev.com>
package goryachev.fxdock;
import goryachev.common.util.CList;
import goryachev.common.util.CLog;
import goryachev.common.util.CMap;
import goryachev.common.util.Log;
import goryachev.fxdock.internal.FxDockSchema;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.Node;


/**
 * FxDock Framework.
 */
public class FxDockFramework
{
	protected static final CLog log = Log.get("FxDockFramework");
	private static final CMap<Object,Object> windows = new CMap();
	
	
	public static int loadLayout(Class<? extends FxDockWindow> appWindowClass)
	{
		int ct = FxDockSchema.getWindowCount();
		for(int i=0; i<ct; i++)
		{
			try
			{
				FxDockWindow w = appWindowClass.newInstance();
				String id = FxDockSchema.windowID(i);
				
				Node n = FxDockSchema.restoreNode(id);
				w.setContent(n);
				
				FxDockSchema.restoreWindow(w, id);
				openPrivate(w, id);
			}
			catch(Exception e)
			{
				log.err(e);
			}
		}
		return ct;
	}
	
	
	public static void saveLayout()
	{
		List<FxDockWindow> ws = getWindows();
		FxDockSchema.setWindowCount(ws.size());
		
		for(FxDockWindow w: ws)
		{
			String id = getWindowID(w);
			FxDockSchema.storeNode(id, w.getContent());
			FxDockSchema.storeWindow(id, w);
		}
	}


	public static void open(FxDockWindow w)
	{
		String id = newWindowID();
		openPrivate(w, id);
	}
	
	
	private static void openPrivate(FxDockWindow w, String id)
	{
		windows.put(id, w);
		windows.put(w, id);
		
		w.showingProperty().addListener((src,old,cur) ->
		{
			if(!cur)
			{
				windows.remove(w);
				windows.remove(id);
			}
		});
		
		w.show();
	}
	
	
	private static String getWindowID(FxDockWindow w)
	{
		return (String)windows.get(w);
	}
	
	
	private static String newWindowID()
	{
		int i = 0;
		for( ; i<100000; i++)
		{
			String id = FxDockSchema.PREFIX_WINDOW + i;
			if(!windows.containsKey(id))
			{
				return id;
			}
		}
		throw new Error("?" + i);
	}
	
	
	public static List<FxDockWindow> getWindows()
	{
		CList<FxDockWindow> rv = new CList(windows.size() / 2);
		for(Object x: windows.keySet())
		{
			if(x instanceof FxDockWindow)
			{
				rv.add((FxDockWindow)x);
			}
		}
		return rv;
	}
	
	
	public static void exit()
	{
		// TODO confirm exiting
		
		saveLayout();
		
		Platform.exit();
	}
}
