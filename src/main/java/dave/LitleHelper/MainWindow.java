package dave.LitleHelper;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import dave.LitleHelper.dao.WorkspaceDAO;
import dave.LitleHelper.panel.OverViewPane;
import dave.LitleHelper.panel.RecordPane;
import dave.LitleHelper.panel.SettingsPane;
import dave.LitleHelper.panel.WorkspacePane;

public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3439004617126119913L;
	private static final Dimension WINDOW_SIZE = new Dimension(1100, 720);

	public MainWindow() {

		initComponents();

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					DriverManager.getConnection("jdbc:derby:;shutdown=true");
				} catch (SQLException e1) {
					// sql error is expected -> do nothing
				}

				WorkspaceDAO.getInstance().saveData();
			}

		});
	}

	private void initComponents() {
		setSize(WINDOW_SIZE);
		setPreferredSize(WINDOW_SIZE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("LitleHelper");
		setLocationRelativeTo(null);

		JTabbedPane tabPane = new JTabbedPane();

		tabPane.add("Zaznam", new RecordPane());
		tabPane.add("Prehled", new OverViewPane());
		tabPane.add("Workspace", new WorkspacePane());
		tabPane.addTab("Nastaveni", new SettingsPane());

		add(tabPane);
	}
}
