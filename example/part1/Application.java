public class Application {
    
    private Menu menu1 = new Menu("menu1");
    private Menu menu2 = new Menu("menu2");
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
    
    private OnClickListener menuListener = new OnClickListener(){
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
    
    private OnClickListener buttonListener = new OnClickListener(){
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