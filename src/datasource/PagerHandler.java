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

        // 准备要传入 getObjectForList(String sql,Object...args) 方法的args参数
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
            throw new RuntimeException("(dataCount)数据总数获取失败！");
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
            DBConnection.release(ps, con, resultSet);
        }
        return list;
    }

    private Map<String, Object> putOneResultSetToMap(ResultSet resultSet, ResultSetMetaData rsmd) throws SQLException {
        Map<String, Object> values = new HashMap<String, Object>();
        // 循环，获取列及对应的列名
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            String columnLabel = rsmd.getColumnLabel(i + 1);
            Object columnValue = resultSet.getObject(columnLabel);// 根据列名从ResultSet结果集中获得对应的值
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
}
