class EndFlag {
    private boolean flag;

    EndFlag() {
        flag = false;
    }

    public synchronized boolean get() {
        return flag;
    }

    public synchronized void set(boolean value) {
        flag = value;
    }
}