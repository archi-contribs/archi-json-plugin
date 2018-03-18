package org.archicontribs.grafico;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;

import com.archimatetool.editor.model.IEditorModelManager;
import com.archimatetool.editor.model.IModelImporter;
import com.archimatetool.editor.model.ISelectedModelImporter;
import com.archimatetool.editor.preferences.Preferences;
import com.archimatetool.model.IArchimateModel;

public class GraficoImportProvider implements ISelectedModelImporter {

	@Override
	public void doImport(IArchimateModel model) throws IOException {
		// TODO Auto-generated method stub
		GraficoImporter importer = new GraficoImporter(model);
		File folder = askOpenFolder();
		model = importer.doImport(folder);
		// Open the Model in the Editor
        IEditorModelManager.INSTANCE.openModel(model);
		
	}
	/**
     * Ask user to select a folder.
     */
    private File askOpenFolder() {
        DirectoryDialog dialog = new DirectoryDialog(Display.getCurrent().getActiveShell());
        // Set default path from preference
        dialog.setFilterPath(Preferences.STORE.getString(GraficoExportProvider.PREF_LAST_FOLDER));
        dialog.setText(Messages.GraficoExporter_0);
        dialog.setMessage(Messages.GraficoImporter_0);
        String path = dialog.open();
        
        if(path == null) {
            return null;
        }
        
        // Save chosen path in preference
        Preferences.STORE.setValue(GraficoExportProvider.PREF_LAST_FOLDER, path);
        
        return new File(path);
    }

}
