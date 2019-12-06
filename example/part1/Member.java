public class Member {
    private static final long DAY30 = 1000 * 60 * 60 * 24 * 30; // 30일
    ... // 다른 데이터
	private Date expiryDate;
    private boolean male;
    
    public boolean isExpired() { // 만료 여부 확인 구현을 캡슐화
        if(male){
            return expiryDate != null 
            && expiryDate.getDate() < System.currentTimeMills();
        }
        return expiryDate != null 
            && expiryDate.getDate() < System.currentTimeMills() - DAY30;
    }
}