package dave.LitleHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.commons.io.IOUtils;

import dave.LitleHelper.exception.LittleException;
import dave.LitleHelper.exception.LittleException.Err;

public class Main {

	private static final Logger LOGGER = Logger.getGlobal();
	private static MainWindow mainWindow;

	public static void main(String[] args) {
		try {
			FileHandler fh = new FileHandler("error_log.log", 5 * 1024 * 1024, 1, true);
			fh.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(fh);
		} catch (SecurityException | IOException e) {
			JOptionPane.showMessageDialog(null, "Chyba při vytváření FileLoggeru", "Chyba", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		});

		checkDatabase();

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
					public void uncaughtException(Thread t, Throwable e) {
						if (!(e instanceof LittleException) || e.getCause() != null) {
							LOGGER.log(Level.SEVERE, e.getMessage(), e);
						}

						JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						if (mainWindow == null || !mainWindow.isVisible()) {
							System.exit(1);
						}
					}
				});

				mainWindow = new MainWindow();
				mainWindow.setVisible(true);
			}
		});

		// EntityManagerFactory factory =
		// Persistence.createEntityManagerFactory("littleHelper");
		// EntityManager em = factory.createEntityManager();
		//
		// LocalDate yesterday = LocalDate.parse("2016-07-06");
		//
		// TaskDAO dao = new TaskDAO();
		// List<Task> list1 = dao.findFilter(LocalDate.now());
		// List<Task> list2 = dao.findFilter(yesterday);
		//
		// System.out.println("Done");
		// System.exit(0);
	}

	private static void checkDatabase() {
		File databaseDirectory = new File("databases/myDb");
		Scanner scanner = null;

		if (!databaseDirectory.exists()) {
			try (Connection conn = DriverManager.getConnection("jdbc:derby:./databases/myDb;create=true");
					Statement st = conn.createStatement()) {

				Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

				scanner = new Scanner(new File("little_helper.sql"));
				scanner.useDelimiter(";");

				while (scanner.hasNext()) {
					String sql = scanner.next();
					st.execute(sql);
				}
			} catch (ClassNotFoundException | SQLException | FileNotFoundException e) {
				throw new LittleException(Err.DB_LOAD, e);
			} finally {
				IOUtils.closeQuietly(scanner);
			}
		}
	}
}
