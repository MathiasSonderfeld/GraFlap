package de.HsH.inform.GraFlap.entity;

import java.util.HashSet;

/**
 * Helper entity to represent the entries of a cyk algorithm.
 * @author Benjamin Held (07-12-2016)
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class CYKTable {

    private final HashSet[][] cykTable;

    public CYKTable(HashSet[][] table) {
        this.cykTable = table;
    }

    public HashSet getProductionsAt(int row, int column) {
        return cykTable[row][column];
    }

}
