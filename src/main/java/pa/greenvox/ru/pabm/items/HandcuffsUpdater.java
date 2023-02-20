package pa.greenvox.ru.pabm.items;

import java.util.ArrayList;

public class HandcuffsUpdater implements Runnable {

    @Override
    public void run() {
        ArrayList<CuffsInteraction> interactions = (ArrayList<CuffsInteraction>) HandcuffsEvents.Interactions.clone();

        for (CuffsInteraction c : interactions) c.Update();
    }
}
