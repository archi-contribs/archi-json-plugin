/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.archicontribs.grafico;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

    private static final String BUNDLE_NAME = "org.archicontribs.grafico.messages"; //$NON-NLS-1$

    public static String GraficoExporter_0;
    public static String GraficoExporter_1;

	public static String GraficoExporter_3;

	public static String GraficoExporter_4;

	public static String GraficoImporter_0;

	public static String GraficoImporter_1;

	public static String GraficoImporter_2;

	public static String GraficoImporter_3;

	public static String GraficoImporter_4;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
