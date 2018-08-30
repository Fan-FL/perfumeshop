package datasource;

import domain.Pager;
import util.ReflectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PagerHandler {
    public <T> Pager<T> getPager(Class<T> clazz, String sqlForDataCount, String
            sqlForData, int
            currPage, int pageSize, Object... args) {
        domain.Pager<T> pager = null;
        sqlForData = sqlForData + " limit ?,?";
        int dataIndex = (currPage - 1) * pageSize;

        // prepare args for getObjectForList(String sql,Object...args)
        Object[] parameters = new Object[args.length + 2];
        for (int i = 0; i < args.length; i++) {
            parameters[i] = args[i];
        }
        parameters[args.length] = dataIndex;
        parameters[args.length + 1] = pageSize;

        List<T> pageDataList = getObjectForList(clazz,sqlForData, parameters);
        int dataCount = 0;
        try {
            dataCount = Integer.parseInt(getValue(sqlForDataCount, args).toString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get (dataCount)！");
        }
        int pageCount = (dataCount % pageSize == 0) ? (dataCount / pageSize) : (dataCount / pageSize + 1);
        pager = new domain.Pager<T>(currPage, pageSize, pageCount, dataCount, pageDataList);
        return pager;
    }

    public <T> List<T> getObjectForList(Class<T> clazz,String sql, Object... args) {
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
            // Use this object to get column count by getColumnCount()
            // get column name by getColumnLabel()
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
            DBConnection.release(ps, con, resultSet);
        }
        return list;
    }

    private Map<String, Object> putOneResultSetToMap(ResultSet resultSet, ResultSetMetaData rsmd) throws SQLException {
        Map<String, Object> values = new HashMap<String, Object>();
        // iteratively get column name and value
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            String columnLabel = rsmd.getColumnLabel(i + 1);
            Object columnValue = resultSet.getObject(columnLabel);// get value by column name
            values.put(columnLabel, columnValue);
        }
        return values;
    }

    public Object getValue(String sql, Object... args) {
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
            DBConnection.release(ps, con, resultSet);
        }
        return value;
    }

    private void prepareStateSetArgs(PreparedStatement ps, Object... args) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
    }

    private <T> T transferMapToBean(Class<T> clazz,Map<String, Object> values) throws InstantiationException, IllegalAccessException {
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
}
