/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.archicontribs.grafico.commandline;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.eclipse.osgi.util.NLS;

import com.archimatetool.commandline.AbstractCommandLineProvider;
import com.archimatetool.commandline.CommandLineState;
import org.archicontribs.grafico.GraficoImporter;
import com.archimatetool.editor.utils.StringUtils;
import com.archimatetool.model.IArchimateModel;

/**
 * Command Line interface for CSV Import
 * 
 * Typical usage - (should be all on one line):
 * 
 * Archi -consoleLog -nosplash -application com.archimatetool.commandline.app
   --createEmptyModel
   --importFromCSV "/elements.csv"
 * 
 * @author Phillip Beauvoir
 */
public class ImportGraficoProvider extends AbstractCommandLineProvider {

    static final String PREFIX = Messages.ImportGraficoProvider_0;
    
    static final String OPTION_IMPORT_GRAFICO = "grafico.import"; //$NON-NLS-1$
    
    public ImportGraficoProvider() {
    }
    
    
    @Override
    public void run(CommandLine commandLine) throws Exception {
        if(!hasCorrectOptions(commandLine)) {
            return;
        }
        
        IArchimateModel model = CommandLineState.getModel();
        
        if(model == null) {
            throw new IOException(Messages.ImportGraficoProvider_1);
        }
        
        // Folder
        String value = commandLine.getOptionValue(OPTION_IMPORT_GRAFICO);
        if(!StringUtils.isSet(value)) {
            logError(Messages.ImportGraficoProvider_2);
            return;
        }
        File elementsFile = new File(value);
        if(!elementsFile.exists()) {
            logError(NLS.bind(Messages.ImportGraficoProvider_3, value));
            return;
        }
        
        //logMessage(NLS.bind(Messages.ImportGraficoProvider_4, elementsFile.getPath(), model.getName()));
        
        GraficoImporter importer = new GraficoImporter(model);
        model = importer.doImport(elementsFile);
        CommandLineState.setModel(model);

        logMessage(Messages.ImportGraficoProvider_5);
    }
    
    @Override
    protected String getLogPrefix() {
        return PREFIX;
    }
    
    public int getPriority() {
        return PRIORITY_IMPORT;
    }
    
    @Override
    public Options getOptions() {
        Options options = new Options();
        
        Option option = Option.builder()
                .longOpt(OPTION_IMPORT_GRAFICO)
                .hasArg().argName(Messages.ImportGraficoProvider_6)
                .desc(Messages.ImportGraficoProvider_7)
                .build();
        options.addOption(option);
        
        return options;
    }
    
    private boolean hasCorrectOptions(CommandLine commandLine) {
        return commandLine.hasOption(OPTION_IMPORT_GRAFICO);
    }

}
