/**
 * Title:        CampoScheda.java
 * Package:      it.algos.base.campo.scheda
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 3 luglio 2003 alle 15.11
 */

package it.algos.base.campo.scheda;

import it.algos.base.campo.base.Campo;

/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Regola la gestione degli attributi di una campo a video nella Scheda <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  3 luglio 2003 ore 15.11
 */
public interface CampoScheda {

    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     */
    public void inizializza();


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo invocato da altre classi (o anche da questa) ogni volta che questo oggetto
     * deve <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public abstract void avvia();


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public abstract boolean isPresenteInScheda();


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public abstract void setPresenteScheda(boolean isPresenteInScheda);


    /**
     * Altezza dei componenti interni al PannelloComponenti
     */
    public abstract int getAltezzaComponenti();


    /**
     * Altezza dei componenti interni al PannelloComponenti
     */
    public abstract void setAltezzaComponenti(int h);


    /**
     * Larghezza dei componenti interni al PannelloComponenti
     */
    public abstract int getLarghezzaComponenti();


    /**
     * Larghezza dei componenti interni al PannelloComponenti
     */
    public abstract void setLarghezzaComponenti(int w);


    /*
    * Flag - se usa il bordo
    */
    public abstract boolean isBordato();


    /*
     * Flag - se usa il bordo
     */
    public abstract void setBordato(boolean bordato);


    /*
     * Flag - se usa il voce nel bordo
     */
    public abstract boolean isBordoTitolato();


    /*
    * Flag - se usa il voce nel bordo
    */
    public abstract void setBordoTitolato(boolean bordoTitolato);


    /*
     * Eventuale voce nel bordo
     */
    public abstract String getTitolo();


    /*
     * Eventuale voce nel bordo
     */
    public abstract void setTitolo(String titolo);


    public abstract int getPosEtichetta();


    public abstract void setPosEtichetta(int posEtichetta);


    /**
     * riferimento al campo 'contenitore' dei vari oggetti che
     * insieme svolgono le funzioni del campo
     */
    public abstract void setCampoParente(Campo unCampoParente);


    public abstract Campo getCampoParente();

//    /**
//     * Determina se il campo e' obbligatorio per registrazione / conferma
//     * <p>
//     * @return true se obbligatorio
//     */
//    public abstract boolean isObbligatorio();
//
//    /**
//     * Campo obbligatorio per registrazione/conferma scheda/dialogo.
//     * <p>
//     * @param obbligatorio
//     */
//    public abstract void setObbligatorio(boolean obbligatorio);


    /**
     * Ritorna una copia profonda dell'oggetto (deep copy) col casting
     */
    public abstract CampoScheda clonaCampo(Campo unCampoParente);

}// fine della interfaccia