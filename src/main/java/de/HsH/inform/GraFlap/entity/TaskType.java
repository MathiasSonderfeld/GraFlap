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

    public boolean isFiniteAutomaton(){
        switch(this){
            case FA:
            case DFA:
            case NFA: return true;
            default:  return false;
        }
    }

    public boolean isPushDownAutomaton(){
        switch(this){
            case PDA:
            case DPDA:
            case NPDA: return true;
            default:   return false;
        }
    }

    public boolean isTuring(){
        switch(this){
            case TM:
            case DTM:
            case NTM: return true;
            default:  return false;
        }
    }
}
