package de.HsH.inform.GraFlap.loncapa.entity;

import java.util.HashSet;

/**
 * Helper entity to represent the entries of a cyk algorithm.
 * @author Benjamin Held (07-12-2016)
 * @since 07-18-2016
 * @version 0.1.0
 */
public class CYKTable {

    private HashSet[][] cykTable;

    public CYKTable(HashSet[][] table) {
        this.cykTable = table;
    }

    public HashSet getProductionsAt(int row, int column) {
        return cykTable[row][column];
    }

}
