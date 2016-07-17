package dave.LitleHelper.table;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

import dave.LitleHelper.entities.AbstractEntity;
import dave.LitleHelper.utils.TableUtils;

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
				List<AbstractEntity<?>> selectedItems = TableUtils.getSelectedItems(table);

				StringBuilder sb = new StringBuilder();
				selectedItems.forEach(item -> {
					sb.append(item.toString() + "\n");
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
