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
    
    private void changeUIToMenu2(){
        currentMenu = "menu2";
         System.out.println("메뉴 2 화면으로 전환");
    }
    
    private void processButton1WhenMenu1(){
         System.out.println("메뉴 1 화면의 버튼 처리");
    }
    
    private void processButton1WhenMenu2(){
        System.out.println("메뉴 2 화면의 버튼 처리");
    }
    
}