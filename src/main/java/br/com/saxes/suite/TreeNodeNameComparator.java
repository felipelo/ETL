package br.com.saxes.suite;

import br.com.saxes.suite.model.TreeNode;
import java.util.Comparator;

public class TreeNodeNameComparator implements Comparator<TreeNode> {

    @Override
    public int compare(TreeNode o1, TreeNode o2) {
        String nameOne = o1.getName();
        String nameTwo = o2.getName();

        return nameOne.compareToIgnoreCase(nameTwo);
    }

}
