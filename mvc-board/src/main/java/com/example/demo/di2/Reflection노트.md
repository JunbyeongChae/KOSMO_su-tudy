### Java Reflection API
- Java의 Reflection API는 런타임에 클래스, 메서드, 필드 등에 대한 정보를 조회하고    
조작할 수 있도록 해주는 기능입니다.    
이를 활용하면 동적 객체 생성, 메서드 호출, 필드 값 변경 등이 가능합니다.    

1. Reflection API란?
   Java Reflection API는 클래스의 구조(메서드, 필드, 생성자 등)를 런타임에 분석하고 조작할 수 있도록 제공하는 기능이다.    
   일반적으로 정적으로 코딩하는 방식과 다르게, 컴파일 시점에 알 수 없는 클래스도 다룰 수 있다.    
   주요 용도:    
   프레임워크 개발(Spring, Hibernate 등)   
   JSON 직렬화/역직렬화    
   런타임 의존성 주입(DI)    
   동적 프록시 및 AOP(Aspect-Oriented Programming)    


2. Reflection API의 주요 클래스
   java.lang.Class
   java.lang.reflect.Method
   java.lang.reflect.Field
   java.lang.reflect.Constructor

3. Class 객체 얻는 방법
   클래스 정보를 얻기 위해서는 Class 객체를 가져와야 한다. 이를 얻는 방법은 여러 가지가 있다.

```java
   // 방법 1: Class.forName("패키지명.클래스명") - 정적 로딩
   Class<?> clazz1 = Class.forName("java.util.ArrayList");
   
   // 방법 2: 클래스명.class - 컴파일 타임에 존재하는 클래스
   Class<?> clazz2 = ArrayList.class;
   
   // 방법 3: 객체.getClass() - 런타임 객체를 통해 얻음
   ArrayList<String> list = new ArrayList<>();
   Class<?> clazz3 = list.getClass();
```

4. 필드(Field) 정보 조회 및 조작
   특정 클래스의 필드를 조회하거나 값을 변경할 수 있다.

```java

import java.lang.reflect.Field;

class Person {
   private String name = "John";
}

public class ReflectionFieldExample {
   public static void main(String[] args) throws Exception {
      Person person = new Person();
      Class<?> clazz = person.getClass();

      // 필드 가져오기
      Field field = clazz.getDeclaredField("name");

      // private 필드 접근 가능하도록 설정
      field.setAccessible(true);

      // 필드 값 가져오기
      String nameValue = (String) field.get(person);
      System.out.println("Before change: " + nameValue);

      // 필드 값 변경
      field.set(person, "Alice");
      System.out.println("After change: " + field.get(person));
   }
}

//출력 결과
//Before change: John
//After change: Alice
```

5. 메서드(Method) 정보 조회 및 호출
   특정 메서드 정보를 가져와서 실행할 수 있다.

```java
import java.lang.reflect.Method;

class Calculator {
    private int add(int a, int b) {
        return a + b;
    }
}

public class ReflectionMethodExample {
    public static void main(String[] args) throws Exception {
        Calculator calc = new Calculator();
        Class<?> clazz = calc.getClass();

        // 메서드 가져오기
        Method method = clazz.getDeclaredMethod("add", int.class, int.class);

        // private 메서드 접근 허용
        method.setAccessible(true);

        // 메서드 실행
        int result = (int) method.invoke(calc, 5, 3);
        System.out.println("Result: " + result);
    }
}
//출력 결과
//Result: 8

```

6. 생성자(Constructor) 정보 조회 및 동적 객체 생성
   Reflection을 사용하면 객체를 동적으로 생성할 수도 있다.

```java
import java.lang.reflect.Constructor;

class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public void sayHello() {
        System.out.println("Hello, my name is " + name);
    }
}

public class ReflectionConstructorExample {
    public static void main(String[] args) throws Exception {
        // 클래스 타입 가져오기
        Class<?> clazz = Person.class;

        // 생성자 가져오기
        Constructor<?> constructor = clazz.getConstructor(String.class);

        // 동적 객체 생성
        Person person = (Person) constructor.newInstance("Alice");

        // 메서드 호출
        person.sayHello();
    }
}
//출력결과
//Hello, my name is Alice
```
7. Reflection API의 단점   
   성능 저하: Reflection은 일반적인 메서드 호출보다 느리다.    
   캡슐화 위반: private 필드 및 메서드에 접근할 수 있어 보안 문제가 발생할 수 있다.   
   코드 가독성 저하: 일반적인 Java 코드보다 복잡하고 유지보수가 어렵다.  

8. Reflection을 활용한 실전 예제 (Spring과 연계)   
   Spring에서는 Reflection API를 활용하여 **의존성 주입(DI)**을 처리한다.   
   예를 들어, Spring 프레임워크는 런타임에 @Autowired가 붙은 필드를 찾아 setAccessible(true)를 설정하고,    
   적절한 객체를 주입한다.

```java
import java.lang.reflect.Field;

class Service {
    public void serve() {
        System.out.println("Service is running...");
    }
}

class Controller {
    @Autowired
    private Service service;

    public void useService() {
        service.serve();
    }
}

public class SpringDIReflectionExample {
    public static void main(String[] args) throws Exception {
        Controller controller = new Controller();
        Service serviceInstance = new Service();

        // Reflection을 이용한 의존성 주입
        Class<?> clazz = controller.getClass();
        Field field = clazz.getDeclaredField("service");
        field.setAccessible(true);
        field.set(controller, serviceInstance);

        // 메서드 실행
        controller.useService();
    }
}
//출력결과
//Service is running...
```