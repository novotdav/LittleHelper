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
		// Task t = new Task();
		// t.setDescription("desc7");
		//
		// em.getTransaction().begin();
		// em.persist(t);
		// em.getTransaction().commit();
		// em.close();
		//
		// System.out.println("Done");
		// System.exit(0);
	}

}
