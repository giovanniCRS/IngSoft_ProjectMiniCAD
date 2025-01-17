package is.shapes.specificcommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.model.GroupObject;
import is.shapes.view.GraphicObjectPanel;

public class UngroupCommand implements Command {
    private final GraphicObjectPanel panel;
    private final GraphicObject group;
    private List<GraphicObject> ungroupedObjects; 
    private Map<GraphicObject, String> originalIds; 
    private String groupId; 

    public UngroupCommand(GraphicObjectPanel panel, GraphicObject group) {
        this.panel = panel;
        this.group = group;
        this.originalIds = new HashMap<>();
    }

    @Override
    public boolean doIt() {
        if (!(group instanceof GroupObject)) {
            System.err.println("L'oggetto specificato non è un gruppo.");
            return false;
        }
    
        // Recupera o verifica l'ID del gruppo
        groupId = getObjectId(group);
        if (groupId == null) {
            System.err.println("Errore: impossibile eseguire 'ungrp'. L'ID del gruppo è null.");
            return false;
        }
    
        // Ripristina il gruppo nel pannello se non è presente
        if (panel.getObjectById(groupId) != group) {
            panel.add(groupId, group);
            System.out.printf("[Gruppo] con ID %s è stato ripristinato per 'ungrp'.%n", groupId);
        }
    
        // Rimuovi il gruppo dal pannello
        panel.remove(groupId);
    
        // Scompatta gli oggetti del gruppo
        ungroupedObjects = ((GroupObject) group).getMembers();
        for (GraphicObject obj : ungroupedObjects) {
            String id = getObjectId(obj);
            if (id == null || panel.getObjectById(id) != null) {
                id = generaIdUnico(); // Genera un nuovo ID univoco
            }
            originalIds.put(obj, id); // Salva l'ID originale
            panel.add(id, obj);       // Aggiungi l'oggetto al pannello
        }
    
        System.out.printf("[Gruppo] con ID %s è stato sciolto.%n", groupId);
        return true;
    }

    @Override
    public boolean undoIt() {
        if (ungroupedObjects == null || groupId == null) {
            System.err.println("Impossibile annullare: nessun gruppo scompattato o ID null.");
            return false;
        }
    
        // Rimuovi gli oggetti scompattati dal pannello
        for (GraphicObject obj : ungroupedObjects) {
            String id = originalIds.get(obj);
            if (id != null && panel.getObjectById(id) == obj) {
                panel.remove(id);
            }
        }
    
        // Ripristina il gruppo nel pannello
        if (panel.getObjectById(groupId) == null) {
            panel.add(groupId, group);
            System.out.printf("[Gruppo] con ID %s è stato ricreato.%n", groupId);
            return true;
        } else {
            System.err.printf("Errore: impossibile ripristinare il gruppo, ID %s già esistente.%n", groupId);
            return false;
        }
    }

    private String generaIdUnico() {
        Random rnd = new Random(); 
        char lettera = (char) ('A' + rnd.nextInt(26)); 
        String id;
        do {
            int number = rnd.nextInt(1000); // ID a 3 cifre
            id = lettera + String.format("%03d", number);
        } while (panel.getObjectById(id) != null);
        return id;
    }

    // Trova l'ID di un oggetto nella mappa di GraphicObjectPanel.
    private String getObjectId(GraphicObject obj) {
        for (Map.Entry<String, GraphicObject> entry : panel.getObjectMap().entrySet()) {
            if (entry.getValue().equals(obj)) {
                return entry.getKey();
            }
        }
        System.err.println("Errore: Impossibile trovare l'ID per l'oggetto " + obj.getType());
        return null; // L'oggetto non è stato trovato
    }
}