public class ByteSourceFactory {
    
    public ByteSource create() { // 객체 생성 기능을 위한 오퍼레이션 정의
        if(useFile()){
            return new FileDataRedaer();
        }else
            return new SocketDataReader();
    }
    
    private boolean useFile(){
        String useFileVal = System.getProperty("useFile");
        return useFileVal != null && Boolean.valueOf(useFileVal);
    }
    
    // 싱글톤 패턴 적용
   private static ByteSourceFactoy instance = new ByteSourceFactoy();
   public static ByteSourceFactoy getInstance(){
       return instance;
   }
    
   private ByteSourceFactoy() {}        
}