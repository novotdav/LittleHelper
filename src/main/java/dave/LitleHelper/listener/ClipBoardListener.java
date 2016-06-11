package dave.LitleHelper.listener;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JTextArea;

public class ClipBoardListener extends Thread implements ClipboardOwner {
	Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
	private boolean monitor = true;
	private JTextArea textArea;

	public ClipBoardListener(JTextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void run() {
		Transferable trans = sysClip.getContents(this);
		takeOwnership(trans);
	}

	public void stopMonitor() {
		monitor = false;
	}

	@Override
	public void lostOwnership(Clipboard c, Transferable t) {

		if (monitor) {
			try {
				ClipBoardListener.sleep(250); // waiting e.g for loading huge
												// elements like word's etc.
			} catch (Exception e) {
				System.out.println("Exception: " + e);
			}
			Transferable contents = sysClip.getContents(this);
			try {
				processClipboard(contents, c);
			} catch (Exception ex) {
				// Logger.getLogger(ClipBoardListener.class.getName()).log(Level.SEVERE,
				// null, ex);
			}
			takeOwnership(contents);
		}
	}

	void takeOwnership(Transferable t) {
		sysClip.setContents(t, this);
	}

	public void processClipboard(Transferable t, Clipboard c) { // your
																// implementation
		String tempText;
		Transferable trans = t;

		try {
			if (trans != null ? trans.isDataFlavorSupported(DataFlavor.stringFlavor) : false) {
				tempText = (String) trans.getTransferData(DataFlavor.stringFlavor);
				textArea.append(tempText + "\n");
			}

		} catch (Exception e) {
		}
	}
}
