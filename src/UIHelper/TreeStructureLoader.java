package UIHelper;

import andortree.AndOrTree;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ErisoHV
 */
public class TreeStructureLoader{
    private LinkedList<String> treeStructure = new LinkedList<>();
    private AndOrTree tree = new AndOrTree();
    private LinkedList<String> atomicTasks = new LinkedList<>();
    private int atomicMinimun;
    
    public void readTree(BufferedReader input) {
        treeStructure = new LinkedList<>();
        tree = new AndOrTree();
        atomicTasks = new LinkedList<>();
        atomicMinimun = 0;
        try {
            readTreeStructure(input);
            readTasksStructure(input);
            fillTreeTasksList();
            atomicMinimun = tree.getAtomicMinimum();
        } catch (IOException ex) {
            Logger.getLogger(TreeStructureLoader.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void readTreeStructure(BufferedReader input) throws IOException{
        String line = input.readLine();
        for (int i = 0; i < line.length(); i++) {
            treeStructure.addLast(String.valueOf(line.charAt(i)));
        }
        tree.createAndOrTree(treeStructure);
    }
    
    private void readTasksStructure(BufferedReader input) throws IOException{
        String line = input.readLine();
        int tasks = Integer.valueOf(line).intValue();
        for (int i = 0; i < tasks; i++) {
            line = input.readLine();
            StringTokenizer operation = new StringTokenizer(line);
            String root = operation.nextToken();
            String type = operation.nextToken();
            int actual = Integer.valueOf(operation.nextToken()).intValue();
            if (actual == 1) {
                tree.executeTask(root);
            }
            if (type.equals(AndOrTree.AND)) {
                tree.changeToAndTask(root);
            } else {
                tree.changeToOrTask(root);
            }
        }
    }
    
    private void fillTreeTasksList(){
       tree.getLeafs(atomicTasks);
    }
    
    public LinkedList<String> getTreeStructure(){
        return treeStructure;
    }
    
    public AndOrTree getAndOrTree(){
        return tree;
    }
    
    public LinkedList<String> getAtomicTasks(){
        return atomicTasks;
    }
    
    public int getAtomicMinimum(){
        return atomicMinimun;
    }
    
    public void doUndoAtomicTask(String task, boolean isExec){
        tree.setExecuteValue(task, isExec);
        //refresk the atomic minimum
        atomicMinimun = tree.getAtomicMinimum();
    }
    
    
}
