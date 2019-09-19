package SemaphoreLogic;

public interface CallBack {
    void methodToCallBack();

    void addToLog(String message);

    void putABall();

    void getABall();

    void playing(Child child);

    void quiet(Child child);

    void blockNoBall(Child child);

    void blockFullBasket(Child child);

}
