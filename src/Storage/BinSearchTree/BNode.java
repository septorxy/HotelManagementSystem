package Storage.BinSearchTree;

import PersonBuilder.Employee;
import PersonBuilder.Person;


public class BNode {
    public Person obj;
    public BNode left;
    public BNode right;

    public BNode(Person iObject){
        obj = iObject;
        left = null;
        right = null;
    }

}