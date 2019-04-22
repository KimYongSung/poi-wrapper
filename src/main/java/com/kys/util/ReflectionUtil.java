package com.kys.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * java Reflection util
 *
 * @author kys0213
 * @since 2018. 6. 28.
 */
public class ReflectionUtil {

	/**
	 * Reflection 성능 향상을 위한 캐싱용 맵 <br>
	 * <br>
	 * <strong> 서비스별 reflection을 사용하는 VO의 숫자는 일정하다는 가정하에 메모리 캐싱</strong>
	 */
	private static final ConcurrentMap<Class<?>, Method[]> cacheMethodMap = MapUtil.makeConcurrentLRUMap(50);

	/**
	 * Reflection 성능 향상을 위한 캐싱용 맵 <br>
	 * <br>
	 * <strong> 서비스별 reflection을 사용하는 VO의 숫자는 일정하다는 가정하에 메모리 캐싱</strong>
	 */
	private static final ConcurrentMap<Class<?>, Field[]> cacheFieldMap = MapUtil.makeConcurrentLRUMap(50);

	/**
	 * Reflection 성능 향상을 위한 캐싱용 맵 <br>
	 * <br>
	 * <strong> 서비스별 reflection을 사용하는 VO의 숫자는 일정하다는 가정하에 메모리 캐싱</strong>
	 */
	private static final ConcurrentMap<AnnotatedElement, Annotation[]> cacheAnnotaionMap = MapUtil.makeConcurrentLRUMap(200);
	
	/**
	 * Reflection 성능 향상을 위한  캐싱용 맵
	 * <br><br><strong> 서비스별 reflection을 사용하는 VO의 숫자는 일정하다는 가정하에 메모리 캐싱</strong>
	 */
	private static final ConcurrentMap<Class<?>, Constructor<?>[]> cacheConstructorMap = MapUtil.makeConcurrentLRUMap(50);

	/**
	 * 메소드가 존재하지 않을 경우 메모리에 캐싱하기 위한 empty Method[]
	 */
	private static final Method[] NO_METHODS = {};

	/**
	 * 필드가 존재하지 않을 경우 메모리에 캐싱하기 위한 empty Field[]
	 */
	private static final Field[] NO_FIELDS = {};

	/**
	 * 어노테이션이 존재하지 않을 경우 메모리에 캐싱하기 위한 empty Annotation[]
	 */
	private static final Annotation[] NO_ANNOTATION = {};
	
	/**
	 * 필드가 존재하지 않을 경우 메모리에 캐싱하기 위한 empty Field[]
	 */
	private static final Constructor<?>[] NO_CONSTRUCTOR = {};
	
	/**
	 * 인수가 없는 경우
	 */
	private final static Class<?>[] EMPTY_ARGS = new Class<?>[0];
	
	/**
	 * 매개변수가 없는 경우
	 */
	private final static Object[] EMPTY_PARAM = new Object[] {};

	/**
	 * 해당 Field의 타입이 특정 class와 일치 여부
	 * 
	 * @param field
	 * @param clazz
	 * @return
	 */
	public static boolean isTypeEquals(Field field, Class<?> clazz) {
		return isTypeEquals(clazz, field.getType());
	}

	/**
	 * class1 과 class2의 일치 여부
	 * 
	 * @param clazz1
	 * @param clazz2
	 * @return
	 */
	public static boolean isTypeEquals(Class<?> clazz1, Class<?> clazz2) {
		return clazz1.isAssignableFrom(clazz2) || clazz2.isAssignableFrom(clazz1);
	}

	/**
	 * Collection 객체의 genericType 확인
	 * 
	 * @param field
	 * @param fieldType
	 * @param genericType
	 * @return
	 */
	public static boolean isCollectionGenericTypeEquals(Field field, SuperTypeToken<?> token) {
		return isTypeEquals(field, Collection.class)
				&& getGenericTypeName(field.getGenericType(), 0).equals(token.typeName());
	}

	/**
	 * Map 객체의 genericType 확인
	 * 
	 * @param field
	 * @param fieldType
	 * @param genericType
	 * @return
	 */
	public static boolean isMapGenericTypeEquals(Field field, SuperTypeToken<?> key, SuperTypeToken<?> value) {

		if (!isTypeEquals(field, Map.class)) {
			return false;
		}

		Type[] genericTypes = getActualTypeArgument(field.getGenericType());

		return getTypeName(genericTypes[0]).equals(key.typeName())
				&& getTypeName(genericTypes[1]).equals(value.typeName());
	}

	/**
	 * Generic 타입명 확인
	 * 
	 * @param type
	 * @param index
	 * @return
	 */
	public static String getGenericTypeName(Type type, int index) {
		return getTypeName(getGenericType(type, index));
	}

	/**
	 * Generic 타입 리턴
	 * 
	 * @param type
	 * @param index
	 * @return
	 */
	public static Type getGenericType(Type type, int index) {
		Type[] actualTypeArguments = getActualTypeArgument(type);

		if ((actualTypeArguments.length - 1) < index) {
			throw new IllegalArgumentException(
					"ActualTypeArgument index out of bounds - " + actualTypeArguments.length);
		}

		return actualTypeArguments[index];
	}

	/**
	 * ActualTypeArgument array
	 * 
	 * @param type
	 * @return
	 * @throws IllegalAccessError
	 */
	public static Type[] getActualTypeArgument(Type type) throws IllegalAccessError {
		ParameterizedType parameterizedType = ObjectUtils.cast(type, ParameterizedType.class);

		if (ObjectUtils.isNull(parameterizedType)) {
			throw new IllegalAccessError("invalid Parameter type : " + type.getClass().getName());
		}

		Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

		if (ObjectUtils.isNull(actualTypeArguments)) {
			throw new IllegalAccessError("invalid actualTypeArguments");
		}
		return actualTypeArguments;
	}

	/**
	 * Annotation 찾기
	 * 
	 * @param element
	 * @param annotationClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T findAnnotation(AnnotatedElement element, Class<T> annotationClass) {

		Annotation[] annotations = getDeclaredAnnotation(element);

		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(annotationClass)) {
				return (T) annotation;
			}
		}
		return null;
	}
	
	/**
	 * 생성자 찾기
	 * @param clazz
	 * @param parameterTypes
	 * @return
	 */
	public static <T> Constructor<T> findConstructor(Class<T> clazz, Class<?>... parameterTypes){
		Constructor<T>[] constructors = getDeclaredConstructor(clazz);
		
		for (Constructor<T> constructor : constructors) {
			if(Arrays.equals(parameterTypes, constructor.getParameterTypes())){
				return constructor;
			}
		}
		
		return null;
	}
	
	/**
	 * 인수가 없는 생성자 찾기
	 * @param clazz
	 * @param parameterTypes
	 * @return
	 */
	public static <T> Constructor<T> findNoArgConstructor(Class<T> clazz){
		Constructor<T>[] constructors = getDeclaredConstructor(clazz);
		
		for (Constructor<T> constructor : constructors) {
			if(Arrays.equals(EMPTY_ARGS, constructor.getParameterTypes())){
				return constructor;
			}
		}
		
		return null;
	}

	/**
	 * 인스턴스 생성
	 * @param clazz 클래스
	 * @param args	생성자 파라미터
	 * @return
	 */
	public static <T> T newInstance(Class<T> clazz, Object... args){
		try {
			Class<?>[] findParameterType = findParameterType(args);
			
			Constructor<T> findConstructor = findConstructor(clazz, findParameterType);
			
			if(findConstructor == null)
				throw new NoSuchMethodException("constructor not found !! - class : " + clazz.getName() + ", args : " + Arrays.toString(args));
			
			return newInstance(findConstructor, args);
		} catch (Exception e) {
			handleReflectionException(e);
		}
		return null;
	}

	/**
	 * 인스턴스 생성
	 * @param className 클래스 명
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(String className){
		try {
			Class<?> clazz = Class.forName(className);
			return (T) clazz.newInstance();
		} catch (Exception e) {
			handleReflectionException(e);
		}
		
		return null;
	}

	/**
	 * 인스턴스 생성
	 * @param clazz
	 * @return
	 */
	public static <T> T newInstance(Class<T> clazz){
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			handleReflectionException(e);
		}
		return null;
	}
	
	/**
	 * 인스턴스 생성
	 * @param constructor 생성자
	 * @return
	 */
	public static <T> T newInstance(Constructor<T> constructor){
		try {
			return constructor.newInstance(EMPTY_PARAM);
		} catch (Exception e) {
			handleReflectionException(e);
		}
		return null;
	}

	/**
	 * 인스턴스 생성
	 * @param constructor 생성자
	 * @param args	생성자 파라미터
	 * @return
	 */
	public static <T> T newInstance(Constructor<T> constructor, Object... args){
		try {
			return constructor.newInstance(args);
		} catch (Exception e) {
			handleReflectionException(e);
		}
		return null;
	}

	/**
	 * Field 찾기
	 * 
	 * @param clazz
	 * @return
	 */
	public static Field[] findFields(Class<?> clazz) {
		return getDeclaredFields(clazz);
	}

	/**
	 * Field 찾기
	 * 
	 * @param clazz
	 * @return
	 */
	public static Field findField(Class<?> clazz, String name) {

		Field[] fields = getDeclaredFields(clazz);

		for (Field field : fields) {
			if (field.getName().equals(name)) {
				return field;
			}
		}

		return null;
	}

	/**
	 * Method 찾기
	 * 
	 * @param clazz
	 * @param name
	 * @return
	 */
	public static Method findMethod(Class<?> clazz, String name) {
		return findMethod(clazz, name, EMPTY_ARGS);
	}

	/**
	 * Method 찾기
	 * 
	 * @param clazz
	 * @param name
	 * @param paramTypes
	 * @return
	 */
	public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
		Method[] methods = getDeclaredMethods(clazz);

		for (Method method : methods) {
			if (method.getName().equals(name) && paramTypes == null
					|| Arrays.equals(paramTypes, method.getParameterTypes())) {
				return method;
			}
		}

		return null;
	}

	/**
	 * Method 실행
	 * 
	 * @param method
	 * @param target
	 * @return
	 */
	public static Object invokeMethod(Method method, Object target) {
		return invokeMethod(method, target, EMPTY_PARAM);
	}

	/**
	 * Method 실행
	 * 
	 * @param method
	 * @param target
	 * @param args
	 * @return
	 */
	public static Object invokeMethod(Method method, Object target, Object... args) {
		try {
			makeAccessible(method);
			return method.invoke(target, args);
		} catch (Exception e) {
			handleReflectionException(e);
		}
		return null;
	}

	/**
	 * Field 에 값 셋팅
	 * 
	 * @param field
	 * @param target
	 * @param value
	 */
	public static void setField(Field field, Object target, Object value) {
		try {
			makeAccessible(field);
			field.set(target, value);
		} catch (IllegalAccessException ex) {
			handleReflectionException(ex);
			throw new IllegalStateException(
					"Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	}

	/**
	 * Field 값 조회
	 * 
	 * @param field
	 * @param target
	 * @return
	 */
	public static Object getField(Field field, Object target) {
		try {
			makeAccessible(field);
			return field.get(target);
		} catch (IllegalAccessException ex) {
			handleReflectionException(ex);
			throw new IllegalStateException(
					"Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	}

	/**
	 * Make the given field accessible, explicitly setting it accessible if
	 * necessary. The {@code setAccessible(true)} method is only called when
	 * actually necessary, to avoid unnecessary conflicts with a JVM
	 * SecurityManager (if active).
	 * 
	 * @param field
	 *            the field to make accessible
	 * @see java.lang.reflect.Field#setAccessible
	 */
	public static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
				|| Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	/**
	 * Make the given method accessible, explicitly setting it accessible if
	 * necessary. The {@code setAccessible(true)} method is only called when
	 * actually necessary, to avoid unnecessary conflicts with a JVM
	 * SecurityManager (if active).
	 * 
	 * @param method
	 *            the method to make accessible
	 * @see java.lang.reflect.Method#setAccessible
	 */
	public static void makeAccessible(Method method) {
		if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
				&& !method.isAccessible()) {
			method.setAccessible(true);
		}
	}

	/**
	 * Handle the given reflection exception. Should only be called if no
	 * checked exception is expected to be thrown by the target method.
	 * <p>
	 * Throws the underlying RuntimeException or Error in case of an
	 * InvocationTargetException with such a root cause. Throws an
	 * IllegalStateException with an appropriate message or
	 * UndeclaredThrowableException otherwise.
	 * 
	 * @param ex
	 *            the reflection exception to handle
	 */
	public static void handleReflectionException(Exception ex) {
		if (ex instanceof NoSuchMethodException) {
			throw new IllegalStateException("Method not found: " + ex.getMessage());
		}
		if (ex instanceof IllegalAccessException) {
			throw new IllegalStateException("Could not access method: " + ex.getMessage());
		}
		if (ex instanceof InvocationTargetException) {
			handleInvocationTargetException((InvocationTargetException) ex);
		}
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		throw new UndeclaredThrowableException(ex);
	}

	/**
	 * Handle the given invocation target exception. Should only be called if no
	 * checked exception is expected to be thrown by the target method.
	 * <p>
	 * Throws the underlying RuntimeException or Error in case of such a root
	 * cause. Throws an UndeclaredThrowableException otherwise.
	 * 
	 * @param ex
	 *            the invocation target exception to handle
	 */
	public static void handleInvocationTargetException(InvocationTargetException ex) {
		rethrowRuntimeException(ex.getTargetException());
	}

	/**
	 * Rethrow the given {@link Throwable exception}, which is presumably the
	 * <em>target exception</em> of an {@link InvocationTargetException}. Should
	 * only be called if no checked exception is expected to be thrown by the
	 * target method.
	 * <p>
	 * Rethrows the underlying exception cast to a {@link RuntimeException} or
	 * {@link Error} if appropriate; otherwise, throws an
	 * {@link UndeclaredThrowableException}.
	 * 
	 * @param ex
	 *            the exception to rethrow
	 * @throws RuntimeException
	 *             the rethrown exception
	 */
	public static void rethrowRuntimeException(Throwable ex) {
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		if (ex instanceof Error) {
			throw (Error) ex;
		}
		throw new UndeclaredThrowableException(ex);
	}
	
	/**
	 * Object배열의 타입 찾기
	 * @param clazz
	 * @return
	 */
	private static Class<?>[] findParameterType(Object... arg){
		int length = arg.length;
		Class<?>[] types = new Class<?>[length];
		for (int index = 0; index < length; index++) {
			types[index] = arg[index].getClass();
		}
		return types;
	}
	
	/**
	 * Constructor 배열 찾기
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T> Constructor<T>[] getDeclaredConstructor(Class<T> clazz){
		Constructor<?>[] constructors = cacheConstructorMap.get(clazz);
		
		if(constructors == null){
			constructors = clazz.getDeclaredConstructors();
			cacheConstructorMap.put(clazz, (constructors.length == 0 ? NO_CONSTRUCTOR : constructors));
		}
		
		return (Constructor<T>[]) constructors;
	}

	/**
	 * Field 배열 찾기
	 * 
	 * @param clazz
	 * @return
	 */
	private static Field[] getDeclaredFields(Class<?> clazz) {
		Field[] fields = cacheFieldMap.get(clazz);

		if (fields == null) {
			fields = clazz.getDeclaredFields();
			cacheFieldMap.put(clazz, (fields.length == 0 ? NO_FIELDS : fields));
		}

		return fields;
	}

	/**
	 * Method 배열 찾기
	 * 
	 * @param clazz
	 * @return
	 */
	private static Method[] getDeclaredMethods(Class<?> clazz) {
		Method[] methods = cacheMethodMap.get(clazz);

		if (methods == null) {
			methods = clazz.getDeclaredMethods();
			cacheMethodMap.put(clazz, (methods.length == 0 ? NO_METHODS : methods));
		}

		return methods;
	}

	/**
	 * Annotation 배열 찾기
	 * 
	 * @param element
	 * @return
	 */
	private static Annotation[] getDeclaredAnnotation(AnnotatedElement element) {
		Annotation[] annotations = cacheAnnotaionMap.get(element);

		if (annotations == null) {
			annotations = element.getDeclaredAnnotations();
			cacheAnnotaionMap.put(element, (annotations.length == 0 ? NO_ANNOTATION : annotations));
		}

		return annotations;
	}

	/**
	 * Type 명 정보
	 * 
	 * @param type
	 * @return
	 */
	private static String getTypeName(Type type) {

		String typeName = type.toString();

		int toStringPrefixIndex = typeName.indexOf("class");

		if (toStringPrefixIndex > -1) {
			typeName = typeName.substring(5);
		}

		return typeName.trim();
	}

	/**
	 * SuperTypeToken 정보
	 * 
	 * @author kys0213
	 * @since 2018. 6. 25.
	 * @param <T>
	 */
	public static abstract class SuperTypeToken<T> {

		private final Type type;

		public SuperTypeToken() {
			Class<?> parameterizedTypeReferenceSubclass = findSuperTypeTokenReferenceSubclass(getClass());
			Type type = parameterizedTypeReferenceSubclass.getGenericSuperclass();
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
			this.type = actualTypeArguments[0];
		}

		private SuperTypeToken(Type type) {
			this.type = type;
		}

		public Type getType() {
			return this.type;
		}

		@Override
		public boolean equals(Object obj) {
			return (this == obj || (obj instanceof SuperTypeToken && this.type.equals(((SuperTypeToken<?>) obj).type)));
		}

		@Override
		public int hashCode() {
			return this.type.hashCode();
		}

		public String typeName() {
			return ReflectionUtil.getTypeName(type);
		}

		@Override
		public String toString() {
			return "ParameterizedTypeReference<" + this.type + ">";
		}

		public static <T> SuperTypeToken<T> forType(Type type) {
			return new SuperTypeToken<T>(type) {
			};
		}

		private static Class<?> findSuperTypeTokenReferenceSubclass(Class<?> child) {
			Class<?> parent = child.getSuperclass();
			if (Object.class == parent) {
				throw new IllegalStateException("Expected ParameterizedTypeReference superclass");
			} else if (SuperTypeToken.class == parent) {
				return child;
			} else {
				return findSuperTypeTokenReferenceSubclass(parent);
			}
		}
	}
}
