package pa.greenvox.ru.pabm.events;

import java.util.ArrayList;

public class Updater implements Runnable {

    @Override
    public void run() {
        BagRandomBlock.PlayerIsAlreadyUsed = new ArrayList<>();
    }
}
