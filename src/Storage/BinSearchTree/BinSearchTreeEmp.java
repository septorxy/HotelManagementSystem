package Storage.BinSearchTree;

import PersonBuilder.Employee;

import javax.swing.*;

public class BinSearchTreeEmp {
    private BNode root;
    private String display = "";

    public BinSearchTreeEmp() {
        root = null;
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


}