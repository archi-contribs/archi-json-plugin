package org.archicontribs.grafico;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;

import com.archimatetool.editor.model.IModelExporter;
import com.archimatetool.editor.preferences.Preferences;
import com.archimatetool.model.IArchimateModel;

public class GraficoExportProvider implements IModelExporter{

  // Preference to use to keep track of last folder used
  static final String PREF_LAST_FOLDER = "graficoLastFolder";

  public GraficoExportProvider() {
	// TODO Auto-generated constructor stub
  }

  @Override
  public void export(IArchimateModel model) throws IOException {
	  GraficoExporter exporter = new GraficoExporter(model);
	  File folder = askSaveFolder();
      exporter.export(folder);
  }

  /**
   * Ask user to select a folder. Check if it is empty and, if not, ask confirmation.
   */
  private File askSaveFolder() throws IOException {
      DirectoryDialog dialog = new DirectoryDialog(Display.getCurrent().getActiveShell());
      // Set default path from preference
      dialog.setFilterPath(Preferences.STORE.getString(PREF_LAST_FOLDER));
      dialog.setText(Messages.GraficoExporter_0);
      dialog.setMessage(Messages.GraficoExporter_3);
      String path = dialog.open();

      if(path == null) {
          return null;
      }

      // Save chosen path in preference
      Preferences.STORE.setValue(PREF_LAST_FOLDER, path);

      File folder = new File(path);

      if(folder.exists()) {
          String[] children = folder.list();
          if(children != null && children.length > 0) {
              boolean result = MessageDialog.openQuestion(Display.getCurrent().getActiveShell(),
              		Messages.GraficoExporter_0,
                    NLS.bind(Messages.GraficoExporter_4, folder));
              if(!result) {
                  return null;
              }
          }
      } else {
      	folder.mkdirs();
      }

      return folder;
  }
}
