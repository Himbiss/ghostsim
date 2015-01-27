void main() {
    while(true) {
        if(fireballOnTile()) {
            takeFireball();
        }
        if(!wallInFront()) {
            moveForward();
        } else {
            turnLeft();
        }
    }
}

public void test() {
    // abcdefg
    moveForward();
    moveForward();
}


