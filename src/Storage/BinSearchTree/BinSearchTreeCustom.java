package Storage.BinSearchTree;

import PersonBuilder.Employee;
import Storage.Database.StorageCustom;
import Storage.Database.StorageEmp;

import java.util.Timer;
import java.util.TimerTask;

public class BinSearchTreeCustom {

    Timer timer = new Timer();
    private BNode root;
    private String display = "";

    public BinSearchTreeCustom() {
        root = null;
        timer.schedule(new Backup(), 1800000);
    }

    //Methods
    private void insert(BNode parent, BNode newNode) {

        if (parent == null) {
            root = newNode;
        } else {
            int newObj = (newNode.obj.getID());
            int check = (parent.obj.getID());

            if (newObj < check) {
                if (parent.left == null)
                    parent.left = newNode;
                else
                    insert(parent.left, newNode);
            } else {
                if (parent.right == null)
                    parent.right = newNode;
                else
                    insert(parent.right, newNode);
            }
        }
    }

    private void insertBST(Employee newObj) {
        BNode newNode = new BNode(newObj);

        insert(root, newNode);
    }


    class Backup extends TimerTask{
        public void run(){
            StorageCustom con = new StorageCustom();
            con.Backup();
            con.close();
            timer.schedule(new Backup(), 1800000);
        }
    }



}