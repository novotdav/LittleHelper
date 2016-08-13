package dave.LitleHelper.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import dave.LitleHelper.dao.WorkspaceDAO;
import dave.LitleHelper.dialog.GetUESBuildDialog;
import dave.LitleHelper.entities.Workspace;
import dave.LitleHelper.enums.WorkspaceType;
import dave.LitleHelper.table.WorkspaceTable;
import dave.LitleHelper.table.model.MyAbstractTableModel;
import net.sf.sevenzipjbinding.ArchiveFormat;
import readsevenzip.HttpIsoReader;

// TODO complete, now only to test functioning
public class AddNewWorkspaceListener implements ActionListener {

	private WorkspaceType type;
	private MyAbstractTableModel<Workspace> model;
	private WorkspaceDAO dao;

	public AddNewWorkspaceListener(WorkspaceTable table, WorkspaceType type) {
		this.type = type;
		model = table.getModel();
		dao = WorkspaceDAO.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// WorkspaceEditPane editPane = new WorkspaceEditPane(type);
		//
		// int option = JOptionPane.showConfirmDialog(null, editPane,
		// "Existujici worskpace",
		// JOptionPane.OK_CANCEL_OPTION);
		//
		// if (option == JOptionPane.OK_OPTION) {
		// Workspace wrk = new Workspace(editPane.getName(), editPane.getPath(),
		// editPane.getType());
		// Workspace persistedWrk = dao.merge(wrk);
		// model.addData(Arrays.asList(persistedWrk));
		// }

		// UESBuildTreeModel model = new UESBuildTreeModel();
		// JTree tree = new JTree(model);
		// model.generateStructure();
		//
		// JPanel content = new JPanel();
		// content.setPreferredSize(new Dimension(500, 500));
		// content.setSize(new Dimension(500, 500));
		// content.add(tree);
		GetUESBuildDialog dialog = new GetUESBuildDialog(null);
		URL uesBuildUrl = dialog.showDialog();
		System.out.println(uesBuildUrl);

		HttpIsoReader reader = new HttpIsoReader();
		reader.open(uesBuildUrl.toString(), ArchiveFormat.ZIP);
		try {
			reader.listArchive();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// RandomAccessFileInStream stream = null;
		// IInArchive archiv = null;
		//
		// try {
		// SevenZip.initSevenZipFromPlatformJAR();
		// ByteArrayStream st = new ByteArrayStream(Integer.MAX_VALUE);
		// System.out.println("before write");
		// st.writeFromInputStream(uesBuildUrl.openStream(), true);
		//
		// // stream = new RandomAccessFileInStream(new RandomAccessFile(new
		// // File(uesBuildUrl), "r"));
		// archiv = SevenZip.openInArchive(ArchiveFormat.ZIP, st);
		//
		// System.out.println("Count of items in archive: " +
		// archiv.getNumberOfItems());
		// } catch (SevenZipNativeInitializationException ex) {
		// ex.printStackTrace();
		// } catch (FileNotFoundException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// } catch (SevenZipException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// } finally {
		// IOUtils.closeQuietly(archiv);
		// IOUtils.closeQuietly(stream);
		// }
	}

}
