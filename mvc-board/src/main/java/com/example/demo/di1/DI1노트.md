## Spring DI 원리
1. 변경에 유리한 코드 - 다형성과 추상화    
   코드 변경을 쉽게 하기 위해 다형성과 추상화를 활용합니다.    
   구체적인 클래스를 직접 사용하면 변경이 어렵지만, 부모 클래스를 활용하면 유연성이 증가합니다.    
   🔹 구체적인 클래스 사용 (변경이 어렵다)   
```java
   class Car {}
   class SportsCar extends Car {}
   class Truck extends Car {}
```


// 특정 클래스에 종속된 객체 생성 (변경이 어렵다)
SportsCar car = new SportsCar();
Truck car = new Truck();
🔹 부모 클래스(추상화) 활용 (변경이 유리하다)
```java
Car car = new SportsCar();
Car car = new Truck();
```
Car 클래스를 부모로 사용하여 변경이 용이한 코드로 만들 수 있습니다.

2. 더 변경에 유리한 코드 - 객체 생성을 메서드에서 처리
   객체를 생성하는 로직을 메서드로 분리하면, 객체 생성의 변경 포인트가 한 곳으로 집중됩니다.
   🔹 객체 생성 메서드를 활용한 코드
```java
   Car car = getCar(); // 객체를 사용하는 쪽
    static Car getCar() {
        return new SportsCar(); // 변경 포인트가 한 곳 (객체를 변경할 수 있음)}
    }
```

위의 코드는 SportsCar 객체를 반환하지만, 필요한 경우 Truck으로 변경할 수도 있습니다.

static Car getCar() {
 return new Truck();
}

장점: getCar() 메서드에서 객체를 변경하면, 이를 사용하는 모든 코드가 영향을 받지 않고 변경이 반영됩니다.

3. 보다 더 변경에 유리한 코드 - Map과 외부 파일 활용
   클래스 이름을 외부 파일 (config.txt) 에 저장하고, 동적으로 객체를 생성하면 완전히 유연한 코드가 됩니다.
   🔹 객체를 동적으로 생성하는 코드
   Car car = (Car) getObject("car");  
   Engine engine = (Engine) getObject("engine");

static Object getObject(String key) throws Exception {
// config.txt 파일을 읽어서 Properties 객체에 저장
Properties p = new Properties();
p.load(new FileReader("config.txt"));

    // 클래스의 정보를 가져와서 Class 객체에 저장
    Class clazz = Class.forName(p.getProperty(key));
    return clazz.newInstance(); // 객체 생성 후 반환
}
위 코드에서 config.txt 파일에 클래스명을 명시하면, 하드코딩 없이 객체를 생성할 수 있습니다.
🔹 config.txt 파일 내용

car = com.example.demo.SportsCar
engine = com.example.demo.Engine
config.txt에서 값을 변경하면 코드 수정 없이 다른 객체를 생성할 수 있습니다.

장점:
새로운 클래스가 추가되더라도 코드 수정 없이 반영 가능
유지보수 및 확장성이 뛰어남
스프링의 IoC (제어의 역전) 원리를 반영

4. Spring DI의 원리와 연결
   위 방법은 Spring의 의존성 주입(DI, Dependency Injection) 원리와 유사합니다.
   Spring에서는 XML 설정 파일이나 Java Config (Annotation) 등을 사용하여 객체를 관리합니다.
   스프링 컨테이너가 객체 생성 및 관리를 담당하고, 개발자는 하드코딩 없이 객체를 사용할 수 있습니다.
   🔹 Spring의 IoC 컨테이너 예시
```java
   @Configuration
   public class AppConfig {
   @Bean
   public Car car() {
   return new SportsCar();
   }
   }
```

   위 코드를 활용하면 Spring 컨테이너가 SportsCar 객체를 자동으로 관리하고 주입할 수 있습니다.

정리
- 변경 용이, 유지보수 유리
- 구체적인 클래스를 직접 사용하면 변경/유지보수 어려움
- 부모클래스(추상화)를 활용하면 유연한 유지보수 가능
- 외부파일(config.txt)활용하면 가장 유연하고 유지보수 유용 >옛날방식
- 다형성을 활용한 설계가 변경에 유리한 코드를 만듦
- 객체 생성을 외부에서 설정하면 완전 유연한 코드가 됨.

이와 같은 원리로 Spring DI동작하며, loc컨테이너를 활용해서 객체 생성을 자동화 해줌.