/**
 * Title:     ListaModCellaEve
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22-mag-2007
 */
package it.algos.base.evento.lista;

import it.algos.base.campo.base.Campo;
import it.algos.base.lista.Lista;

/**
 * Evento di tipo ListaSel
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 2 feb 2006
 */
public final class ListaModCellaEve extends ListaEve {

    /**
     * riferimento al campo asociato alla colonna
     */
    private Campo campo;

    /**
     * riferimento al codice record associato alla riga
     */
    private int codice;


    /**
     * Costruttore completo con parametri.
     */
    public ListaModCellaEve(Lista lista, Campo campo, Integer codice) {
        /* rimanda al costruttore della superclasse */
        super(lista);

        /* regola le variabili di istanza coi parametri */
        this.setCampo(campo);
        this.setCodice(codice);
    }


    public Campo getCampo() {
        return campo;
    }


    private void setCampo(Campo campo) {
        this.campo = campo;
    }


    public int getCodice() {
        return codice;
    }


    private void setCodice(int codice) {
        this.codice = codice;
    }
} // fine della classe
