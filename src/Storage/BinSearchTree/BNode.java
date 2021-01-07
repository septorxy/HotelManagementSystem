package Storage.BinSearchTree;

import PersonBuilder.Employee;


public class BNode {
    public Employee obj;
    public BNode left;
    public BNode right;

    public BNode(Employee iObject){
        obj = iObject;
        left = null;
        right = null;
    }

}