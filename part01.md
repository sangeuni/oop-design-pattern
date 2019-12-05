# Part 01 객체지향

## Chap 01 들어가기

### " 객체지향 설계의 필요성 "

- 시간이 지날수록 느려지는 고객의 요구사항 반영

이는 SW의 설계가 엉망일 때 발생하는 전형적인 증상이다. 

이런 증상의 주요 원인은 SW의 개발자가 변화에 대응할 수 있는 구조를 설계하는 데에 미숙하기 때문이다.



### 1. 지저분해지는 코드

- 최초의 코드 [리스트 1.1]

메뉴 영역에서 메뉴 1과 메뉴 2를 누르면 화면 영역에 알맞은 내용이 출력된다. 또한 모든 화면은 공통 버튼을 한 개 가지며, 그 버튼이 눌릴 때 마다 화면 영역의 데이터가 변경된다.

[리스트 1.1] 최초의 코드

```java
public class Application implements OnclickListener {
    
    private Menu menu1 = new Menu("menu1");
    private Menu menu2 = new Menu("menu2");
    private Button button1 = new Button("button1");
    
    private String currentMenu = null;
    
    public Application() {
        menu1.setOnClickListener(this);
        menu2.setOnClickListener(this);
        button1.setOnClickListener(this);
    }
    
    public void clicked(Component eventSource) {
        if(eventSource.getId().equals("menu1")){
            changeUIToMenu1();
        } else if(eventSource.getId().equals("menu2")){
            changeUIToMenu2();
        } else if(eventSource.getId().equals("button1")){
            if(currentMenu == null)
                return;
            if(currentMenu.equals("menu1"))
                processButton1WhenMenu1();
            else if(currentMenu.equals("menu2"))
                processButton1WhenMenu2();
        }  
    }
    
    private void changeUIToMenu1(){
        currentMenu = "menu1";
        System.out.println("메뉴 1 화면으로 전환");
    }
    
    private void changeUIToMenu1(){
        currentMenu = "menu2";
         System.out.println("메뉴 2 화면으로 전환");
    }
    
    private processButton1WhenMenu1(){
         System.out.println("메뉴 1 화면의 버튼 처리");
    }
    
    private processButton1WhenMenu2(){
        System.out.println("메뉴 2 화면의 버튼 처리");
    }
    
}
```



위 코드는 두 개의 메뉴와 한 개의 버튼에서 이벤트가 발생하면 그 이벤트를 clicked() 메서드에서 처리한다. clicked() 메서드는 이벤트를 누가 발생시켰는지에 따라 if-else 블록을 이용해서 이벤트를 처리한다. 

그런데 프로그램이 완성될 때 쯤 추가 요구사항이 들어왔다. 버튼 2가 필요하다는 것이다. 이제 코드는  button1 처리 코드와 완전히 동일한 중첩된 if-else 블록이 추가된다. 

만약 메뉴가 5개로 늘어나고 버튼이 5개로 늘어난다면 위 코드는 중첩된 if-else 블록된 거의 90여 줄에 이르게 된다.  if-else가 이렇게 커지다 보면 자연스럽게 코드를 복붙하는 방식으로 코드를 작성하게 된다. 또한 코드를 누락하는 경우도 발생하게 된다.

이는 코드를 수정하기가 점점 어려워진다는 것을 의미하며, 새로운 요구사항이 발생했을 때 그 요구 사항을 반영하는데 오랜 시간이 걸리게된다. 바로 **"초기에는 새로운 요구 사항을 빠르게 개발해 주었는데, 시간이 지날수록 간단한 요구 사항 도카도 제 때 개발이 안 되는"** 상황이 발생하는 것이다.





### 2. 수정하기 좋은 구조를 가진 코드

이제 같은 상황을 객체 지향 방식으로 풀어보자. 객체 지향에서는 추상화와 다형성을 이용해서 변화되는 부분을 관리한다. 먼저 최초의 상황인 메뉴 1, 메뉴 2, 그리고 버튼 1이 존재하는 상태에서 출발해보자.



- 메뉴가 선택되면 해당 화면을 보여준다.
- 버튼 1을 클릭하면 선택된 메뉴 화면에서 알맞은 처리를 한다.



위 동작을 메뉴 3이나 메뉴 4가 추가되더라도 동일하게 동작하는 것들이다. 이 공통 동작을 표현하기 위해 ScreenUI 타입을 정의하였다.



- [리스트 1.2] ScreenUI 인터페이스 정의

```java
public interface ScreenUI {
    public void show();
    public void handleButton1Click();
}
```

ScreenUI 의 **show()** 메서드는 메뉴에 해당하는 알맞은 화면을 보여주기 위해 사용된다. **handleButton1Click()** 메서드는 버튼1이 눌렸을 때 실행된다.

메뉴 별로 실제 화면에 보이는 구성 요서와 버튼1 클릭을 처리하는 코드가 다르므로 [리스트 1.3]과 같이 각 메뉴 별로 ScreenUI 인터페이스를 구현한 클래스를 작성해준다.



- [리스트 1.3] 메뉴1과 메뉴2를 위한 ScreenUI 구현

```java
public class Menu1ScreenUI implements ScreenUI {
    public void show(){
        System.out.println("메뉴 1 화면으로 전환");
    }
    public void handleButton1Click(){
         System.out.println("메뉴 1 화면의 버튼 처리");
    }
}

public class Menu2ScreenUI implements ScreenUI {
    public void show(){
        System.out.println("메뉴 2 화면으로 전환");
    }
    public void handleButton1Click(){
         System.out.println("메뉴 2 화면의 버튼 처리");
    }
}
```



이제 Application 클래스는 ScreenUI 인터페이스와 Menu1ScreenUI 클래스 및  Menu12ScreenUI 클래스를 이용해서 구현할 수 있다.



- [리스트 1.4] ScreenUI를 이용한 Application 구현

```java
public class Application implements OnClickedListener{
    
    private Menu menu1 = new Menu("menu1");
    private Menu menu2 = new Menu("menu2");
    private Button button1 = new Button("button1");
    
    private ScreenUI currentScreen = null;
    
    public Application() {
        menu1.setOnClickListener(this);
        menu2.setOnClickListener(this);
        button1.setOnClickListener(this);
    }
    
    public void clicked(Component eventSource) {
        String sourceId = eventSource.getId();
        if(sourceId.equals("menu1")){
            currentScreen = new Menu1ScreenUI();
            currentScreen.show();
        } else if(sourceId.equals("menu2")){
           currentScreen = new Menu2ScreenUI();
            currentScreen.show();
        } else if(sourceId.equals("button1")){
            if(currentScreen == null)
                return;
            // 메뉴1 인지 메뉴2 인지에 상관없이 currentScreen의 메서드를 실행
            currentScreen.handleButton1Click();
        }  
    }
}
```



Application은 menu1이나 menu2를 클릭하면 각각 Menu1ScreenUI 클래스나 Menu2ScreenUI 클래스의 인스턴스를 생성해서 currentScreen 필드에 할당한 뒤, currentScreen.show() 메서드를 호출한다. 그리고 button1을 클릭하면 currentScreen의 handleButton1Click() 메서드를 호출한다.

여기서 중요한 점은 button1 클릭을 처리하는 코드는 현재 화면이 메뉴1 화면인지 메뉴2 화면인지에 상관없이 currentScreen.handleButton1Click()을 실행한다는 점이다.



- 메뉴/버튼 이벤트 처리 코드의 분리

서로 다른 이유로 변경되는 코드가 한 메서드에 섞여 있으면 향후에 코드 가독성이 떨어져서 유지 보수를 하기 어려워질 수 있으니, 메뉴 클릭 처리 코드와 버튼 클릭 처리 코드를 분리하였다.



```java
public class Application {
    ...
    public Application(){
        menu1.setOnClickListener(menuListner);
        menu2.setOnClickListener(menuListner);
        button1.setOnClickListener(buttonListner);
    }
    
    private OnClickListenr menuListner = new OnClickLister(){
        public void clicked()(Componenet eventSource){
            String sourceId = eventSource,getId();
            if(sourceId.equals("menu1")){
                currentScreen = new Menu1ScreenUI();
            } else if(sourceId.equals("menu2")){
                currentScreen = new Menu2ScreenUI();
            }
            currentScreen.show();
        }
    };
    
    private OnClickLister buttonLister = new OnClickLister(){
         public void clicked()(Componenet eventSource){
             if(currentScreen == null)
                 return;
             String sourceId = eventSource.getId();
             if(sourceId.equals("button1")){
                 currentScreen.handleButton1Click();
             }
         }
    };
}
```



버튼2를 추가해달라는 요청이 왔다. 버튼2를 처리해야 하므로 ScreenUI에 [리스트 1.5] 처럼 새로운 메서드를 추가한다.



- [메서드 1.5] 버튼2 처리를 위한 메서드가 추가된 ScreenUI 인터페이스

```java
public interface ScreenUI {
    public void show();
    public void handleButton1Click();
    // 버튼2 메서드
    public void handleButton2Click();
}
```



ScreenUI 인터페이스가 변경되었으므로, Menu1ScreenUI 클래스와 Menu2ScreenUI 클래스는handleButton2Click() 메서드를 구현하지 않았다는 컴파일 에러가 발생한다. 두 클래스에 각각 메서드를 구현해준다.



- [리스트 1.6] 버튼2를 위한 handleButton2Click() 메서드 구현 추가

```java
public class Menu1ScreenUI implements ScreenUI {
    public void show(){
        System.out.println("메뉴 1 화면으로 전환");
    }
    public void handleButton1Click(){
         System.out.println("메뉴 1 화면의 버튼1 처리");
    }
    public void handleButton2Click(){
         System.out.println("메뉴 1 화면의 버튼2 처리");
    }
}

public class Menu2ScreenUI implements ScreenUI {
    public void show(){
        System.out.println("메뉴 2 화면으로 전환");
    }
    public void handleButton1Click(){
         System.out.println("메뉴 2 화면의 버튼1 처리");
    }
    public void handleButton2Click(){
         System.out.println("메뉴 2 화면의 버튼2 처리");
    }
}
```



Application 클래스에 모든 코드를 작성했었던 방식에서는 메뉴1 관련 코드와 메뉴2 관련 코드가 한 소스 코드에 섞여있다. 따라서 메뉴1에 대한 관련 코드를 분석하거나 수정하려면, Application 소스 코드의 이곳 저곳을 더 많이 이동해야 한다. 메뉴의 개수가 증가할수록 소스 위치를 찾는 시간은 점점 길어지게 되며, 이는 개발 시간을 불필요하게 증가시키는 문제를 야기한다.

Application에서 모든 걸 구현했던 첫 번째 방식과 달리 ScreenUI가 출현한 두 번째 방식은 작성하는 클래스 개수가 다소 증가했다. 하지만 메뉴 관련 코드들이 알맞게 분리되었다. 



Application과 ScreenUI를 분리했을 때 생기는 장점은 메뉴3을 추가할 때 더 잘 드러난다. 이제 메뉴3을 추가해보자. 메뉴3을 추가하려면 먼저 메뉴3과 관련된 ScreenUIfmf 만들어주어야한다.

- [리스트 1.7] 메뉴3 화면 처리 위한 Menu3ScreenUI 클래스

```java
public class Menu3ScreenUI implements ScreenUI {
    public void show(){
        System.out.println("메뉴 3 화면으로 전환");
    }
    public void handleButton1Click(){
         System.out.println("메뉴 3 화면의 버튼1 처리");
    }
    public void handleButton2Click(){
         System.out.println("메뉴 3 화면의 버튼2 처리");
    }
}
```



이제 Application 클래스에 메뉴3을 추가해보자.

- [리스트 1.8] Application 클래스에 메뉴3 관련 코드 추가

```java
public class Application {
    ...
    // 새로 추가된 부분
    private Menu menu2 = new Menu("menu3");
    private Button button1 = new Button("button1");
    
    private ScreenUI currentScreen = null;
    
    public Application(){
        menu1.setOnClickListener(menuListner);
        menu2.setOnClickListener(menuListner);
         // 새로 추가된 부분
        menu3.setOnClickListener(menuListner);
        button1.setOnClickListener(buttonListner);
    }
    
    private OnClickListenr menuListner = new OnClickLister(){
        public void clicked()(Componenet eventSource){
            String sourceId = eventSource,getId();
            if(sourceId.equals("menu1")){
                currentScreen = new Menu1ScreenUI();
            } else if(sourceId.equals("menu2")){
                currentScreen = new Menu2ScreenUI();
            } // 새로 추가된 부분 
            else if(sourceId.equals("menu3")){
                currentScreen = new Menu3ScreenUI();
            }
            currentScreen.show();
        }
    };
    
    private OnClickLister buttonLister = new OnClickLister(){
         public void clicked()(Componenet eventSource){
             if(currentScreen == null)
                 return;
             String sourceId = eventSource.getId();
             if(sourceId.equals("button1")){
                 currentScreen.handleButton1Click();
             }
         }
    };
}
```



이 코드에서 주목할 점은 버튼 클릭 부분의 코드는 전혀 바뀌지 않았다는 점이다. 앞서 Application 소스에 코든 코드를 넣었을 때와 비교해 보면 구조는 다소 복잡해졌지만, 다음과 같은 장점을 얻을 수 있게 되었다.



- 새로운 메뉴 추가 시, 버튼 처리 코드가 영향을 받지 않음
- 한 메뉴 관련 코드가 한 개의 클래스로 모여서 코드 분석/수정이 용이함
- 서로 다른 메뉴에 대한 처리 코드가 섞여 있지 않아 수정이 용이함



즉, **요구 사항이 바뀔 때, 그 변화를 좀 더 수월하게 적용할 수 있다는 장점**을 얻었다. 이런 장점을 얻기 위해 사용된 것이 바로 객체 지향 기법이다. 객체 지향 기법을 적용하면 SW를 더 쉽게 변경할 수 있는 유연함을 얻을 수 있게 되고 이는 곧 요구 사항의 변화를 더 빠르게 수용할 수 있다는 것을 뜻한다.

