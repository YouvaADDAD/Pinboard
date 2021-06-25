package pobj.pinboard.editor;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import pobj.pinboard.document.Board;
import pobj.pinboard.editor.tools.Tool;
import pobj.pinboard.editor.tools.ToolEllipse;
import pobj.pinboard.editor.tools.ToolImage;
import pobj.pinboard.editor.tools.ToolPincil;
import pobj.pinboard.editor.tools.ToolRect;
import pobj.pinboard.editor.tools.ToolSelection;
import pobj.pinboard.editor.Selection;
import pobj.pinboard.editor.commands.Command;
import pobj.pinboard.editor.commands.CommandAdd;
import pobj.pinboard.editor.commands.CommandGroup;
import pobj.pinboard.editor.commands.CommandRemove;
import pobj.pinboard.editor.commands.CommandUngroup;

public class EditorWindow implements EditorInterface,ClipboardListener {
	//outils
	private Board planch=new Board();
	private Tool outilCourant;
	private Canvas canvas = new Canvas(800, 600);
	private VBox vbox = new VBox();
	private Selection select=new Selection();
    private CommandStack stack=new CommandStack();
    private ColorPicker colorPicker = new ColorPicker();
    private Color color;
    
	//les menus
	MenuBar menu=new MenuBar();
    Menu file=new Menu("File");
	Menu edit=new Menu("Edit");
	Menu tools =new Menu("Tools");
	//les buttons
	Button box = new Button("Box");
	Button ellipse = new Button("Ellipse");
	Button img = new Button("Img...");
	Button Select=new Button("Select");
	Button Pincil=new Button("Pincil");
	
	//new && close
	MenuItem New=new MenuItem("New");
	MenuItem Close=new MenuItem("Close");
	MenuItem Open=new MenuItem("Open");
	MenuItem Save=new MenuItem("Save");
	//Rectangle && Ellipse
	MenuItem Rectangle=new MenuItem("Rectangle");
	MenuItem Ellipse=new MenuItem("Ellipse");
	
    //copy & paste & Delete
	MenuItem Copy=new MenuItem("Copy");
	MenuItem Paste=new MenuItem("Paste");
	MenuItem Delete=new MenuItem("Delete");
	MenuItem  Group=new MenuItem("Group");
	MenuItem  Ungroup=new MenuItem("Ungroup");
	MenuItem Undo=new MenuItem("Undo");
	MenuItem Redo=new MenuItem("Redo");
	
	public EditorWindow(Stage stage) {
		stage.setTitle("PinBoard");
		clipboardChanged();
	    //Ajout du Menu file Edit Tools
		menu.getMenus().addAll(file, edit, tools);
		
		//ajout des buttons
		ToolBar menuBoutton=new ToolBar(box,ellipse,img,Select,Pincil,colorPicker);
	
	    //Ajout Separateur et label
        Separator s = new Separator();
		Label label = new Label("");
		
		//Ajout new et close a file
		file.getItems().addAll(New, Close,Save,Open);
		
		//Ajout Option Ractangle et Ellipse
		tools.getItems().addAll(Rectangle, Ellipse);
		
		//Ajout Copy Paste Delete a Edit
		edit.getItems().addAll(Copy, Paste,Delete,Group,Ungroup,Undo,Redo);
		
		//Ajout action pour New et Close
		New.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				new EditorWindow(new Stage());
			}
		});
		
		Close.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Clipboard.getInstance().removeListener(EditorWindow.this);
				stage.close();
				
			}
			
		});
		
		
		//Ajout Action pour box et ellipse
		box.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				outilCourant = new ToolRect();
				((ToolRect) outilCourant).setColor(EditorWindow.this.color);
				label.setText("Filled "+ outilCourant.getName(EditorWindow.this) +" tool");
				
			}
		});
		

		ellipse.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				outilCourant = new ToolEllipse();
				((ToolEllipse) outilCourant).setColor(EditorWindow.this.color);
				label.setText("Filled "+ outilCourant.getName(EditorWindow.this) +" tool");
				
			}
		});
		
		Pincil.setOnAction((e)->{
			outilCourant = new ToolPincil(EditorWindow.this.canvas.getGraphicsContext2D());
			((ToolPincil) outilCourant).setColor(EditorWindow.this.color);
			label.setText("Filled "+ outilCourant.getName(EditorWindow.this) +" tool");
			
		});
		//Option Action
		Rectangle.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				outilCourant = new ToolRect();
				((ToolRect) outilCourant).setColor(EditorWindow.this.color);
				label.setText("Filled "+ outilCourant.getName(EditorWindow.this) +" tool");
				
			}
		});
		
		Ellipse.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				outilCourant = new ToolEllipse();
				((ToolEllipse) outilCourant).setColor(EditorWindow.this.color);
				label.setText("Filled "+ outilCourant.getName(EditorWindow.this) +" tool");
				
			}
		});
		
		
		//Inserer Image
		img.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Resource File");
				fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
				File selectedFile = fileChooser.showOpenDialog(stage);
				 if (selectedFile != null) {
					 outilCourant = new ToolImage(selectedFile);
						label.setText("Filled "+ outilCourant.getName(EditorWindow.this) +" tool");
				 }
				
				
			}
		});
		colorPicker.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				EditorWindow.this.color=colorPicker.getValue();
				if(outilCourant!=null) {
					if(outilCourant instanceof ToolRect) {
						((ToolRect) outilCourant).setColor(colorPicker.getValue());
					}
					if(outilCourant instanceof ToolEllipse) {
						((ToolEllipse) outilCourant).setColor(colorPicker.getValue());
					}
					if(outilCourant instanceof ToolPincil) {
						((ToolPincil) outilCourant).setColor(colorPicker.getValue());
					}
				}
			}
			
		});
		
		//Action Copy Paste Select Delete Group Ungroup
		Copy.setOnAction((e) -> {
			Clipboard.getInstance().copyToClipboard(select.getContents());
			clipboardChanged();
		});
		
		Paste.setOnAction((e) -> {
			//planch.addClip(Clipboard.getInstance().copyFromClipboard());
			Command c = new CommandAdd(EditorWindow.this, Clipboard.getInstance().copyFromClipboard());
			stack.addCommand(c);
			c.execute();
			clipboardChanged();
		});
		
		Delete.setOnAction((e) -> {
			//planch.removeClip(select.getContents());
			Command c = new CommandRemove(EditorWindow.this, select.getContents());
			stack.addCommand(c);
			c.execute();
			clipboardChanged();
		});
		
		
		Select.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				outilCourant=new ToolSelection();
				label.setText("Filled "+ outilCourant.getName(EditorWindow.this) +" tool");
			}
			
		});
		
		Group.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Command c = new CommandGroup(EditorWindow.this, select.getContents());
				stack.addCommand(c);
				c.execute();
				clipboardChanged();
			}
			
		});
		
		Ungroup.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Command c = new CommandUngroup(EditorWindow.this, select.getContents());
				stack.addCommand(c);
				c.execute();
				clipboardChanged();
				
			}
		});
		Undo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				stack.undo();
				clipboardChanged();
				
			}
			
		});
		Redo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				stack.redo();
				clipboardChanged();
			}
			
		});
		
		//Open & Save
		Open.setOnAction((e)->{
            FileChooser openFile = new FileChooser();
            openFile.setTitle("Open File");
            File file = openFile.showOpenDialog(stage);
            if (file != null) {
                try {
                    InputStream io = new FileInputStream(file);
                    Image img = new Image(io);
                    canvas.getGraphicsContext2D().drawImage(img, 0, 0);
                } catch (IOException ex) {
                    System.out.println("Error!");
                }
            }
        });
		
		Save.setOnAction((e)->{
            FileChooser savefile = new FileChooser();
            savefile.setTitle("Save File");
            
            File file = savefile.showSaveDialog(stage);
           if (file != null) {
                try {
                    WritableImage writableImage = new WritableImage(1080, 790);
                    canvas.snapshot(null, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", file);
                } catch (IOException ex) {
                    System.out.println("Error!");
                }
            }
            
        });
		//Dessine
		canvas.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				if(outilCourant instanceof ToolPincil) {
					outilCourant.press(EditorWindow.this, event);
				}else {
					outilCourant.press(EditorWindow.this, event);
					draw();
					
				}

			}
			
		});
		canvas.setOnMouseDragged(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				if(outilCourant instanceof ToolPincil) {
					outilCourant.drag(EditorWindow.this, event);
				}else {
					outilCourant.drag(EditorWindow.this, event);
					draw();
					
				}
				
			}
			
		});
		canvas.setOnMouseReleased(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				if(outilCourant instanceof ToolPincil) {
					outilCourant.release(EditorWindow.this, event);
				}else {
					outilCourant.release(EditorWindow.this, event);
					draw();
					
				}
				
			}
			
		});
		vbox.getChildren().addAll(menu,menuBoutton,canvas);
		vbox.getChildren().add(s);
		vbox.getChildren().add(label);
		stage.setScene(new javafx.scene.Scene(vbox));
		stage.show();
		
		
	}
	@Override
	public Board getBoard() {
		// TODO Auto-generated method stub
		return planch;
	}
	@Override
	public Selection getSelection() {
		// TODO Auto-generated method stub
		return select;
	}
	@Override
	public CommandStack getUndoStack() {
		// TODO Auto-generated method stub
		return stack;
	}
	
	public void draw() {
		// TODO Auto-generated method stub
		planch.draw(canvas.getGraphicsContext2D());
		outilCourant.drawFeedback(EditorWindow.this, canvas.getGraphicsContext2D());
	}
	
	
	@Override
	public void clipboardChanged() {
		// TODO Auto-generated method stub
		if(Clipboard.getInstance().isEmpty()||getSelection().getContents().isEmpty()){
		   Paste.setDisable(true);
		}else {
			Paste.setDisable(false);
		}
		if(stack.isUndoEmpty()) {
			Undo.setDisable(true);
		}else {
			Undo.setDisable(false);
		}
		if(stack.isRedoEmpty()) {
			Redo.setDisable(true);
		}else {
			Redo.setDisable(false);
		}
	}
	
}
