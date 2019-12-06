public class Member {
    ... // 다른 데이터
	private Date expiryDate;
    private boolean male;
    
    public boolean isExpired() { // 만료 여부 확인 구현을 캡슐화
        return expiryDate != null 
            && expiryDate.getDate() < System.currentTimeMills();
    }
}