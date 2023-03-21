package PaooGame.UI_Objects;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import PaooGame.Handler;
/*!
\class UIManager
\brief Clasa responsabila de gestionarea UI

Aici se retin toate obiectele de tip UIObject din joc.
Clasa contine metode existente in UIObject (Update, Draw, MoveMouse, MouseRelease) in care se apeleaza pentru toate obiectele retinute aceeasi metoda, plus inca doua metode
de intretinere (addObj si deleteObj);
 */
public class UIManager {
    /// Referinta catre obiectul cu referinte importante ale jocului.
    private Handler refLinks;

    ///Lista de obiecte UI.
    private ArrayList<UIObject> objects;

    /*!
   \fn public UIManager ( RefLinks refLinks )
   Constructor ce seteaza atrinutul refLinks al clasei si initializeaza lista de obiecte
    */
    public UIManager(Handler refLinks){
        this.refLinks = refLinks;
        objects = new ArrayList<UIObject>();
    }

    ///tick() pentru toate obiectele UI ale jocului
    public void tick(){
        for(UIObject o : objects)
            o.tick();
    }

    ///render pentru toate obiectele UI ale jocului
    public void render(Graphics g){
        for(UIObject o : objects)
            o.render(g);
    }

    public void onMouseMove(MouseEvent e){
        for(UIObject o : objects)
            o.onMouseMove(e);
    }

    public void onMouseRelease(MouseEvent e){
        for(UIObject o : objects)
            o.onMouseRelease(e);
    }

    public void addObject(UIObject o){
        objects.add(o);
    }

    public void removeObject(UIObject o){
        objects.remove(o);
    }

    public Handler getRefLinks() {
        return refLinks;
    }

    public void setRefLinks(Handler refLinks) {
        this.refLinks= refLinks;
    }


    public ArrayList<UIObject> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<UIObject> objects) {
        this.objects = objects;
    }

    public void Update() {
    }
}