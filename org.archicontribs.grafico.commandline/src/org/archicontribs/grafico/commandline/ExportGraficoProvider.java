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
import org.archicontribs.grafico.commandline.Messages;

import org.archicontribs.grafico.GraficoExporter;
import com.archimatetool.editor.utils.StringUtils;
import com.archimatetool.model.IArchimateModel;

/**
 * Command Line interface for CSV Export
 * 
 * Typical usage - (should be all on one line):
 * 
 * Archi -consoleLog -nosplash -application com.archimatetool.commandline.app
   --loadModel "/pathToModel/model.archimate"
   --exportToCSV "/pathToOutputFolder"
 * 
 * @author Phillip Beauvoir
 */
public class ExportGraficoProvider extends AbstractCommandLineProvider {

    static final String PREFIX = Messages.ExportGraficoProvider_0;
    
    static final String OPTION_EXPORT_GRAFICO = "grafico.export"; //$NON-NLS-1$

    public ExportGraficoProvider() {
    }
    
    
    @Override
    public void run(CommandLine commandLine) throws Exception {
        if(!hasCorrectOptions(commandLine)) {
            return;
        }
        
        IArchimateModel model = CommandLineState.getModel();
        
        if(model == null) {
            throw new IOException(Messages.ExportGraficoProvider_1);
        }
        GraficoExporter exporter = new GraficoExporter(model);
        
     // Folder
        String grafico_export_path = commandLine.getOptionValue(OPTION_EXPORT_GRAFICO);
        if(!StringUtils.isSet(grafico_export_path)) {
            logError(Messages.ExportGraficoProvider_2);
            return;
        }
        File folderOutput = new File(grafico_export_path);
        folderOutput.mkdirs();
        if(!folderOutput.exists()) {
            logError(NLS.bind(Messages.ExportGraficoProvider_3, grafico_export_path));
            return;
        }
        
        exporter.export(folderOutput);
        logMessage(Messages.ExportGraficoProvider_5);
    }
    
    @Override
    protected String getLogPrefix() {
        return PREFIX;
    }
    
    @Override
    public Options getOptions() {
        Options options = new Options();
        
        Option option = Option.builder()
                .longOpt(OPTION_EXPORT_GRAFICO)
                .hasArg().argName(Messages.ExportGraficoProvider_6)
                .desc(Messages.ExportGraficoProvider_7)
                .build();
        options.addOption(option);
        
        return options;
    }
    
    private boolean hasCorrectOptions(CommandLine commandLine) {
        return commandLine.hasOption(OPTION_EXPORT_GRAFICO);
    }
}
