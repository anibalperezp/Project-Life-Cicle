package ceis.grehu.units.undoManager;

import java.util.ArrayList;

import ceis.grehu.gui.paint.Relation;
import ceis.grehu.gui.paint.Stereotype;

public class UndoAction {

    private ArrayList<Stereotype> arrayActions;

    private ArrayList<Relation> arrayRelations;
    
    private String actionType = "";

    public UndoAction(String type){
	arrayActions = new ArrayList<Stereotype>();
	arrayRelations = new ArrayList<Relation>();
	actionType = type;
    }

    public String getActionType() {
	return actionType;
    }

    public void setActionType(String actionType) {
	this.actionType = actionType;
    }

    public ArrayList<Stereotype> getArrayActions() {
	return arrayActions;
    }

    public void setArrayActions(ArrayList<Stereotype> arrayActions) {
	this.arrayActions = arrayActions;
    }

    public ArrayList<Relation> getArrayRelations() {
        return arrayRelations;
    }

    public void setArrayRelations(ArrayList<Relation> arrayRelations) {
        this.arrayRelations = arrayRelations;
    }

}
