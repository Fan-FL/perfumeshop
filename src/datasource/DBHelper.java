package datasource;

import util.ReflectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper {
    /**
     * Return map when selecting key-foreign key data
     * @param keySql sql query for key
     * @param keyClazz Class object of key
     * @param valueSql sql query for value
     * @param valueClazz Class object of value
     * @param columnName column alias of key-foreign key map
     * @param args  keySql args，valueSql query condition is the value of columnName of key keySql
     *              query
     * @return
     */
    public static <K, V> Map<K, V> getMapHandler(String keySql, Class<K> keyClazz, String valueSql,
                                           Class<V> valueClazz,
                                          String columnName, Object... args) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        K kEntity = null;
        V vEntity = null;
        Map<K, V> map = null;
        try {
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(keySql);
            prepareStateSetArgs(ps, args);
            map = new HashMap<K, V>();
            resultSet = ps.executeQuery();
            // Use this object to get column count by getColumnCount()
            // get column name by getColumnLabel()
            ResultSetMetaData rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                Map<String, Object> beanValues = new HashMap<String, Object>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    // get column alias
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    // get column value by column alias
                    Object columnValue = resultSet.getObject(columnLabel);
                    beanValues.put(columnLabel, columnValue);
                    //get column name
                    String columnNameInRS = rsmd.getColumnName(i + 1);
                    //if column name is the same as input key-foreign key column name, use this
                    // as key to query from DB. The results of valueSql query is the value of
                    // in type of valueClazz
                    if(columnNameInRS.equals(columnName)){
                        Object columnNameValue = resultSet.getObject(i + 1);
                        vEntity = getObject(valueClazz, valueSql, columnNameValue);
                    }else {
                        continue;
                    }
                }
                //if beanValues is not empty, make this Map as the key in type of keyClazz
                if (!beanValues.isEmpty()) {
                    kEntity = transferMapToBean(keyClazz,beanValues);
                }
                //if kEntity and vEntity are not null, put into map，
                if(kEntity != null && vEntity != null){
                    map.put(kEntity, vEntity);
                }else {
                    System.out.println("-----Map association failed. Move to next round------");
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, resultSet);
        }
        return map;
    }

    /**
     * padding for sql query
     * @param ps
     * @param args
     * @throws SQLException
     */
    private static void prepareStateSetArgs(PreparedStatement ps, Object... args) throws
            SQLException {
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
    }

    /**
     * General query method, return object according to SQL, Class
     *
     * @param sql
     *            :sql sentence, can contain placeholders
     * @param args
     *            :args for placeholders
     * @return
     */
    public static <T> T getObject(Class<T> clazz,String sql, Object... args) {
        List<T> list = getObjectForList(clazz,sql, args);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     *  transform a map into Bean according to clazz
     * @param clazz
     * @param values
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private static <T> T transferMapToBean(Class<T> clazz,Map<String, Object> values) throws
            InstantiationException, IllegalAccessException {
        T entity;
        entity = (T) clazz.newInstance();
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();
            try {
                ReflectionUtils.setterValue(entity, fieldName, value);
            } catch (IllegalArgumentException e) {
            }
        }
        return entity;
    }

    /**
     *
     * @param clazz  Class object of T
     * @param sql
     * @param args
     * @return
     */
    public static <T> List<T> getObjectForList(Class<T> clazz,String sql, Object... args) {
        List<T> list = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        T entity = null;
        try {
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(sql);
            prepareStateSetArgs(ps, args);
            list = new ArrayList<T>();
            resultSet = ps.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();

            while (resultSet.next()) {
                Map<String, Object> values = putOneResultSetToMap(resultSet, rsmd);
                if (!values.isEmpty()) {
                    entity = transferMapToBean(clazz,values);
                    list.add(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, resultSet);
        }
        return list;
    }

    /**
     * put one record from result set into map in key-value form
     *
     * @param resultSet
     * @param rsmd
     * @return
     * @throws SQLException
     */
    private static Map<String, Object> putOneResultSetToMap(ResultSet resultSet,
                                                            ResultSetMetaData rsmd) throws SQLException {
        Map<String, Object> values = new HashMap<String, Object>();
        // iteratively get column name and value
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            String columnLabel = rsmd.getColumnLabel(i + 1);
            Object columnValue = resultSet.getObject(columnLabel);// get value by column name
            values.put(columnLabel, columnValue);
        }
        return values;
    }

    /**
     * get value of first column in first row, can use this method to get record count
     *
     * @param sql
     * @param args
     * @return
     */
    public static Object getValue(String sql, Object... args) {
        Object value = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(sql);
            prepareStateSetArgs(ps, args);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                value = resultSet.getObject(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, resultSet);
        }
        return value;
    }

    /**
     * generate method for  insert、delete、update
     *
     * @throws Exception
     */
    public static void update(String sql, Object... args) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(sql);
            prepareStateSetArgs(ps, args);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, null);
        }
    }

    /**
     * insert one row and return id
     * return -1 if failed to insert
     *
     * @param sql
     * @param args
     * @return
     */
    public static int updateGetGeneratedKeys(String sql, Object... args) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int generatedKey = -1;
        try {
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepareStateSetArgs(ps, args);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }
        return generatedKey;
    }
}
