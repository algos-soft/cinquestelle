/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      10-mag-2006
 */
package it.algos.albergo.pensioni;

import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.conto.addebito.Addebito;
import it.algos.albergo.conto.addebitofisso.AddebitoFisso;
import it.algos.albergo.conto.addebitofisso.AddebitoFissoModulo;
import it.algos.base.database.connessione.Connessione;

import java.util.Date;

/**
 * Contenitore per le informazioni relative a
 * un pacchetto di addebiti da listino relative a un conto.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 8-feb-2008 ore 12.18.49
 */
public final class WrapAddebitiConto extends WrapAddebiti {

    /**
     * Costruttore completo.
     * <p/>
     * Crea l'oggetto e lo riempie con i dati indicati
     *
     * @param codice del conto
     * @param conn connessione da utilizzare per le letture dal database
     */
    public WrapAddebitiConto(int codice, Connessione conn) {
        /* rimanda al costruttore della superclasse */
        this(null, null, 0, codice, conn);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo.
     * <p/>
     * Crea l'oggetto e lo riempie con i dati indicati
     *
     * @param dataIni data di inizio del periodo (giorno di arrivo)
     * @param dataEnd data di fine del periodo (giorno di partenza)
     * @param persone numero persone previste
     * @param codConto codice del conto
     * @param conn connessione da utilizzare per le letture dal database
     */
    public WrapAddebitiConto(Date dataIni,
                             Date dataEnd,
                             int persone,
                             int codConto,
                             Connessione conn) {
        /* rimanda al costruttore della superclasse */
        super(dataIni,
                dataEnd,
                persone,
                codConto,
                conn,
                ContoModulo.get(),
                AddebitoFissoModulo.get(),
                AddebitoFissoModulo.get().getCampo(Addebito.Cam.conto),
                AddebitoFissoModulo.get().getCampo(Addebito.Cam.listino),
                AddebitoFissoModulo.get().getCampo(Addebito.Cam.codRigaListino),
                AddebitoFissoModulo.get().getCampo(AddebitoFisso.Cam.dataInizioValidita),
                AddebitoFissoModulo.get().getCampo(AddebitoFisso.Cam.dataFineValidita),
                AddebitoFissoModulo.get().getCampo(Addebito.Cam.quantita),
                AddebitoFissoModulo.get().getCampo(Addebito.Cam.prezzo),
                ContoModulo.get().getCampo(Conto.Cam.validoDal),
                ContoModulo.get().getCampo(Conto.Cam.validoAl),
                ContoModulo.get().getCampo(Conto.Cam.numPersone));
    }// fine del metodo costruttore completo


}// fine della classe