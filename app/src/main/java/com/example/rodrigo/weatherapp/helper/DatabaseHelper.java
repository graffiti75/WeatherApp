package com.example.rodrigo.weatherapp.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.rodrigo.weatherapp.AppConfiguration;
import com.example.rodrigo.weatherapp.model.AppInfo;
import com.example.rodrigo.weatherapp.model.City;
import com.example.rodrigo.weatherapp.model.Entity;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A helper class that manages your database connection and your persisted objects.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	
	//----------------------------------------------
	// Statics
	//----------------------------------------------
	
	// Put here all of your database classes.
	private static final Class<?>[] sDatabaseTables = {
		AppInfo.class,
		City.class
	};

    // Cache for foreign fields.
    private static final Map<Class<?>, List<Field>> sClassFieldMap;
    static {
        sClassFieldMap = new HashMap<Class<?>, List<Field>>();
        for (Class<?> databaseTableClass : sDatabaseTables) {
            registerForeignFields(databaseTableClass);
        }
    }

	//----------------------------------------------
	// Attributes
	//----------------------------------------------
    
	// The database context.
	private Context mContext;

	//----------------------------------------------
	// Constructor
	//----------------------------------------------
	
	/**
	 * Creates a new DatabaseHelper instance.
	 */
	public DatabaseHelper(Context context) {
		super(context, AppConfiguration.DATABASE_NAME, null, AppConfiguration.DATABASE_VERSION);
		mContext = context;
	}

	//----------------------------------------------
	// Database Setup
	//----------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		try {
			// Creating the database tables.
			for (Class<?> databaseTableClass : sDatabaseTables) {
				TableUtils.createTable(connectionSource, databaseTableClass);
			}
		} catch (SQLException e) {
			// Can't create database.
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		// Upgrading database.
		try {
			// If you have any important information on database, save it now on a temp object.
			// Dropping tables.
			for (Class<?> databaseTableClass : sDatabaseTables) {
				TableUtils.dropTable(connectionSource, databaseTableClass, true);
			}
			// After dropping the tables, we create the new ones.
			onCreate(database, connectionSource);
			// Restore any temp stored data.
		} catch (SQLException e) {
			// Can't drop tables.
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Returns this helper context.
	 * 
	 * @return The Context that is being used by this helper.
	 */
	public Context getContext() {
		return mContext;
	}
	
	/**
	 * Static method for obtaining helper reference. Must call release when finished.
	 * 
	 * @param context The context to be used.
	 * @return The DatabaseHelper instance.
	 */
	public static DatabaseHelper getHelper(Context context) {
		return (DatabaseHelper)OpenHelperManager.getHelper(context, DatabaseHelper.class);
	}
	
	/**
	 * Static method to release the most recent helper instance. 
	 */
	public static void releaseHelper() {
		OpenHelperManager.releaseHelper();
	}

	//----------------------------------------------
	// CRUD
	//----------------------------------------------

    /**
     * Persist an entity to the underlying database.
     * If the entity has a field with a DatabaseField annotation with foreign set to true,
     * and is an instance of Entity, recursive attempt to persist that object as well.
     * If the entity already exists, it will be updated.
     * 
     * @param databaseHelper
     * @param entity
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws SQLException
     */
    public synchronized void createOrUpdate(Entity entity) throws IllegalArgumentException, IllegalAccessException, SQLException {
        // Class type of entity.
        Class<? extends Entity> clazz = entity.getClass();
        // Get cached fields.
        List<Field> foreignFields = getForeignFields(clazz);
        if (foreignFields != null) {
            // Recursively create fields
            for (Field field : foreignFields) {
                Object object = field.get(entity);
                createOrUpdate((Entity)object);
            }
        }
        // We are at leaf child, retrieve the common DAO for the entity class.
        @SuppressWarnings("unchecked")
        Dao<Entity, Integer> dao = (Dao<Entity, Integer>)getDao(clazz);
        try {
            // Persist the entity to the database.
            dao.createOrUpdate(entity);
        } catch (SQLException e) {
            // SQLException.
            throw e;
        }
    }

	/**
	 * Removes a entry from the database.
	 * 
	 * @param entity
	 * @return True if the entity was removed.
	 */
	public synchronized boolean delete(Entity entity) {
		try {
			@SuppressWarnings("unchecked")
			Dao<Entity, Integer> dao = (Dao<Entity, Integer>)getDao(entity.getClass());
			if (dao.delete(entity) != 1) {
				return false;
			}
		} catch (SQLException e) {
			// Can't remove object.
			return false;
		}
		return true;
	}

    /**
     * Update an entity with its primary key set. Recursively refresh child entities.
     * Return the number of parent rows affected. Should be 1.
     * 
     * @param entity
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public synchronized int refresh(Entity entity) throws SQLException, IllegalArgumentException, IllegalAccessException {
        @SuppressWarnings("unchecked")
        Dao<Entity, Integer> dao = (Dao<Entity, Integer>)getDao(entity.getClass());
        // Recursive save reference entity.
        int result = dao.refresh(entity);
        // Get cached fields.
        List<Field> foreignFields = getForeignFields(entity.getClass());
        if (foreignFields == null) {
            return result;
        }
        // Recursively refresh fields.
        for (Field field : foreignFields) {
            Object object = field.get(entity);
            refresh((Entity)object);
        }
        // Return original result.
        return result;
    }
    
	/**
	 * 
	 * @param list
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SQLException
	 * @throws IllegalAccessException
	 */
	public boolean refreshList(List<? extends Entity> list) throws IllegalArgumentException, SQLException, IllegalAccessException {
		for (Entity entity : list) {
			if (refresh(entity) != 1) 
				return false;
		}
		return true;
	}

    /**
     * Gets all persisted objects from the given entity class.
     * 
     * @param entityClass
     * @return A list with all persisted entity objects.
     */
    public synchronized List<? extends Entity> queryForAll(Class<? extends Entity> entityClass) {
        try {
            // Retrieve the common DAO for the entity class.
            @SuppressWarnings("unchecked")
            Dao<Entity, Integer> dao = (Dao<Entity, Integer>)getDao(entityClass);
            // Return query results.
            return dao.queryForAll();
        } catch (SQLException e) {
            // Can not query objects.
        }
        return null;
    }

    /**
     * Gets the persisted object from the given entity class and mCurrentId. 
     * The parent and all subsequent child will be refreshed.
     * 
     * @param entityClass
     * @param id
     * @return The persisted entity object from the given entity class and mCurrentId.
     */
    public synchronized Entity queryById(Class<? extends Entity> entityClass, Integer id) {
        try {
            // Create entity instance to 'refresh'.
            Entity entity = entityClass.newInstance();
            // Set the required primary key.
            entity.setId(id);
            // Perform a recursive refresh
            int result = refresh(entity);
            // Return entity when refresh was successful
            if (result == 1) {
                return entity;
            }
        } catch (SQLException e) {
        	// Can not query objects.
        } catch (IllegalArgumentException e) {
        	// Can not query objects.
        } catch (IllegalAccessException e) {
        	// Can not query objects.
        } catch (InstantiationException e) {
        	// Can not query objects.
        }
        return null;
    }

	//----------------------------------------------
	// Foreign Fields
	//----------------------------------------------

    /**
     * Retrieve and possibly populate fields list.
     * 
     * @param clazz
     * @return The foreign fields of the given class.
     */
    private static List<Field> getForeignFields(Class<?> clazz) {
        List<Field> foreignfields = sClassFieldMap.get(clazz);
        if (foreignfields == null) {
            registerForeignFields(clazz);
            return sClassFieldMap.get(clazz);
        }
        return foreignfields;
    }

    /**
     * Registers the foreign fields for a particular class.
     * 
     * @param clazz
     */
    private static void registerForeignFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<Field>();
        sClassFieldMap.put(clazz, fields);

        // Search declared fields and refresh child entities.
        for (Field field : clazz.getDeclaredFields()) {
            // Inspect annotations.
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                // Only consider fields with the DatabaseField annotation.
                if (annotation instanceof DatabaseField) {
                    // Check for foreign attribute.
                    if (((DatabaseField)annotation).foreign()) {
                        // Check for Entity subclass.
                        if (Entity.class.isAssignableFrom(field.getDeclaringClass())) {
                            fields.add(field);
                        }
                    }
                }
            }
        }
    }
}