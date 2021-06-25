package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;

public class Clipboard{
   private List<Clip> copy;
   private List<ClipboardListener> list;
   private static Clipboard singleton;
   
   private Clipboard() {
	   copy=new ArrayList<>();
	   list=new ArrayList<>();
   }
   
   public void copyToClipboard(List<Clip> clips) {
	   clear();
	   for (Clip e :clips) {
		   copy.add(e.copy());
	   }
	   informeAll();
   }
   
   
   public List<Clip> copyFromClipboard(){
	   List<Clip> copie=new ArrayList<>();
	   for(Clip e : copy) {
		   copie.add(e.copy());
	   }
	   informeAll();
	   return copie;
   }
   public void clear() {
	   copy.clear();
	   informeAll();
   }
   public boolean isEmpty() {
	  return copy.isEmpty();
   }
   public static Clipboard getInstance() {
	   if(singleton == null)
			singleton=new Clipboard();
		return singleton;
   }
   public void addListener(ClipboardListener listener) {
	   list.add(listener);
   }
   public void removeListener(ClipboardListener listener) {
	   list.remove(listener);
   }
   
   public void informeAll() {
	   for(ClipboardListener elem : list) {
		   elem.clipboardChanged();
	   }
   }
   
}
