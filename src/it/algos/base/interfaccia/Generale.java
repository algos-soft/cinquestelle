/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      14-gen-2005
 */
package it.algos.base.interfaccia;

import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;

/**
 * Interfaccia di riferimento per ogni interfaccia di un Modulo.
 * <p/>
 * Questa interfaccia: <ul>
 * <li> Mantiene la codifica delle viste standard (per la lista) </li>
 * <li> Mantiene la codifica dei set di campi standard (per la scheda) </li>
 * <li> Mantiene la codifica dei campi standard (per il modello e per la scheda) </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 14-gen-2005 ore 7.37.07
 * @see it.algos.base.modello.Modello
 * @see it.algos.base.modulo.Modulo
 */
public interface Generale {

    /**
     * Codifica delle viste (espanse e clonate) per la Lista
     */
    public static final String VISTA_COMPLETA = Modulo.VISTA_BASE_DEFAULT;

    public static final String VISTA_SIGLA = Modulo.VISTA_SIGLA;

    public static final String VISTA_DESCRIZIONE = Modulo.VISTA_DESCRIZIONE;

    /**
     * codifica dei set di campi per la scheda
     */
    public static final String SET_COMPLETO = Modulo.SET_BASE_DEFAULT;

    /**
     * Codifica dei nomi dei campi (per il modello e per la scheda)
     */
    public static final String CAMPO_SIGLA = Modello.NOME_CAMPO_SIGLA;

    public static final String CAMPO_DESCRIZIONE = Modello.NOME_CAMPO_DESCRIZIONE;

    /**
     * Codifica dei nomi dei campi delle tavole di incrocio
     */
    public static final String CAMPO_LINK_TESTA = "linktesta";

    public static final String CAMPO_LINK_RIGHE = "linkrighe";

    /**
     * Codifica del testo della legenda sotto il campo sigla nella scheda
     */
    public static final String LEGENDA_SIGLA = "sigla come appare nelle liste di altri moduli";

    /**
     * Codifica del testo della legenda sotto il campo descrizione nella scheda
     */
    public static final String LEGENDA_DESCRIZIONE = "descrizione completa della tabella";


}// fine della interfaccia
