package pobj.pinboard.editor.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipImage;
import pobj.pinboard.editor.CommandStack;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.Command;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolImage implements Tool {
	private Clip image;
	private File filename;
	private List<Command> list=new ArrayList<>();
	public ToolImage(File filename) {
		this.filename = filename;
	}
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		image = new ClipImage(e.getX(), e.getY(), filename);
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		image.setGeometry(e.getX(), e.getY(), image.getRight(), image.getBottom());
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		CommandAdd cv=new CommandAdd(i,image);
		list.add(cv);
		cv.execute();
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName(EditorInterface editor) {
		// TODO Auto-generated method stub
		return "Image";
	}
	public void addCommand(CommandStack stack) {
		
		for(Command cmd :list) {
			stack.addCommand(cmd);
		}
	}
}
