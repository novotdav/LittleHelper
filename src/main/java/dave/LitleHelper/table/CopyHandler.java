package dave.LitleHelper.table;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

import dave.LitleHelper.entities.AbstractEntity;
import dave.LitleHelper.table.model.MyAbstractTableModel;

public class CopyHandler extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -236491440867535617L;

	@SuppressWarnings("unchecked")
	@Override
	public void exportToClipboard(JComponent comp, Clipboard clip, int action) throws IllegalStateException {

		if ((action == COPY || action == MOVE)) {
			if (comp instanceof JTable) {
				JTable table = (JTable) comp;
				int[] selectedRows = table.getSelectedRows();
				int[] modelRows = new int[selectedRows.length];

				for (int i = 0; i < selectedRows.length; i++) {
					modelRows[i] = table.convertRowIndexToModel(selectedRows[i]);
				}

				MyAbstractTableModel<AbstractEntity> model = (MyAbstractTableModel<AbstractEntity>) table.getModel();
				StringBuilder sb = new StringBuilder();
				Arrays.stream(modelRows).forEach(row -> {
					sb.append(model.getEntity(row).toString() + "\n");
				});
				clip.setContents(new FilesTransferable(sb.toString()), null);
			}
		}
	}

	private class FilesTransferable implements Transferable {

		private String data;

		public FilesTransferable(String data) {
			this.data = data;
		}

		@Override
		public DataFlavor[] getTransferDataFlavors() {
			DataFlavor f = DataFlavor.plainTextFlavor;
			return new DataFlavor[] { f };
		}

		@Override
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return flavor.equals(DataFlavor.plainTextFlavor);
		}

		@Override
		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
			return data;
		}

	}

}
