package datasource;

import util.ReflectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper {
    /**
     * 从数据库中查询具有 键-值 关系的数据时，返回Map集合
     * @param keySql 查询key的sql语句
     * @param keyClazz key的Class对象
     * @param valueSql 查询value的sql语句
     * @param valueClazz value的Class对象
     * @param columnName 映射键值关系的列名
     * @param args 填充SQL语句的可变参数，实际是填充keySql的可变参数  ，而valueSql的查询条件是   keySql查询到的列名为columnName的对应的  值
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
            //con = JDBCTools.getConnection();
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(keySql);
            prepareStateSetArgs(ps, args);
            map = new HashMap<K, V>();
            resultSet = ps.executeQuery();
            // 通过该对象调用getColumnCount()获取列总数；getColumnLabel()获取列别名
            ResultSetMetaData rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                Map<String, Object> beanValues = new HashMap<String, Object>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    // 获取列对应的列的别名
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    // 根据列的别名从ResultSet结果集中获得对应的值
                    Object columnValue = resultSet.getObject(columnLabel);
                    beanValues.put(columnLabel, columnValue);
                    //获取列名
                    String columnNameInRS = rsmd.getColumnName(i + 1);
                    //如果得到的列名 与传入的进行键值关联的列名相同 则通过此键 从数据库获取  valueSql查询中对应的数据 构建为 valueClazz对应的V的对象
                    if(columnNameInRS.equals(columnName)){
                        Object columnNameValue = resultSet.getObject(i + 1);
                        vEntity = getObject(valueClazz, valueSql, columnNameValue);
                    }else {
                        continue;
                    }
                }
                //beanValues不为空，则将该Map构建为 keyClazz 对应的 K 的对象
                if (!beanValues.isEmpty()) {
                    kEntity = transferMapToBean(keyClazz,beanValues);
                }
                //kEntity 和 vEntity都不为null 时，将他们已键值对的形式放入Map集合中
                if(kEntity != null && vEntity != null){
                    map.put(kEntity, vEntity);
                }else {
                    System.out.println("-----Map对象组合失败,进入下一次循环------");
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
     * 为预编译的 sql 语句填充占位符
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
     * 通用的查询方法，可以根据传入的SQL、Class 对象返回SQL对应的记录的对象
     *
     * @param sql
     *            :查询语句，可能带占位符
     * @param args
     *            :填充占位符的参数
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
     *  将一个Map转换为clazz对应的Bean对象
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
			/*if(value !=null){
				System.out.println(value.getClass());
			}*/
			/*if(value instanceof BigDecimal){
				Field field = ReflectionUtils.getDeclaredField(entity, fieldName);
				//System.out.println(field.getType());
				value = ReflectionUtils.invokeMethod(((BigDecimal) value), field.getType().toString()+"Value",new Class[0]);
			}*/
            // 调用ReflectionUtil.setFieldValue()为result对象的每个属性赋值
            // ReflectionUtils.setFieldValue(entity, fieldName, value);
            // 通过调用setter方法对属性进行赋值
            try {
                //System.out.println(entry);
                ReflectionUtils.setterValue(entity, fieldName, value);
            } catch (IllegalArgumentException e) {
                //System.out.println("IllegalArgumentException异常,赋值失败");
            }
        }
        return entity;
    }

    /**
     *
     * @param clazz T 的Class对象
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
            // con = JDBCTools.getConnection();
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(sql);
            prepareStateSetArgs(ps, args);
            list = new ArrayList<T>();
            resultSet = ps.executeQuery();
            // 通过该对象调用getColumnCount()获取列总数；getColumnLabel()获取列别名
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
     * 将结果集中的一条记录以 key-value 的形式放入Map中
     *
     * @param resultSet
     * @param rsmd
     * @return
     * @throws SQLException
     */
    private static Map<String, Object> putOneResultSetToMap(ResultSet resultSet,
                                                            ResultSetMetaData rsmd) throws SQLException {
        Map<String, Object> values = new HashMap<String, Object>();
        // 循环，获取列及对应的列名
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            String columnLabel = rsmd.getColumnLabel(i + 1);
            Object columnValue = resultSet.getObject(columnLabel);// 根据列名从ResultSet结果集中获得对应的值
            values.put(columnLabel, columnValue);
        }
        return values;
    }

    /**
     * 获取返回结果中 第一行第一列的值，可通过此方法获取满足条件的共有多少条数据
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
            // con = JDBCTools.getConnection();
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(sql);
            prepareStateSetArgs(ps, args);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                value = resultSet.getObject(1);
                //System.out.println("getValue:" + value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, resultSet);
        }
        return value;
    }

    /**
     * 通用的更新方法：insert、delete、update
     *
     * @throws Exception
     */
    public static void update(String sql, Object... args) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            // con = JDBCTools.getConnection();
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
     * 插入一条数据 同时返回自增的 id 值 返回-1表明获取失败
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
