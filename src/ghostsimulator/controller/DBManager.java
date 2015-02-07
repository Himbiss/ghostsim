package ghostsimulator.controller;

import ghostsimulator.util.Resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class manages the derby database and enables the user to load and save examples from and into the database.
 * @author Vincent
 *
 */
public class DBManager {

	private static DBManager instance;
	private Connection connection;
	
	private DBManager() {
		System.out.print("Connecting to "+Resources.getSystemProperty("database")+".. ");
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			connection = DriverManager
					.getConnection("jdbc:derby:"+Resources.getSystemProperty("database")+";create=true");
			System.out.println("done");
			
			createTableStructure();
		} catch (SQLException e) {
			System.err.println("Error: A SQLException occurred!");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("Error: Could not load the Embedded driver!");
			e.printStackTrace();
		}
		
		// add shutdown hook to close the connection
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				if(connection != null) {
					try {
						if(!connection.isClosed()) {
							System.out.print("Closing connection..");
							connection.close();
							System.out.println(" ..done");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}));
	}
	
	/**
	 * Creates the initial Table-Structure
	 */
	private void createTableStructure() {
		String createTerritoryTableQuery = "CREATE TABLE Examples (EXAMPLE_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY, PROGRAM VARCHAR(25000) NOT NULL, TERRITORY VARCHAR(25000) NOT NULL, PRIMARY KEY( EXAMPLE_ID ))";
		Statement examples_stmt = null;
		try{
			System.out.print("Creating table Examples in database..");
			examples_stmt = connection.createStatement();
			examples_stmt.executeUpdate(createTerritoryTableQuery);
			System.out.print(" ..done\n");
		} catch (SQLException se) {
			// check for error code X0Y32 ( Table already exists )
			if(se.getErrorCode() != 30000) {
				se.printStackTrace();
			} else {
				System.out.print(" ..already exists\n");
			}
		} finally {
			try{
				examples_stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		String createTagsTableQuery = "CREATE TABLE Tags (EXAMPLE_ID INTEGER NOT NULL, TAG VARCHAR(255) NOT NULL)";
		Statement territory_stmt = null;
		try{
			System.out.print("Creating Table Tags in database..");
			territory_stmt = connection.createStatement();
			territory_stmt.executeUpdate(createTagsTableQuery);
			System.out.print(" ..done\n");
		} catch (SQLException se) {
			// check for error code X0Y32 ( Table already exists )
			if(se.getErrorCode() != 30000) {
				se.printStackTrace();
			} else {
				System.out.print(" ..already exists\n");
			}
		} finally {
			try{
				territory_stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static DBManager getInstance() {
		if(instance == null)
			return (instance = new DBManager());
		return instance;
	}
	
	/**
	 * Retrieves all examples matching 'tag' from the database.
	 * Returns an array of strings with the identifier of every example.
	 * @param tag
	 * @return examples
	 */
	public String[] getExamples(String tag) {
		ArrayList<String> examples = new ArrayList<>();
		String selectExamplesQuery = "SELECT TAG, EXAMPLE_ID FROM (Examples NATURAL JOIN Tags) WHERE TAG=? ORDER BY EXAMPLE_ID ASC";
		PreparedStatement stmt = null;
		ResultSet set = null;
		try{
			stmt = connection.prepareStatement(selectExamplesQuery);
			stmt.setString(1, tag);
			set = stmt.executeQuery();
			// iterate over the set and add every tag to the examples list
			while(set.next()) {
				examples.add(String.valueOf(set.getInt(2)));
			}
		} catch (SQLException se) {
			System.err.println("An error occurred while retrieving examples from the database!");
			se.printStackTrace();
		} finally {
			try{
				stmt.close();
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return examples.toArray(new String[examples.size()]);
	}

	/**
	 * Loads an example from the database identified by 'example'.
	 * Loads the program and the territory and updates them.
	 * @param example
	 */
	public void loadExample(String example) {
		int example_id = Integer.parseInt(example);
		String selectExamplesQuery = "SELECT PROGRAM, TERRITORY FROM Examples WHERE EXAMPLE_ID=?";
		PreparedStatement stmt = null;
		ResultSet set = null;
		try{
			stmt = connection.prepareStatement(selectExamplesQuery);
			stmt.setInt(1, example_id);
			set = stmt.executeQuery();
			// check, if there is an example with this id
			if(set.next()) {
				String programStr = set.getString(1);
				String territoryStr = set.getString(2);
				EntityManager.getInstance().getEditorManager().loadEditor(programStr);
				EntityManager.getInstance().getXmlSerializationController().loadWithSAX(territoryStr);
			} else {
				System.err.println("No  found with the ");
			}
		} catch (SQLException se) {
			System.err.println("An error occurred while retrieving examples from the database!");
			se.printStackTrace();
		} finally {
			try{
				stmt.close();
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Stores the current program and the territory in the database under 'tags'
	 * @param tags
	 */
	public void storeCurrentExample(String[] tags) {
		System.out.println("Storing example with tags: "+Arrays.toString(tags));
		// first get the program and territory as xml code
		String program = EntityManager.getInstance().getEditorManager().getProgram();
		String territoryXML = EntityManager.getInstance().getTerritoryManager().getTerritoryAsXML();
		
		// then insert the example ( program + territory ) into the table
		String insertExampleQuery = "INSERT INTO Examples( PROGRAM, TERRITORY ) VALUES ( ?, ?)";
		String insertTagQuery = "INSERT INTO Tags VALUES ( ?, ? )";
		PreparedStatement insertExampleStmt = null;
		PreparedStatement insertTagStmt = null;
		ResultSet keySet = null;
		try{
			connection.setAutoCommit(false);
			insertExampleStmt = connection.prepareStatement(insertExampleQuery, new String[] { "EXAMPLE_ID" });
			insertExampleStmt.setString(1, program);
			insertExampleStmt.setString(2, territoryXML);
			int updated = insertExampleStmt.executeUpdate();
			// check, if the insert was completed
			if(updated == 1) {
				// get the generated example_id key
				keySet = insertExampleStmt.getGeneratedKeys();
				if(keySet.next()) {
					int example_id = keySet.getInt(1);
					System.out.println("Auto generated key for this example: "+example_id);
					// now insert the tags into the tags table
					insertTagStmt = connection.prepareStatement(insertTagQuery);
					for(String tag : tags) {
						insertTagStmt.setInt(1, example_id);
						insertTagStmt.setString(2, tag);
						insertTagStmt.executeUpdate();
					}
				}
			}
			connection.commit();
		} catch (SQLException se) {
			System.err.println("An error occurred while inserting the example into the database!");
			se.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			try{
				insertExampleStmt.close();
				insertTagStmt.close();
				keySet.close();
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Shuts the DerbyDB down.
	 */
	public void shutdownDB() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
