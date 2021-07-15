package de.HsH.inform.GraFlap.entity;

/**
 * Enumeration to represent the input mode that is specified
 * @author Benjamin Held (07-04-2016)
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public enum TaskMode {
    ERROR,
    AR, ART, AG, AGT, ARW, AGW, ARTW, AGTW, EAT, AA,
    ARP, ARTP, AGP, AGTP, ARWP, AGWP, ARTWP, AGTWP,
    GG, GGW, GGT, GGTW, EGT, GR, GRT, GRW, GRTW,
    WW,
    MP, MMW,
    CYK, DER,
    SVGG, SVGA;

    public boolean isAutomaton(){
        switch(this){
            case AA:
            case AR:
            case ART:
            case AG:
            case AGT:
            case ARW:
            case AGW:
            case ARTW:
            case AGTW:
            case EAT:
            case ARP:
            case ARTP:
            case AGP:
            case AGTP:
            case ARWP:
            case AGWP:
            case ARTWP:
            case AGTWP: return true;
            default:    return false;
        }
    }

    public boolean isGrammar(){
        switch(this){
            case GG:
            case GGW:
            case GGT:
            case GGTW:
            case EGT:
            case GR:
            case GRT:
            case GRW:
            case GRTW: return true;
            default:  return false;
        }
    }

    public boolean isTyped(){
        switch(this){
            case ART:
            case AGT:
            case ARTW:
            case AGTW:
            case EAT:
            case ARTP:
            case AGTP:
            case ARTWP:
            case AGTWP:
            case GGT:
            case GGTW:
            case EGT:
            case GRT:
            case GRTW: return true;
            default:   return false;
        }
    }

    public boolean isParameterized(){
        switch(this){
            case ARP:
            case ARTP:
            case AGP:
            case AGTP:
            case ARWP:
            case AGWP:
            case ARTWP:
            case AGTWP: return true;
            default:   return false;
        }
    }
}
