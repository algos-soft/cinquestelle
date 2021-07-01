/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      22-gen-2006
 */
package it.algos.base.libreria;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE . </p> Questa classe: <ul> <li> </li> <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-gen-2006 ore 18.17.12
 */
public final class Parametro {

    /**
     * classe
     */
    private Class classe;

    /**
     * valore
     */
    private Object valore;


    /**
     * Costruttore completo con parametri.
     *
     * @param classe
     * @param valore
     */
    public Parametro(Class classe, Object valore) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setClasse(classe);
        this.setValore(valore);

    }// fine del metodo costruttore completo


    public Class getClasse() {
        return classe;
    }


    private void setClasse(Class classe) {
        this.classe = classe;
    }


    public Object getValore() {
        return valore;
    }


    private void setValore(Object valore) {
        this.valore = valore;
    }

}// fine della classe
