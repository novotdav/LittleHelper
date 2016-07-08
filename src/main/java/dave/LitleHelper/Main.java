package dave.LitleHelper;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new MainWindow().setVisible(true);
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

}
