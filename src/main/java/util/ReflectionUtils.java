package util;

import java.lang.reflect.*;

public class ReflectionUtils {


    /**
     * Using reflection get declared field by iterating super classes
     *
     * @param obj
     * @param fieldName
     * @return Field
     */
    public static Field getDeclaredField(Object obj, String fieldName) {
        Field field = null;
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                field = superClass.getDeclaredField(fieldName);
                return field;
            } catch (NoSuchFieldException e) {
                // not declared in current object
            }
        }
        return field;
    }

    /**
     * assign value to the field of fieldName in obj
     *
     * @param obj
     * @param fieldName
     * @param value
     */
    public static void setterValue(Object obj, String fieldName, Object value) {
        Field field = getDeclaredField(obj, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Can not find field[" + fieldName + "] on target [" + obj + "]");
        }
        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        invokeMethod(obj, methodName, new Class<?>[]{field.getType()}, value);
    }


    /**
     * Using reflection get declared method by iterating super classes
     *
     * @param object:
     * @param methodName：
     * @param parameterTypes：Class list of arguments
     * @return Method
     */
    public static Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
            } catch (Exception e) {
                // not declared in current object
            }
        }
        return method;
    }

    /**
     * invoke method in certain object regardless of private or protected
     *
     * @param object:              object that method will invoke on
     * @param methodName：
     * @param parameterTypes：Class list of arguments
     * @param parameters:          argument of method
     * @return
     */
    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes,
                                      Object... parameters) throws IllegalArgumentException {
        Method method = getDeclaredMethod(object, methodName, parameterTypes);
        method.setAccessible(true);
        try {
            return method.invoke(object, parameters);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
