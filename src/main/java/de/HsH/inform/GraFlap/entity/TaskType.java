package de.HsH.inform.GraFlap.entity;

public enum TaskType {
    FA, DFA, NFA, NON, PDA, DPDA, NPDA, TM, DTM, NTM, TMWW, MEALY, MOORE, RL, RLCFG, CFG, NCFG, GINT, ERROR;

    public boolean isDeterministic(){
        switch(this){
            case DFA:
            case DPDA:
            case DTM: return true;
            default: return false;
        }
    }

    public boolean isNonDeterministic(){
        switch(this){
            case NFA:
            case NPDA:
            case NTM: return true;
            default: return false;
        }
    }
}
