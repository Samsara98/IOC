package demo;

import demo.annotation.Autowired;
import demo.annotation.Component;
import demo.annotation.Value;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class MyApplicationContext implements BeanFactory {

    private Map<String, Object> beans = new HashMap<>();

    public MyApplicationContext() throws Exception {
        File f = new File("./src/demo/bean");
        String[] files = f.list();
        for (String s : files) {
            Class<?> aClass = Class.forName("demo.bean." + s.split("\\.")[0]);
            if (aClass.isAnnotationPresent(Component.class)) {
                String value = aClass.getAnnotation(Component.class).value();
                Object o = aClass.getDeclaredConstructor().newInstance();
                beans.put(value, o);
            }
        }
        for (String s : files) {
            Class<?> aClass = Class.forName("demo.bean." + s.split("\\.")[0]);
            if (aClass.isAnnotationPresent(Component.class)) {
                String value = aClass.getAnnotation(Component.class).value();
                Object o = beans.get(value);
                Field[] fields = aClass.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Value.class)) {
                        injectionValue(aClass, o, field);
                        continue;
                    }
                    if (field.isAnnotationPresent(Autowired.class)) {
                        String fieldName = field.getName();
                        Object beanObject = beans.get(fieldName);
                        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        System.out.println("setter Object method name = " + methodName);
                        Method m = o.getClass().getMethod(methodName, beanObject.getClass());
                        m.invoke(o, beanObject);//调用setter方法
                    }
                }
            }
        }

    }

    private void injectionValue(Class<?> aClass, Object o, Field field) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String fieldValue = field.getAnnotation(Value.class).value();
        String fieldName = field.getName();
        Class<?> fieldType = field.getType();
        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        System.out.println("setter method name = " + methodName);
        Method method = aClass.getMethod(methodName, fieldType);
        Object val = fieldValue;
        switch (fieldType.getName()) {
            case "int":
            case "java.lang.Integer":
                val = Integer.valueOf(fieldValue);
                break;
            case "boolean":
            case "java.lang.Boolean":
                val = Boolean.valueOf(fieldValue);
                break;
            case "char":
            case "java.lang.Character":
                val = fieldValue.charAt(0);
                break;
            case "long":
            case "java.lang.Long":
                val = Long.valueOf(fieldValue);
                break;
            case "double":
            case "java.lang.Double":
                val = Double.valueOf(fieldValue);
                break;
            case "float":
            case "java.lang.Float":
                val = Float.valueOf(fieldValue);
                break;
            case "byte":
            case "java.lang.Byte":
                val = Byte.valueOf(fieldValue);
                break;
            case "short":
            case "java.lang.Short":
                val = Short.valueOf(fieldValue);
                break;
        }
        method.invoke(o, val);
    }

    @Override
    public Object getBean(String name) {
        return beans.get(name);
    }
}
